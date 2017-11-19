/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import resources.Address;
import resources.Appointment;
import resources.City;
import resources.Country;
import resources.Customer;
import resources.UserInfo;

/**
 *
 * @author nicholasdrew
 */
public class Database {
    private static String serverName = "52.206.157.109 ";
    private static String databaseName = "U01JJx";
    private static String userName = "U01JJx";
    private static String password = "53687469881";
    
    public static String getServerName(){
        return serverName;
    }
    public static String getDatabaseName(){
        return databaseName;
    }
    public static String getUserName(){
        return userName;
    }
    public static String getPassword(){
        return password;
    }
    public static void getDatabaseInformation() throws ClassNotFoundException{
            Connection conn = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            ArrayList<Appointment> appointments = new ArrayList<Appointment>();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                Statement stmt4 = conn.createStatement();
                Statement stmt5 = conn.createStatement();
                                
                ResultSet apptrs = stmt.executeQuery("SELECT appointmentId,customerId,title,description,location,contact,url,start,end FROM U01JJx.appointment");
                ResultSet addrs = stmt2.executeQuery("SELECT addressId, address, address2, cityId, postalCode, phone FROM U01JJx.address");
                ResultSet custrs= stmt3.executeQuery("SELECT customerId,customerName,addressId,active FROM U01JJx.customer");
                ResultSet cityrs = stmt4.executeQuery("SELECT cityId, city, countryId FROM U01JJx.city");
                ResultSet countryrs = stmt5.executeQuery("SELECT countryId, country FROM U01JJx.country");
                while(apptrs.next()){
                    int appID = apptrs.getInt("appointmentId");
                    int custID = apptrs.getInt("customerId");
                    String title = apptrs.getString("title");
                    String description = apptrs.getString("description");
                    String location = apptrs.getString("location");
                    String contact = apptrs.getString("contact");
                    String appUrl = apptrs.getString("url");
                    String startTimeString = apptrs.getString("start");
                    startTimeString = startTimeString.substring(0, 19);
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
                    LocalDateTime ldtStart = LocalDateTime.parse(startTimeString, df);
                    String endTime = apptrs.getString("end");
                    endTime = endTime.substring(0,19);
                    LocalDateTime ldtEnd = LocalDateTime.parse(endTime,df);
                    Appointment appt = new Appointment();
                    appt.setTitle(title);
                    appt.setURL(appUrl);
                    appt.setCustomerID(custID);
                    appt.setAppointmentID(appID);
                    appt.setDescription(description);
                    appt.setLocation(location);
                    appt.setContact(contact);
                    appointments.add(appt);
                    appt.setStartTime(ldtStart);
                    appt.setEndTime(ldtEnd);                    
                }            
                UserInfo.setAppointments(appointments);
                while(custrs.next()){
                    int customerId = custrs.getInt("customerId");
                    String customerName = custrs.getString("customerName");
                    int addressId = custrs.getInt("addressId");
                    int active = custrs.getInt("active");
                    Customer customer = new Customer(customerName, customerId, addressId, active);
                    UserInfo.customers.add(customer);
                }
                while(addrs.next()){
                    int addressId = addrs.getInt("addressId");
                    String address = addrs.getString("address");
                    String address2 = addrs.getString("address2");
                    int cityId = addrs.getInt("cityId");
                    String postalCode = addrs.getString("postalCode");
                    String phone = addrs.getString("phone");
                    Address city = new Address(addressId, address, address2, cityId, postalCode, phone);
                    UserInfo.addresses.add(city);
                }
                while(cityrs.next()){
                //cityId, city, countryId
                int cityId = cityrs.getInt("cityId");
                String city = cityrs.getString("city");
                int countryId = cityrs.getInt("countryId");
                City newCity = new City (cityId,city,countryId);
                UserInfo.cities.add(newCity);
                }
                while(countryrs.next()){
                //countryId, country
                int countryId = countryrs.getInt("countryId");
                String country = countryrs.getString("country");
                Country newCountry = new Country(countryId, country);
                UserInfo.countries.add(newCountry);
                }
                apptrs.close();
                custrs.close();
                addrs.close();
                cityrs.close(); 
                countryrs.close();
                stmt.close();
                conn.close();
                Scheduler.changeScene(Scheduler.calendarScene);
             
             } catch (SQLException e) {
             System.out.println("SQLException: "+e.getMessage());
             System.out.println("SQLState: "+e.getSQLState());
             System.out.println("VendorError: "+e.getErrorCode());

             }
    }
    
}
