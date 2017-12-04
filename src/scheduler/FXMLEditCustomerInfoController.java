
package scheduler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import resources.*;

/**
 * FXML Controller class
 *
 * @author nicholasdrew
 */
public class FXMLEditCustomerInfoController implements Initializable {
  
    private static Customer customer;
    private static Address address;
    private static Country country;
    private static City city;
    private static Customer originalCustomer;
    private static Address originalAddress;
    private static Country originalCountry;
    private static City originalCity;
    
    private static int selectedMenuID;
    private static int selectedAddressID;
    private static int selectedCityID;
    private static int selectedCountryID;
    private static boolean isNewCustomer = false;
    private static boolean isNewAddress = false;
    private static boolean isNewCity = false;
    private static boolean isNewCountry = false;
    
    
    @FXML
    private MenuButton customerMenu;
    private static MenuButton cuMenu;
    @FXML
    private MenuButton addressMenu;
    private static MenuButton aMenu;
    @FXML
    private MenuButton cityMenu;
    private static MenuButton ciMenu;
    @FXML
    private MenuButton countryMenu;
    private static MenuButton coMenu;
    @FXML
    private Button newCustomer;
    private static Button newCuButton;
    @FXML
    private Button newCity;
    private static Button newCiButton;
    @FXML
    private Button newCountry;
    private static Button newCoButton;
    @FXML
    private Button doneButton;
    private static Button dButton;
    @FXML
    private Button editButton;
    private static Button eButton;
    @FXML
    private Button saveButton;
    private static Button sButton;
    @FXML
    private Button newAddress;
    private static Button nAddress;
    @FXML
    private TextField customerName;
    private static TextField cName;
    @FXML
    private TextField addressLine1;
    private static TextField aLine1;
    @FXML
    private TextField addressLine2;
    private static TextField aLine2;
    @FXML
    private TextField postalCode;
    private static TextField pCode;
    @FXML
    private TextField phoneNumber;
    private static TextField pNumber;
    @FXML
    private TextField cityBox;
    private static TextField ciBox;
    @FXML
    private TextField countryBox;
    private static TextField coBox;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newCuButton = newCustomer;
        newCiButton = newCity;
        newCoButton = newCountry;
        
        cuMenu = customerMenu;
        dButton = doneButton;
        eButton = editButton;
        sButton = saveButton;
        cName = customerName;
        nAddress = newAddress;
        aMenu = addressMenu;
        ciMenu = cityMenu;
        coMenu = countryMenu;
        aLine1 = addressLine1;
        aLine2 = addressLine2;
        pCode = postalCode;
        pNumber = phoneNumber;
        ciBox = cityBox;
        coBox = countryBox;
        
