/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import lp3.cemiterio.consts.ConcessionType;

public class ConcessionGrave {
    
    private int idConcessao;
    private int idJazigo;
    private Integer quadra;
    private Integer sepultura;
    private Integer gavetasDisponiveis;
    private ConcessionType type;
    private boolean temConcessao;
    
    public ConcessionGrave() {
        
    }

    public ConcessionGrave(
            int idConcessao,
            int idJazigo, 
            Integer quadra, 
            Integer sepultura, 
            Integer gavetasDisponiveis, 
            ConcessionType type,
            boolean temConcessao
    ) {
        this.idConcessao = idConcessao;
        this.idJazigo = idJazigo;
        this.quadra = quadra;
        this.sepultura = sepultura;
        this.gavetasDisponiveis = gavetasDisponiveis;
        this.type = type;
        this.temConcessao = temConcessao;
    }
    
    public int getIdConcessao() {
        return idConcessao;
    }

    public int getIdJazigo() {
        return idJazigo;
    }

    public Integer getQuadra() {
        return quadra;
    }

    public Integer getSepultura() {
        return sepultura;
    }

    public Integer getGavetasDisponiveis() {
        return gavetasDisponiveis;
    }

    public ConcessionType getType() {
        return type;
    }

    public void setIdJazigo(int idJazigo) {
        this.idJazigo = idJazigo;
    }

    public void setQuadra(Integer quadra) {
        this.quadra = quadra;
    }

    public void setSepultura(Integer sepultura) {
        this.sepultura = sepultura;
    }

    public void setGavetasDisponiveis(Integer gavetasDisponiveis) {
        this.gavetasDisponiveis = gavetasDisponiveis;
    }

    public void setType(ConcessionType type) {
        this.type = type;
    }

    public boolean isTemConcessao() {
        return temConcessao;
    }

    public void setTemConcessao(boolean temConcessao) {
        this.temConcessao = temConcessao;
    }
   
}
