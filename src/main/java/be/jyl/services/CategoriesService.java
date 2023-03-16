package be.jyl.services;

import be.jyl.entities.Categories;
import be.jyl.tools.EMF;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


public class CategoriesService {
    private Logger log = Logger.getLogger(RentalsService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    public List<Categories> getAllCategories() {
        try{
            Query query = em.createNamedQuery("Categories.findAll");
            return query.getResultList();
        }catch (Exception e){
            log.error("Error while getting all categories", e);
            return null;
        }
    }

    public void addCategory(Categories category) {
        try{
            transaction.begin();
            em.persist(category);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
            log.error("Error while adding category: " + category.getCategoryName(), e);
        }

    }

    public void updateCategory(Categories category) {
        try{
            transaction.begin();
            em.merge(category);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            log.error("Error while updating category: " + category.getCategoryName(), e);
        }
    }

    public void deleteCategory(Categories category) {
        try{
            Categories managedCategory = em.find(Categories.class, category.getIdCategory());
            em.remove(managedCategory);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            log.error("Error while deleting category: " + category.getCategoryName(), e);
        }
    }

}
