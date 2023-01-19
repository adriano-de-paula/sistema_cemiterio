/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class InsufficientDrawersAmountException extends CustomException {
    public InsufficientDrawersAmountException() {
        super("O número de gavetas fornecido é insuficiente!");
    }
    
    public InsufficientDrawersAmountException(int amount) {
        super("O número mínimo de gavetas é "+amount+"!");
    }
}
