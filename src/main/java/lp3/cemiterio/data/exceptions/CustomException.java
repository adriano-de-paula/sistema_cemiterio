/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.data.exceptions;

public class CustomException extends Exception {
    CustomException(String message) {
        super(message);
    }
    
    @Override
    public String toString() {
        return super.getMessage();
    }
}


