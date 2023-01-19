/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lp3.cemiterio.dao.DeceasedDAO;
import lp3.cemiterio.dao.GraveDAO;
import lp3.cemiterio.data.exceptions.AlreadyRegisteredGraveException;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.ConstraintViolationException;
import lp3.cemiterio.data.exceptions.GraveHasDeceasedException;
import lp3.cemiterio.data.exceptions.InsufficientDrawersAmountException;
import lp3.cemiterio.data.exceptions.UnableToCreateGraveException;
import lp3.cemiterio.data.exceptions.UnableToDeleteException;
import lp3.cemiterio.data.exceptions.UnableToDeleteGraveException;
import lp3.cemiterio.data.exceptions.UnableToFetchException;
import lp3.cemiterio.data.exceptions.UnableToFetchGravesException;
import lp3.cemiterio.data.exceptions.UnableToUpdateGraveException;
import lp3.cemiterio.models.Grave;

public class GraveService implements SearchableService<Grave> {
    GraveDAO graveDAO;
    DeceasedDAO deceasedDAO;
    
    public GraveService() {
        this.graveDAO = new GraveDAO();
        this.deceasedDAO = new DeceasedDAO();
    }
    
    public List<Grave> fetchAvailableGraves() throws UnableToFetchGravesException {
        try {
            return this.graveDAO.getGravesWithConcession(true);
        } catch (ConnectionException error) {
            throw new UnableToFetchGravesException();
        }
    }
    
    public List<Grave> fetchGraves() throws UnableToFetchGravesException {
        try {
            return this.graveDAO.getGraves(false);
        } catch (ConnectionException error) {
            throw new UnableToFetchGravesException();
        }
    }
    
    public Grave getGrave(int id) throws UnableToFetchGravesException{
        try {
            return this.graveDAO.getGrave(id);
        } catch (ConnectionException ex) {
            Logger.getLogger(GraveService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToFetchGravesException();
        }
    }
    
    public List<Grave> fetchGraves(String filter, String value) throws UnableToFetchGravesException {
        try {
            return this.graveDAO.getGraves(filter, value);
        } catch (ConnectionException error) {
            throw new UnableToFetchGravesException();
        }
    }
    
    public void createGrave(Grave grave) throws UnableToCreateGraveException, AlreadyRegisteredGraveException {
        try {
            this.graveDAO.createGrave(grave);
        } catch (ConstraintViolationException ex) {
            throw new AlreadyRegisteredGraveException(grave.getQuadra(), grave.getSepultura());
        }catch (ConnectionException ex) {
            Logger.getLogger(GraveService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToCreateGraveException();
        }
    }
    
    public void updateGraveDrawers(int id, int drawersAmount) throws InsufficientDrawersAmountException, UnableToUpdateGraveException {
        try {
            var registeredDeceased = this.deceasedDAO.getDeceasedByGrave(id);
            if (registeredDeceased.size() > drawersAmount) {
                throw new InsufficientDrawersAmountException(registeredDeceased.size());
            } else {
                this.graveDAO.updateGraveDrawers(id, drawersAmount);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(GraveService.class.getName()).log(Level.SEVERE, null, ex);
            throw new UnableToUpdateGraveException();
        }
    }
    
    public void deleteGrave(Grave grave) throws GraveHasDeceasedException, UnableToDeleteGraveException {
        try {
            if (!this.deceasedDAO.getDeceasedByGrave(grave.getIdJazigo()).isEmpty()) {
                throw new GraveHasDeceasedException();
            } else {
                this.graveDAO.deleteGrave(grave.getIdJazigo());
            }
        } catch (ConstraintViolationException ex) {
            throw new GraveHasDeceasedException();
        } catch (ConnectionException ex) {
            throw new UnableToDeleteGraveException();
        }
    }

    @Override
    public List<Grave> fetch() throws UnableToFetchException {
        try {
            return this.fetchGraves();
        } catch (UnableToFetchGravesException error) {
            throw new UnableToFetchException(error.toString());
        }
    }

    @Override
    public List<Grave> fetch(String filter, String value) throws UnableToFetchException {
        try {
            return this.fetchGraves(filter, value);
        } catch (UnableToFetchGravesException error) {
            throw new UnableToFetchException(error.toString());
        }
    }

    @Override
    public void delete(Grave value) throws UnableToDeleteException {
        try {
            this.deleteGrave(value);
        } catch (GraveHasDeceasedException | UnableToDeleteGraveException ex) {
            throw new UnableToDeleteException(ex.toString());
        }
    }
}
