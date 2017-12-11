
package CalendarResources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import resources.Appointment;

public class DayOfMonth {
    private LocalDate date;
    private int dayOfMonth;
    private int numOfAppointments;
    private ArrayList<Appointment> dailyAppointments = new ArrayList<Appointment>();
    
    public LocalDate getdate(){
        return date;
    }
    public void setDate(LocalDate newDate){
        date = newDate;
    }
    public int getDayOfMonth(){
        return dayOfMonth;
    }
    public void setDayOfMonth(int newDayOfMonth){
        dayOfMonth = newDayOfMonth;
    }
    public int getNumberOfAppointments(){
        return dailyAppointments.size();
    }
    public  ArrayList<Appointment> getDailyAppointments(){
        return dailyAppointments;
    }
    public void setDailyAppointments(ArrayList<Appointment> newDailyAppointments){
        dailyAppointments = newDailyAppointments;
    }
    public void addDailyAppointment(Appointment appt){
        dailyAppointments.add(appt);
    }
}
