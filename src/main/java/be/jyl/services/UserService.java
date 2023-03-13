package be.jyl.services;

import be.jyl.entities.Accounts;
import be.jyl.entities.Cities;
import be.jyl.entities.Roles;
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

    /**
     * ----------------------------------
     * Methode et Fonctions :
     * ----------------------------------
     */

    private List<Users> listUsers(){
        Query query = em.createNamedQuery("Users.findProfsStudentsOnly");
        return query.getResultList();
    }
    private List<Users> listUsersForAdmin(){
        Query query = em.createNamedQuery("Users.findAll");
        return query.getResultList();
    }
    private List<Users> listUserByName(String name){

        Query query = em.createNamedQuery("Users.findWhereProfStudentOnly")
                .setParameter("pFirstname","%"+name+"%")
                .setParameter("pLastname","%"+name+"%");
        return query.getResultList();
    }
    private List<Users> listUserByNamForAdmin(String name){

        Query query = em.createNamedQuery("Users.findWhere")
                .setParameter("pFirstname","%"+name+"%")
                .setParameter("pLastname","%"+name+"%");
        return query.getResultList();
    }
    public List<Users> listUserWithoutAccount(){
        Query query = em.createNamedQuery("User.findAllNoAccount");
        return query.getResultList();
    }
    public List<Users>listUserWithoutAccountByName(String name){
        Query query = em.createNamedQuery("User.findWhereNoAccount")
                .setParameter("pFirstname","%"+name+"%")
                .setParameter("pLastname","%"+name+"%");
        return query.getResultList();
    }

    /**
     * Insertion d'un nouvelle utilisateur
     * avec role d'emprunteur par défaut
     * @param user
     */
    public void insert(Users user){
        //obtention et affectation du Role emprunteur :
        Query query = em.createNamedQuery("Roles.findWhereRoleNameIs")
                .setParameter("pRoleName","emprunteur");
        Roles roleEmprunteur = (Roles) query.getSingleResult();
        user.setRolesByIdRole(roleEmprunteur);

        // Lancement de la Transaction Persist :
        transaction.begin();
        em.persist(user);
        transaction.commit();
    }
    public void updateUser(Users user){
        if (user.getRolesByIdRole() == null){
            Query query = em.createNamedQuery("Roles.findWhereRoleNameIs")
                    .setParameter("pRoleName","emprunteur");
            Roles roleEmprunteur = (Roles) query.getSingleResult();
            user.setRolesByIdRole(roleEmprunteur);
        }
        if (!transaction.isActive()){
            transaction.begin();
            em.merge(user);
            transaction.commit();
        }
    }
    public void insertAccountToUser(Users user, Accounts accounts){
        if (!transaction.isActive()){
            transaction.begin();
            em.persist(accounts);
            user.setAccountsByIdAccount(accounts);
            em.merge(user);
            transaction.commit();
        }
    }
    public List<Cities> listCities(){
        Query query = em.createNamedQuery("Cities.findAll");
        return query.getResultList();
    }

    /**
     * Renvois la liste d'utilisateurs dépendant du rôle
     * Exemple : si connecté avec Secrétariat, elle n'auras pas les admins dans la liste
     * @param userSession : pour connaitre quel roles il s'agit
     * @return
     */
    public List<Users> listUsers(Users userSession){
        if (userSession.getRolesByIdRole().getRoleName().toString().equals("administrateur")){
            return listUsersForAdmin() ;
        }
        else {
            return listUsers();
        }
    }

    /**
     * Idem que listUsers avec recherche par nom ou prenom
     * @param name
     * @param userSession
     * @return
     */
    public List<Users> listUserByName(String name, Users userSession){
        if (userSession.getRolesByIdRole().getRoleName().toString().equals("administrateur")){
            return listUserByNamForAdmin(name) ;
        }
        else {
            return listUserByName(name);
        }
    }
}
