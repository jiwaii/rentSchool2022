package be.jyl.services;

import be.jyl.entities.Roles;
import be.jyl.entities.Users;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UsersService {
    private Logger log = Logger.getLogger(UsersService.class);
    public EntityManager em = EMF.getEM();
    public EntityTransaction transaction = em.getTransaction();

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
    public Users getConnectionLogin(String pLogin, String pPassword){
        log.log(Level.INFO,"getConnectionLogin ( )");
        log.log(Level.INFO,pLogin);
        log.log(Level.INFO,pPassword);

        Query querytest = em.createNamedQuery("Users.all");
        List<Object> viewusers = querytest.getResultList();
        for (Object vu:viewusers
        ) {
            log.log(Level.INFO,vu.toString());
        }
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
    public boolean isAnUsedUser(Users user){
        Query query = em.createNamedQuery("Users.isUsedUser", Users.class)
                .setParameter("pUser",user);
        Long result = (Long) query.getSingleResult();
        return result > 0;
    }
}
