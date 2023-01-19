/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.ScreenNavigationHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp3.cemiterio.consts.Permission;
import lp3.cemiterio.models.ConcessionHolder;
import lp3.cemiterio.models.Deceased;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.models.User;
import lp3.cemiterio.services.ConcessionHolderService;
import lp3.cemiterio.services.DeceasedService;
import lp3.cemiterio.services.GraveService;
import lp3.cemiterio.services.LoginService;

public class MenuController extends CloseController implements Initializable {
    
    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton btnAcessMenu;
    @FXML
    private MenuItem btnServiceOrder;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = LoginService.getInstance().getUser();
        this.welcomeLabel.setText("Seja bem-vindo, "+user.getName()+"!");
        
        if (user.getPermission() != Permission.ADMIN) {
            this.btnAcessMenu.setDisable(true);
            this.btnServiceOrder.setDisable(true);
        }
    }
    
    @FXML
    private void openRegisterDeceasedScreen() {
        ScreenNavigationHelper.openApplicationModal("scrDeceasedAcess", "Falecidos");
    }
    
    @FXML
    private void openCreateGraveScreen() {
        ScreenNavigationHelper.openApplicationModal("scrGraveAcess", "Jazigos");
    }
    
    @FXML
    private void openCreateConcessionHolderScreen() {
        ScreenNavigationHelper.openApplicationModal("scrConcessionHolderAcess", "Concessionários");
    }
    
    @FXML
    private void openSearchDeceasedScreen() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scrSearch.fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Busca Falecido");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            SearchController controller = fxmlLoader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(new DeceasedService(), Deceased.getProperties(), "scrDeceasedAcess");
            
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openSearchGraveScreen() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scrSearch.fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Busca Jazigos");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            SearchController controller = fxmlLoader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(new GraveService(), Grave.getProperties(), "scrGraveAcess");
            
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openSearchConcessionHolderScreen() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scrSearch.fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Busca Concessionários");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            SearchController controller = fxmlLoader.getController();
            controller.setDialogStage(dialogStage);
            controller.setService(new ConcessionHolderService(), ConcessionHolder.getProperties(), "scrConcessionHolderAcess");
            
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openCreateSOScreen() {
        ScreenNavigationHelper.openApplicationModal("scrServiceOrder", "Ordem de Serviço");
    }
    
    @FXML
    private void openListSOScreen() {
        ScreenNavigationHelper.openApplicationModal("scrServiceOrder", "Ordem de Serviço");
    }
    
    @FXML
    private void logout() {
        LoginService.getInstance().logout();
        this.close();
    }
}