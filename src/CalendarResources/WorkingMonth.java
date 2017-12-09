package CalendarResources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import resources.Appointment;
import resources.UserInfo;

public class WorkingMonth {
    private static ArrayList<DayOfMonth> days= new ArrayList<DayOfMonth>();
    private static Date startOfMonth;
    private static Date endOfMonth;
    private static int numDaysInMonth;
    private static String month;
    private static LocalDateTime loginTime = LocalDateTime.now();
    private static LocalDateTime loginTimeInFifteen = loginTime.plusMinutes(15);
    private static boolean notified = false;
    
    public static LocalDateTime workingDate;
    public static YearMonth getThisMonth;
    public static String firstDay;
    
    
    @Override
    public String toString(){
        return month;
    }    
    public static void setMonth(String theMonth){
        month = theMonth;
    }    
    public static Date getStartOfMonth(){
        return startOfMonth;
    }
    public static void setStartOfMonth(Date sow){
        startOfMonth = sow;
    }
    public static Date getEndOfMonth(){
        return endOfMonth;
    }
    public static void setEndOfMonth(Date eow){
        endOfMonth = eow;
    }
    public static void setNumDaysInMonth(int dow){
        numDaysInMonth = dow;
    }
    public static int getNumDaysInMonth(){
        return numDaysInMonth;
    }
    public static ArrayList<DayOfMonth> getDays(){
        return days;
    }
    public static void setDays(ArrayList<DayOfMonth> newDays){
        days = newDays;
    }
    public static ArrayList<Appointment> getAppointmentsForDay(int id){
        System.out.println("Getting " + id);
        //decrement 1 to translate to array
        ArrayList<Appointment> appts = days.get((id-1)).getDailyAppointments();
        return appts;
    }
    public static LocalDateTime getWorkingDate(){
         return workingDate;
    }
    public static void setWorkingDate(LocalDateTime wd){
        workingDate = wd;
       // System.out.println("Month sent " + wd);
        //System.out.println("Month set " + workingDate.getMonth());
        numDaysInMonth = workingDate.getMonth().maxLength();
        //System.out.println(numDaysInMonth);
        getThisMonth = YearMonth.of(workingDate.getYear(), workingDate.getMonth());
        firstDay = getThisMonth.atDay(1).getDayOfWeek().toString().toLowerCase();
    }
    public static void initMonth(){
        days.clear();        
        ArrayList<Appointment> appts = UserInfo.getAppointments();
        System.out.println("Attempting to add appointments...");
        System.out.println("Size of Array " + appts.size());
        if(appts == null){ return;}

        for(int i=0; i < numDaysInMonth; i++){
            DayOfMonth day = new DayOfMonth();
            day.setDayOfMonth((i+1));
            day.setDate(getThisMonth.atDay((i+1)));
            for(int x = 0; x < appts.size();x++) {
                Appointment appt = appts.get(x);
                if(appt.getStartTime().getMonth() == day.getdate().getMonth() && appt.getStartTime().getDayOfMonth() == day.getdate().getDayOfMonth()){
                    day.addDailyAppointment(appt);
                }
                //Checks to see if there is an appointment soon.
                if(!notified){
                if(loginTime.isBefore(appt.getStartTime()) && loginTimeInFifteen.isAfter(appt.getStartTime())){
                    if(appt.getConsultant().equals(UserInfo.getUserName())){
                    Alert alert = new Alert(AlertType.ERROR, "Appointment with " + appt.getContact() + " coming up at " + appt.getStartTime().getHour()+ 
                        ":" + appt.getStartTime().getMinute());
                     alert.showAndWait();
                     notified = true;
                    }
                }
                }

            }
            days.add(day);
        }
    }
    
    public int numberOfAppointmentsForDay(int thisDay){
        try{
        DayOfMonth day = days.get(thisDay);        
        return day.getNumberOfAppointments();
        } catch(Exception ex) {
            return 0;
        }
    }
    public LocalDate getDateofDay(int thisDay){
        DayOfMonth day = days.get(thisDay);
                return day.getdate();
    }
}