        dButton.setOnAction(ev -> done());
        sButton.setOnAction(ev -> {
            try {
                saveButtonPushed();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLEditCustomerInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        eButton.setOnAction(ev -> editButtonPushed());
        newCiButton.setOnAction(ev -> newCityPushed());
        newCoButton.setOnAction(ev -> newCountryPushed());
        newAddress.setOnAction(ev -> newAddressPushed());
        newCustomer.setOnAction(ev -> newCustomerPushed());
        setButtonsNotEditable();        
         
        loadMenus();
    }    
    
    private static void loadFields(){
        cName.setText(customer.getName());
        int getAdd = customer.getAddressID();
        for(Address addr : UserInfo.addresses){
            if(addr.getId() == getAdd){
                address=addr;
                originalAddress= new Address(address.getId(), address.getAddressOne(), address.getAddressTwo(), address.getCity(), address.getPostalCode(), address.getPhoneNumber());
            }
        }
        int getCity = address.getCity();
        for(City cty: UserInfo.cities){
            if(cty.getCityID()==getCity){
                city = cty;
                originalCity = new City(city.getCityID(), city.getCity(), city.getCountryID());
            }
        }
        int getCountry = city.getCountryID();
        System.out.println("Loading country ID: " + getCountry);
        for(Country cnty : UserInfo.countries){
            System.out.println(cnty.getCountry() + " - ID: " + cnty.getCountryID());
            if(cnty.getCountryID()==getCountry){
                country = cnty;
                String newCountry = new String(country.getCountry());
                originalCountry = new Country(country.getCountryID(), newCountry);
            }
        }   
        
        loadAddressTexts();
        loadMenus();
    }
    
    private static void loadAddressTexts(){
        if(address != null){
        aLine1.setText(address.getAddressOne());
        aLine2.setText(address.getAddressTwo());
        pCode.setText(address.getPostalCode());
        pNumber.setText(address.getPhoneNumber());
        }
        if(city != null){
        ciBox.setText(city.getCity());
        }
        if(country != null){
        coBox.setText(country.getCountry());
        }
    }
    
    private static void loadMenus(){
        loadCustomers();
        loadAddresses();
        loadCities();
        loadCountries();
    }
    
    public static void loadCustomers(){
        cuMenu.getItems().clear();
        eButton.setVisible(false);
        for(Customer cust : UserInfo.customers){
            MenuItem nmi = new MenuItem(cust.getName());
            nmi.setId(Integer.toString(cust.getCustomerID()));            
            cuMenu.getItems().add(nmi);
            nmi.setOnAction(e -> setCustId(e));            
        } 
    }
    private static void loadAddresses(){
        aMenu.getItems().clear();
        for(Address addr : UserInfo.addresses){
            MenuItem nmi = new MenuItem(addr.getAddressOne());
            nmi.setId(Integer.toString(addr.getId()));
            aMenu.getItems().add(nmi);
            nmi.setOnAction(e -> setAddressID(e));
        }
    }
    private static void loadCities(){
        ciMenu.getItems().clear();
        for(City cty : UserInfo.cities){
            MenuItem nmi = new MenuItem(cty.getCity());
            nmi.setId(Integer.toString(cty.getCityID()));
            ciMenu.getItems().add(nmi);
            nmi.setOnAction(e -> setCityID(e));
        }
    }
    private static void loadCountries(){
        coMenu.getItems().clear();
        for(Country cty : UserInfo.countries){
            MenuItem nmi = new MenuItem(cty.getCountry());
            nmi.setId(Integer.toString(cty.getCountryID()));
            coMenu.getItems().add(nmi);
            nmi.setOnAction(e -> setCountryID(e));
        }
    }
    
    
    private static void setCustId(ActionEvent e){
        MenuItem mi = (MenuItem)e.getSource();        
        selectedMenuID = Integer.parseInt(mi.getId());        
        for(int i = 0; i < UserInfo.customers.size() ;i++){            
            Customer custom = UserInfo.customers.get(i);            
            if(custom.getCustomerID() == selectedMenuID){
                customer = custom;
            }            
        }
        cuMenu.setText(customer.getName());
        originalCustomer = new Customer(customer.getName(), customer.getCustomerID(), customer.getCustomerID(), customer.getActive());
        loadFields();
        eButton.setVisible(true);
    }
    
    private static void setAddressID(ActionEvent e){
        MenuItem mi = (MenuItem)e.getSource();
        selectedAddressID = Integer.parseInt(mi.getId());
        for(int i = 0; i < UserInfo.addresses.size() ;i++){            
            Address addr = UserInfo.addresses.get(i);            
            if(addr.getId()== selectedAddressID){
                address = addr;
                if(customer != null){
                    customer.setAddress(address.getId());
                }
            }  
        }
        selectedCityID = address.getCity();
        for(int i = 0; i < UserInfo.cities.size() ;i++){            
            City cty = UserInfo.cities.get(i);            
            if(cty.getCityID()== selectedCityID){
                city = cty;
                if(address != null){
                    address.setCity(city.getCityID());
                }
            }
        }
        selectedCountryID = city.getCountryID();
        for(int i = 0; i < UserInfo.countries.size() ;i++){            
            Country cty = UserInfo.countries.get(i);
            if(cty.getCountryID() == selectedCountryID){
                country = cty;
                if(city != null){
                    city.setCountryID(country.getCountryID());
                }
            }
        }
        loadAddressTexts();
    }
    private static void setCityID(ActionEvent e){
        MenuItem mi = (MenuItem)e.getSource();
        selectedCityID = Integer.parseInt(mi.getId());
        for(int i = 0; i < UserInfo.cities.size() ;i++){            
            City cty = UserInfo.cities.get(i);            
            if(cty.getCityID()== selectedCityID){
                city = cty;
                if(address != null){
                    address.setCity(city.getCityID());
                }
            }
        }
        selectedCountryID = city.getCountryID();
        for(int i = 0; i < UserInfo.countries.size() ;i++){            
            Country cty = UserInfo.countries.get(i);
            if(cty.getCountryID() == selectedCountryID){
                country = cty;
                if(city != null){
                    city.setCountryID(country.getCountryID());
                }
            }
        }
        loadAddressTexts();
    }
    private static void setCountryID(ActionEvent e){
        MenuItem mi = (MenuItem)e.getSource();
        selectedCountryID = Integer.parseInt(mi.getId());
        for(int i = 0; i < UserInfo.countries.size() ;i++){            
            Country cty = UserInfo.countries.get(i);
            if(cty.getCountryID() == selectedCountryID){
                country = cty;
                if(city != null){
                    city.setCountryID(country.getCountryID());
                }
            }
        }
        loadAddressTexts();
    }
    private void done(){
        editButton.setVisible(true);
        saveButton.setVisible(false);
        FXMLCalendarPageController.setCalendar();
        //Need to add weekly controller set calendar function here as well
        Scheduler.changeScene(Scheduler.currentScene);
        customer = null;
        address = null;
        country = null;
        city = null;
        
        aLine1.setText("");
        aLine2.setText("");
        pCode.setText("");
        pNumber.setText("");
        ciBox.setText("");
        coBox.setText("");
        cName.setText("");
        cuMenu.setText("Customers");
        setButtonsNotEditable();
    }
    private static void newCustomerPushed(){
        isNewCustomer = true;
        cuMenu.setText("");
        cName.setText("");        
        setButtonsEditable();
        loadAddresses();
        loadCities();
        loadCountries();
        aLine1.setEditable(false);
        aLine2.setEditable(false);
        pCode.setEditable(false);
        pNumber.setEditable(false);
    }
    private static void newAddressPushed(){
        aLine1.setEditable(true);
        aLine2.setEditable(true);
        pCode.setEditable(true);
        pNumber.setEditable(true);
        isNewAddress = true;
        aLine1.setText("");
        aLine2.setText("");
        pCode.setText("");
        pNumber.setText("");
    }
    private static void newCountryPushed(){
        isNewCountry = true;
        coBox.setText("");
        coBox.setEditable(true);
    }
    private static void newCityPushed(){
        isNewCity = true;
        ciBox.setText("");
        ciBox.setEditable(true);
    }
    private static void setButtonsEditable(){
        eButton.setVisible(false);
        sButton.setVisible(true);
        nAddress.setVisible(true);
        aMenu.setVisible(true);
        ciMenu.setVisible(true);
        coMenu.setVisible(true);
        newCiButton.setVisible(true);
        newCoButton.setVisible(true);
       
        cName.setEditable(true);
        aLine1.setEditable(true);
        aLine2.setEditable(true);
        pCode.setEditable(true);
        pNumber.setEditable(true);
    }
    private static void setButtonsNotEditable(){
        eButton.setVisible(true);
        sButton.setVisible(false);
        nAddress.setVisible(false);
        aMenu.setVisible(false);
        ciMenu.setVisible(false);
        coMenu.setVisible(false);
        newCiButton.setVisible(false);
        newCoButton.setVisible(false);

        aLine1.setEditable(false);
        aLine2.setEditable(false);
        pCode.setEditable(false);
        pNumber.setEditable(false);
        ciBox.setEditable(false);
        coBox.setEditable(false);
        cName.setEditable(false);
    }
    private static void editButtonPushed(){
        setButtonsEditable();
    }
    private static void saveButtonPushed() throws ClassNotFoundException{
        if(cName.getText().equals("")){
            missingInformation("Customer Name required.");
            return;
        }
        if(aLine1.getText().equals("")){
            missingInformation("Address required.");
            return;
        }
        if(aLine2.getText().equals("")){
            aLine2.setText(" ");            
        }
        if(pCode.getText().equals("")){
            missingInformation("Postal code required.");
            return;
        }
        if(pNumber.getText().equals("")){
            missingInformation("Phone Number required.");
            return;
        }
        if(ciBox.getText().equals("")){
            missingInformation("City required.");
            return;
        }
        if(coBox.getText().equals("")){
            missingInformation("Country required.");
            return;
        }        
        setButtonsNotEditable();        
        if(isNewCountry){
                int newCountryID = UserInfo.countries.get(UserInfo.countries.size()-1).getCountryID();
                newCountryID += 1;
                Country newCountry = new Country(newCountryID, coBox.getText());
                country = newCountry;
                UserInfo.countries.add(newCountry);
                Database.addNewCountry(country);
        }
        if(isNewCity){
            int newCityID = UserInfo.cities.get(UserInfo.cities.size()-1).getCityID();
            newCityID += 1;
            int newCityCountryID = country.getCountryID();            
            City newCity = new City(newCityID, ciBox.getText(), newCityCountryID);
            city=newCity;
            UserInfo.cities.add(newCity);
            Database.addNewCity(city);
        }
        if(isNewAddress){
            int newAddressID = UserInfo.addresses.get(UserInfo.addresses.size()-1).getId();
            newAddressID += 1;
            Address newAddress = new Address(newAddressID, aLine1.getText().toString(), aLine2.getText().toString(), city.getCityID(), pCode.getId(), pNumber.getText().toString());
            address=newAddress;
            UserInfo.addresses.add(newAddress);
            Database.addNewAddress(address);
        } else {
            int addressID = customer.getAddressID();
            for(Address addr : UserInfo.addresses){
                if(addr.getId()==addressID){
                    address = addr;
                }
            }
            address.setAddressOne(aLine1.getText());
            address.setAddressTwo(aLine2.getText());
            address.setPostalCode(pCode.getText());
            address.setPhoneNumber(pNumber.getText());
            Database.updateAddress(address);
        }
        if(isNewCustomer){
            int newCustomerID = UserInfo.customers.get(UserInfo.customers.size()-1).getCustomerID();
            newCustomerID += 1;
            Customer newCustomer = new Customer(cName.getText().toString(), newCustomerID, address.getId(), 1);
            customer = newCustomer;
            UserInfo.customers.add(newCustomer);
            Database.addNewCustomer(customer);
        } else {
            Database.updateCustomer(customer);
        }
    isNewCustomer = false;
    isNewAddress = false;
    isNewCity = false;
    isNewCountry = false;
    }
    private static void missingInformation(String error){            
        Alert alert = new Alert(AlertType.ERROR, error);
        alert.showAndWait();
    }
    
}
