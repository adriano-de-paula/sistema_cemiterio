/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import lp3.cemiterio.consts.ConcessionType;

public class Concession {
    private int id;
    private final String cpf;
    private final int graveId;
    private final ConcessionType type;

    public Concession(
            int id, 
            String cpf, 
            int graveId, 
            ConcessionType type
    ) {
        this.id = id;
        this.cpf = cpf;
        this.graveId = graveId;
        this.type = type;
    }
    
    public Concession(
            String cpf, 
            int graveId, 
            ConcessionType type
    ) {
        this.cpf = cpf;
        this.graveId = graveId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public int getGraveId() {
        return graveId;
    }

    public ConcessionType getType() {
        return type;
    }    
}
