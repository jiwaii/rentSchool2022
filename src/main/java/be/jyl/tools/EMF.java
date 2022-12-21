package be.jyl.tools;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("rentSchool2022");

    public static EntityManagerFactory getEmfInstance() {
        return emfInstance;
    }
    public static void setEmfInstance(EntityManagerFactory emfInstance) {
        EMF.emfInstance = emfInstance;
    }
}
