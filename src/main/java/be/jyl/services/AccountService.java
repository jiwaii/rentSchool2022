package be.jyl.services;

import be.jyl.entities.Accounts;
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

public class AccountService {
    private Logger log = Logger.getLogger(AccountService.class);
    private EntityManager em = EMF.getEM();
    private EntityTransaction transaction = em.getTransaction();
    public List<Accounts> getAccounts (){
        Query query = em.createNamedQuery("Account.findAll",Accounts.class);
        return query.getResultList();
    }
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
    public boolean accountExist(String login){
        Query query = em.createNamedQuery("Account.findWhereLogin",Accounts.class)
                .setParameter("pLogin",login.trim().toLowerCase());

        List<Accounts> accountsList = query.getResultList();
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
