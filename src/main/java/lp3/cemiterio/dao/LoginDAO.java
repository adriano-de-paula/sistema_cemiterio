/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.consts.Permission;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.UserNotFoundException;
import lp3.cemiterio.models.User;

public class LoginDAO {
    public User login(String email, String password) throws ConnectionException {
        User user = null;
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM usuario WHERE email='"+email+"' AND senha = '"+password+"';";
            
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                user = new User(
                    rs.getInt("idUsuario"),
                    rs.getString("email"),
                    rs.getString("nome"),
                    Permission.valueOf(rs.getString("permissao").toUpperCase()));
            }
            
            return user;

        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }

                if (st != null) {
                    st.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
    }
}
