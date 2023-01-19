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
import lp3.cemiterio.models.Service;
import lp3.cemiterio.models.ServiceOrder;
import lp3.cemiterio.services.SOService;

public class ServiceOrderDAO {
    public List<ServiceOrder> fetchServiceOrders() throws ConnectionException {
        List<ServiceOrder> serviceOrders = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        Statement st = null;

        try {
            con = BDFabricaConexao.getConnection();
            String sql = "SELECT * FROM ordemservico;";
            st = (Statement) con.createStatement();
            rs = st.executeQuery(sql);

            ServiceOrder so;
            while (rs.next()) {
                so = new ServiceOrder(
                    rs.getInt("idOrdemServico"),
                    rs.getString("cpfconcessionario_FK"),
                    rs.getDate("dataCriacao"),
                    rs.getDate("dataVencimento"),
                    rs.getDouble("total"));
                serviceOrders.add(so);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SOService.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SOService.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return serviceOrders;
    }
    
    public int createServiceOrder(ServiceOrder so) throws ConnectionException, ConstraintViolationException {
        Connection con = null;
        PreparedStatement insertSO = null;
        ResultSet rs = null;
        int soKey = -1;
        
        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO ordemservico (cpfconcessionario_fk, datacriacao, datavencimento, total)" +
                                    "VALUES ( ?, ?, ?, ?);";
            insertSO = con.prepareStatement(sql);
            insertSO.setString(1, so.getConcessionHolderCPF());
            insertSO.setDate(2, new java.sql.Date(so.getCreationDate().getTime()));
            insertSO.setDate(3, new java.sql.Date(so.getExpiringDate().getTime()));
            insertSO.setDouble(4, so.getTotal());
            
            System.out.println(insertSO.toString());
            
            insertSO.execute();
            
            Statement stmt = con.createStatement();
            
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM ordemservico");
            if (rs.next()) {
                soKey = rs.getInt(1);
            }
           
        } catch (SQLIntegrityConstraintViolationException ex) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConstraintViolationException();
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (insertSO != null) {
                    insertSO.close();
                }
                
                if (rs != null) {
                    rs.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro no SQL 2");
            }
        }
        return soKey;
    }
    
    public void addServiceToOrder(Service service, int idSO) throws ConnectionException {
        Connection con = null;
        PreparedStatement insertSO = null;
        
        try {
            con = BDFabricaConexao.getConnection();
            
            String sql = "INSERT INTO tiposervico (idordemservico_fk, tiposervico, valorservico, quantidadeservico)" +
                                    "VALUES ( ?, ?, ?, ?);";
            insertSO = con.prepareStatement(sql);
            insertSO.setInt(1, idSO);
            insertSO.setString(2, service.getServiceName());
            insertSO.setDouble(3, service.getValue());
            insertSO.setDouble(4, service.getAmount());
            
            System.out.println(insertSO.toString());
            
            insertSO.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConnectionException();
        } finally {

            try {
                if (con != null) {
                    con.close();
                }
                
                if (insertSO != null) {
                    insertSO.close();
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Erro no SQL 2");
            }
        }
    }
}
