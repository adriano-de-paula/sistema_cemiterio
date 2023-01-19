/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp3.cemiterio.consts.Action;
import lp3.cemiterio.consts.ConcessionType;
import lp3.cemiterio.consts.Gender;
import lp3.cemiterio.consts.State;
import lp3.cemiterio.data.exceptions.CPFAlreadyInUseException;
import lp3.cemiterio.data.exceptions.ConcessionOccupiedException;
import lp3.cemiterio.data.exceptions.UnableToCreateConcessionHolderException;
import lp3.cemiterio.data.exceptions.UnableToFetchConcessionsException;
import lp3.cemiterio.data.exceptions.UnableToUpdateConcessionHolderException;
import lp3.cemiterio.models.Concession;
import lp3.cemiterio.models.ConcessionGrave;
import lp3.cemiterio.models.ConcessionHolder;
import lp3.cemiterio.services.ConcessionHolderService;
import lp3.cemiterio.services.ConcessionService;

/**
 * FXML Controller class
 *
 */
public class ScrConcessionHolderAcessController extends UpdatableController<ConcessionHolder> implements Initializable {

    @FXML
    private TextField txfCPF;
    @FXML
    private TextField txfConcessionHolderName;
    @FXML
    private ChoiceBox<Gender> cbConcessionHolderGender;
    @FXML
    private TextField txfConcessionHolderTelNumber;
    @FXML
    private TextField txfConcessionHolderStreetAdress;
    @FXML
    private TextField txfConcessionHolderDistrict;
    @FXML
    private TextField txfConcessionHolderCity;
    @FXML
    private ChoiceBox<State> cbConcessionHolderState;
    @FXML
    private Button btnConcessionHolderAcessConfirmation;
    
    @FXML
    private TableView<ConcessionGrave> concessionsTable;
    @FXML
    private TableColumn<ConcessionGrave, Integer> drawersColumn;
    @FXML
    private TableColumn<ConcessionGrave, Integer> squareColumn;
    @FXML
    private TableColumn<ConcessionGrave, ConcessionType> typeColumn;
    @FXML
    private TableColumn<ConcessionGrave, Integer> graveColumn;
    @FXML
    private TableColumn<ConcessionGrave, Boolean> hasConcessionColumn;
    
    private ConcessionHolderService service;
    private ConcessionService concessionService;
    
    private ConcessionHolder concessionHolder;
    private List<ConcessionGrave> concessions;
    private ConcessionGrave selectedConcession;
    
    private Action action;
    
    private static final String UPDATE_BUTTON_TEXT = "Alterar";
    private static final String CREATE_BUTTON_TEXT = "Cadastrar";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.service = new ConcessionHolderService();
        this.concessionService = new ConcessionService();
        
        this.btnConcessionHolderAcessConfirmation.setText(CREATE_BUTTON_TEXT);
        this.action = Action.CREATE;
        
        this.concessions = new ArrayList<>();
        
