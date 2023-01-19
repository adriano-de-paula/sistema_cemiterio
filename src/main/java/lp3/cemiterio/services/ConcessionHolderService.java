/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.dao.ConcessionDAO;
import lp3.cemiterio.dao.ConcessionHolderDAO;
import lp3.cemiterio.dao.DeceasedDAO;
import lp3.cemiterio.dao.GraveDAO;
import lp3.cemiterio.data.exceptions.CPFAlreadyInUseException;
import lp3.cemiterio.data.exceptions.ConcessionHolderHasConcessionException;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.ConstraintViolationException;
import lp3.cemiterio.data.exceptions.InvalidFieldException;
import lp3.cemiterio.data.exceptions.UnableToCreateConcessionHolderException;
import lp3.cemiterio.data.exceptions.UnableToDeleteConcessionException;
import lp3.cemiterio.data.exceptions.UnableToDeleteException;
import lp3.cemiterio.data.exceptions.UnableToFetchConcessionHoldersException;
import lp3.cemiterio.data.exceptions.UnableToFetchConcessionsException;
import lp3.cemiterio.data.exceptions.UnableToFetchException;
import lp3.cemiterio.data.exceptions.UnableToUpdateConcessionHolderException;
import lp3.cemiterio.models.Concession;
import lp3.cemiterio.models.ConcessionGrave;
import lp3.cemiterio.models.ConcessionHolder;

public class ConcessionHolderService implements SearchableService<ConcessionHolder> {
    
    ConcessionHolderDAO concessionHolderDAO;
    ConcessionDAO concessionDAO;
    DeceasedDAO deceasedDAO;
    GraveDAO graveDAO;
    
    public ConcessionHolderService() {
        this.concessionHolderDAO = new ConcessionHolderDAO();
        this.concessionDAO = new ConcessionDAO();
        this.deceasedDAO = new DeceasedDAO();
        this.graveDAO = new GraveDAO();
    }
    
    public List<ConcessionHolder> getConcessionHolders() throws UnableToFetchConcessionHoldersException {
        try {
            return this.concessionHolderDAO.getConcessionHolders();
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToFetchConcessionHoldersException();
        }
    }
    
    public List<ConcessionHolder> getConcessionHolders(String field, String value) throws UnableToFetchConcessionHoldersException, InvalidFieldException {
        if(field != null && !field.isEmpty()){
            try {
                return this.concessionHolderDAO.getConcessionHolders(field, value);
            } catch (ConnectionException ex) {
                Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
                throw new UnableToFetchConcessionHoldersException();
            }
        }
        else{
            throw new InvalidFieldException();
        }
    }
    
    public void deleteConcessionHolder(String cpf) throws UnableToFetchConcessionsException, ConcessionHolderHasConcessionException, UnableToDeleteConcessionException{
        try {
            List<Concession> concessions = this.concessionDAO.getConcessions(cpf);
            if (concessions != null && !concessions.isEmpty()) {
                for (Concession concession : concessions) {
                    if (!this.deceasedDAO.getDeceasedByGrave(concession.getGraveId()).isEmpty()) {
                        throw new ConcessionHolderHasConcessionException();
                    }
                }
                
                try {
                    this.concessionDAO.deleteConcessionByCPF(cpf);
                } catch (ConnectionException ex) {
                    throw new UnableToDeleteConcessionException();
                }
            }
            
            this.concessionHolderDAO.deleteConcessionHolder(cpf);
            
        } catch (ConnectionException ex) {
            throw new UnableToFetchConcessionsException();
        }
        
    }
    
    public void createConcessionHolder(ConcessionHolder concessionHolder)
             throws UnableToCreateConcessionHolderException, CPFAlreadyInUseException {
        try {
            this.concessionHolderDAO.createConcessionHolder(concessionHolder);
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToCreateConcessionHolderException();
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CPFAlreadyInUseException();
        }
    }
    
    public void createConcessionHolderWithConcession(
            ConcessionHolder concessionHolder, 
            List<Concession> concessions) 
            throws UnableToCreateConcessionHolderException, CPFAlreadyInUseException {
        try {
            this.concessionHolderDAO.createConcessionHolder(concessionHolder);
            for (Concession concession : concessions) {
                this.concessionDAO.createConcession(concession);
                this.graveDAO.updateConcession(concession.getGraveId(), true);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToCreateConcessionHolderException();
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new CPFAlreadyInUseException();
        }
    }

    public List<ConcessionGrave> getConcessionHolderConcessions(String cpf) 
            throws UnableToFetchConcessionsException{
        try {
            return this.concessionDAO.getConcessionsGraves(cpf);
        } catch (ConnectionException ex) {
            throw new UnableToFetchConcessionsException();
        }
    }
    
    public void updateConcessionHolderWithConcessions(
            ConcessionHolder concessionHolder, 
            List<Concession> concessions) 
            throws UnableToUpdateConcessionHolderException {
        try {
            this.concessionHolderDAO.updateConcessionHolder(concessionHolder);
            for (Concession concession : concessions) {
                this.concessionDAO.createConcession(concession);
                this.graveDAO.updateConcession(concession.getGraveId(), true);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(ConcessionHolderService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToUpdateConcessionHolderException();
        }
    }
    
    @Override
    public List<ConcessionHolder> fetch() throws UnableToFetchException {
        try {
            return this.getConcessionHolders(); 
        } catch (UnableToFetchConcessionHoldersException ex) {
            throw new UnableToFetchException(ex.toString());
        }
    }

    @Override
    public List<ConcessionHolder> fetch(String filter, String value) throws UnableToFetchException {
        try {
            return this.getConcessionHolders(filter, value);
        } catch (UnableToFetchConcessionHoldersException | InvalidFieldException ex) {
            throw new UnableToFetchException(ex.toString());
        }
    }

    @Override
    public void delete(ConcessionHolder value) throws UnableToDeleteException {
        try {
            this.deleteConcessionHolder(value.getCpf());
        } catch (UnableToFetchConcessionsException | ConcessionHolderHasConcessionException | UnableToDeleteConcessionException ex) {
            throw new UnableToDeleteException(ex.toString());
        }
    }
    
}
