package be.jyl.services;

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

}
