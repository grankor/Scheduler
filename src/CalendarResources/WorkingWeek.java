
package CalendarResources;

import java.util.ArrayList;
import java.util.Date;


public class WorkingWeek {
    private ArrayList<DayOfMonth> days= null;
    private Date startOfWeek;
    private Date endOfWeek;
    private int numDaysInWeek;
    private String month;
    
    @Override
    public String toString(){
        return month;
    }
    
    public Date getStartOfWeek(){
        return startOfWeek;
    }
    public void setStartOfWeek(Date sow){
        startOfWeek = sow;
    }
    public Date getEndOfWeek(){
        return endOfWeek;
    }
    public void setEndOfWeek(Date eow){
        endOfWeek = eow;
    }
    public void setNumDaysInWeek(int dow){
        numDaysInWeek = dow;
    }
    public int getNumDaysInWeek(){
        return numDaysInWeek;
    }
    public ArrayList<DayOfMonth> getDays(){
        return days;
    }
    public void setDays(ArrayList<DayOfMonth> newDays){
        days = newDays;
    }
}
