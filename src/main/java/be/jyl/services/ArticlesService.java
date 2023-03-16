package be.jyl.services;

import be.jyl.entities.*;
import be.jyl.entities.Articles;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ArticlesService {

    private Logger log = Logger.getLogger(ArticlesService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    public List<Articles> articlesAvailableList(){
        try{
            log.log(Level.INFO,"articleList()");
            Query query = em.createNamedQuery("articles.availableBasedOnDateReturn");
            return query.getResultList();
        }catch (Exception e){
            log.error("Error while getting available articles", e);
            return null;
        }
    }
    public List<Articles> articlesAvailableListBySearch(String articlesSearch){
        try{
            log.log(Level.INFO,"articlesAvailableListBySearch()");
            Query query = em.createNamedQuery("articles.findWhere")
                    .setParameter("pArticleSearch","%"+articlesSearch+"%");

            return query.getResultList();
        }catch (Exception e){
            log.error("Error while getting available articles search", e);
            return null;
        }

    }

    public List<Articles> findByRefSn(String refSn){
        try{
            log.log(Level.INFO,"articlesFindByRefSn()");
            Query query = em.createNamedQuery("articles.findByRefSn")
                    .setParameter("refSn", refSn);
            log.log(Level.INFO,query.getResultList());
            return query.getResultList();
        }catch (Exception e){
            log.error("Error while finding article by refSn", e);
            return null;
        }
    }

    public List<Articles> findByBarcode(String barcode){
        try{
            log.log(Level.INFO,"articlesFindByBarcode()");
            Query query = em.createNamedQuery("articles.findByBarcode")
                    .setParameter("barcode", barcode);
            return query.getResultList();
        }catch (Exception e){
            log.error("Error while finding article by barcode", e);
            return null;
        }
    }

    public List<Articles> getAllArticles() {
        try{
            Query query = em.createNamedQuery("articles.findAll", Articles.class);
            return query.getResultList();
        }catch (Exception e){
            log.error("Error while getting all articles", e);
            return null;
        }

    }

    public boolean isCurrentlyRented(Articles article){
        try{
            Query query = em.createNamedQuery("articles.isCurrentlyRented", ArticlesRentals.class).setParameter("article", article);
            return (boolean) query.getSingleResult();
        }catch (Exception e){
            log.error("Error while getting if article is currently rented", e);
            return false;
        }

    }

    public boolean existsInRentals(Articles article){
        try{
            Query query = em.createNamedQuery("articles.existsInRentals", ArticlesRentals.class).setParameter("article", article);
            return (boolean) query.getSingleResult();
        }catch (Exception e){
            log.error("Error while getting if article is in rentals", e);
            return false;
        }

    }

    public void addArticle(Articles article) {
        try{
            transaction.begin();
            em.persist(article);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            log.error("Error while adding article", e);
        }

    }

    public void updateArticle(Articles article) {
        try{
            transaction.begin();
            em.merge(article);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            log.error("Error while updating article", e);
        }

    }

    public void deleteArticle(Articles article) {
        try{
            Articles managedArticle = em.find(Articles.class, article.getIdArticle());
            em.remove(managedArticle);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            log.error("Error while deleting article", e);
        }

    }

}
