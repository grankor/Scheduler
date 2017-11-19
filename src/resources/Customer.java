
package resources;


public class Customer {
    private String name;
    private int customerID;
    private int addressID;
    private int active;
    //customerId, customerName, addressId, active 
    public Customer(String nme, int id, int addId, int act ){
        name = nme;
        customerID = id;
        addressID = addId;
        active = act;
    }
    public String getName(){
        return name;
    }
    public void setName(String newName){
        name = newName;
    }
    public int getCustomerID(){
        return customerID;
    }
    public int getAddressID(){
        return addressID;
    }
    public void setAddress(int newAddress){
        addressID = newAddress;
    }
    public int getActive(){
        return active;
    }
    public void setActive(int act){
        active = act;
    }
    
}
