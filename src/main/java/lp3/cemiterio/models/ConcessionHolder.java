/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.util.Date;
import lp3.cemiterio.consts.Gender;
import lp3.cemiterio.consts.State;

public final class  ConcessionHolder extends Person {
    
    private String telephoneNumber, streetAdress, district, city;
    State state;

    public ConcessionHolder(
            String name, 
            String cpf, 
            Gender gender, 
            String telephoneNumber, 
            String streetAdress, 
            String district, 
            String city, 
            State state) {
        super(name, cpf, gender);
        this.telephoneNumber = telephoneNumber;
        this.streetAdress = streetAdress;
        this.district = district;
        this.city = city;
        this.state = state;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Gender getGender() {
        return gender;
    }
    
    public void setState(State state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setStreetAdress(String streetAdress){
        
        this.streetAdress = streetAdress;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public static FieldTitleName[] getProperties() {
        FieldTitleName[] fields = {
            new FieldTitleName("CPF", "cpf", "CPF"),
            new FieldTitleName("Nome", "name", "nome"),
            new FieldTitleName("Sexo", "gender", "sexo"),
            new FieldTitleName("Telefone", "telephoneNumber", "Telefone"),
            new FieldTitleName("Rua", "streetAdress", "EnderecoRua"),
            new FieldTitleName("Bairro", "district", "EnderecoBairro"),
            new FieldTitleName("Cidade", "city", "EnderecoCidade"),
            new FieldTitleName("Estado", "state", "EnderecoEstado")
            };
        return fields;
    }
    
}
