/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

import java.util.Date;
import lp3.cemiterio.consts.Situacao;

public class SituacaoConcessao {
    private Situacao situacao;
    private Date data;
    
    SituacaoConcessao(
       Situacao situacao,
       Date data
    ) {
        this.situacao = situacao;
        this.data = data;
    }
}
