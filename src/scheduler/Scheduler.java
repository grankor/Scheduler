/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nicholasdrew
 */
public class Scheduler extends Application {
    public static Stage stageController;
    public static Scene loginScene;
    public static Scene calendarScene;
    
    @Override
    public void start(Stage stage) throws Exception {
        stageController = stage;
        Parent login = FXMLLoader.load(getClass().getResource("FXMLLoginPage.fxml"));
        Parent calendar = FXMLLoader.load(getClass().getResource("FXMLCalendarPage.fxml"));
        
        loginScene = new Scene(login);
        calendarScene = new Scene(calendar);
        
        stage.setScene(loginScene);
        stage.show();

    }
    public static void changeScene(Scene newScene){
        stageController.setScene(newScene);
        stageController.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
