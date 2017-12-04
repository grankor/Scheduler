package resources;

/**
 *
 * @author ndrew
 */
public class Country {
    private int countryId;
    private String country;
    
    public Country(int id, String cntry){
        countryId = id;
        country = cntry;
    }
    
    
    public int getCountryID(){
        return countryId;
    }
    public void setCountryID(int id){
        countryId = id;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String cnty){
        country = cnty;
    }
}
