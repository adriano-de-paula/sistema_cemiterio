/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lp3.cemiterio.bdconexao;

import java.sql.Connection;

public abstract class BDConexao {
    
    protected Connection con;
    protected String servidor;
    protected String bd;
    protected String usuario;
    protected String senha;
    protected String driver;
    protected int porta;
    
    public abstract Connection getConnection();
    
    public abstract String getURL();
    
}
