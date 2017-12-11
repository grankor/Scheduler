/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.sql.SQLException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javax.naming.NamingException;
import resources.UserInfo;

/**
 *
 * @author nicholasdrew
 */
public class FXMLLoginPageController implements Initializable {
    private String language;        

    
    @FXML
    private PasswordField userPW;
    @FXML
    private TextField userName;    
    @FXML
    private Label userNameLabel;    
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;
    
    @FXML
    private void login (ActionEvent event) throws NamingException, SQLException, ClassNotFoundException, IOException{
        String uname = userName.textProperty().getValue();
        String pword = userPW.textProperty().getValue();
        if(uname.equals("") && pword.equals("")){
            if(language.equals("es_ES")){
                loginProblem("Debe ingresar nombre de usuario y contraseña");
            } else{
            loginProblem("Must enter Username and Password");
            }
        }

        if(checkDatabase(uname, pword)){    
            System.out.println("Login Successful!");
            writeToLoginLog();
            Database.getDatabaseInformation();
            
        }else{
            if(language.equals("es_ES")){
                loginProblem("Nombre de usuario o contraseña incorrecta.");
            } else{
            loginProblem("Incorrect Username or Password");
            }
        }
    }
    
    private void writeToLoginLog() throws IOException{
        String timeStamp = LocalDateTime.now().toString();
        timeStamp += " - " + UserInfo.getUserName() + "\n";
        FileWriter fw = null;
        try{
            System.out.println("Writing new");
            fw = new FileWriter("loginLog.txt", true);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(timeStamp);
            out.newLine();
            out.close();
        } catch (Exception ev){
            System.out.println("Appending");
            fw = new FileWriter("loginLog.txt", true);
            fw.append(timeStamp);
        
        }finally{
            try{
            fw.close();
            } catch (Exception ex){
            
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Getting the locale from the machine. Tested this on Mac :)
        language = Locale.getDefault().toString();        
        
        OffsetDateTime isUTC = OffsetDateTime.now(Clock.systemUTC());
        LocalDateTime saveStartDate = OffsetDateTime.now(isUTC.getOffset()).toLocalDateTime();
        System.out.println(saveStartDate);
        
        
         if(language.equals("es_ES")){
            userNameLabel.setText("Nombre de usuario");
            passwordLabel.setText("Contraseña");
            loginButton.setText("acceder");
        } else {
            userNameLabel.setText("Username");
            passwordLabel.setText("Password"); 
        }
    }    
    
    private void loginProblem(String error){
        Alert alert = new Alert(AlertType.ERROR, error);
        alert.showAndWait();
    }
    
    private boolean checkDatabase(String uname, String pword) throws NamingException, SQLException, ClassNotFoundException{

            Connection conn2 = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            try {
                Class.forName(driver);
                conn2 = DriverManager.getConnection(url,user,pass);

                Statement stmt = conn2.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT userId,userName,password FROM U01JJx.user");
                while(rs.next()){
                    int uid = rs.getInt("userId");
                    String names = rs.getString("userName");
                    String passwords = rs.getString("password");
                    if(uname.equals(names) && pword.equals(passwords)){
                        UserInfo.setUserName(names);
                        UserInfo.setUserId(uid);
                        rs.close();
                        stmt.close();
                        conn2.close();                    
                        return true;
                    }
            
                }            
                rs.close();
                stmt.close();
                conn2.close();         
             
             } catch (SQLException e) {
             System.out.println("SQLException: "+e.getMessage());
             System.out.println("SQLState: "+e.getSQLState());
             System.out.println("VendorError: "+e.getErrorCode());
             return false;
             }
        return false;
    }
    public void onEnter(ActionEvent e) throws NamingException, SQLException, ClassNotFoundException, IOException{        
        login(e);    
    }
    
}
