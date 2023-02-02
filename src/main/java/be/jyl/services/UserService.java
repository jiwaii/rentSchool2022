package be.jyl.services;

import be.jyl.entities.Cities;
import be.jyl.entities.Users;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
public class UserService {
    private Logger log = Logger.getLogger(UserService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    public void insert(Users user){
        transaction.begin();
            em.persist(user);
        transaction.commit();
    }
    public List<Cities> listCities(){
       Query query = em.createNamedQuery("Cities.findAll");
       return query.getResultList();
    }

}
