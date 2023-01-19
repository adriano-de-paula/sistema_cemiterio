/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class UnavailableDrawerException extends CustomException {
    public UnavailableDrawerException(int quadra, int sepultura) {
        super("O jazigo da quadra "+quadra+", sepultura "+sepultura+" não possui mais gavetas disponíveis.");
    }
}
