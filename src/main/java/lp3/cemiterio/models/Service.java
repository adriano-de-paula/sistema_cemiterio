/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.text.NumberFormat;

public class Service {
    private int id;
    private int idSO;
    private String serviceName;
    private double value;
    private int amount;

    public Service(int id, int idSO, String serviceName, double value, int amount) {
        this.id = id;
        this.idSO = idSO;
        this.serviceName = serviceName;
        this.value = value;
        this.amount = amount;
    }

    public Service(int idSO, String serviceName, double value, int amount) {
        this.idSO = idSO;
        this.serviceName = serviceName;
        this.value = value;
        this.amount = amount;
    }
   
    public Service(String serviceName, double value, int amount) {
        this.serviceName = serviceName;
        this.value = value;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getIdSO() {
        return idSO;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }
    
    @Override
    public String toString() {
        return this.amount+" - "+this.serviceName+" - "+NumberFormat.getCurrencyInstance().format(this.value);
    }
}
