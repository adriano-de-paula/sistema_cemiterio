/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class ConcessionHolderNotFoundException extends CustomException {
    public ConcessionHolderNotFoundException() {
        super("Este concessionário não está cadastrado!!");
    }
}
