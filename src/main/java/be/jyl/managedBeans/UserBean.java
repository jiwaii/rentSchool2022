package be.jyl.managedBeans;

import be.jyl.entities.Users;
import be.jyl.enums.ResponsibleType;
import be.jyl.services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private UserService userService = new UserService();

    @PostConstruct
    public void init(){
        user = new Users();
    }
    /**
     * @jiwaii commentaire:
     *Ok Mais, revoir la liaison avec l'entité City
     *
     * **/
    public void addNewUser(){
        log.log(Level.INFO,"UserBean.addNewUser()");
        log.log(Level.INFO,"___NEW USER___ ");
        log.log(Level.INFO,"Firstname : "+ user.getFirstname());
        log.log(Level.INFO,"Lastname : "+ user.getLastname());
        log.log(Level.INFO,"ResponsibleType : "+ user.getResponsibleType());
        log.log(Level.INFO,"Address : "+ user.getAddress());
        user.setIdCity(1);
        log.log(Level.INFO,"IdCity : "+ user.getIdCity());

        userService.insert(user);
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
