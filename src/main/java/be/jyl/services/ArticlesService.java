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
// TODO : TRY CATCH

public class ArticlesService {

    private Logger log = Logger.getLogger(ArticlesService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    public List<Articles> articlesAvailableList(){
        log.log(Level.INFO,"articleList() ");
        Query query = em.createNamedQuery("articles.availableBasedOnDateReturn");
        return query.getResultList();
    }
    public List<Articles> articlesAvailableListBySearch(String articlesSearch){
        log.log(Level.INFO,"articlesAvailableListBySearch()");
        Query query = em.createNamedQuery("articles.findWhere")
                .setParameter("pArticleSearch","%"+articlesSearch+"%");

        return query.getResultList();
    }

    public List<Articles> getAllArticles() {
        Query query = em.createNamedQuery("articles.findAll", Articles.class);
        return query.getResultList();
    }
    public boolean isCurrentlyRented(Articles article){
        Query query = em.createNamedQuery("Article.isCurrentlyRented", ArticlesRentals.class).setParameter("article", article);
        return (boolean) query.getSingleResult();
    }

    public void addArticle(Articles article) {
        transaction.begin();
        em.persist(article);
        transaction.commit();
    }

    public void updateArticle(Articles article) {
        log.log(Level.INFO, article.getCategoryByIdCategory().getCategoryName());
        log.log(Level.INFO, article.getCategoryByIdCategory().getIdCategory());
        transaction.begin();
        em.merge(article);
        transaction.commit();
    }

    public void deleteArticle(Articles article) {
        //TO DO
        Articles managedArticle = em.find(Articles.class, article.getIdArticle());
        em.remove(managedArticle);
    }

}
