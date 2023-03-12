package be.jyl.managedBeans;

import be.jyl.entities.Accounts;
import be.jyl.entities.Cities;
import be.jyl.entities.Users;
import be.jyl.services.UserService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private String userSearchText;
    private UserService userService = new UserService();
    private List<Cities> citiesList;
    private List<Users> usersList;
    private List<Users> filteredUser;
    private Users userSession;
    private Users userSelected;
    private Accounts newAccount;

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        user = new Users();
    }
    /**-------------------------
     * @jiwaii CRUDs
    --------------------------*/
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

    private List<Users> listUsersForUserRoleSession(){
        return userService.listUsers(userSession);
    }
    private List<Users> listUsersForUserRoleSessionByName(String searchText){
        return userService.listUserByName(searchText,userSession);
    }
    /**
     * Renvois la liste d'utilisateurs dépendant du rôle
     * Exemple : si connecté avec Secrétariat, elle n'auras pas les admins dans la liste
     * @return list Users
     */
    public void update(){
        log.log(Level.INFO,"update()");
        userService.updateUser(userSelected);
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
    }
    public String updateAccountToUser(){
        log.log(Level.INFO, "updateAccountToUser");
        if (!newAccount.getLogin().trim().isEmpty()
                && !newAccount.getPassword().trim().isEmpty()
                && userSelected != null){
            userService.insertAccountToUser(userSelected,newAccount);
            userSelected.setAccountsByIdAccount(newAccount);
            log.log(Level.INFO, userSelected.getFirstname()+" " +
                    " with login :"+userSelected.getAccountsByIdAccount().getLogin()+" " +
                    "password : "+ userSelected.getAccountsByIdAccount().getPassword());
            return "usersList";
        }else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", " No Selection or login/password forgot "));

            return "userLinkAccount";
        }

    }

    /**-----------------------------
     * Navigation Pages
     -----------------------------*/

    /** Page pour ajouter un utilisateur
     * et Chargement des villes
     *
     * @return String nom de la jsf
     */
    public String addUserPage(){
        citiesList = userService.listCities();
        return "userAdd";
    }
    public String addAccountToUserPage(){
        newAccount = new Accounts();
        usersList = userService.listUserWithoutAccount();
        return "userLinkAccount";
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

    public void dtUserSelection(SelectEvent selectEvent){
        userSelected = (Users) selectEvent.getObject();
        log.log(Level.INFO,userSelected.getFirstname()+" Selected");
    }
    public void searchUserBar(){
        log.log(Level.INFO,"UserBean.searchUserBar called !");
        log.log(Level.INFO,"UserBean.userSeachText is = " + userSearchText);
        usersList = listUsersForUserRoleSessionByName(userSearchText);
    }
    public void searchUserBarToLinkAccount(){
        usersList = userService.listUserWithoutAccountByName(userSearchText);
    }

    /** ------------------
     * GETTERS AND SETTERS
     ----------------------*/
//<editor-fold desc="Getters Setters">
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

    public Users getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(Users userSelected) {
        this.userSelected = userSelected;
    }

    public List<Users> getFilteredUser() {
        return filteredUser;
    }

    public void setFilteredUser(List<Users> filteredUser) {
        this.filteredUser = filteredUser;
    }

    public Accounts getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(Accounts newAccount) {
        this.newAccount = newAccount;
    }


    //</editor-fold>
}
