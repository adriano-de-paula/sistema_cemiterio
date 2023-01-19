/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

public class Grave {
    private int idJazigo;
    private Integer quadra;
    private Integer sepultura;
    private Integer gavetasDisponiveis;
    private boolean temConcessao;
    
    public Grave() {
        
    }

    public Grave(int idJazigo, int quadra, int sepultura, int gavetasDisponiveis, boolean temConcessao) {
        this.idJazigo = idJazigo;
        this.quadra = quadra;
        this.sepultura = sepultura;
        this.gavetasDisponiveis = gavetasDisponiveis;
        this.temConcessao = temConcessao;
    }

    public Grave(
            int quadra, 
            int sepultura, 
            int gavetasDisponiveis) {
        this.quadra = quadra;
        this.sepultura = sepultura;
        this.gavetasDisponiveis = gavetasDisponiveis;
        this.temConcessao = false;
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

    public void setTemConcessao(boolean temConcessao) {
        this.temConcessao = temConcessao;
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

    public boolean isTemConcessao() {
        return temConcessao;
    }    

    public void setIdJazigo(int idJazigo) {
        this.idJazigo = idJazigo;
    }
    
    public static FieldTitleName[] getProperties() {
        FieldTitleName[] fields = {
            new FieldTitleName("Quadra", "quadra", "Quadra"),
            new FieldTitleName("Sepultura", "sepultura", "Sepultura"),
            new FieldTitleName("Gavetas Disponíveis", "gavetasDisponiveis", "GavetasDisponiveis"),
            new FieldTitleName("Possui Concessão", "temConcessao", "TemConcessao")
            };
        return fields;
    }

}
