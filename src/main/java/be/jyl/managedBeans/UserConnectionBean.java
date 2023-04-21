package be.jyl.managedBeans;

import be.jyl.entities.Users;
import be.jyl.services.BorrowersService;
import be.jyl.services.UsersService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@ViewScoped
public class UserConnectionBean implements Serializable {
    private Logger log = Logger.getLogger(UserConnectionBean.class);
    private String login ;
    private String password ;

    public String connectionLogin(){
        /** Test Login and password */
        UsersService userService = new UsersService();
        password = userService.hashingPassword(password);
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
