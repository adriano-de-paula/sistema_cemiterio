/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;

import com.lp3.cemiterio.utils.Alerts;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import lp3.cemiterio.data.exceptions.ConcessionHolderNotFoundException;
import lp3.cemiterio.data.exceptions.UnableToCreateServiceOrderException;
import lp3.cemiterio.models.GeneratedService;
import lp3.cemiterio.models.Service;
import lp3.cemiterio.models.ServiceOrder;
import lp3.cemiterio.services.PDFService;
import lp3.cemiterio.services.SOService;

public class ServiceOrderController extends CloseController implements Initializable {
    
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBillEmission;

    @FXML
    private Button btnItemDelete;

    @FXML
    private ChoiceBox<GeneratedService> cbServiceOrder;

    @FXML
    private Label lblTotalPriceValue;

    @FXML
    private TextField txfConcessionHolderName;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private TextField txfServiceAmountNumber;
    
    @FXML
    private TableColumn<ServiceOrder, Integer> amountColumn;
    @FXML
    private TableColumn<ServiceOrder, String> serviceColumn;
    @FXML
    private TableColumn<ServiceOrder, Double> subtotalColumn;
    
    @FXML
    private TableView<Service> servicesTable;
    
    private SOService service;
    private List<Service> services;
    
    private Service selectedService = null;
    
    double total = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.service = new SOService();
        this.services = new ArrayList<>();
        
        this.amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        this.serviceColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        this.subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        this.cbServiceOrder.setItems(FXCollections.observableArrayList(getServicesList()));
    }
    
    private GeneratedService[] getServicesList() {
        GeneratedService[] servicesList = {
            new GeneratedService(
                    "Sepultamento",
                    100
            ),
            new GeneratedService(
                    "Exumação",
                    300
            ),
            new GeneratedService(
                    "Velório",
                    300
            )
        };
        
        return servicesList;
    }
    
    private void fillConcessionsTable() {
        this.total = 0.0;
        for (Service s: this.services) {
            this.total += s.getValue();
        }
        
        this.lblTotalPriceValue.setText(NumberFormat.getCurrencyInstance().format(total));
        
        ObservableList<Service> obsServices = FXCollections.observableList(this.services);
        this.servicesTable.setItems(obsServices);
    }
    
    private boolean isEveryFieldFilled() {
        return !this.txfConcessionHolderName.getText().isEmpty() &&
                this.cbServiceOrder.getValue() != null &&
                !this.txfServiceAmountNumber.getText().isEmpty() &&
                this.expirationDatePicker.getValue() != null;
    }
    
    @FXML
    private void onClearConcessionHolderClicked() {
        this.txfConcessionHolderName.clear();
    }
    
    @FXML
    private void onAddServiceClicked() {
        if (this.isEveryFieldFilled()) {
            
            try {
                double value = this.cbServiceOrder.getValue().getValue()*Integer.parseInt(this.txfServiceAmountNumber.getText());

                this.services.add(
                    new Service(
                            this.cbServiceOrder.getValue().getServiceName(),
                            value,
                            Integer.parseInt(this.txfServiceAmountNumber.getText())
                    )
                );
                this.fillConcessionsTable();
            } catch (NumberFormatException ex) {
                Alerts.showError("O campo quantidade deve ser preenchido apenas com números");
            }
        }
    }
    
    @FXML
    private void onServicesTableClicked() {
        this.selectedService = this.servicesTable.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void onDeleteItemClicked() {
        if (this.selectedService != null) {
            this.services.remove(this.selectedService);
            this.fillConcessionsTable();
        }
    }
    
    @FXML
    private void onBillEmissionClicked() {
        
        ServiceOrder order = new ServiceOrder(
                            this.txfConcessionHolderName.getText(),
                            new java.util.Date(),
                            java.util.Date.from(this.expirationDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                            this.total
                    );
        try {
            Integer soId = this.service.createSO(
                    order,
                    this.services
            );
            order.setId(soId);
            PDFService pdf = new PDFService();
        
            
            pdf.createBill(
                    order,
                    this.services
            );
            
            Alerts.showSuccess("Ordem de serviço criada com sucesso! O boleto se encontra na pasta raiz.");
            
        } catch (FileNotFoundException | ConcessionHolderNotFoundException | UnableToCreateServiceOrderException ex) {
            Alerts.showError(ex.toString());
        }
    }
    
    @FXML
    private void onNotifyClicked() {
        
    }
}
