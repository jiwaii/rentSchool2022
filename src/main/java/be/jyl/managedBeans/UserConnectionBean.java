package be.jyl.managedBeans;

import be.jyl.entities.Accounts;
import be.jyl.services.AccountService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserConnectionBean implements Serializable {
    private Logger log = Logger.getLogger(UserConnectionBean.class);
    private String message = "Bienvenue ";
    private String login ;
    private String password ;
    private Accounts account = new Accounts();
    //private AccountService accountService = new AccountService();


    public String connectionLogin(){
        log.log(Level.INFO,"From UserConnectionBean.connectionLogin() : "+ login);

        /** Test EntityManager */
        //AccountService accountService = new AccountService();
        //List<AccountsEntity> accounts =  accountService.getAccounts();
        //System.out.println(account.getLogin()+"  "+account.getPassword());

        if (login.equals("jiwaii")){
            log.log(Level.INFO,"UserConnectionBean.connectionLogin() : success");
            return "success";
         }
         else {
             log.log(Level.INFO,"UserConnectionBean.connectionLogin() : fail");
             return "fail";
         }
    }
    public String disconnect(){
        log.log(Level.INFO,"UserConnectionBean.disconnect() : disconnect");
        return "login.xhtml";
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
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
