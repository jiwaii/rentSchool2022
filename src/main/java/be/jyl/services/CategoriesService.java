package be.jyl.services;

import be.jyl.entities.Categories;
import be.jyl.tools.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

// TODO : TRY CATCH
public class CategoriesService {
    private Logger log = Logger.getLogger(RentalsService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    public List<Categories> getAllCategories() {
        Query query = em.createNamedQuery("Categories.findAll");
        return query.getResultList();
    }

    public void addCategory(Categories category) {
        transaction.begin();
        em.persist(category);
        transaction.commit();
    }

    public void updateCategory(Categories category) {
        transaction.begin();
        em.merge(category);
        transaction.commit();
    }

    public void deleteCategory(Categories category) {
        //TO DO
        Categories managedCategory = em.find(Categories.class, category.getIdCategory());
        em.remove(managedCategory);
    }

}
