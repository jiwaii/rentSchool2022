package be.jyl.managedBeans;

import be.jyl.entities.Cities;
import be.jyl.entities.Users;
import be.jyl.services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private String userSearchText;
    private UserService userService = new UserService();
    private List<Cities> citiesList;
    private List<Users> usersList;
    private Users userSession;

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        user = new Users();
    }
    /**
     * @jiwaii commentaire:
     *Ok Mais, revoir la liaison avec l'entité City
     *
     * **/
    public String addNewUser(){
        //<editor-fold desc="logs Info New User">
        log.log(Level.INFO,"UserBean.addNewUser()");
        log.log(Level.INFO,"___NEW USER___ ");
        log.log(Level.INFO,"Firstname : "+ user.getFirstname());
        log.log(Level.INFO,"Lastname : "+ user.getLastname());
        log.log(Level.INFO,"ResponsibleType : "+ user.getResponsibleType());
        log.log(Level.INFO,"Address : "+ user.getAddress());
        log.log(Level.INFO,"IdCity : "+ user.getIdCity());
        //</editor-fold>
//        user.setCitiesByIdCity(userCity);

        userService.insert(user);
        usersList = listUsersForUserRoleSession();
        Collections.reverse(usersList);
        user = new Users();
        return "usersList";
    }

    /**
     * Renvois la liste d'utilisateurs dépendant du rôle
     * Exemple : si connecté avec Secrétariat, elle n'auras pas les admins dans la liste
     * @return list Users
     */
    private List<Users> listUsersForUserRoleSession(){
        return userService.listUsers(userSession);
    }
    private List<Users> listUsersForUserRoleSessionByName(String searchText){
//        if (userSession.getRolesByIdRole().getRoleName().toString().equals("administrateur")){
//            return userService.listUserByNamForAdmin(searchText) ;
//        }
//        else {
//            return userService.listUserByName(searchText);
//        }
        return userService.listUserByName(searchText,userSession);
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
        usersList = listUsersForUserRoleSession();
        return "usersList";
    }
    public void searchUserBar(){
        log.log(Level.INFO,"UserBean.searchUserBar called !");
        log.log(Level.INFO,"UserBean.userSeachText is = " + userSearchText);
        usersList = listUsersForUserRoleSessionByName(userSearchText);
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

    public String getUserSearchText() {
        return userSearchText;
    }

    public void setUserSearchText(String userSearchText) {
        this.userSearchText = userSearchText;
    }
}
