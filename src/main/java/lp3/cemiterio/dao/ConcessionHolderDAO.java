/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.consts.Gender;
import lp3.cemiterio.consts.State;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.ConstraintViolationException;
import lp3.cemiterio.models.ConcessionHolder;


public class ConcessionHolderDAO {
    
    public List<ConcessionHolder> getConcessionHolders() throws ConnectionException {
        List<ConcessionHolder> concessionarios = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM concessionario;";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            ConcessionHolder concessionario;
            while (rs.next()) {
                concessionario = new ConcessionHolder(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getString("telefone"),
                    rs.getString("enderecorua"),
                    rs.getString("enderecobairro"),
                    rs.getString("enderecocidade"),
                    State.valueOf(rs.getString("enderecoestado").toUpperCase()));
                concessionarios.add(concessionario);
            }

        } catch (SQLException ex) {
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
                System.out.println("Erro no SQL 2");
            }
        }
        return concessionarios;
    }
    
    public List<ConcessionHolder> getConcessionHolders(String name) throws ConnectionException {
        List<ConcessionHolder> concessionarios = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM concessionario WHERE nome LIKE '%"+name+"%';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            ConcessionHolder concessionHolder;
            while (rs.next()) {
                concessionHolder = new ConcessionHolder(
                    rs.getString("nome"),
                    rs.getString("CPF"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getString("telefone"),
                    rs.getString("EnderecoRua"),
                    rs.getString("EnderecoBairro"),
                    rs.getString("EnderecoCidade"),
                    State.valueOf(rs.getString("EnderecoEstado").toUpperCase()));
                concessionarios.add(concessionHolder);
            }

        } catch (SQLException ex) {
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
                System.out.println("Erro no SQL 2");
            }
        }
        return concessionarios;
    }
    
    public List<ConcessionHolder> getConcessionHolders(String filter, String value) throws ConnectionException {
        List<ConcessionHolder> concessionarios = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM concessionario WHERE "+filter+" LIKE '%"+value+"%';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            ConcessionHolder concessionHolder;
            while (rs.next()) {
                concessionHolder = new ConcessionHolder(
                    rs.getString("nome"),
                    rs.getString("CPF"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getString("telefone"),
                    rs.getString("EnderecoRua"),
                    rs.getString("EnderecoBairro"),
                    rs.getString("EnderecoCidade"),
                    State.valueOf(rs.getString("EnderecoEstado").toUpperCase()));
                concessionarios.add(concessionHolder);
            }

        } catch (SQLException ex) {
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
                System.out.println("Erro no SQL 2");
            }
        }
        return concessionarios;
    }
    
    public boolean createConcessionHolder(ConcessionHolder concessionHolder) 
            throws ConnectionException, ConstraintViolationException {
        Connection con = null;
        boolean result;
        PreparedStatement insertConcessionHolder = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO Concessionario " +
                                    "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
            insertConcessionHolder = con.prepareStatement(sql);
            insertConcessionHolder.setString(1, concessionHolder.getCpf());
            insertConcessionHolder.setString(2, concessionHolder.getName());
            insertConcessionHolder.setString(3, concessionHolder.getGender().toString());
            insertConcessionHolder.setString(4, concessionHolder.getTelephoneNumber());
            insertConcessionHolder.setString(5, concessionHolder.getStreetAdress());
            insertConcessionHolder.setString(6, concessionHolder.getDistrict());
            insertConcessionHolder.setString(7, concessionHolder.getCity());
            insertConcessionHolder.setString(8, concessionHolder.getState().toString());
            
            System.out.println(insertConcessionHolder.toString());
            
            result = insertConcessionHolder.execute();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new ConstraintViolationException();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (insertConcessionHolder != null) {
                    insertConcessionHolder.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public void deleteConcessionHolder(String cpf) throws ConnectionException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "DELETE FROM concessionario WHERE cpf='"+cpf+"';";
            st = (Statement) con.createStatement();
            st.execute(sql);
        } catch (SQLException ex) {
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }

                if (st != null) {
                    st.close();
                }

            } catch (SQLException ex) {
                System.out.println("Erro no SQL 2");
            }
        }
    }
    
    public void updateConcessionHolder(ConcessionHolder concessionHolder) throws ConnectionException {
        
    }
    
    public void notifyConcessionHolder() {
        
    }
}
