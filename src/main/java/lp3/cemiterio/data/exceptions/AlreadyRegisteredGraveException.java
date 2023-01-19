/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class AlreadyRegisteredGraveException extends CustomException {
    public AlreadyRegisteredGraveException(int square, int grave) {
        super("O jazigo com quadra "+square+" e sepultura "+grave+" já está cadastrado!!");
    }
}
