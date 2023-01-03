package be.jyl.managedBeans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class UserConnectionBean implements Serializable {
    private String message = "Hello La compagnie Créole";
    private String login ;
    private String password ;

    public String connectionLogin(){
     if (login == "jiwaii"){
         return "success";
     }
     else {
         return "fail";
     }
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
