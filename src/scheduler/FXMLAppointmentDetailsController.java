
package scheduler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import resources.*;


public class FXMLAppointmentDetailsController implements Initializable {

    private static Appointment appointment;
    private static Customer customer;
    private static City city;
    private static Address address;
    private static Country country;
    private static int apptID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public static void initAppointment(int ID){
        apptID = ID;
        
        Appointment thisAppt = new Appointment();
        ArrayList<Appointment> appts = UserInfo.getAppointments();
        for(int i =0 ; i < appts.size();i++){
            thisAppt = appts.get(i);
            if(apptID == thisAppt.getAppointmentID()){
                appointment = thisAppt;
                return;
            }
        }
        
    }
}
