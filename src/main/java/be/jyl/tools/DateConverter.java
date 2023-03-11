package be.jyl.tools;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {
    private Logger log = Logger.getLogger(DateConverter.class);
    public Date getSqlDateFromUtilDate(java.util.Date date) throws ParseException {
        log.log(Level.INFO,"util Date : "+date);
        java.sql.Date dateSqlNow = new java.sql.Date(date.getTime());
        log.log(Level.INFO,"SQLDATE = "+ dateSqlNow );
        return dateSqlNow;
    }
}
