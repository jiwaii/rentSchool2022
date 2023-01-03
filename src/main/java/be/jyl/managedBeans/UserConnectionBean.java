package be.jyl.managedBeans;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class UserConnectionBean implements Serializable {
    private Logger log = Logger.getLogger(UserConnectionBean.class);
    private String message = "Bienvenue ";
    private String login ;
    private String password ;


    public String connectionLogin(){
        System.out.println(login);
        log.log(Level.INFO,"From UserConnectionBean.connectionLogin() : "+ login);
         if (login.equals("jiwaii")){
             System.out.println("success");
             return "success";
         }
         else {
             System.out.println("fail");
             return "fail";
         }
    }
    public String disconnect(){
        System.out.println("disconecte to login");
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
