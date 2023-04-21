package be.jyl.tools;

import be.jyl.services.BorrowersService;
import be.jyl.services.UsersService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {
    private Logger log = Logger.getLogger(LoginValidator.class);
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        UsersService usersService = new UsersService();
        if (usersService.userExist(o.toString())){
            log.log(Level.INFO, o.toString());
            log.log(Level.INFO, usersService.userExist(o.toString()));

            throw new ValidatorException((new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Login déjà pris",null)));
        }
    }
}
