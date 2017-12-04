
package scheduler;

import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import CalendarResources.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.Appointment;
import resources.UserInfo;


public class FXMLCalendarPageController implements Initializable {

    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthLabel;
    @FXML
    private Button nextMonth;
    @FXML
    private Button previousMonth;
    @FXML
    private TextField outputText;
    @FXML
    private Pane dailyAppointments;
    @FXML
    private Button editCustomers;
    
    private static GridPane calGrid;
    private static Label mntLabel;
    private static Button nxtMonth;
    private static Button prvMonth;
    private static Button editCustomersButton;
    private static TextField outputTxt;
    private static Pane dlyAppts;
    
    private static WorkingMonth month = new WorkingMonth(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calGrid = calendarGrid;
        mntLabel = monthLabel;
        nxtMonth = nextMonth;
        prvMonth = previousMonth;
        outputTxt = outputText;
        dlyAppts = dailyAppointments;
        editCustomersButton = editCustomers;
        editCustomersButton.setOnMouseClicked(e -> editCustomer());
        month.setWorkingDate(LocalDateTime.now());
        setCalendar();
            }
    private static void editCustomer(){
        FXMLEditCustomerInfoController.loadCustomers();
        Scheduler.changeScene(Scheduler.editCustomerScene);
    }
    
    public static void setCalendar(){
        month.initMonth();
        dlyAppts.getChildren().clear();
        mntLabel.setText("" + month.workingDate.getMonth() + " - " + month.workingDate.getYear());
        outputTxt.setText("" + month.firstDay);
        for(int i=0; i < 42;i++){
            Pane spot = (Pane)calGrid.getChildren().get(i);
            spot.getChildren().clear();
            spot.setId(null);
        }
        int startDay = checkDayOfWeek(month.firstDay);
        int incDay = 1;
                
        if(UserInfo.getAppointments() != null){
            month.initMonth();
        }        
        for(int i = startDay; i < (month.getNumDaysInMonth()+startDay) ; i++){
            Label dayNum = new Label("" + (incDay));
            Label numOfAppts = new Label();
            int numAppts = month.numberOfAppointmentsForDay((i-startDay));
            if (numAppts > 0){
            numOfAppts.setText("Appointments: " + numAppts);
            }
            numOfAppts.setStyle("-fx-padding: 12 0 0 2;");
            Pane spot = (Pane)calGrid.getChildren().get(i);
            spot.getChildren().add(dayNum);
            spot.getChildren().add(numOfAppts);
            spot.setId(Integer.toString(incDay));            
            spot.setOnMouseClicked(e -> clicked(e));
            incDay++;
        }        
    }
    
    private static void clicked(Event e){
        Pane thisPane = (Pane)e.getSource();
        int day = Integer.parseInt(thisPane.getId());        
        dlyAppts.getChildren().clear();        
        ArrayList<Appointment> todayAppt = month.getAppointmentsForDay(day);
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(1);
        buttonBox.setPadding(new Insets(10 ,0,0,0));
        for(Appointment appt : todayAppt){            
            String apptName = appt.toString();
            LocalDateTime start = appt.getStartTime();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm");
            String startFormat = start.format(df);
            LocalDateTime end = appt.getEndTime();
            String endFormat = end.format(df);
            String apptTime = startFormat + "-" + endFormat;            
            String labelInfo = apptName +" : " + apptTime;
            Button anAppt = new Button(labelInfo);
            anAppt.setStyle("-fx-height: 25");
            
            anAppt.setId(Integer.toString(appt.getAppointmentID()));
            buttonBox.getChildren().add(anAppt);           
            anAppt.setOnMouseClicked(ev -> apptClicked(ev));            
        }
        
        Button newAppt = new Button("New Appointment");         
        newAppt.setOnMouseClicked( ev -> newAppointment(day));
        buttonBox.getChildren().add(newAppt);
        dlyAppts.getChildren().add(buttonBox);
    }
    
    public static void apptClicked(Event ev){
        Button thisLabel = (Button)ev.getSource();
        int apptID = Integer.parseInt(thisLabel.getId());  
        Scheduler.changeScene(Scheduler.appointmentDetailsScene);
        FXMLAppointmentDetailsController.initAppointment(apptID);
        
    }
    public static void newAppointment(int d){
        Scheduler.changeScene(Scheduler.appointmentDetailsScene);
        LocalDate clickDate = month.getDateofDay((d-1));
        FXMLAppointmentDetailsController.initNewAppt(clickDate);
    }
    
    public void nextMonth(){
        LocalDateTime temp = month.workingDate.plusMonths(1);
        month.setWorkingDate(temp);        
        setCalendar();
    }
    public void prevMonth(){
        LocalDateTime temp = month.workingDate.minusMonths(1);
        month.setWorkingDate(temp);
        setCalendar();
    }
    private static int checkDayOfWeek(String dayOfWeek){
        switch(dayOfWeek){
            case "sunday": 
                return 0;               
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            case "saturday":
                return 6;
            default:
                return 0;               
        }
    }
    
}
