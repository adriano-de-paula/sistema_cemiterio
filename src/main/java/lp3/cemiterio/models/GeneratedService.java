/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GeneratedService {
    private String serviceName;
    private double value;

    public GeneratedService(String serviceName, double value) {
        this.serviceName = serviceName;
        this.value = value;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return this.serviceName+" - "+NumberFormat.getCurrencyInstance().format(this.value);
    }
}
