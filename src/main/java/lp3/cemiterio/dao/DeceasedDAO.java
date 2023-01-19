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
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.consts.DeceasedSituation;
import lp3.cemiterio.consts.Gender;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.UnableToUpdateGraveException;
import lp3.cemiterio.models.Deceased;

public class DeceasedDAO {
    
    public List<Deceased> getDeceased() throws ConnectionException {
        List<Deceased> falecidos = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM falecido;";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Deceased falecido;
            while (rs.next()) {
                falecido = new Deceased(
                    rs.getInt("idFalecido"),
                    rs.getString("nome"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getDate("dataNascimento"),
                    DeceasedSituation.valueOf(rs.getString("situacao").toUpperCase()),
                    rs.getDate("dataObito"),
                    rs.getInt("FK_IdJazigo"));
                falecidos.add(falecido);
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
        return falecidos;
    }
    
    public List<Deceased> getDeceased(String nome) throws ConnectionException {
        List<Deceased> falecidos = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM falecido WHERE nome LIKE '%"+nome+"%';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Deceased falecido;
            while (rs.next()) {
                falecido = new Deceased(
                    rs.getInt("idFalecido"),
                    rs.getString("nome"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getDate("dataNascimento"),
                    DeceasedSituation.valueOf(rs.getString("situacao").toUpperCase()),
                    rs.getDate("dataObito"),
                    rs.getInt("FK_IdJazigo"));
                falecidos.add(falecido);
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
        return falecidos;
    }
    
    public List<Deceased> getDeceased(String filter, String value) throws ConnectionException {
        List<Deceased> falecidos = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM falecido WHERE "+filter+" LIKE '%"+value+"%';";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Deceased falecido;
            while (rs.next()) {
                falecido = new Deceased(
                    rs.getInt("idFalecido"),
                    rs.getString("nome"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getDate("dataNascimento"),
                    DeceasedSituation.valueOf(rs.getString("situacao").toUpperCase()),
                    rs.getDate("dataObito"),
                    rs.getInt("FK_IdJazigo"));
                falecidos.add(falecido);
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
        return falecidos;
    }
    
    public boolean registerDeceased(Deceased deceased) throws ConnectionException {
        Connection con = null;
        boolean result;
        PreparedStatement insertDeceased = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO Falecido (Nome, Sexo, Situacao, DataNascimento, DataObito, FK_IdJazigo)" +
                                    "VALUES ( ?, ?, ?, ?, ?, ?);";
            insertDeceased = con.prepareStatement(sql);
            insertDeceased.setString(1, deceased.getName());
            insertDeceased.setString(2, deceased.getGender().toString().toLowerCase());
            insertDeceased.setString(3, deceased.getSituacao().toString().toLowerCase());
            insertDeceased.setDate(4, new java.sql.Date(deceased.getBirthDate().getTime()));
            insertDeceased.setDate(5, new java.sql.Date(deceased.getDeathDate().getTime()));
            insertDeceased.setInt(6, deceased.getIdGrave());
            
            System.out.println(insertDeceased.toString());
            
            result = insertDeceased.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (insertDeceased != null) {
                    insertDeceased.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public boolean updateDeceased(Deceased deceased) throws ConnectionException {
        Connection con = null;
        boolean result;
        PreparedStatement updateDeceased = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "UPDATE Falecido SET Nome=?, Sexo=?, Situacao=?, DataNascimento=?, DataObito=?, FK_IdJazigo=? " +
                                    "WHERE idFalecido="+deceased.getIdDeceased()+";";
            updateDeceased = con.prepareStatement(sql);
            updateDeceased.setString(1, deceased.getName());
            updateDeceased.setString(2, deceased.getGender().toString().toLowerCase());
            updateDeceased.setString(3, deceased.getSituacao().toString().toLowerCase());
            updateDeceased.setDate(4, new java.sql.Date(deceased.getBirthDate().getTime()));
            updateDeceased.setDate(5, new java.sql.Date(deceased.getDeathDate().getTime()));
            updateDeceased.setInt(6, deceased.getIdGrave());
            
            System.out.println(updateDeceased.toString());
            
            result = updateDeceased.execute();           
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (updateDeceased != null) {
                    updateDeceased.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public boolean exhumeDeceased(int idFalecido) throws UnableToUpdateGraveException, ConnectionException {
        Connection con = null;
        boolean result;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "UPDATE Falecido SET Situacao = 'Exumado' WHERE idFalecido = "+idFalecido+";";
            st = (Statement) con.createStatement();
            result = st.execute(sql);
            
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
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public List<Deceased> getDeceasedByGrave(int idJazigo) throws ConnectionException {
        List<Deceased> falecidos = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM falecido WHERE FK_IdJazigo = "+idJazigo+";";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Deceased falecido;
            while (rs.next()) {
                falecido = new Deceased(
                    rs.getInt("idFalecido"),
                    rs.getString("nome"),
                    Gender.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getDate("dataNascimento"),
                    DeceasedSituation.valueOf(rs.getString("situacao").toUpperCase()),
                    rs.getDate("dataObito"),
                    rs.getInt("FK_IdJazigo"));
                falecidos.add(falecido);
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
        return falecidos;
    }
    
    public void deleteDeceased(int id) throws ConnectionException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "DELETE FROM falecido WHERE idFalecido="+id+";";
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
}
