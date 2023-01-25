package be.jyl.managedBeans;

import be.jyl.entities.Users;
import be.jyl.enums.ResponsibleType;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private Logger log = Logger.getLogger(UserBean.class);
    private Users user;
    public ResponsibleType[] responsibleTypesList(){
        return ResponsibleType.values();
    }

    public void addUser(){

    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
