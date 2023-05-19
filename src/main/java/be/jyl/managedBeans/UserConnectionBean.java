package be.jyl.managedBeans;

import be.jyl.entities.Borrowers;
import be.jyl.entities.Roles;
import be.jyl.entities.Users;
import be.jyl.enums.ResponsibleType;
import be.jyl.services.BorrowersService;
import be.jyl.services.UsersService;
import be.jyl.tools.EMF;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@ViewScoped
public class UserConnectionBean implements Serializable {
    private Logger log = Logger.getLogger(UserConnectionBean.class);
    private String login ;
    private String password ;

    public String connectionLogin(){
        //TODO TESTS pour lancer la créate table : CODEFIRST entity to DB
//        EntityManager em = EMF.getEM();
//        EntityTransaction trs  = em.getTransaction();
//        trs.begin();
//        Roles role = new Roles();
//        role.setRoleName("administrateur");
//        em.persist(role);
//
//        //myBorrower
//        Borrowers borrower = new Borrowers();
//        borrower.setEmail("borrower@oeamil.com");
//        borrower.setAddress("borowoer adress 123");
//        borrower.setResponsibleType(ResponsibleType.staff);
//        borrower.setFirstname("BOB");
//        borrower.setLastname("Baudelaire");
//        em.persist(borrower);
//        //myUser
//        Users user1 = new Users();
//        user1.setFirstname("user");
//        user1.setLastname("user1");
//        user1.setAddress("rue des utilisateurs");
//        user1.setEmail("user1@user.com");
//        user1.setResponsibleType(ResponsibleType.teacher);
//        user1.setLogin("user1");
//        user1.setPassword("1234");
//        user1.setRole(role);
//        em.persist(user1);
//        //Borow2
//        Borrowers borrower2 = new Borrowers();
//        borrower2.setFirstname("B2");
//        borrower2.setLastname("BOROWER2");
//        borrower2.setAddress("rue des emprunteurs");
//        borrower2.setEmail("emprunteur2@jjejej.com");
//        borrower2.setResponsibleType(ResponsibleType.teacher);
////        user2.setLogin("jack");
////        user2.setPassword("1234");
////        user2.setRole(role);
//        em.persist(borrower2);
//        trs.commit();




        /** Test Login and password */
        UsersService userService = new UsersService();
        //password = userService.hashingPassword(password);
        Users myUser = userService.getConnectionLogin(login,password);
        if (myUser != null){
            if (myUser.getRole().getRoleName().equals("emprunteur")){
                myUser = null;
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,null,"votre compte \"emprunteur\" n'est pas autorisé de se connecté"));
                return "fail";
            }
            else{
            FacesContext context = FacesContext.getCurrentInstance();
            log.log(Level.INFO, "user is :"+ myUser.getFirstname());
//            context.getExternalContext().getSessionMap().put("accountSession",myUser);
            context.getExternalContext().getSessionMap().put("userSession",myUser);
            log.log(Level.INFO,"UserConnectionBean.connectionLogin() : success = "+myUser.getLogin() +" With id: "+ myUser.getId());
            return "success";

            }
         }
         else {
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,null,"Login ou password incorecte"));
             return "fail";
         }
    }
    public String disconnect(){
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        log.log(Level.INFO,"UserConnectionBean.disconnect() : disconnect");
        return "login.xhtml";
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
