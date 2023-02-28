package be.jyl.services;

import be.jyl.entities.Articles;
import be.jyl.entities.Rentals;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
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
    public void persistNewRental(Rentals rental, Articles articles){
        transaction.begin();
        log.log(Level.INFO, "persisteNewRental() : Start Transaction");
        log.log(Level.INFO, " rental.id_user : "+ rental.getUser().getIdUser());
        log.log(Level.INFO, " rental.dateBegin : "+ rental.getDateBegin());
        log.log(Level.INFO, " rental.dateEnd : "+ rental.getDateEnd());
        log.log(Level.INFO, " rental.id_userRental : "+ rental.getUserRent().getIdUser());
        log.log(Level.INFO, " rental.id_rental : "+ rental.getIdRental());

        log.log(Level.INFO, " Call em.persist ");

        em.persist(rental);

        transaction.commit();
    }
}
