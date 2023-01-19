/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.dao.ServiceOrderDAO;
import lp3.cemiterio.data.exceptions.ConcessionHolderNotFoundException;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.ConstraintViolationException;
import lp3.cemiterio.data.exceptions.UnableToCreateServiceOrderException;
import lp3.cemiterio.data.exceptions.UnableToFetchSOException;
import lp3.cemiterio.models.Service;
import lp3.cemiterio.models.ServiceOrder;

public class SOService {
    
    private ServiceOrderDAO serviceOrderDAO;
    
    public SOService() {
        this.serviceOrderDAO = new ServiceOrderDAO();
    }
    
    public List<ServiceOrder> fetchSOs() throws UnableToFetchSOException {
        try {
            return this.serviceOrderDAO.fetchServiceOrders();
        } catch (ConnectionException ex) {
            throw new UnableToFetchSOException();
        }
    }
    
    public Integer createSO(ServiceOrder so, List<Service> services) throws UnableToCreateServiceOrderException, ConcessionHolderNotFoundException {
        Integer soId = null;
        try {
            soId = this.serviceOrderDAO.createServiceOrder(so);
            for (Service s: services) {
                this.serviceOrderDAO.addServiceToOrder(s, soId);
            }
        } catch (ConnectionException ex) {
            throw new UnableToCreateServiceOrderException();
        } catch (ConstraintViolationException ex) {
            throw new ConcessionHolderNotFoundException();
        }
        return soId;
    }
}
