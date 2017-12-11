/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 * FXML Controller class
 *
 * @author nicholasdrew
 */
public class FXMLWeekPageController implements Initializable {

@FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthLabel;
    @FXML
    private Button nextMonth;
    @FXML
    private Button previousMonth;
    @FXML
    private Pane dailyAppointments;
    @FXML
    private Button editCustomers;
    @FXML    
    private Button report;
    @FXML
    private Button changeToMonth;
    
    private static GridPane calGrid;
    private static Label mntLabel;
    private static Button nxtMonth;
    private static Button prvMonth;
    private static Button editCustomersButton;
    private static Button reports;
    private static Pane dlyAppts;
    private static Button monthView;
    
    private static LocalDateTime startOfWeek;
    private static LocalDateTime endOfWeek;
    
    private static int startDay;
    private static int incrementDay;
    private static int startOfNextWeek;
    
    public static ArrayList<Appointment> appointmentsForDay = new ArrayList();
    
    public static WorkingWeek month = new WorkingWeek();
    private static LocalDateTime monthCompare = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthView = changeToMonth;
        reports = report;
        calGrid = calendarGrid;
        mntLabel = monthLabel;
        nxtMonth = nextMonth;
        prvMonth = previousMonth;
        dlyAppts = dailyAppointments;
        editCustomersButton = editCustomers;
        editCustomersButton.setOnMouseClicked(e -> editCustomer());
        month.setWorkingDate(LocalDateTime.now());
        reports.setOnMouseClicked(e -> printReports());
        monthView.setOnMouseClicked(e -> changeToMonthView());
        setCalendar();
 
        monthCompare = LocalDateTime.now();
        
        int dom = month.workingDate.getDayOfMonth();
        LocalDateTime firstDayOfMonth = month.workingDate.minusDays((dom-1));
        month.setWorkingDate(firstDayOfMonth);
    }
    
    private static void changeToMonthView(){
        FXMLCalendarPageController.setCalendar();
        Scheduler.currentScene = Scheduler.calendarScene;
        Scheduler.changeScene(Scheduler.calendarScene);
    }
    
    private static void editCustomer(){
        FXMLEditCustomerInfoController.loadCustomers();
        Scheduler.changeScene(Scheduler.editCustomerScene);
    }
    
    private static void printReports(){
        FXMLReportsPageController.initReports(month.workingDate);
        Scheduler.changeScene(Scheduler.reportsScene);
    }
    
    public static void setCalendar(){
        startOfNextWeek = 0;
        dlyAppts.getChildren().clear();
        mntLabel.setText("" + month.workingDate.getMonth() + " - " + month.workingDate.getYear());        
        for(int i=0; i < 7;i++){
            Pane spot = (Pane)calGrid.getChildren().get(i);
            spot.getChildren().clear();
            spot.setId(null);
        }
        startDay = checkDayOfWeek(month.firstDay);
        incrementDay = 1;
            if(UserInfo.getAppointments() != null){
            month.initMonth();
            }        
        for(int i = startDay; i < 7 ; i++){
            
            Label dayNum = new Label("" + (incrementDay));
            Label numOfAppts = new Label();
            int numAppts = month.numberOfAppointmentsForDay((i-startDay));
            if (numAppts > 0){
            numOfAppts.setText("Appointments: " + numAppts);
            }
            numOfAppts.setStyle("-fx-padding: 12 0 0 2;");
            Pane spot = (Pane)calGrid.getChildren().get(i);
            spot.getChildren().add(dayNum);
            spot.getChildren().add(numOfAppts);
            spot.setId(Integer.toString(incrementDay));            
            spot.setOnMouseClicked(e -> clicked(e));
            incrementDay++;
        }
        //startDay = 8;
    }
        public static void nextWeek(){        
        mntLabel.setText("" + month.workingDate.getMonth() + " - " + month.workingDate.getYear());
        if(month.workingDate.getMonth().equals(monthCompare.getMonth())){
            incrementDay = startOfNextWeek+1;            
            writeCalendar();
        }
        else {
            monthCompare = month.workingDate;            
            setCalendar();
        }
        startOfNextWeek += 7;
    }
        public static void previousWeek(){
        mntLabel.setText("" + month.workingDate.getMonth() + " - " + month.workingDate.getYear());
        if(month.workingDate.getMonth().equals(monthCompare.getMonth())){
            incrementDay = startOfNextWeek+1;            
            writeCalendar();
        }
        else {
            monthCompare = month.workingDate;             
            setCalendar();
        }
        startOfNextWeek -= 7;
        }
    
    private static void writeCalendar(){
        dlyAppts.getChildren().clear();
        for(int i=0; i < 7;i++){
            Pane spot = (Pane)calGrid.getChildren().get(i);
            spot.getChildren().clear();
            spot.setId(null);
        }
        
        if(UserInfo.getAppointments() != null){
            month.initMonth();
            }
            int id = 0;
        for(int i = startOfNextWeek; i < (startOfNextWeek+7) ; i++){
            
            Label dayNum = new Label("" + (incrementDay));
            Label numOfAppts = new Label();
            int numAppts = month.numberOfAppointmentsForDay((incrementDay-1));
            if (numAppts > 0){
            numOfAppts.setText("Appointments: " + numAppts);
            }
            numOfAppts.setStyle("-fx-padding: 12 0 0 2;");
            Pane spot = (Pane)calGrid.getChildren().get(id);
            spot.getChildren().add(dayNum);
            spot.getChildren().add(numOfAppts);
            spot.setId(Integer.toString(incrementDay));            
            spot.setOnMouseClicked(e -> clicked(e));
            incrementDay++;
            id++;
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
            anAppt.setOnMouseClicked(ev -> apptClicked(ev, day));            
        }
        
        Button newAppt = new Button("New Appointment");         
        newAppt.setOnMouseClicked( ev -> newAppointment(day, day));
        buttonBox.getChildren().add(newAppt);
        dlyAppts.getChildren().add(buttonBox);
    }
    
    public static void apptClicked(Event ev, int day){
        Button thisLabel = (Button)ev.getSource();
        int apptID = Integer.parseInt(thisLabel.getId());  
        Scheduler.changeScene(Scheduler.appointmentDetailsScene);
        FXMLAppointmentDetailsController.initAppointment(apptID, day);
        
    }
    public static void newAppointment(int d, int day){
        Scheduler.changeScene(Scheduler.appointmentDetailsScene);
        LocalDate clickDate = month.getDateofDay((d-1));
        FXMLAppointmentDetailsController.initNewAppt(clickDate, day);
    }
    
    public void nextMonth(){
        LocalDateTime temp = month.workingDate.plusWeeks(1);
        System.out.println("Moving to date - " + temp);
        month.setWorkingDate(temp);        
        nextWeek();
    }
    public void prevMonth(){
        LocalDateTime temp = month.workingDate.minusWeeks(1);
        System.out.println("Moving to date - " + temp);
        month.setWorkingDate(temp);
        previousWeek();
    }
    private static int checkDayOfWeek(String dayOfWeek){
        switch(dayOfWeek){
            case "sunday":
                startOfNextWeek = 7;
                return 0;               
            case "monday":
                startOfNextWeek = 6;
                return 1;
            case "tuesday":
                startOfNextWeek = 5;
                return 2;
            case "wednesday":
                startOfNextWeek = 4;
                return 3;
            case "thursday":
                startOfNextWeek = 3;
                return 4;
            case "friday":
                startOfNextWeek = 2;
                return 5;
            case "saturday":
                startOfNextWeek = 1;
                return 6;
            default:
                return 0;               
        }
    }   
    
}
