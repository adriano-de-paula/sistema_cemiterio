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
import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.bdconexao.BDFabricaConexao;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.ConstraintViolationException;
import lp3.cemiterio.models.Grave;
import lp3.cemiterio.services.GraveService;


public class GraveDAO {
        
    public List<Grave> getGraves(boolean onlyAvailable) throws ConnectionException {
        List<Grave> graves = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM jazigo";
            
            if (onlyAvailable) {
                sql += " WHERE GavetasDisponiveis > 0;";
            }
            
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Grave grave;
            while (rs.next()) {
                grave = new Grave(
                    rs.getInt("idJazigo"),
                    rs.getInt("quadra"),
                    rs.getInt("sepultura"),
                    rs.getInt("gavetasDisponiveis"),
                    rs.getBoolean("temConcessao"));
                graves.add(grave);
            }

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
        return graves;
    }
    
    public List<Grave> getGraves(String filter, String value) throws ConnectionException {
        List<Grave> graves = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM jazigo WHERE "+filter+" LIKE '%"+value+"%';";
            
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Grave grave;
            while (rs.next()) {
                grave = new Grave(
                    rs.getInt("idJazigo"),
                    rs.getInt("quadra"),
                    rs.getInt("sepultura"),
                    rs.getInt("gavetasDisponiveis"),
                    rs.getBoolean("temConcessao"));
                graves.add(grave);
            }

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
        return graves;
    }
    
    public List<Grave> getGravesWithConcession(boolean onlyAvailable) throws ConnectionException {
        List<Grave> graves = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM jazigo WHERE TemConcessao = true";
            
            if (onlyAvailable) {
                sql += " AND GavetasDisponiveis > 0;";
            }
            
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            Grave grave;
            while (rs.next()) {
                grave = new Grave(
                    rs.getInt("idJazigo"),
                    rs.getInt("quadra"),
                    rs.getInt("sepultura"),
                    rs.getInt("gavetasDisponiveis"),
                    rs.getBoolean("temConcessao"));
                graves.add(grave);
            }

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
        return graves;
    }
    
    public Grave getGrave(int id) throws ConnectionException {
        Grave grave = null;
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM jazigo WHERE idJazigo ="+id+";";
            
            System.out.println(sql);
            
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                grave = new Grave(
                    rs.getInt("idJazigo"),
                    rs.getInt("quadra"),
                    rs.getInt("sepultura"),
                    rs.getInt("gavetasDisponiveis"),
                    rs.getBoolean("temConcessao"));
            }
            
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
        return grave;
    }
    
    public boolean createGrave(Grave grave) throws ConnectionException, ConstraintViolationException {
        Connection con = null;
        boolean result;
        PreparedStatement insertGrave = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO Jazigo (Quadra, Sepultura, GavetasDisponiveis, TemConcessao)" +
                                    " VALUES ( ?, ?, ?, ?);";
            
            insertGrave = con.prepareStatement(sql);
            insertGrave.setInt(1, grave.getQuadra());
            insertGrave.setInt(2, grave.getSepultura());
            insertGrave.setInt(3, grave.getGavetasDisponiveis());
            insertGrave.setBoolean(4, grave.isTemConcessao());
            
            System.out.println(insertGrave.toString());
            
            result = insertGrave.execute();
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
                
                if (insertGrave != null) {
                    insertGrave.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public boolean updateGraveDrawers(int id, int drawersAmount) throws ConnectionException {
        Connection con = null;
        boolean result;
        PreparedStatement updateGrave = null;

        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "UPDATE Jazigo SET GavetasDisponiveis=? " +
                                    "WHERE idJazigo=?;";
            
            updateGrave = con.prepareStatement(sql);
            updateGrave.setInt(1, drawersAmount);
            updateGrave.setInt(2, id);
            
            System.out.println(updateGrave.toString());
            
            result = updateGrave.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (updateGrave != null) {
                    updateGrave.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return result;
    }
    
    public boolean fillDrawer(int idJazigo) throws ConnectionException {
        Connection con = null;
        Statement st = null;
        boolean result;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "UPDATE Jazigo SET GavetasDisponiveis = GavetasDisponiveis-1 WHERE idJazigo ="+idJazigo+" AND TemConcessao = true;";
            
            st = (Statement) con.createStatement();
            result = st.execute(sql);
            
            //System.out.println("O retorno do fillDrawer foi "+result);
            
            
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
    
    public boolean releaseDrawer(int idJazigo) throws ConnectionException {
        
        Connection con = null;
        Statement st = null;
        boolean result;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "UPDATE Jazigo SET GavetasDisponiveis = GavetasDisponiveis+1 WHERE idJazigo ="+idJazigo+" AND TemConcessao = true;";
            
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
    
    public void updateConcession(int id, boolean hasConcession) throws ConnectionException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "UPDATE Jazigo SET temConcessao = "+hasConcession+" WHERE idJazigo ="+id+";";
            
            st = (Statement) con.createStatement();
            st.execute(sql);
            
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
    }
    
    public void deleteGrave(int id) throws ConnectionException, ConstraintViolationException {
        Connection con = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "delete from jazigo where idJazigo = "+id+";";
            System.out.println(sql);
            st = (Statement) con.createStatement();
            st.execute(sql);
        } catch (SQLIntegrityConstraintViolationException ex) {
            Logger.getLogger(GraveService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConstraintViolationException();
        } catch (SQLException ex) {
            Logger.getLogger(GraveService.class.getName()).log(Level.SEVERE, null, ex);
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
