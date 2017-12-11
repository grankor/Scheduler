/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import CalendarResources.WorkingMonth;
import CalendarResources.WorkingWeek;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import resources.Appointment;
import resources.UserInfo;

/**
 * FXML Controller class
 *
 * @author nicholasdrew
 */
public class FXMLReportsPageController implements Initializable {

   @FXML 
   private Label label;
   private static Label title;
   @FXML
   private Button backButton;
   private static Button back;
   @FXML
   private Button printNumOfApptTypes;
   private static Button numOfApptTypes;
   @FXML
   private Button printNumOfApptsPerConsultant;
   private static Button numApptsPerConsultant;
   @FXML
   private Button printApptsPerCustomer;
   private static Button apptsPerCustomer; 
    
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";  
    private static LocalDateTime month;
           
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        title = label;
        back = backButton;
        numOfApptTypes = printNumOfApptTypes;
        numApptsPerConsultant = printNumOfApptsPerConsultant;
        apptsPerCustomer = printApptsPerCustomer;
        
        apptsPerCustomer.setOnMouseClicked( e -> appointmentsPerCustomer());
        numApptsPerConsultant.setOnMouseClicked(e -> schedulePerConsultant());
        numOfApptTypes.setOnMouseClicked(e -> numberOfAppointmentTypesPerMonth());
        back.setOnMouseClicked(e -> backToCalendar());
    }
    
    
    class AppointmentTypes{
        private String type = "";
        private int num = 1;
        
        public AppointmentTypes(String typ){
            type = typ;
        }        
        public void addNum(){
            num++;
        }
        public int getTotal(){
            return num;
        }
        public String getType(){
            return type;
        }
    }
    
    public static void initReports(LocalDateTime mnt){
            month = mnt;
            title.setText("Reports for " + month.getMonth());
        }
    
    private void backToCalendar(){
        Scheduler.changeScene(Scheduler.currentScene);
    }
    
    private void notifyDone(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "File exported to " + msg);
            alert.showAndWait();
    }
    
    private void numberOfAppointmentTypesPerMonth(){
        String fileHeader = "type,number";
        String fileName = "ApptTypes_For_" + month.getMonth() + ".csv";
        ArrayList<Appointment> appts = new ArrayList<Appointment>();
        for(Appointment appt: UserInfo.getAppointments()){
            if(appt.getStartTime().getYear() == month.getYear() && appt.getStartTime().getMonth().equals(month.getMonth())){
                appts.add(appt);            
            }
        }
        if(appts.size()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointments found for " + month.getMonth());
            alert.showAndWait();
        }
        ArrayList<AppointmentTypes> types = new ArrayList<AppointmentTypes>();
        
        //Add the first appointment type, and its going to be unique
        types.add(new AppointmentTypes(appts.get(0).getTitle()));
        for(int i =1 ; i<appts.size();i++){
            boolean found = false;
            for(AppointmentTypes apt : types){
                if(appts.get(i).getTitle().equals(apt.getType())){
                    apt.addNum();
                    found = true;
                }
            }
            if(!found){
                types.add(new AppointmentTypes(appts.get(i).getTitle()));
            }
        }
        
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(fileName);
            fw.append(fileHeader);
            fw.append(NEW_LINE_SEPARATOR);
            
            for(AppointmentTypes appt : types){
                fw.append(appt.getType());
                fw.append(COMMA_DELIMITER);
                fw.append(Integer.toString(appt.getTotal()));
                fw.append(NEW_LINE_SEPARATOR);
            }
            notifyDone(fileName);
            
        } catch (Exception ex){
        
        } finally {
            try{
                fw.flush();
                fw.close();
                
            } catch (Exception e){
            
            }
        }
    }
    
    private void schedulePerConsultant(){
        String fileHeader = "consultant,date, title";
        String fileName = "ConsultantAppts_For_" + month.getMonth() + ".csv";
        
        ArrayList<Appointment> appts = new ArrayList<Appointment>();
        
        LocalDateTime monthDate = month;
        for(Appointment appt: UserInfo.getAppointments()){
            if(appt.getStartTime().getYear() == monthDate.getYear() && appt.getStartTime().getMonth().equals(monthDate.getMonth())){
                appts.add(appt);            
            }
        }
        if(appts.size()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointments found for " + month.toString());
            alert.showAndWait();
        }
        ArrayList<String> names = new ArrayList<String>();
        
        //Add the first appointment type, and its going to be unique
        names.add(appts.get(0).getConsultant());
        for(int i =1 ; i<appts.size();i++){
            boolean found = false;
            for(String name : names){
                if(appts.get(i).getConsultant().equals(name)){
                    found = true;
                }
            }
            if(!found){
                names.add(appts.get(i).getConsultant());
            }
        }
        
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(fileName);
            fw.append(fileHeader);
            fw.append(NEW_LINE_SEPARATOR);
            
            for(String name : names){
                for(Appointment appt: appts){
                    if(appt.getConsultant().equals(name)){
                    fw.append(appt.getConsultant());
                    fw.append(COMMA_DELIMITER);
                    fw.append(appt.getStartTime().toString() + " To " + appt.getEndTime().toString());
                    fw.append(COMMA_DELIMITER);
                    fw.append(appt.getTitle());
                    fw.append(NEW_LINE_SEPARATOR);
                    }
                }
            }
            notifyDone(fileName);
            
        } catch (Exception ex){
        
        } finally {
            try{
                fw.flush();
                fw.close();
                
            } catch (Exception e){
            
            }
        }
    }
    private void appointmentsPerCustomer(){
        String fileHeader = "customer,date, title";
        String fileName = "CustomerAppts_For_" + month.getMonth() + ".csv";
        
        ArrayList<Appointment> appts = new ArrayList<Appointment>();
        
        LocalDateTime monthDate = month;
        for(Appointment appt: UserInfo.getAppointments()){
            if(appt.getStartTime().getYear() == monthDate.getYear() && appt.getStartTime().getMonth().equals(monthDate.getMonth())){
                appts.add(appt);            
            }
        }
        if(appts.size()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointments found for " + month.toString());
            alert.showAndWait();
        }
        ArrayList<String> names = new ArrayList<String>();
        
        //Add the first appointment type, and its going to be unique
        names.add(appts.get(0).getContact());
        for(int i =1 ; i<appts.size();i++){
            boolean found = false;
            for(String name : names){
                if(appts.get(i).getContact().equals(name)){
                    found = true;
                }
            }
            if(!found){
                names.add(appts.get(i).getContact());
            }
        }
        
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(fileName);
            fw.append(fileHeader);
            fw.append(NEW_LINE_SEPARATOR);
            
            for(String name : names){
                for(Appointment appt: appts){
                    if(appt.getContact().equals(name)){
                    fw.append(appt.getContact());
                    fw.append(COMMA_DELIMITER);
                    fw.append(appt.getStartTime().toString() + " To " + appt.getEndTime().toString());
                    fw.append(COMMA_DELIMITER);
                    fw.append(appt.getTitle());
                    fw.append(NEW_LINE_SEPARATOR);
                    }
                }
            }
            notifyDone(fileName);
            
        } catch (Exception ex){
        
        } finally {
            try{
                fw.flush();
                fw.close();
                
            } catch (Exception e){
            
            }
        }
    }

    
    
}
