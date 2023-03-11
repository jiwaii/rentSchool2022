package be.jyl.services;

import be.jyl.entities.Articles;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ArticlesService {
    private Logger log = Logger.getLogger(AccountService.class);
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
}
