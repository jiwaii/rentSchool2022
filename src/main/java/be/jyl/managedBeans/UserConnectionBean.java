package be.jyl.managedBeans;

import be.jyl.entities.Accounts;
import be.jyl.entities.Users;
import be.jyl.services.AccountService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named
@SessionScoped
public class UserConnectionBean implements Serializable {
    private Logger log = Logger.getLogger(UserConnectionBean.class);
    private String login ;
    private String password ;
    private Accounts account = new Accounts();

    public String connectionLogin(){
        log.log(Level.INFO,"From UserConnectionBean.connectionLogin() : "+ login);

        /** Test Login and password */
        AccountService accountService = new AccountService();
        Accounts myAccount = accountService.getConnectionLogin(this.login,this.password);
        if (myAccount != null){
            FacesContext context = FacesContext.getCurrentInstance();
            Users myUser = myAccount.getUsersByIdAccount().stream().findFirst().get();
            context.getExternalContext().getSessionMap().put("accountSession",myAccount);
            context.getExternalContext().getSessionMap().put("userSession",myUser);
            log.log(Level.INFO,"UserConnectionBean.connectionLogin() : success = "+myAccount.getLogin() +" With id: "+ myAccount.getIdAccount());
            return "success";
         }
         else {
             log.log(Level.INFO,"UserConnectionBean.connectionLogin() : fail");
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
