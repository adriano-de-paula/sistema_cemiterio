/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.consts.Permission;
import lp3.cemiterio.dao.LoginDAO;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.UnableToGetUserException;
import lp3.cemiterio.data.exceptions.UserNotFoundException;
import lp3.cemiterio.data.exceptions.WrongPasswordException;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.models.User;

public class LoginService {
    
    private static LoginService instance = null;
    
    private boolean isLoggedIn = false;
    private User user;
    private LoginDAO loginDAO;
    
    private LoginService(){
        this.loginDAO = new LoginDAO();
    }
    
    // Este service só será criado uma vez
    public static LoginService getInstance()
    {
        if (instance == null) {
            instance = new LoginService();
        }
  
        return instance;
    }
    
    public void login(String email, String password) throws UserNotFoundException, UnableToGetUserException {
        try {
            User user = this.loginDAO.login(email, password);
            if (user != null) {
                this.user = user;
                this.isLoggedIn = true;
            } else {
                throw new UserNotFoundException();
            }

        } catch (ConnectionException ex) {
            throw new UnableToGetUserException();
        }
    }
    
    public void logout() {
        this.user = null;
        this.isLoggedIn = false;
    }
    
    public boolean estaLogado() {
        return this.isLoggedIn;
    }
    
    public User getUser() {
        return this.user;
    }
}
