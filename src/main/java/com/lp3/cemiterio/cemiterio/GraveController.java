/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.Alerts;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lp3.cemiterio.consts.Action;
import lp3.cemiterio.data.exceptions.AlreadyRegisteredGraveException;
import lp3.cemiterio.data.exceptions.InsufficientDrawersAmountException;
import lp3.cemiterio.data.exceptions.UnableToCreateGraveException;
import lp3.cemiterio.data.exceptions.UnableToUpdateGraveException;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.services.GraveService;

public class GraveController extends UpdatableController<Grave> implements Initializable {

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField txfAmountDrawer;

    @FXML
    private TextField txfGraveNumber;

    @FXML
    private TextField txfSquareNumber;
    
    private GraveService service;
    
    private Grave grave;
    private Action action;
    
    private static final String UPDATE_BUTTON_TEXT = "Alterar";
    private static final String CREATE_BUTTON_TEXT = "Cadastrar";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.service = new GraveService();
        this.btnConfirm.setText(CREATE_BUTTON_TEXT);
        this.action = Action.CREATE;
    }
    
    @Override
    public void setUpdateValue(Grave grave) {
        this.action = Action.UPDATE;
        
        this.grave = grave;
        fillFieldsFromGrave();
        this.btnConfirm.setText(UPDATE_BUTTON_TEXT);
        
        this.txfSquareNumber.setDisable(true);
        this.txfGraveNumber.setDisable(true);
    }

    private void fillFieldsFromGrave() {
        this.txfSquareNumber.setText(this.grave.getQuadra().toString());
        this.txfGraveNumber.setText(this.grave.getSepultura().toString());
        this.txfAmountDrawer.setText(this.grave.getGavetasDisponiveis().toString());
    }
    
    @FXML
    private void onConfirmClicked() {
        if (isEveryFieldFilled()) {
            try {
                int squareNumber = Integer.parseInt(this.txfSquareNumber.getText());
                int graveNumber = Integer.parseInt(this.txfGraveNumber.getText());
                int drawers = Integer.parseInt(this.txfAmountDrawer.getText());

                if (this.action == Action.CREATE) {
                    try {
                        this.service.createGrave(
                                new Grave(
                                        squareNumber,
                                        graveNumber,
                                        drawers
                                )
                        );
                        Alerts.showSuccess("Jazigo criado com sucesso!!");
                        this.clearAllFields();
                    } catch (AlreadyRegisteredGraveException | UnableToCreateGraveException ex) {
                        Alerts.showError(ex.toString());
                    }
                } else {
                    try {
                        this.service.updateGraveDrawers(this.grave.getIdJazigo(), drawers);
                        Alerts.showSuccess("Jazigo atualizado com sucesso!!");
                        this.dialogStage.close();
                    } catch (InsufficientDrawersAmountException | UnableToUpdateGraveException ex) {
                        Alerts.showError(ex.toString());
                    }
                }
            } catch (NumberFormatException error) {
                Alerts.showError("Os campos devem ser preenchidos apenas com números!!");
            }
        } else {
            Alerts.showError("Todos os campos são obrigatórios!!");
        }
    }
    
    private boolean isEveryFieldFilled() {
        return !this.txfSquareNumber.getText().isEmpty() && 
                !this.txfGraveNumber.getText().isEmpty() &&
                !this.txfAmountDrawer.getText().isEmpty();
    }
    
    private void clearAllFields() {
        this.txfSquareNumber.clear();
        this.txfGraveNumber.clear();
        this.txfAmountDrawer.clear();
        this.grave = null;
    }

}