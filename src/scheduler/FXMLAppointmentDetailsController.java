
package scheduler;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.WindowEvent;
import resources.*;


public class FXMLAppointmentDetailsController implements Initializable {

    private static Appointment appointment = null;
    private static Customer customer = null;
    private static City city = null;
    private static Address address = null;
    private static Country country = null;
    private static int apptID;
    private static int dayOfMonth;
    private static ArrayList<Appointment> dailyAppointments = new ArrayList<Appointment>();
    private static boolean isNew = false;
    public static Label custName = new Label("Bacon");
    public static Label appointmentType = new Label("");
    public static Label cons = new Label("");
    public static Label loc = new Label("");
    public static Label descr = new Label("");
    public static Label cont = new Label("");
    public static Label start = new Label("");
    public static Label end = new Label("");
    public static Label title = new Label("");
    public static MenuButton cInput;
    private static Button eButton;
    public static TextField tInput;
    public static TextField dInput;
    public static TextField lInput;
    private static int selectedMenuID;
    private static String[] hourTimes = new String[]{"00","01","02","03","04","05","06","07","08","09","10",
        "11","12","13","14","15","16","17","18","19","20","21","22","23"};
    private static String[] minuteTime = new String[]{"00","15","30","45"};
    
    public static ChoiceBox s1Input;
    public static ChoiceBox e1Input;
    public static ChoiceBox s2Input;
    public static ChoiceBox e2Input;
    public static DatePicker dateInput;
            
