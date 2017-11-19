
package resources;

public class Address {
    private int addressID; 
    private String addressLineOne;
    private String addressLineTwo;
    int cityID;
    private String postalCode;
    private String phoneNumber;

    public Address(int id, String lineOne, String lineTwo, int cty, String pCode, String pNumber){
        addressID = id;
        addressLineOne = lineOne;
        addressLineTwo = lineTwo;
        cityID = cty;
        postalCode = pCode;
        phoneNumber = pNumber;
    }

    public int getId(){
        return addressID;
    }
    public String getAddressOne(){
        return addressLineOne;
    }
    public void setAddressOne(String address){
        addressLineOne = address;
    }
    public String getAddressTwo(){
        return addressLineTwo;
    }
    public void setAddressTwo(String address){
        addressLineTwo = address;
    }
    public int getCity(){
        return cityID;
    }
    public void setCity(int id){
        cityID = id;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public void setPostalCode(String newPostalCode){
        postalCode = newPostalCode;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String pNum){
        phoneNumber = pNum;
    }
}



