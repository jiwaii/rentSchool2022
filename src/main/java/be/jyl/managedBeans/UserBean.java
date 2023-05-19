package be.jyl.managedBeans;

import be.jyl.entities.*;
import be.jyl.enums.ResponsibleType;
import be.jyl.services.BorrowersService;
import be.jyl.services.UsersService;
import be.jyl.tools.NotificationManager;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    private List<Roles> rolesList;
    private String borrowerSearchText;
    private UsersService usersService = new UsersService();
    private BorrowersService borrowerService = new BorrowersService();
    private List<Cities> citiesList;
    private List<Borrowers> listBorrowers;
    private List<Users> filteredUser;
    private Users userSession;
    private Borrowers borrowerSelected;
    private Borrowers newBorrower;
    private Users newUser;
    private List<Users> usersList;
    private String userSearch;
    private Users userSelected;
    private String newPassword;

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.userSession = (Users) context.getExternalContext().getSessionMap().get("userSession") ;
        this.citiesList = usersService.listCities();

        user = new Users();
    }
    /**-------------------------
     * @jiwaii CRUDs
    --------------------------*/
    public String addBorrower(){
        //<editor-fold desc="logs Info New User">
        log.log(Level.INFO,"UserBean.addBorrower()");
        //{
        // controle des champs email,adress etc ...
        //}

        try {
            usersService.em.getTransaction().begin();
            usersService.em.persist(newBorrower);
            usersService.em.getTransaction().commit();
        }
        finally {
            if (usersService.em.getTransaction().isActive()) {
                usersService.em.getTransaction().rollback();
            }
            usersService.em.close();
        }


        //</editor-fold>
//        user.setCitiesByIdCity(userCity);
//        usersService.createUser(user);
//        user = new Users();
        listBorrowers = usersService.listBorrowers();
        Collections.reverse(listBorrowers);

        return "usersList";
    }

//    private List<Users> listUsersForUserRoleSession(){
//        return usersService.listUsers(userSession);
//    }
//    private List<Users> listUsersForUserRoleSessionByName(String searchText){
//        return usersService.lis(searchText);
//    }
    /**
     * Renvois la liste d'utilisateurs dépendant du rôle
     * Exemple : si connecté avec Secrétariat, elle n'auras pas les admins dans la liste
     * @return list Users
     */
    public String update(){
        //TRANSACTION ici



        log.log(Level.INFO,"update()");
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(borrowerSelected.getEmail());
        log.log(Level.INFO,matcher.matches());
        if (matcher.matches()){

            usersService.updateUser(userSelected);
            PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
            listBorrowers = usersService.listBorrowers();
            NotificationManager.addInfoMessage("notification.users.useradded");
        }
        else {
            PrimeFaces.current().ajax().update("form:messages", "");
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,null,"Email invalide !"));
            NotificationManager.addErrorMessage("notification.users.error");
        }
        listBorrowers = usersService.listBorrowers();

        return "usersList";

    }
    public void updatePassword(){
        // TODO réinitialisation password
        PrimeFaces.current().executeScript("PF('manageUserDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-users");
        newPassword = "";
    }
    public String updateAccountToUser(){
        log.log(Level.INFO, "updateAccountToUser");

        if (!newUser.getLogin().trim().isEmpty()
                && !newUser.getPassword().trim().isEmpty()
                && borrowerSelected != null){

            borrowerSelected.setResponsibleType(ResponsibleType.staff);
            String encordedPassword = newUser.getPassword();
            encordedPassword = usersService.hashingPassword(encordedPassword);
            newUser.setPassword(encordedPassword);
            usersService.createUserFromBorrower(borrowerSelected, newUser);
            log.log(Level.INFO, newUser.getFirstname()+" with login :"+ newUser.getLogin());
            listBorrowers = usersService.listBorrowers();
            NotificationManager.addInfoMessage("notification.users.accountLinked");
            return "usersList";
        }else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", " No Selection or login/password forgot "));

            return "userLinkAccount";
        }

    }
    //Ouvrir la modal et initialiser les champs:
    public void openNewUser() {
        this.rolesList = usersService.listRoles();
        log.log(Level.INFO,"Role list size : "+rolesList.size());
        this.borrowerSelected = new Users();
        this.citiesList = usersService.listCities();
        //setting the user to don't return null to the view wich cause a problem
        listBorrowers = usersService.listBorrowers();

        log.log(Level.INFO, "userLis: " + listBorrowers);
    }
    // Si loging existe déjà
    public boolean loginExist(){
        log.log(Level.INFO,"call : loginExist()");
        return usersService.userExist("%"+ newUser.getLogin()+"%");
    }
    public boolean loginExistInUpdate(){
        log.log(Level.INFO,"call : loginExistInUpdate()");

        return usersService.userExist("%"+ userSelected.getLogin()+"%");
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
        citiesList = usersService.listCities();
        return "userAdd";
    }
    public String addAccountToUserPage(){
        newUser = new Users();
        this.rolesList = usersService.listRoles();
        log.log(Level.INFO,"Role list size : "+rolesList.size());
        listBorrowers = usersService.listBorrowers();
        return "userLinkAccount";
    }
    public String listUserAccountsPage(){
        usersList = usersService.listUsers();

        return "accountUserList";
    }

    /** Page pour lister les utilisateurs
     *  Chargement des utilisateur
     *
     * @return String nom de la jsf
     */
    public String listUserPage(){
        listBorrowers = usersService.listBorrowers();
        return "usersList";
    }

    public void dtUserSelection(SelectEvent selectEvent){
        borrowerSelected = (Users) selectEvent.getObject();
        log.log(Level.INFO, borrowerSelected.getFirstname()+" Selected");
    }
    public void inputSearchBorrower(){
        log.log(Level.INFO,"inputSearchBorrower() : UserBean.userSeachText is = "+ borrowerSearchText);
        listBorrowers = usersService.listBorrowers(borrowerSearchText);
    }
    public void inputSearchBorrowerToUser(){
        listBorrowers = usersService.listBorrowers(borrowerSearchText);
    }
    public void usersLoad(){
        usersList = usersService.listUsers(userSearch);
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

    public List<Borrowers> getListBorrowers() {
        return listBorrowers;
    }

    public void setListBorrowers(List<Borrowers> listBorrowers) {
        this.listBorrowers = listBorrowers;
    }

    public String getBorrowerSearchText() {
        return borrowerSearchText;
    }

    public void setBorrowerSearchText(String borrowerSearchText) {
        this.borrowerSearchText = borrowerSearchText;
    }

    public Borrowers getBorrowerSelected() {
        //pour tester :
        rolesList = usersService.listRoles();
        return borrowerSelected;
    }

    public void setBorrowerSelected(Borrowers borrowerSelected) {
        this.borrowerSelected = borrowerSelected;
    }

    public List<Users> getFilteredUser() {
        return filteredUser;
    }

    public void setFilteredUser(List<Users> filteredUser) {
        this.filteredUser = filteredUser;
    }

    public Users getNewUser() {
        return newUser;
    }

    public void setNewUser(Users newUser) {
        this.newUser = newUser;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public String getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(String userSearch) {
        this.userSearch = userSearch;
    }

    public Users getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(Users userSelected) {
        this.userSelected = userSelected;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Borrowers getNewBorrower() {
        return newBorrower;
    }

    public void setNewBorrower(Borrowers newBorrower) {
        this.newBorrower = newBorrower;
    }
    //</editor-fold>
}
