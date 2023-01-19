/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.util.List;
import lp3.cemiterio.data.exceptions.UnableToDeleteException;
import lp3.cemiterio.data.exceptions.UnableToFetchException;

public interface SearchableService<T> {
    public List<T> fetch() throws UnableToFetchException;
    public List<T> fetch(String filter, String value) throws UnableToFetchException;
    public void delete(T value) throws UnableToDeleteException;
}
