/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import lp3.cemiterio.consts.DeceasedSituation;
import lp3.cemiterio.consts.Gender;

public final class Deceased {
    
    private int idDeceased;
    private String name;
    private Gender gender;
    private final DeceasedSituation situacao;
    private final Date birthDate;
    private final Date deathDate;
    private final int idGrave;

    public Deceased(
            int idDeceased,
            String name, 
            Gender gender, 
            Date birthDate, 
            DeceasedSituation situacao, 
            Date deathDate,
            int idGrave)
    {
        this.name = name;
        this.gender = gender;
        this.situacao = situacao;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.idGrave = idGrave;
        this.idDeceased = idDeceased;
    }
    
    public Deceased(
            String name, 
            Gender gender, 
            Date birthDate, 
            DeceasedSituation situacao, 
            Date deathDate,
            int idGrave)
    {
        this.name = name;
        this.gender = gender;
        this.situacao = situacao;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.idGrave = idGrave;
    }

    public int getId() {
        return this.idDeceased;
    }
    
    public int getIdGrave() {
        return this.idGrave;
    }

    public int getIdDeceased() {
        return idDeceased;
    }

    public DeceasedSituation getSituacao() {
        return situacao;
    }

    public Date getDeathDate() {
        return deathDate;
    }
    
    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }


    public static FieldTitleName[] getProperties() {
        FieldTitleName[] fields = {
            new FieldTitleName("Nome", "name", "nome"),
            new FieldTitleName("Sexo", "gender", "sexo"),
            new FieldTitleName("Data de Nascimento", "birthDate", "DataNascimento"),
            new FieldTitleName("Data de Falecimento", "deathDate", "DataObito"),
            new FieldTitleName("Situação", "situacao", "Situacao")
            };
        return fields;
    }
    
    @Override
    public String toString() {
        return this.name+", "+this.gender.toString()+", "+this.birthDate.toString()+", "+this.deathDate.toString()+", "+this.situacao.toString();
    }

}