        this.squareColumn.setCellValueFactory(new PropertyValueFactory<>("quadra"));
        this.graveColumn.setCellValueFactory(new PropertyValueFactory<>("sepultura"));
        this.drawersColumn.setCellValueFactory(new PropertyValueFactory<>("gavetasDisponiveis"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.hasConcessionColumn.setCellValueFactory(new PropertyValueFactory<>("temConcessao"));

        this.hasConcessionColumn.setCellFactory(tc -> new TableCell<ConcessionGrave, Boolean>() {
            @Override
            protected void updateItem(Boolean hasConcession, boolean isEmpty) {
                super.updateItem(hasConcession, isEmpty);
                
                // Operador ternário: equivale a um if (?) e else (:)
                setText(isEmpty ? null :
                    hasConcession ? "" : "Nova");
            }
        });
                
        this.cbConcessionHolderGender.setItems(FXCollections.observableArrayList(Gender.values()));
        this.cbConcessionHolderState.setItems(FXCollections.observableArrayList(State.values()));
    }
    
    @Override
    public void setUpdateValue(ConcessionHolder concessionHolder) {
        this.action = Action.UPDATE;
        this.btnConcessionHolderAcessConfirmation.setText(UPDATE_BUTTON_TEXT);
        this.concessionHolder = concessionHolder;
        try {
            this.concessions = this.service.getConcessionHolderConcessions(this.concessionHolder.getCpf());
        } catch (UnableToFetchConcessionsException ex) {
            Alerts.showError("Erro ao buscar as concessões do titular!!");
        }
        fillFieldsFromConcessionHolder();
    }

    private void fillFieldsFromConcessionHolder() {
        this.txfCPF.setText(this.concessionHolder.getCpf());
        this.txfConcessionHolderName.setText(this.concessionHolder.getName());
        this.txfConcessionHolderTelNumber.setText(this.concessionHolder.getTelephoneNumber());
        this.txfConcessionHolderStreetAdress.setText(this.concessionHolder.getStreetAdress());
        this.txfConcessionHolderDistrict.setText(this.concessionHolder.getDistrict());
        this.txfConcessionHolderCity.setText(this.concessionHolder.getCity());
        
        this.cbConcessionHolderGender.setValue(this.concessionHolder.getGender());
        this.cbConcessionHolderState.setValue(this.concessionHolder.getState());

        fillConcessionsTable();
    }
    
    private void fillConcessionsTable() {
        ObservableList<ConcessionGrave> observableConcessions = FXCollections.observableList(this.concessions);
        this.concessionsTable.setItems(observableConcessions);
    }
    
    @FXML
    private void onConfirmButtonClicked() {
        if (isEveryFieldFilled()) {
            
            List<Concession> concessions = new ArrayList<>();
            for (ConcessionGrave c : this.concessions) {
                if (!c.isTemConcessao()) {
                    concessions.add(new Concession(
                            this.txfCPF.getText(),
                            c.getIdJazigo(),
                            c.getType()
                    ));
                }
            }
            
            if (this.action == Action.CREATE) {
                try {
                    this.service.createConcessionHolderWithConcession(
                            new ConcessionHolder(
                                this.txfConcessionHolderName.getText(),
                                this.txfCPF.getText(),
                                this.cbConcessionHolderGender.getValue(),
                                this.txfConcessionHolderTelNumber.getText(),
                                this.txfConcessionHolderStreetAdress.getText(),
                                this.txfConcessionHolderDistrict.getText(),
                                this.txfConcessionHolderCity.getText(),
                                this.cbConcessionHolderState.getValue()
                            ),
                            concessions
                    );
                    Alerts.showConfirmation("Concessionário cadastrado com sucesso!!");
                    this.clearAllFields();
                } catch (CPFAlreadyInUseException | UnableToCreateConcessionHolderException ex) {
                    Alerts.showError(ex.toString());
                }
            } else {
                try {
                    this.service.updateConcessionHolderWithConcessions(
                            new ConcessionHolder(
                                    this.txfConcessionHolderName.getText(),
                                    this.txfCPF.getText(),
                                    this.cbConcessionHolderGender.getValue(),
                                    this.txfConcessionHolderTelNumber.getText(),
                                    this.txfConcessionHolderStreetAdress.getText(),
                                    this.txfConcessionHolderDistrict.getText(),
                                    this.txfConcessionHolderCity.getText(),
                                    this.cbConcessionHolderState.getValue()
                            ),
                            concessions
                    );
                    Alerts.showConfirmation("Concessionário atualizado com sucesso!!");
                    this.close();
                } catch (UnableToUpdateConcessionHolderException ex) {
                    Alerts.showError(ex.toString());
                }
            }
        } else {
            Alerts.showError("Todos os campos são obrigatórios!!");
        }
    }
    
    private boolean isEveryFieldFilled() {
        return !this.txfCPF.getText().isEmpty() &&
                !this.txfConcessionHolderName.getText().isEmpty() &&
                !this.txfConcessionHolderTelNumber.getText().isEmpty() &&
                !this.txfConcessionHolderStreetAdress.getText().isEmpty() &&
                !this.txfConcessionHolderDistrict.getText().isEmpty() &&
                !this.txfConcessionHolderCity.getText().isEmpty() &&
                this.cbConcessionHolderGender.getValue() != null &&
                this.cbConcessionHolderState.getValue() != null;
    }
    
    private void clearAllFields() {
        this.txfCPF.clear();
        this.txfConcessionHolderName.clear();
        this.txfConcessionHolderTelNumber.clear();
        this.txfConcessionHolderStreetAdress.clear();
        this.txfConcessionHolderDistrict.clear();
        this.txfConcessionHolderCity.clear();
        
        this.cbConcessionHolderGender.setValue(null);
        this.cbConcessionHolderState.setValue(null);
        
        this.concessionHolder = null;
        this.concessions.clear();
    }
    
    @FXML
    private void onAddConcessionClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ConcessionScreen.fxml"));
            AnchorPane dialog = (AnchorPane) fxmlLoader.load();
                        
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Concessões");
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);
            
            ConcessionController controller = fxmlLoader.getController();
            controller.setDialogStage(dialogStage);
            
            controller.setConcessionHolderInfo(this.txfConcessionHolderName.getText(), this.concessions);
            
            dialogStage.showAndWait();
            
            fillConcessionsTable();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void onRemoveConcessionClicked() {
        if (selectedConcession != null) {
            String message = "Deseja continuar com a exclusão?\nQuadra "
                    +this.selectedConcession.getQuadra()+", Sepultura "
                    +this.selectedConcession.getSepultura()+", Tipo "
                    +this.selectedConcession.getType();
            if (Alerts.showConfirmation(message)) {
                if (this.selectedConcession.isTemConcessao()) {
                    try {
                        this.concessionService.deleteConcession(this.selectedConcession.getIdConcessao());
                        this.concessions.remove(this.selectedConcession);
                        this.selectedConcession = null;
                        this.fillConcessionsTable();
                    } catch (ConcessionOccupiedException ex) {
                        Alerts.showError(ex.toString());
                    }
                } else {
                    this.concessions.remove(this.selectedConcession);
                    this.selectedConcession = null;
                    this.fillConcessionsTable();
                }
            }
        } else {
            Alerts.showError("Selecione a concessão que deseja remover!");
        }
    }
    
    @FXML
    private void onConcessionsTableClicked() {
        this.selectedConcession = this.concessionsTable.getSelectionModel().getSelectedItem();
    }
}
