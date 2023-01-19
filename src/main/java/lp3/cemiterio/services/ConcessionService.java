/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.dao.ConcessionDAO;
import lp3.cemiterio.dao.DeceasedDAO;
import lp3.cemiterio.dao.GraveDAO;
import lp3.cemiterio.data.exceptions.ConcessionOccupiedException;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.UnableToCreateConcessionException;
import lp3.cemiterio.models.Concession;

public class ConcessionService {
    GraveDAO jazigoDAO;
    ConcessionDAO concessaoDAO;
    DeceasedDAO falecidoDAO;
    
    public ConcessionService() {
        this.jazigoDAO = new GraveDAO();
        this.concessaoDAO = new ConcessionDAO();
        this.falecidoDAO = new DeceasedDAO();
    }
    
    public void criaConcessao(Concession concessao) throws UnableToCreateConcessionException {
        try {
            this.concessaoDAO.createConcession(concessao);
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToCreateConcessionException();
        }
    }
    
    public void deleteConcession(int idConcessao) throws ConcessionOccupiedException {
        try {
            // A concessão só pode ser apagada caso não existam
            // falecidos sem exumar nas gavetas.
            if (!this.falecidoDAO.getDeceasedByGrave(idConcessao).isEmpty()) {
                throw new ConcessionOccupiedException();
            } else {
                this.concessaoDAO.deleteConcession(idConcessao);
               
             
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConcessionOccupiedException();
        }
    }
}