    @FXML
    public Label cname = new Label("");
    @FXML
    private Label apptType = new Label("");
    @FXML
    private Label consultant = new Label("");
    @FXML
    private Label location = new Label("");
    @FXML
    private Label description = new Label("");
    @FXML
    private Label contact = new Label("");
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    public Label apptTitle;
    @FXML
    private MenuButton custNameInput;
    @FXML
    private TextField titleInput;
    @FXML
    private TextField descriptionInput;
    @FXML
    private ChoiceBox startInput1 = new ChoiceBox(FXCollections.observableArrayList(hourTimes));
    @FXML
    private ChoiceBox endInput1 = new ChoiceBox(FXCollections.observableArrayList(minuteTime));
    @FXML
    private ChoiceBox startInput2 = new ChoiceBox(FXCollections.observableArrayList(hourTimes));
    @FXML
    private ChoiceBox endInput2 = new ChoiceBox(FXCollections.observableArrayList(minuteTime));
    @FXML
    private TextField locationInput;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private DatePicker dateSelectorInput;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    custName = cname;
    appointmentType = apptType;
    cons = consultant;
    loc = location;
    descr = description;
    cont = contact;
    start = startTime;
    end = endTime;
    title = apptTitle;
    cInput= custNameInput;
    tInput=titleInput;
    dInput=descriptionInput;
    s1Input=startInput1;
    e1Input=endInput1;
    s2Input=startInput2;
    e2Input=endInput2;
    lInput = locationInput;
    dateInput = dateSelectorInput;
    eButton = editButton;
    s1Input.setItems(FXCollections.observableArrayList(hourTimes));
    s2Input.setItems(FXCollections.observableArrayList(minuteTime));
    e1Input.setItems(FXCollections.observableArrayList(hourTimes));
    e2Input.setItems(FXCollections.observableArrayList(minuteTime));
    
    }
    private static void clearFields(){
    
        appointmentType.setText("");
        cons.setText("");
        loc.setText("");
        descr.setText("");
        cont.setText("");
        start.setText("");
        end.setText("");
        tInput.setText("");
        dInput.setText("");
        lInput.setText("");
        title.setText("");
        custName.setText("");
        cInput.setText("");
    }
   

    public static void initAppointment(int ID, int day){
        dayOfMonth = day;
        dailyAppointments = FXMLCalendarPageController.month.getAppointmentsForDay(dayOfMonth);
        clearFields();
        cInput.setVisible(false);
        tInput.setVisible(false);
        dInput.setVisible(false);
        s1Input.setVisible(false);
        e1Input.setVisible(false);
        s2Input.setVisible(false);
        e2Input.setVisible(false);
        lInput.setVisible(false);
        
        apptID = ID;
        ArrayList<Appointment> appts = UserInfo.getAppointments();
        for(Appointment appt : appts){
            if(appt.getAppointmentID() == ID){
                appointment = appt;                
            }
        }
        for(Customer cust : UserInfo.customers){
            if (cust.getCustomerID() == appointment.getCustomerID()){
                customer = cust;
                selectedMenuID = cust.getCustomerID();
            }
        }
        
        for(Address addr : UserInfo.addresses){
            if(addr.getId() == customer.getAddressID()){
                address = addr;
            }
        }
        for(City cty : UserInfo.cities){
            if(cty.getCityID() == address.getCity()){
                city = cty;
            }
        }
        for(Country cntry : UserInfo.countries){
            if(cntry.getCountryID()==city.getCountryID()){
                country = cntry;
            }
        }
      showInfo();
      custName.setText(customer.getName());
      cInput.setText(customer.getName());
        }
    public static void initNewAppt(LocalDate clickDate, int day){
        dayOfMonth = day;
        dailyAppointments = FXMLCalendarPageController.month.getAppointmentsForDay(dayOfMonth);        
        clearFields();
        isNew = true;
        cInput.setVisible(false);
        tInput.setVisible(false);
        dInput.setVisible(false);
        s1Input.setVisible(false);
        e1Input.setVisible(false);
        s2Input.setVisible(false);
        e2Input.setVisible(false);
        lInput.setVisible(false);
        ArrayList<Appointment> appts = UserInfo.getAppointments();
        apptID = (appts.get((appts.size()-1)).getAppointmentID())+1;
        
        appointment = new Appointment();
        appointment.setAppointmentID(apptID);
        LocalDateTime newApptTime = clickDate.atTime(LocalTime.NOON);
        
        appointment.setStartTime(newApptTime);
        appointment.setEndTime(newApptTime);
        appointment.setConsultant(UserInfo.getUserName());
        appointment.setTitle("New Appointment");
        appointment.setDescription("Description");
        appointment.setLocation("location");
        showInfo();
        eButton.fire();
    }
    private static void showInfo(){
        LocalDateTime apptStart = appointment.getStartTime();       
        String titleText = "AppointMent for ";
        titleText += apptStart.getMonth() + "," + Integer.toString(apptStart.getDayOfMonth()) + " " + apptStart.getYear();
        title.setText(titleText);        
        cInput.getItems().clear();
        for(Customer cust : UserInfo.customers){
            MenuItem nmi = new MenuItem(cust.getName());
            nmi.setId(Integer.toString(cust.getCustomerID()));            
            cInput.getItems().add(nmi);
            nmi.setOnAction(e -> setCustId(e));            
        }  
        appointmentType.setText(appointment.getTitle());
        cons.setText(appointment.getConsultant());
        loc.setText(appointment.getLocation());
        descr.setText(appointment.getDescription());
        cont.setText(appointment.getContact());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm");
        String startFormat = apptStart.format(df);
        LocalDateTime apptEnd = appointment.getEndTime();
        String endFormat = apptEnd.format(df);
        start.setText(startFormat);
        end.setText(endFormat);
        
        
        tInput.setText(appointment.getTitle());
        dInput.setText(appointment.getDescription());
        lInput.setText(appointment.getLocation());
    }
    
    public void cancel(){
        editButton.setVisible(true);
        saveButton.setVisible(false);
        FXMLCalendarPageController.setCalendar();
        //Need to add weekly controller set calendar function here as well
        Scheduler.changeScene(Scheduler.currentScene);
        appointment = null;
        customer = null;
        address = null;
        country = null;
        city = null;
    }
    public void editAppt(){
        dateInput.setVisible(true);
        title.setVisible(false);
        cInput.setVisible(true);
        tInput.setVisible(true);
        dInput.setVisible(true);
        s1Input.setVisible(true);
        e1Input.setVisible(true);
        s2Input.setVisible(true);
        e2Input.setVisible(true);
        lInput.setVisible(true);
        editButton.setVisible(false);
        saveButton.setVisible(true);
        s1Input.setValue(appointment.getStartTime().getHour());
        s2Input.setValue(appointment.getStartTime().getMinute());
        e1Input.setValue(appointment.getEndTime().getHour());
        e2Input.setValue(appointment.getEndTime().getMinute());
        dateInput.setValue(appointment.getStartTime().toLocalDate());
        
        String startHour = Integer.toString(appointment.getStartTime().getHour());
        String startMinute = Integer.toString(appointment.getStartTime().getMinute());
        String endHour = Integer.toString(appointment.getEndTime().getHour());
        String endMinute = Integer.toString(appointment.getEndTime().getMinute()); 
        //00 hour minutes are passed as a single 0, checking for this case.
        if(startMinute.equals("0")){
            startMinute = "00";
        }
        if(endMinute.equals("0")){
            endMinute = "00";
        }       
        s1Input.getSelectionModel().select(startHour);
        s2Input.getSelectionModel().select(startMinute);
        e1Input.getSelectionModel().select(endHour);
        e2Input.getSelectionModel().select(endMinute);
   }
    public void saveAppt() throws ClassNotFoundException{
        if(customer == null){
            Alert alert = new Alert(AlertType.ERROR, "Customer must be selected.");
            alert.showAndWait();
            return;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        String saveStartDate = dateInput.getValue().toString()+ " " + s1Input.getValue()+":"+s2Input.getValue();
        LocalDateTime saveStart = LocalDateTime.parse(saveStartDate, df);
        String saveEndDate = dateInput.getValue().toString() + " " + e1Input.getValue() + ":" + e2Input.getValue();
        LocalDateTime saveEnd = LocalDateTime.parse(saveEndDate, df);
        LocalTime businessStart = UserInfo.getWorkingStart();
        LocalTime businessEnd = UserInfo.getWorkingEnd();
        if(saveStart.isAfter(saveEnd)){
            Alert alert = new Alert(AlertType.ERROR, "Appoitment start must be before End.");
            alert.showAndWait();
            return;
        }
        for(Appointment appt : dailyAppointments){
            if(saveStart.isAfter(appt.getStartTime()) && saveStart.isBefore(appt.getEndTime())){
                Alert alert = new Alert(AlertType.ERROR, "Appoitment overlaps with " + appt.getContact() + " from " +
                appt.getStartTime().toString() + " to " + appt.getEndTime().toString());
                alert.showAndWait();
                return;
            }
            if(saveEnd.isAfter(appt.getStartTime()) && saveEnd.isBefore(appt.getEndTime())){
                Alert alert = new Alert(AlertType.ERROR, "Appoitment overlaps with " + appt.getContact() + " from " +
                appt.getStartTime().toString() + " to " + appt.getEndTime().toString());
                alert.showAndWait();
                return;
            }
        }        
        if(saveStart.toLocalTime().isBefore(businessStart)){
            Alert alert = new Alert(AlertType.ERROR, "Earliest appointment can be is " + businessStart.toString());
            alert.showAndWait();
        } else{
            if(saveEnd.toLocalTime().isAfter(businessEnd)){
                Alert alert = new Alert(AlertType.ERROR, "Lastest appointment can be is " + businessEnd.toString());
                alert.showAndWait();
            } else {
                dateInput.setVisible(false);
                title.setVisible(true);
                cInput.setVisible(false);
                tInput.setVisible(false);
                dInput.setVisible(false);
                s1Input.setVisible(false);
                e1Input.setVisible(false);
                s2Input.setVisible(false);
                e2Input.setVisible(false);
                lInput.setVisible(false);
                editButton.setVisible(true);
                saveButton.setVisible(false);
                if(isNew) {
                    UserInfo.addAppointment(appointment);
                    }
                appointment.setCustomerID(customer.getCustomerID());
                appointment.setContact(customer.getName());
                appointment.setTitle(tInput.getText());
                appointment.setLocation(lInput.getText());
                appointment.setDescription(dInput.getText());
                appointment.setStartTime(saveStart);        
                appointment.setEndTime(saveEnd);
                if(isNew){
                    Database.saveNewAppointment(appointment);
                    } else {
                    Database.updateAppointment(appointment);
                    }
                isNew = false;
                initAppointment(apptID, dayOfMonth);
            }
        }
    }
    public static void setCustId(ActionEvent e){
        MenuItem mi = (MenuItem)e.getSource();        
        selectedMenuID = Integer.parseInt(mi.getId());        
        for(int i = 0; i < UserInfo.customers.size() ;i++){            
            Customer custom = UserInfo.customers.get(i);            
            if(custom.getCustomerID() == selectedMenuID){
                customer = custom;
            }
        }
        cInput.setText(customer.getName());
        
    }
}
