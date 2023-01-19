/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.time.LocalDate;
import java.util.Date;
import lp3.cemiterio.consts.Gender;

public class Person {
    
    protected String name;
    protected final String cpf;
    protected Gender gender;
    protected Date birthDate;
    
    public Person(String name, String cpf, Gender gender) {
        this.name = name;
        this.cpf = cpf;
        this.gender = gender;
    }
    
    public Person(
            String name, 
            Date birthDate, 
            Gender gender
    ) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cpf = null;
    }
    
    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDataNascimento() {
        return birthDate;
    }
    
    public Gender getGender(){
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setGender(Gender gender){
        this.gender = gender;
    }
    
}

    
