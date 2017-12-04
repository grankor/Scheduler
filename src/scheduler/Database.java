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
                                
                ResultSet apptrs = stmt.executeQuery("SELECT appointmentId,customerId,title,description,location,contact,url,start,end,createdBy FROM U01JJx.appointment");
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
                    String createdBy = apptrs.getString("createdBy");
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
                    appt.setConsultant(createdBy);
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
                System.out.println("Adding country: " + newCountry.getCountry());
                UserInfo.countries.add(newCountry);
                }
                apptrs.close();
                custrs.close();
                addrs.close();
                cityrs.close(); 
                countryrs.close();
                stmt.close();
                conn.close();
                Scheduler.currentScene = Scheduler.calendarScene;
                Scheduler.changeScene(Scheduler.calendarScene);
                FXMLCalendarPageController.setCalendar();
             
             } catch (SQLException e) {
             System.out.println("SQLException: "+e.getMessage());
             System.out.println("SQLState: "+e.getSQLState());
             System.out.println("VendorError: "+e.getErrorCode());

             }
            
            
    }
    public static void updateAppointment(Appointment appt) throws ClassNotFoundException{
            Connection conn = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                
                LocalDateTime saveStartDate = LocalDateTime.now();
                stmt.executeUpdate("UPDATE U01JJx.appointment " +
                            "SET  customerId='" + appt.getCustomerID()+"', title='"+appt.getTitle()+"', description="+ "'"+ appt.getDescription()+"',"+
                            " location='"+appt.getLocation()+"', contact='"+appt.getContact()+"', " + 
                            "start='"+ appt.getStartTime()+ "', end='"+appt.getEndTime()+"', "+
                        " lastUpdate='" + saveStartDate.toString() + "', lastUpdateBy='"+UserInfo.getUserName()+"' " +
                        "WHERE appointmentId="+ appt.getAppointmentID());
                stmt.close();
                conn.close();
                        } catch (SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                            System.out.println("SQLState: "+e.getSQLState());
                            System.out.println("VendorError: "+e.getErrorCode());
                        }
            
            }
    public static void saveNewAppointment(Appointment appt) throws ClassNotFoundException{
            Connection conn = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
                LocalDateTime saveStartDate = LocalDateTime.now();
                stmt.executeUpdate("INSERT INTO U01JJx.appointment (appointmentId, customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)\n" +
                            "VALUES  (" + appt.getAppointmentID() +", " + appt.getCustomerID()+", '"+appt.getTitle()+"', "+ "'"+ appt.getDescription()+"',"+
                            " '"+appt.getLocation()+"', '"+appt.getContact()+"', " + "'www.google.com',"+
                            "'"+ appt.getStartTime()+ "','"+appt.getEndTime()+"', "+
                        "'"+saveStartDate.toString() + "', '"+UserInfo.getUserName()+"', '"+saveStartDate.toString() + "', '"+UserInfo.getUserName()+"')");
                stmt.close();
                conn.close();
                        } catch (SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                            System.out.println("SQLState: "+e.getSQLState());
                            System.out.println("VendorError: "+e.getErrorCode());
                        }           
            }
    public static void addNewCountry(Country cnty) throws ClassNotFoundException{
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String db = Database.getDatabaseName();
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = Database.getUserName();
        String pass = Database.getPassword();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
            LocalDateTime saveDate = LocalDateTime.now();
            stmt.executeUpdate("INSERT INTO U01JJx.country (countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "VALUES  (" + cnty.getCountryID() +", '"+cnty.getCountry()+"', '"+ saveDate+ "', '"+ UserInfo.getUserName()+ "', "+
                    "'" +saveDate+ "', '"+ UserInfo.getUserName() + "')");
            stmt.close();
            conn.close();
            } catch (SQLException e){
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            }      
    }
    public static void addNewCity(City cty) throws ClassNotFoundException{
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String db = Database.getDatabaseName();
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = Database.getUserName();
        String pass = Database.getPassword();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
            LocalDateTime saveDate = LocalDateTime.now();
            stmt.executeUpdate("INSERT INTO U01JJx.city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "VALUES  (" + cty.getCityID() +", '"+cty.getCity()+ "', "+ cty.getCountryID() +", '"+ saveDate+ "', '"+ UserInfo.getUserName()+ "', "+
                    "'" +saveDate+ "', '"+ UserInfo.getUserName() + "')");
            stmt.close();
            conn.close();
            } catch (SQLException e){
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            }
    }
    public static void addNewAddress(Address addr) throws ClassNotFoundException{
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String db = Database.getDatabaseName();
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = Database.getUserName();
        String pass = Database.getPassword();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
            LocalDateTime saveDate = LocalDateTime.now();
            stmt.executeUpdate("INSERT INTO U01JJx.address (addressId, address, address2, cityid, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "VALUES  (" + addr.getId() +", '"+addr.getAddressOne()+ "', '"+ addr.getAddressTwo() + "', " + addr.getCity() +
                    ", '" + addr.getPostalCode() + "', '" + addr.getPhoneNumber() +
                    "', '"+ saveDate+ "', '"+ UserInfo.getUserName()+ "', '" +saveDate+ "', '"+ UserInfo.getUserName() + "')");
            stmt.close();
            conn.close();
            } catch (SQLException e){
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            }
    }
    public static void updateAddress(Address addr) throws ClassNotFoundException{
            Connection conn = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                
                LocalDateTime saveStartDate = LocalDateTime.now();
                stmt.executeUpdate("UPDATE U01JJx.address " +
                            "SET  address='" + addr.getAddressOne()+"', "+
                                "address2='" + addr.getAddressTwo() + "', " + 
                                "cityId=" + addr.getCity() + ", " + 
                                "postalCode='" + addr.getPostalCode() + "', " + 
                                "phone='" + addr.getPhoneNumber() + "', " + 
                                "lastUpdate='" + saveStartDate + "', " + 
                                "lastUpdateBy='" + UserInfo.getUserName() + "' " + 
                        "WHERE addressId= "+ addr.getId());
                stmt.close();
                conn.close();
                        } catch (SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                            System.out.println("SQLState: "+e.getSQLState());
                            System.out.println("VendorError: "+e.getErrorCode());
                        }            
            
    }
    public static void addNewCustomer(Customer cust) throws ClassNotFoundException{
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String db = Database.getDatabaseName();
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = Database.getUserName();
        String pass = Database.getPassword();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,pass);
            Statement stmt = conn.createStatement();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
            LocalDateTime saveDate = LocalDateTime.now();
            stmt.executeUpdate("INSERT INTO U01JJx.customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "VALUES  (" + cust.getCustomerID() +", '" + cust.getName() + "', "+ cust.getAddressID() + ", " + cust.getActive() + 
                    ", '"+ saveDate+ "', '"+ UserInfo.getUserName()+ "', '" +saveDate+ "', '"+ UserInfo.getUserName() + "')");
            stmt.close();
            conn.close();
            } catch (SQLException e){
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            }
    }
    public static void updateCustomer(Customer cust) throws ClassNotFoundException{
            Connection conn = null;
            String driver = "com.mysql.jdbc.Driver";
            String db = Database.getDatabaseName();
            String url = "jdbc:mysql://52.206.157.109/" + db;
            String user = Database.getUserName();
            String pass = Database.getPassword();
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url,user,pass);
                Statement stmt = conn.createStatement();
                
                LocalDateTime saveStartDate = LocalDateTime.now();
                stmt.executeUpdate("UPDATE U01JJx.customer " +
                            "SET  customerName='" + cust.getName()+"', "+
                                "addressId='" + cust.getAddressID() + "', " +    
                                "lastUpdate='" + saveStartDate + "', " + 
                                "lastUpdateBy='" + UserInfo.getUserName() + "' " + 
                        "WHERE customerId= "+ cust.getCustomerID());
                stmt.close();
                conn.close();
                        } catch (SQLException e){
                            System.out.println("SQLException: "+e.getMessage());
                            System.out.println("SQLState: "+e.getSQLState());
                            System.out.println("VendorError: "+e.getErrorCode());
                        } 
    }
 
}