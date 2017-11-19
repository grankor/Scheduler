package resources;

import java.time.LocalDateTime;
import java.util.Date;

public class Appointment {
    private LocalDateTime dateTime;
    private String type;
    private int customerID;
    private Customer customer;
    private String customerName;
    private String consultant;
    private int appointmentID;
    private String description;
    private String location;
    private String contact;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private String title;
    private String url;
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return contact;
    }
    
    
    public LocalDateTime getDate(){
        return dateTime;
    }
    public void setDate(LocalDateTime newDate){
        dateTime = newDate;
    }
    public String getType(){
        return type;
    }
    public void setType(String newType){
        type = newType;
    } 
    public String getCustomerName(){
        return customerName;
    }
    public int getCustomerID(){
        return customerID;
    }
    public void setCustomerID(int newID){
        customerID = newID;
    }
    public String getConsultant(){
        return consultant;
    }
    public void setConsultant(String newConsultant){
        consultant = newConsultant;
    }
    public int getAppointmentID(){
        return appointmentID;
    }
    public void setAppointmentID(int id){
        appointmentID = id;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String newDescription){
        description = newDescription;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String newLocation){
        location = newLocation;
    }
    public String getContact(){
        return contact;
    }
    public void setContact(String newContact){
        contact = newContact;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public void setStartTime(LocalDateTime newStartTime){
        startTime = newStartTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public void setEndTime(LocalDateTime newEndTime){
        endTime = newEndTime;
    }
    public LocalDateTime getCreateDate(){
        return createDate;
    }
    public String getCreatedBy(){
        return createdBy;
    }
    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }
    public void setLastUpdate(LocalDateTime newLastUpdate){
        lastUpdate = newLastUpdate;
    }
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(String newLastUpdatedBy){
        lastUpdatedBy = newLastUpdatedBy;
    }
        public void setTitle(String newTitle){
        title = newTitle;
    }
    public String getTitle(){
        return title;
    }
    public void setURL(String newURL){
        url = newURL;
    }
    public String getURL(){
        return url;
    }
}
