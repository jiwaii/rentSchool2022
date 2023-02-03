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
    private String userSeachText;
    private UserService userService = new UserService();
    private List<Cities> citiesList;
    private List<Users> usersList;

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
        log.log(Level.INFO,"IdCity : "+ user.getIdCity());
//        user.setCitiesByIdCity(userCity);

        userService.insert(user);
    }

    /** Page pour ajouter un utilisateur
     * et Chargement des villes
     *
     * @return String nom de la jsf
     */
    public String addUserPage(){
        citiesList = userService.listCities();
        return "userAdd";
    }
    /** Page pour lister les utilisateurs
     *  Chargement des utilisateur
     *
     * @return String nom de la jsf
     */
    public String listUserPage(){
        usersList = userService.listUsers();
        return "usersList";
    }
    public void searchUserBar(){
        log.log(Level.INFO,"UserBean.searchUserBar called !");
        log.log(Level.INFO,"UserBean.userSeachText is = " + userSeachText);
        usersList = userService.listUserByName(userSeachText);
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

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public String getUserSeachText() {
        return userSeachText;
    }

    public void setUserSeachText(String userSeachText) {
        this.userSeachText = userSeachText;
    }
}
