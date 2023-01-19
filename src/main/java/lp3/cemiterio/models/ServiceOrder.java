/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.util.Date;

public class ServiceOrder {
    private int id;
    private final String concessionHolderCPF;
    private final Date creationDate;
    private final Date expiringDate;
    private final double total;

    public ServiceOrder(int id, String concessionHolderCPF, Date creationDate, Date expiringDate, double total) {
        this.id = id;
        this.concessionHolderCPF = concessionHolderCPF;
        this.creationDate = creationDate;
        this.expiringDate = expiringDate;
        this.total = total;
    }

    public ServiceOrder(String concessionHolderCPF, Date creationDate, Date expiringDate, double total) {
        this.concessionHolderCPF = concessionHolderCPF;
        this.creationDate = creationDate;
        this.expiringDate = expiringDate;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getConcessionHolderCPF() {
        return concessionHolderCPF;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }    
}
