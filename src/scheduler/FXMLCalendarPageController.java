
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
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    private WorkingMonth month = new WorkingMonth(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        month.setWorkingDate(LocalDateTime.now());
        setCalendar();

            }
    
    public void setCalendar(){
            
        monthLabel.setText("" + month.workingDate.getMonth() + " - " + month.workingDate.getYear());
        outputText.setText("" + month.firstDay);
        for(int i=0; i < 42;i++){
            Pane spot = (Pane)calendarGrid.getChildren().get(i);
            spot.getChildren().clear();
            spot.setId(null);
        }
        int startDay = checkDayOfWeek(month.firstDay);
        int incDay = 1;
        System.out.println(month.getNumDaysInMonth());
        
        if(UserInfo.getAppointments() != null){
            month.initMonth();
        }
        System.out.println("Displaying appointments");
        for(int i = startDay; i < (month.getNumDaysInMonth()+startDay) ; i++){
            Label dayNum = new Label("" + (incDay));
            Label numOfAppts = new Label();
            int numAppts = month.numberOfAppointmentsForDay((i-startDay));
            if (numAppts > 0){
            numOfAppts.setText("Appointments: " + numAppts);
            }
            numOfAppts.setStyle("-fx-padding: 12 0 0 2;");
            Pane spot = (Pane)calendarGrid.getChildren().get(i);
            spot.getChildren().add(dayNum);
            spot.getChildren().add(numOfAppts);
            spot.setId(Integer.toString(incDay));            
            spot.setOnMouseClicked(e -> clicked(e));
            incDay++;
        }
        
    }
    
    private void clicked(Event e){
        Pane thisPane = (Pane)e.getSource();
        int day = Integer.parseInt(thisPane.getId());
        System.out.println(day);
        dailyAppointments.getChildren().clear();
        ArrayList<Appointment> todayAppt = month.getAppointmentsForDay(day);
        for(Appointment appt : todayAppt){
            System.out.println(appt.toString());
            String apptName = appt.toString();
            LocalDateTime start = appt.getStartTime();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("kk:mm");
            String startFormat = start.format(df);
            LocalDateTime end = appt.getEndTime();
            String endFormat = end.format(df);
            String apptTime = startFormat + "-" + endFormat;
            
            String labelInfo = apptName +" : " + apptTime;
            Label anAppt = new Label(labelInfo);            
            anAppt.setId(Integer.toString(appt.getAppointmentID()));
            anAppt.setStyle("-fx-padding: 12 0 0 2;");
            dailyAppointments.getChildren().add(anAppt);
            anAppt.setOnMouseClicked(ev -> apptClicked(ev));
        }
    }
    
    public void apptClicked(Event ev){
        Label thisLabel = (Label)ev.getSource();
        int apptID = Integer.parseInt(thisLabel.getId());
  
        Scheduler.changeScene(Scheduler.appointmentDetailsScene);
        FXMLAppointmentDetailsController.initAppointment(apptID);
        
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
    private int checkDayOfWeek(String dayOfWeek){
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
