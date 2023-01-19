/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp3.cemiterio.consts.Permission;
import lp3.cemiterio.data.exceptions.UnableToDeleteException;
import lp3.cemiterio.data.exceptions.UnableToFetchException;
import lp3.cemiterio.models.FieldTitleName;
import lp3.cemiterio.services.LoginService;
import lp3.cemiterio.services.SearchableService;

public class SearchController extends CloseController implements Initializable{

    @FXML
    private Button btnSearchDelete;

    @FXML
    private Button btnSearchUpdateSelect;

    @FXML
    private ChoiceBox<FieldTitleName> cbSearchFilter;

    @FXML
    private TextField txfSearch;
    
    @FXML
    private Label lblTitle;
    
    @FXML
    private TableView searchTable;
    
    SearchableService service;
    String screenPath;
    
    Object selectedItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnSearchUpdateSelect.setDisable(true);
        this.btnSearchDelete.setDisable(true);
    }
    
    public void setService(SearchableService service, FieldTitleName[] fields, String screenPath) {
        this.service = service;
        this.screenPath = screenPath;
        
        this.lblTitle.setText(this.dialogStage.getTitle());
        
        this.createColumns(fields);
        this.populateTable();
        
        this.cbSearchFilter.setItems(FXCollections.observableArrayList(fields));
    }
        
    private void createColumns(FieldTitleName[] fields) {
        for (FieldTitleName field : fields) {
            TableColumn<Object, Object> column = new TableColumn<>(field.title);
            column.setCellValueFactory(new PropertyValueFactory<>(field.fieldName));
            this.searchTable.getColumns().addAll(column);
        }
    }
    
    private void populateTable() {
        try {
            ObservableList observable = FXCollections.observableList(this.service.fetch());
            this.searchTable.setItems(observable);
        } catch (UnableToFetchException error) {
            this.searchTable.setPlaceholder(new Label(error.toString()));
        }
    }
    
    @FXML
    private void filter() {
        if (this.cbSearchFilter.getValue() != null && !this.txfSearch.getText().isEmpty()) {
            
            String filter = this.cbSearchFilter.getValue().dbField;
            String value = this.txfSearch.getText();
            
            try {
                ObservableList observable = FXCollections.observableList(this.service.fetch(filter, value));
                this.searchTable.setItems(observable);
            } catch (UnableToFetchException error) {
                this.searchTable.setPlaceholder(new Label(error.toString()));
            }
        }
    }
    
    @FXML
    private void delete() {
        if (Alerts.showConfirmation("Deseja continuar com a exclusão?")) {
            if (this.selectedItem != null) {
                try {
                    this.service.delete(this.selectedItem);
                    Alerts.showConfirmation("Exclusão realizada com sucesso!");
                    this.clearSearch();
                } catch (UnableToDeleteException error) {
                    Alerts.showError(error.toString());
                }
            }
        }
    }
    
    @FXML
    private void clearSearch() {
        this.cbSearchFilter.setValue(null);
        this.txfSearch.setText("");
        this.populateTable();
        this.btnSearchUpdateSelect.setDisable(true);
        this.btnSearchDelete.setDisable(true);
    }
    
    //libera botoes de acordo com a permissão do usuário
    @FXML
    private void setSelectedItem() {
        this.selectedItem = this.searchTable.getSelectionModel().getSelectedItem();
        
        if (LoginService.getInstance().getUser().getPermission() == Permission.ADMIN) {
            this.btnSearchUpdateSelect.setDisable(false);
            this.btnSearchDelete.setDisable(false);
        }
    }
    
    @FXML
    private void goToEditionPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(this.screenPath+".fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            UpdatableController controller = fxmlLoader.getController();
            
            controller.setDialogStage(dialogStage);
            controller.setUpdateValue(this.selectedItem);
            
            dialogStage.showAndWait();
            this.clearSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

