/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class ConcessionHolderHasConcessionException extends CustomException {
    public ConcessionHolderHasConcessionException() {
        super("O concessionário possui concessões associadas!!");
    }
}
