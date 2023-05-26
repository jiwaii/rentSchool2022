package be.jyl.services;

import be.jyl.entities.Borrowers;
import be.jyl.entities.Cities;
import be.jyl.entities.Roles;
import be.jyl.entities.Users;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
public class BorrowersService {
    private Logger log = Logger.getLogger(BorrowersService.class);
    public EntityManager em = EMF.getEM();
    public EntityTransaction transaction = em.getTransaction();

    /**
     * ----------------------------------
     * Methode et Fonctions :
     * ----------------------------------
     */

    public List<Borrowers> listBorrowers(){
        Query query = em.createNamedQuery("Borrowers.all");
        return query.getResultList();
    }
    public List<Borrowers> listBorrowers(String name){
        Query query = em.createNamedQuery("Borrowers.where")
                .setParameter("pFirstname","%"+name+"%")
                .setParameter("pLastname","%"+name+"%");
        return query.getResultList();
    }




    /**
     * Insertion d'un nouvelle utilisateur
     * avec role d'emprunteur par défaut
     * @param user
     */
    public void createUser(Users user){

    }
    public void updateUser(Users user){

    }
    public void deleteUser(Users users){

    }
//    /**
//     * Renvois la liste d'utilisateurs dépendant du rôle
//     * Exemple : si connecté avec Secrétariat, elle n'auras pas les admins dans la liste
//     * @param userSession : pour connaitre quel roles il s'agit
//     * @return
//     */
//    public List<Users> listUsers(Users userSession){
//        if (userSession.getRole().getRoleName().toString().equals("administrateur")){
//            return listUsersForAdmin() ;
//        }
//        else {
//            return listUsers();
//        }
//    }
    public void createUserFromBorrower(Borrowers borrower,Users user){

        user = (Users)borrower;
    }
    public List<Cities> listCities(){
        Query query = em.createNamedQuery("Cities.findAll");
        return query.getResultList();
    }


//    /**
//     * Idem que listUsers avec recherche par nom ou prenom
//     * @param name
//     * @param userSession
//     * @return
//     */
//    public List<Users> listUserByName(String name, Users userSession){
//        if (userSession.getRole().getRoleName().toString().equals("administrateur")){
//            return listUsersForAdmin(name) ;
//        }
//        else {
//            return listUserByName(name);
//        }
//    }




}
