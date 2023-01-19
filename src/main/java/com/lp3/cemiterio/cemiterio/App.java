package com.lp3.cemiterio.cemiterio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import lp3.cemiterio.dao.DeceasedDAO;
import lp3.cemiterio.data.exceptions.UnableToFetchDeceasedException;
import lp3.cemiterio.data.exceptions.UserNotFoundException;
import lp3.cemiterio.data.exceptions.WrongPasswordException;
import lp3.cemiterio.models.Deceased;
import lp3.cemiterio.services.DeceasedService;
import lp3.cemiterio.services.LoginService;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LoginScreen"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
//        Deceased teste = new Deceased();
//        var properties = teste.getProperties();
//        for (Object property : properties) {
//            String details = property.toString();
//            String[] splitted = details.split("\\.");
//            System.out.println(details);
//            System.out.println(splitted[splitted.length-1]);
//            System.out.println(property.getClass());
//            System.out.println(property.getClass().descriptorString()+"\n");
//        }
        launch();
    }

}