package be.jyl.tools;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class LanguageChanger implements Serializable {
    private static final long serialVersionUID = 1L;
    private String locale;

    private Map<String,Object> countries;
    private List<String> countriesList;
    @PostConstruct
    public void init() {
        countries = new LinkedHashMap<String,Object>();
        countries.put("fr", Locale.FRENCH);
        countries.put("en", Locale.ENGLISH);
        countriesList = new ArrayList<>();
        countriesList.add("fr");
        countriesList.add("en");
    }

    public List<String> getCountriesList() {
        return countriesList;
    }

    public void setCountriesList(List<String> countriesList) {
        this.countriesList = countriesList;
    }

    public Map<String, Object> getCountries() {
        return countries;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }

    //value change event listener
    public void localeChanged(ValueChangeEvent e) {
        locale = e.getNewValue().toString();

        if ("fr".equals(locale)) {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.FRENCH);
        }
        if ("en".equals(locale)) {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale(Locale.ENGLISH);
        }

//        for (Map.Entry<String, Object> entry : countries.entrySet()) {
//
//            if(entry.getValue().toString().equals(locale)) {
//                FacesContext.getCurrentInstance()
//                        .getViewRoot().setLocale((Locale)entry.getValue());
//                //locale = newLocaleValue;
//
////                Map<String,Object> selectedEntry = (Map<String, Object>) entry;
////                countries.remove(entry);
////                countries.putAll(selectedEntry);
//            }
//        }
    }
}
