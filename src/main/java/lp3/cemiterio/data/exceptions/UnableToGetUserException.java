/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class UnableToGetUserException extends CustomException {
    public UnableToGetUserException() {
        super("Não foi possível buscar o usuário. Tente novamente.");
    }
}
