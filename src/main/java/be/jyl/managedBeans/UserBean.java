package be.jyl.managedBeans;

import be.jyl.entities.*;
import be.jyl.enums.ResponsibleType;
import be.jyl.services.AccountService;
import be.jyl.services.UserService;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private List<Roles> rolesList;
    private String userSearchText;
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private List<Cities> citiesList;
    private List<Users> usersList;
    private List<Users> filteredUser;
    private Users userSession;
    private Users userSelected;
    private Accounts newAccount;
    private List<Users> usersAccountsList;
    private String userAccountSearch;
    private Users userAccountSelected;
    private String newPassword;

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        this.citiesList = userService.listCities();

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
        log.log(Level.INFO,"IdCity : "+ user.getCitiesByIdCity().getCityName());
        //</editor-fold>
//        user.setCitiesByIdCity(userCity);
        userService.insert(user);
        user = new Users();
        usersList = listUsersForUserRoleSession();
        Collections.reverse(usersList);

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
    public String update(){
        log.log(Level.INFO,"update()");
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(userSelected.getEmail());
        log.log(Level.INFO,matcher.matches());
        if (matcher.matches()){
            userService.updateUser(userSelected);
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
            usersList = userService.listUsers(userSession);
        }
        else {
            PrimeFaces.current().ajax().update("form:messages", "");
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,null,"Email invalide !"));
        }
        usersList = userService.listUsers(userSession);
        return "usersList";

    }
    public void updatePassword(){
        userService.updatePasswordService(userAccountSelected, newPassword);
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
        newPassword = "";
    }
    public String updateAccountToUser(){
        log.log(Level.INFO, "updateAccountToUser");

        if (!newAccount.getLogin().trim().isEmpty()
                && !newAccount.getPassword().trim().isEmpty()
                && userSelected != null){

            userSelected.setResponsibleType(ResponsibleType.staff);
            String encordedPassword = newAccount.getPassword();
            encordedPassword = accountService.hashingPassword(encordedPassword);
            newAccount.setPassword(encordedPassword);
            userService.insertAccountToUser(userSelected,newAccount);
            userSelected.setAccountsByIdAccount(newAccount);
            log.log(Level.INFO, userSelected.getFirstname()+" with login :"+userSelected.getAccountsByIdAccount().getLogin());
            return "usersList";
        }else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", " No Selection or login/password forgot "));

            return "userLinkAccount";
        }

    }
    //Ouvrir la modal et initialiser les champs:
    public void openNewUser() {
        this.rolesList = userService.listRoles();
        log.log(Level.INFO,"Role list size : "+rolesList.size());
        this.userSelected = new Users();
        this.citiesList = userService.listCities();
        //setting the user to don't return null to the view wich cause a problem
        usersList = userService.listUserWithoutAccount();

        log.log(Level.INFO, "userLis: " + usersList);
    }
    // Si loging existe déjà
    public boolean loginExist(){
        log.log(Level.INFO,"call : loginExist()");
        AccountService accountService = new AccountService();
        return accountService.accountExist("%"+newAccount.getLogin()+"%");
    }
    public boolean loginExistInUpdate(){
        log.log(Level.INFO,"call : loginExistInUpdate()");
        AccountService accountService = new AccountService();
        return accountService.accountExist("%"+userAccountSelected.getAccountsByIdAccount().getLogin()+"%");
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
        this.rolesList = userService.listRoles();
        log.log(Level.INFO,"Role list size : "+rolesList.size());
        usersList = userService.listUserWithoutAccount();
        return "userLinkAccount";
    }
    public String listUserAccountsPage(){
        usersAccountsList = userService.listUserWithAccount();

        return "accountUserList";
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
    public void usersAccountsLoad(){
        usersAccountsList = userService.listUserWithAccount(userAccountSearch);
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
        //pour tester :
        rolesList = userService.listRoles();
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

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public List<Users> getUsersAccountsList() {
        return usersAccountsList;
    }

    public void setUsersAccountsList(List<Users> usersAccountsList) {
        this.usersAccountsList = usersAccountsList;
    }

    public String getUserAccountSearch() {
        return userAccountSearch;
    }

    public void setUserAccountSearch(String userAccountSearch) {
        this.userAccountSearch = userAccountSearch;
    }

    public Users getUserAccountSelected() {
        return userAccountSelected;
    }

    public void setUserAccountSelected(Users userAccountSelected) {
        this.userAccountSelected = userAccountSelected;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    //</editor-fold>
}
