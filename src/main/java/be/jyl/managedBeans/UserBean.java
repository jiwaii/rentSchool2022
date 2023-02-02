package be.jyl.managedBeans;

import be.jyl.entities.Cities;
import be.jyl.entities.Users;
import be.jyl.services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private UserService userService = new UserService();
    private List<Cities> citiesList;

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
//        user.setIdCity(1);
        log.log(Level.INFO,"IdCity : "+ user.getIdCity());
//        user.setCitiesByIdCity(userCity);

//        userService.insert(user);
    }
    public String addUserPage(){
        citiesList = userService.listCities();
        return "addUser";
    }



    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Cities> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<Cities> citiesList) {
        this.citiesList = citiesList;
    }

}
