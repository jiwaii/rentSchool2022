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
public class UsersService {
    private Logger log = Logger.getLogger(UsersService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();

    /**
     * ----------------------------------
     * Methode et Fonctions :
     * ----------------------------------
     */

    public List<Users> listBorrowers(){
        Query query = em.createNamedQuery("Borrowers.all");
        return query.getResultList();
    }
    public List<Users> listBorrowers(String name){
        Query query = em.createNamedQuery("Borrowers.where")
                .setParameter("pFirstname","%"+name+"%")
                .setParameter("pLastname","%"+name+"%");
        return query.getResultList();
    }
    public List<Users> listUsers(){
        Query query = em.createNamedQuery("Users.all");
        return query.getResultList();
    }
    public List<Users> listUsers(String name){
        Query query = em.createNamedQuery("Users.where").setParameter("pName","%"+name+"%");
        return query.getResultList();
    }
    public List<Roles> listRoles(){
        Query query = em.createNamedQuery("Roles.findAll");
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

    public Users getConnectionLogin(String pLogin, String pPassword){
        log.log(Level.INFO,"getConnectionLogin ( )");
        log.log(Level.INFO,pLogin);
        log.log(Level.INFO,pPassword);

        Query query= em.createNamedQuery("User.login")
                .setParameter("pLogin",pLogin)
                .setParameter("pPassword",pPassword);
        Users user = null ;
        try{
            user = (Users) query.getSingleResult();
            log.log(Level.INFO,"User is OK :"+user.getFirstname());
        }
        catch (Exception e){
            user = null;
            log.log(Level.INFO,"user is NULL : "+e.getMessage());
        }finally {
            return user;
        }
    }
    public boolean userExist(String login){
        Query query = em.createNamedQuery("User.findWhereLogin",Users.class)
                .setParameter("pLogin",login.trim().toLowerCase());

        List<Users> accountsList = query.getResultList();
        if (accountsList.size() == 0){
            return false;
        }
        else {
            return true;
        }
    }
    public String hashingPassword(String rawPassword){
        String encoded;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            encoded = DatatypeConverter.printHexBinary(hash);
            log.log(Level.INFO,"Password encoded is : "+encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return encoded;
    }
}
