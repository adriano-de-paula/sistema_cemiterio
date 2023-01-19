/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.utils;

import com.lp3.cemiterio.cemiterio.App;
import com.lp3.cemiterio.cemiterio.CloseController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ScreenNavigationHelper {
    public static void openApplicationModal(String fileName, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fileName+".fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();
                        
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);
            
            CloseController controller = fxmlLoader.getController();
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
