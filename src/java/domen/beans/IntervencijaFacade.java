/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen.beans;

import domen.Intervencija;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Djox
 */
@Stateless
public class IntervencijaFacade extends AbstractFacade<Intervencija> {

    @PersistenceContext(unitName = "diplomski3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IntervencijaFacade() {
        super(Intervencija.class);
    }
    
}
