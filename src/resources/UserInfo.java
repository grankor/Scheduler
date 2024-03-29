
package resources;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


public final class UserInfo {
   private static ArrayList<Appointment> appointments = new ArrayList<Appointment>();
   public static ArrayList<Country> countries = new ArrayList<Country>();
   public static ArrayList<City> cities = new ArrayList<City>();
   public static ArrayList<Address> addresses = new ArrayList<Address>();
   public static ArrayList<Customer> customers = new ArrayList<Customer>();
   
   private static int userID;
   private static String userName;
   // Setting working hours to 8:00 - 18:00  /  8:00am - 6:00pm
   private static final LocalTime workingHoursStart = LocalTime.of(8, 00);
   private static final LocalTime workingHoursEnd = LocalTime.of(18, 00);
   
   public static ArrayList<Appointment> getAppointments(){
       return appointments;
   }
   public static LocalTime getWorkingStart(){
       return workingHoursStart;
   }
   public static LocalTime getWorkingEnd(){
       return workingHoursEnd;
   }
   
   public static void setAppointments(ArrayList<Appointment> appts){
       appointments = appts;
   }
   public static void addAppointment(Appointment newAppointment){
        appointments.add(newAppointment);
   }
   public static boolean removeAppointment(int appointmentID){
       //add functionality to remove appoinement here
       return true;
   }
   public static int getUserID(){
       return userID;
   }
   public static void setUserId(int ID){
       userID = ID;
   }
   public static String getUserName(){
       return userName;
   }
   public static void setUserName(String uname){
       userName = uname;
   }
}
