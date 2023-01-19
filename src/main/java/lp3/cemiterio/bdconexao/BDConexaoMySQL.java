/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp3.cemiterio.bdconexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BDConexaoMySQL extends BDConexao {

    public BDConexaoMySQL(){ //Aqui coloca a configuração do seu banco de dados
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.porta = 3306;
        this.servidor = "localhost";
        this.bd = "cemiteriodescanseempaz";
        this.usuario = "root";
        this.senha = "aSenhaDoSeuBD";
    }
    
    @Override
    public Connection getConnection() { 
        
        try {
            Class.forName(driver);
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(getURL(), usuario, senha);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BDConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }

    @Override
    public String getURL() {
        
        return "jdbc:mysql://" + this.servidor + ":" + this.porta + "/" + this.bd
                + "?useTimezone=true&serverTimezone=UTC";
        
    }

}
