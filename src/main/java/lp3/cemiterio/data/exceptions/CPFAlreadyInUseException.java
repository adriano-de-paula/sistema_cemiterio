/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class CPFAlreadyInUseException extends CustomException {
    public CPFAlreadyInUseException() {
        super("Já existe um concessionário csdastrado com este CPF!!");
    }
}
