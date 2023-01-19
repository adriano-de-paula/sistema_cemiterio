/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.List;
import lp3.cemiterio.consts.DeceasedSituation;
import lp3.cemiterio.dao.DeceasedDAO;
import lp3.cemiterio.dao.GraveDAO;
import lp3.cemiterio.data.exceptions.ConnectionException;
import lp3.cemiterio.data.exceptions.UnableToDeleteDeceasedException;
import lp3.cemiterio.data.exceptions.UnableToDeleteException;
import lp3.cemiterio.data.exceptions.UnableToFetchDeceasedException;
import lp3.cemiterio.data.exceptions.UnableToFetchException;
import lp3.cemiterio.data.exceptions.UnableToReleaseDrawer;
import lp3.cemiterio.data.exceptions.UnableToUpdateDeceasedException;
import lp3.cemiterio.data.exceptions.UnableToUpdateGraveException;
import lp3.cemiterio.models.Deceased;
import lp3.cemiterio.models.Grave;

public class DeceasedService implements SearchableService<Deceased> {
    
    DeceasedDAO falecidoDAO;
    GraveDAO graveDAO;
    Grave grave;
    
    public DeceasedService() {
        this.falecidoDAO = new DeceasedDAO();
        this.graveDAO = new GraveDAO();
    }
    
    public List<Deceased> buscaFalecidos() throws UnableToFetchDeceasedException {
        try {
            return this.falecidoDAO.getDeceased();
        } catch (ConnectionException error) {
            throw new UnableToFetchDeceasedException();
        }
    }
    
    public List<Deceased> fetchDeceased(String filter, String value) throws UnableToFetchDeceasedException {
        try {
            return this.falecidoDAO.getDeceased(filter, value);
        } catch (ConnectionException error) {
            throw new UnableToFetchDeceasedException();
        }
    }
    
    
    public void buryDeceased(Deceased deceased) {
        try {
            this.graveDAO.fillDrawer(deceased.getIdGrave());
            this.falecidoDAO.registerDeceased(deceased);
        } catch (ConnectionException error) {
            System.out.println(error);
        }
    }
    
    public void updateDeceased(Deceased oldDeceased, Deceased updatedDeceased) throws UnableToUpdateDeceasedException{
        try {
            // Casos em que o cadastro foi feito errado e é necessário alterar
            // o jazigo em que o falecido foi cadastrado.
            // Não representa uma situação em que um falecido é efetivamente
            // transportado entre jazigos.
            if (oldDeceased.getIdGrave() != updatedDeceased.getIdGrave()) {
                if (oldDeceased.getSituacao() == DeceasedSituation.SEPULTADO) {
                    this.graveDAO.releaseDrawer(oldDeceased.getIdGrave());
                }
                
                // Um falecido exumado não ocupa uma gaveta, por isso só
                // preenche gaveta caso o falecido esteja sepultado.
                if (updatedDeceased.getSituacao() == DeceasedSituation.SEPULTADO) {
                    this.graveDAO.fillDrawer(updatedDeceased.getIdGrave());
                }
            } else {
                
                // Caso o jazigo continue o mesmo, mas houve alteração de
                // situação do falecido
                if (oldDeceased.getSituacao() != updatedDeceased.getSituacao()) {
                    if (updatedDeceased.getSituacao() == DeceasedSituation.EXUMADO) {
                        this.graveDAO.releaseDrawer(updatedDeceased.getIdGrave());
                    } else {
                        this.graveDAO.fillDrawer(updatedDeceased.getIdGrave());
                    }
                }
            }
            
            this.falecidoDAO.updateDeceased(updatedDeceased);
        } catch (ConnectionException error) {
            throw new UnableToUpdateDeceasedException();
        }
    }
    
    public void exumaFalecido(Deceased falecido) {
        try {
             this.falecidoDAO.exhumeDeceased(falecido.getId());
             this.graveDAO.releaseDrawer(falecido.getIdGrave());
        } catch (ConnectionException| UnableToUpdateGraveException error) {
            System.out.println(error);
        }
        
    }
    
    public void deleteDeceased(Deceased deceased) throws UnableToReleaseDrawer, UnableToDeleteDeceasedException {
        if (deceased.getSituacao() == DeceasedSituation.SEPULTADO) {
            try {
                this.graveDAO.releaseDrawer(deceased.getIdGrave());
            } catch (ConnectionException ex) {
                throw new UnableToReleaseDrawer();
            }
        }
        
        try {
            this.falecidoDAO.deleteDeceased(deceased.getId());
        } catch (ConnectionException ex) {
            throw new UnableToDeleteDeceasedException();
        }
    }

    @Override
    public List<Deceased> fetch() throws UnableToFetchException {
        try {
            return this.buscaFalecidos();
        } catch (UnableToFetchDeceasedException error) {
            throw new UnableToFetchException(error.toString());
        }
    } 

    @Override
    public List<Deceased> fetch(String filter, String value) throws UnableToFetchException {
        try {
            return this.fetchDeceased(filter, value);
        } catch (UnableToFetchDeceasedException error) {
            throw new UnableToFetchException(error.toString());
        }
    }
    
    @Override
    public void delete(Deceased deceased) throws UnableToDeleteException {
        try {
            this.deleteDeceased(deceased);
        } catch (UnableToReleaseDrawer | UnableToDeleteDeceasedException error) {
            throw new UnableToDeleteException(error.toString());
        }
    }
}
