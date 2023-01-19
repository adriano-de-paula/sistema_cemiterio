/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp3.cemiterio.bdconexao;

import java.sql.Connection;

public class BDFabricaConexao {
    
    public static Connection getConnection(){
        return new BDConexaoMySQL().getConnection();
    }
    
}
