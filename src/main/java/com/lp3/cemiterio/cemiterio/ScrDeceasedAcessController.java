/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import java.time.Period; 
import com.lp3.cemiterio.utils.Alerts;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lp3.cemiterio.consts.Action;
import lp3.cemiterio.consts.DeceasedSituation;
import lp3.cemiterio.consts.Gender;
import lp3.cemiterio.data.exceptions.UnableToFetchGravesException;
import lp3.cemiterio.data.exceptions.UnableToUpdateDeceasedException;
import lp3.cemiterio.models.Deceased;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.services.DeceasedService;
import lp3.cemiterio.services.GraveService;
/**
 * FXML Controller class
 *
 */
public class ScrDeceasedAcessController extends UpdatableController<Deceased> implements Initializable {
    
    private GraveService graveService;
    private DeceasedService deceasedService;

    @FXML private TableView<Grave> gravesTable;
    @FXML 
    private TableColumn<Grave, Integer> tableColumnSquareNumber;
    @FXML 
    private TableColumn<Grave, Integer> tableColumnGraveNumber;
    @FXML 
    private TableColumn<Grave, Integer> tableColumnMorgueDrawerNumber;
    
    @FXML
    private TextField txfDeceasedName;
    
    @FXML
    private DatePicker deathDatePicker;
    @FXML
    private DatePicker birthDatePicker;
    
    @FXML
    private ChoiceBox<Gender> cbDeceasedGender;
    @FXML
    private ChoiceBox<DeceasedSituation> cbDeceasedSituation;
    
    @FXML
    private TextField txfGraveNumber;
    @FXML
    private TextField txfSquareNumber;
    
    @FXML
    private Button btnDeceasedAcessConfirmation;
    
    @FXML
    private Label situationLabel;
    
    @FXML
    private Label lblYears;
    @FXML
    private Label lblMonths;
    @FXML
    private Label lblDays;
    @FXML
    private Label lblTitle;
    
    Grave grave;
    Deceased deceased;
    
    Action action;
    
    private final String UPDATE_TITLE = "Editar Falecido";
    private final String CREATE_TITLE = "Incluir Falecido";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.action = Action.CREATE;
        this.lblTitle.setText(CREATE_TITLE);
        
        this.graveService = new GraveService();
        this.deceasedService = new DeceasedService();
        
        this.cbDeceasedGender.setItems(FXCollections.observableArrayList(Gender.values()));
        this.cbDeceasedSituation.setItems(FXCollections.observableArrayList(DeceasedSituation.values()));
        
        this.cbDeceasedSituation.setVisible(false);
        this.situationLabel.setVisible(false);
        this.cbDeceasedSituation.setValue(DeceasedSituation.SEPULTADO);
        
        this.tableColumnSquareNumber.setCellValueFactory(new PropertyValueFactory<>("quadra"));
        this.tableColumnGraveNumber.setCellValueFactory(new PropertyValueFactory<>("sepultura"));
        this.tableColumnMorgueDrawerNumber.setCellValueFactory(new PropertyValueFactory<>("gavetasDisponiveis"));
        
