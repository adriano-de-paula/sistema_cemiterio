/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp3.cemiterio.cemiterio;


import com.lp3.cemiterio.utils.Alerts;
import com.lp3.cemiterio.utils.ScreenNavigationHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lp3.cemiterio.data.exceptions.UnableToGetUserException;
import lp3.cemiterio.data.exceptions.UserNotFoundException;
import lp3.cemiterio.services.LoginService;


public class LoginController {
    
    final private LoginService loginService;
    
    @FXML
    private TextField txfLogin;
    @FXML
    private PasswordField txfPassword;
    
    
    public LoginController() {
        this.loginService = LoginService.getInstance();
    }
    
    @FXML
    private void login() throws UnableToGetUserException {
        String email = this.txfLogin.getText();
        String password = this.txfPassword.getText();
        
        if (validateLogin(email, password)) {
            try {
                this.loginService.login(email, password);
                this.clearAllFields();
                this.goToMenu();
            } catch (UserNotFoundException | UnableToGetUserException error) {
                Alerts.showError(error.toString());
            }
        } else {
            Alerts.showError("Todos os campos devem estar preenchidos e a senha deve possuir mais do que 3 caracteres!!");
        }
    }
    
    private void clearAllFields() {
        this.txfLogin.clear();
        this.txfPassword.clear();
    }
    
    private void goToMenu() {
        ScreenNavigationHelper.openApplicationModal("scrMenu", "Cemitério Descanse em Paz - Menu");
    }
    
    private boolean validateLogin(String email, String password) {
        return (email != null && password != null && password.length() > 3);
    }
    
    @FXML
    private void close() {
        Platform.exit();
    }
}
