package be.jyl.services;

import be.jyl.entities.Articles;
import be.jyl.entities.ArticlesRentals;
import be.jyl.entities.Rentals;
import be.jyl.enums.State;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

public class RentalsService {
    private Logger log = Logger.getLogger(RentalsService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();
    public List<Rentals> rentalsList(){
        Query query = em.createNamedQuery("Rentals.findAll");
        return query.getResultList();
    }
    public void persistNewRental(Rentals rental, Articles article){
        log.log(Level.INFO, " Call em.persist and merge article ");
        transaction.begin();
        em.persist(rental);
        em.merge(article);
        transaction.commit();
    }
    public void removeRental(Rentals rental){
        if (!transaction.isActive()){
            transaction.begin();
            log.log(Level.INFO,"removeRental : transaction begin");
            log.log(Level.INFO,"rental selected : "+rental.getUserRent().getFirstname());
            Collection<ArticlesRentals> articlesRentalsCollection = rental.getRentalsArticlesByIdRental();
            for (ArticlesRentals articlesRentalsItem :articlesRentalsCollection) {
                articlesRentalsItem.getArticlesByIdArticle().setState(State.available);
                em.merge(articlesRentalsItem.getArticlesByIdArticle());
                em.remove(articlesRentalsItem);
            }
            em.remove(rental);
            transaction.commit();

        }
    }
}