        try {
            ObservableList<Grave> observableGraves = FXCollections.observableList(this.graveService.fetchAvailableGraves());
            this.gravesTable.setItems(observableGraves);
        } catch (UnableToFetchGravesException error) {
            this.gravesTable.setPlaceholder(new Label(error.toString()));
        }
    }
    
    @Override
    public void setUpdateValue(Deceased deceased) {
        this.action = Action.UPDATE;
        this.btnDeceasedAcessConfirmation.setText("Alterar");
        
        this.lblTitle.setText(UPDATE_TITLE);
        this.deceased = deceased;
        
        
        try {
            this.grave = this.graveService.getGrave(this.deceased.getIdGrave());
        } catch (UnableToFetchGravesException error) {
            Alerts.showError(error.toString());
            this.dialogStage.close();
        }
        
        this.cbDeceasedSituation.setVisible(true);
        this.situationLabel.setVisible(true);
        
        this.fillFieldsFromDeceased();
    }
    
    private void fillFieldsFromDeceased() {
        if (this.deceased != null && this.grave != null) {
            this.txfDeceasedName.setText(this.deceased.getName());
            this.cbDeceasedGender.setValue(this.deceased.getGender());
            this.cbDeceasedSituation.setValue(this.deceased.getSituacao());
            
            LocalDate birthDate =  Instant.ofEpochMilli(this.deceased.getBirthDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate deathDate = Instant.ofEpochMilli(this.deceased.getDeathDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            this.birthDatePicker.setValue(birthDate);
            this.deathDatePicker.setValue(deathDate);
            
            this.txfGraveNumber.setText(this.grave.getSepultura().toString());
            this.txfSquareNumber.setText(this.grave.getQuadra().toString());
            
            this.calculateAge(deathDate, birthDate);
        }
    }

    @FXML
    private void onGravesTableClicked() {
        this.grave = this.gravesTable.getSelectionModel().getSelectedItem();
        this.txfGraveNumber.setText(this.grave.getSepultura().toString());
        this.txfSquareNumber.setText(this.grave.getQuadra().toString());
    }
    
    @FXML
    private void onConfirmClicked() {
        if (this.action == Action.CREATE) {
            registerDeceased();
        } else {
            updateDeceased();
        }
    }
    
    private void registerDeceased() {
        if (isEveryFieldFilled()) {
            if (this.isDeathDateAfterBirthDate(this.deathDatePicker.getValue(),this.birthDatePicker.getValue())) {
                this.deceasedService.buryDeceased(
                    new Deceased(
                        this.txfDeceasedName.getText(),
                        this.cbDeceasedGender.getValue(),
                        java.util.Date.from(this.birthDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                        this.cbDeceasedSituation.getValue(),
                            java.util.Date.from(this.deathDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                        this.grave.getIdJazigo()
                ));
                Alerts.showSuccess("Cadastro efetuado com sucesso!!");
                this.clearAll();
                this.refreshGraves();
            } else {
                Alerts.showError("A data de falecimento não pode ser menor que a data de nascimento!!");
            }
        } else {
            Alerts.showError("Todos os campos são obrigatórios.");
        }
    }
    
    private void updateDeceased() {
        if (isEveryFieldFilled()) {
            if (this.isDeathDateAfterBirthDate(this.deathDatePicker.getValue(),this.birthDatePicker.getValue())) {
                try {
                    this.deceasedService.updateDeceased(
                        this.deceased,
                        new Deceased(
                            this.deceased.getIdDeceased(),
                            this.txfDeceasedName.getText(),
                            this.cbDeceasedGender.getValue(),
                            java.util.Date.from(this.birthDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                            this.cbDeceasedSituation.getValue(),
                            java.util.Date.from(this.deathDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                            this.grave.getIdJazigo()
                        )
                    ); 
                    Alerts.showSuccess("Atualização cadastral efetuada com sucesso!!");
                    this.dialogStage.close();
                } catch (UnableToUpdateDeceasedException error) {
                    Alerts.showError(error.toString());
                }
            } else {
                Alerts.showError("A data de falecimento não pode ser menor que a data de nascimento!!");
            }
        } else {
            Alerts.showError("Todos os campos são obrigatórios.");
        }
    }
    
    private void clearAll() {
        this.txfDeceasedName.clear();
        this.cbDeceasedGender.setValue(null);
        this.cbDeceasedSituation.setValue(null);
        this.birthDatePicker.setValue(null);
        this.deathDatePicker.setValue(null);
        this.txfGraveNumber.clear();
        this.txfSquareNumber.clear();
        this.grave = null;
        this.deceased = null;
    }
    
    private void refreshGraves() {
         try {
            ObservableList<Grave> observableGraves = FXCollections.observableList(this.graveService.fetchAvailableGraves());
            this.gravesTable.setItems(observableGraves);
        } catch (UnableToFetchGravesException error) {
            this.gravesTable.setPlaceholder(new Label(error.toString()));
        }
    }
    
    private boolean isEveryFieldFilled() {
        return (!this.txfDeceasedName.getText().isEmpty() && 
                this.birthDatePicker.getValue() != null && 
                this.deathDatePicker.getValue() != null &&
                this.cbDeceasedGender.getValue() != null &&
                this.cbDeceasedSituation.getValue() != null &&
                !this.txfSquareNumber.getText().isEmpty() &&
                !this.txfGraveNumber.getText().isEmpty());
    }
    
    private boolean isDeathDateAfterBirthDate(LocalDate deathDate, LocalDate birthDate){
        
        return (birthDate.isBefore(deathDate));
    }
    
    @FXML private void validateDates(){
        
        if(this.birthDatePicker.getValue() != null && this.deathDatePicker.getValue() != null){
            
            if(!isDeathDateAfterBirthDate(deathDatePicker.getValue(),birthDatePicker.getValue())){
                
                Alerts.showError("A data de falecimento não pode ser menor que a data de nascimento!!");
            } else {
                
                calculateAge(deathDatePicker.getValue(), birthDatePicker.getValue());
            }
            
        }   
    }
    
    private void calculateAge(LocalDate deathDate, LocalDate birthDate ){
        
        int years = Period.between(birthDate, deathDate).getYears();
        int months = Period.between(birthDate, deathDate).getMonths();
        int days = Period.between(birthDate, deathDate).getDays();
        
        this.lblYears.setText(years + "");
        this.lblMonths.setText(months + "");
        this.lblDays.setText(days + "");
    }
}
