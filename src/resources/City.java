
package resources;

/**
 *
 * @author ndrew
 */
public class City {
    private int cityId; 
    private String city;
    private int countryId;
    
    public City (int id, String cty, int cntyId){
        cityId = id;
        city = cty;
        countryId = cntyId;
    }
    public int getCityID(){
        return cityId;
    }
    public void setCityID(int id){
        cityId = id;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String cty){
        city = cty;
    }
    public int getCountryID(){
        return countryId;
    }
    public void setCountryID(int id){
        countryId = id;
    }
    
}

