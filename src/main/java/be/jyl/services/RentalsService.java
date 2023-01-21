package be.jyl.services;

import be.jyl.entities.Rentals;
import be.jyl.tools.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class RentalsService {
    private Logger log = Logger.getLogger(RentalsService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();
    public List<Rentals> rentalsList(){
        Query query = em.createNamedQuery("Rentals.findAll");
        return query.getResultList();
    }
}
