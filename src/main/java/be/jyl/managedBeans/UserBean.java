package be.jyl.managedBeans;
import be.jyl.entities.UsersEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class UserBean implements Serializable {
    private String message = "Hello La compagnie Créole";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
