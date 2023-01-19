/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.consts.ConcessionType;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.models.Concession;
import lp3.cemiterio.models.ConcessionGrave;

public class ConcessionDAO {
    public List<Concession> getConcessions(String cpf) throws ConnectionException {
        List<Concession> concessions = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM concessao WHERE cpfconcessionario_fk='"+cpf+"';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Concession concession;
            while (rs.next()) {
                concession = new Concession(
                    rs.getInt("idConcessao"),
                    rs.getString("cpfconcessionario_fk"),
                    rs.getInt("idJazigo_fk"),
                    ConcessionType.valueOf(rs.getString("tipo").toUpperCase())
                    );
                concessions.add(concession);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConcessionDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return concessions;
    }
    
    public List<ConcessionGrave> getConcessionsGraves(String cpf) throws ConnectionException {
        List<ConcessionGrave> concessions = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "select concessao.*, jazigo.* from jazigo inner "
                    + "join concessao on jazigo.IdJazigo = concessao.IdJazigo_FK"
                    + " where concessao.CPFConcessionario_FK = '"+cpf+"';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            ConcessionGrave concession;
            while (rs.next()) {
                concession = new ConcessionGrave(
                    rs.getInt("idConcessao"),
                    rs.getInt("idJazigo_fk"),
                    rs.getInt("quadra"),
                    rs.getInt("sepultura"),
                    rs.getInt("gavetasDisponiveis"),
                    ConcessionType.valueOf(rs.getString("tipo").toUpperCase()),
                    rs.getBoolean("temConcessao")
                );
                concessions.add(concession);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConcessionDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return concessions;
    }
    
    public void createConcession(Concession concession) throws ConnectionException {
        Connection con = null;
        PreparedStatement insertConcession = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO Concessao (IdJazigo_FK, CPFConcessionario_FK, Tipo) " +
                                    "VALUES ( ?, ?, ?);";
            
            insertConcession = con.prepareStatement(sql);
            insertConcession.setInt(1, concession.getGraveId());
            insertConcession.setString(2, concession.getCpf());
            insertConcession.setString(3, concession.getType().toString());
            
            System.out.println(insertConcession.toString());
            
            insertConcession.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (insertConcession != null) {
                    insertConcession.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
    }
    
    public void deleteConcessionByCPF(String cpf) throws ConnectionException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "DELETE FROM concessao WHERE cpf="+cpf+";";
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
    
    public void deleteConcession(int idConcession) throws ConnectionException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "DELETE FROM concessao WHERE idConcessao="+idConcession+";";
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
    
    public void editaConcessao(Concession concessao) {
        
    }
}
