/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.Alerts;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lp3.cemiterio.consts.ConcessionType;
import lp3.cemiterio.data.exceptions.UnableToFetchGravesException;
import lp3.cemiterio.models.ConcessionGrave;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.services.GraveService;


public class ConcessionController extends CloseController implements Initializable {
    
    @FXML
    private TextField txfConcessionHolder;
    @FXML
    private TableView<Grave> gravesTable;
    @FXML
    private TableColumn<Grave, Integer> tableColumnDrawers;
    @FXML
    private TableColumn<Grave, Integer> tableColumnGraveNumber;
    @FXML
    private TableColumn<Grave, Integer> tableColumnSquareNumber;
    @FXML
    private TextField txfGraveNumber;
    @FXML
    private TextField txfSquareNumber;
    @FXML
    private ChoiceBox<ConcessionType> cbConcessionType;
    
    private GraveService service;
    private ConcessionGrave concession;
    private List<ConcessionGrave> concessions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.service = new GraveService();
        
        this.concession = new ConcessionGrave();
        
        this.cbConcessionType.setItems(FXCollections.observableArrayList(ConcessionType.values()));
        
        this.tableColumnSquareNumber.setCellValueFactory(new PropertyValueFactory<>("quadra"));
        this.tableColumnGraveNumber.setCellValueFactory(new PropertyValueFactory<>("sepultura"));
        this.tableColumnDrawers.setCellValueFactory(new PropertyValueFactory<>("gavetasDisponiveis"));
        
        try {
            ObservableList<Grave> observableGraves = FXCollections.observableList(this.service.fetchGraves("temConcessao", "0"));
            this.gravesTable.setItems(observableGraves);
        } catch (UnableToFetchGravesException error) {
            this.gravesTable.setPlaceholder(new Label(error.toString()));
        }
    }
    
    public void setConcessionHolderInfo(String name, List<ConcessionGrave> concessions) {
        this.txfConcessionHolder.setText(name);
        this.concessions = concessions;
    }
    
    @FXML
    private void onGravesTableClicked() {
        Grave selectedGrave = this.gravesTable.getSelectionModel().getSelectedItem();
        
        if (selectedGrave != null) {
            this.concession.setIdJazigo(selectedGrave.getIdJazigo());
            this.concession.setQuadra(selectedGrave.getQuadra());
            this.concession.setSepultura(selectedGrave.getSepultura());
            this.concession.setGavetasDisponiveis(selectedGrave.getGavetasDisponiveis());
            this.concession.setTemConcessao(selectedGrave.isTemConcessao());

            this.txfGraveNumber.setText(concession.getSepultura().toString());
            this.txfSquareNumber.setText(concession.getQuadra().toString());
        }
    }
    
    @FXML
    private void onConfirmAddConcessionClicked() {
        if (isEveryFieldFilled()) {
            if (isANewConcession()) {
                this.concession.setType(this.cbConcessionType.getValue());
                this.concessions.add(this.concession);
            }
            this.close();
        } else {
            Alerts.showError("É necessário escolher o tipo de concessão e o jazigo!!");
        }
    }
    
    private boolean isEveryFieldFilled() {
        return this.cbConcessionType.getValue() != null &&
                !this.txfGraveNumber.getText().isEmpty() &&
                !this.txfSquareNumber.getText().isEmpty();
    }
    
    private boolean isANewConcession() {
        for (ConcessionGrave c : this.concessions) {
            if (c.getIdJazigo() == this.concession.getIdJazigo()) {
                return false;
            }
        }
        return true;
    }

}
