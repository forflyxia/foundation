package cn.tcc.foundation.core.convert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeTo {

    public static Date toDate(XMLGregorianCalendar xmlDate) {
        if (xmlDate == null) {
            return null;
        }
        return xmlDate.toGregorianCalendar().getTime();
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = toLocalDateTime(date);
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalDate();
    }

    public static LocalDate toLocalDate(XMLGregorianCalendar xmlGregorianCalendar) {
        LocalDateTime localDateTime = toLocalDateTime(xmlGregorianCalendar);
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        return xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }

    public static XMLGregorianCalendar toXmlDate(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return toXmlDate(localDate);
    }

    public static XMLGregorianCalendar toXmlDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        LocalDateTime localDateTime = localDate.atTime(0, 0, 0);
        return toXmlDate(localDateTime);
    }

    public static XMLGregorianCalendar toXmlDate(LocalDateTime localDateTime) {

        String iso = localDateTime.toString();
        if (localDateTime.getSecond() == 0 && localDateTime.getNano() == 0) {
            iso += ":00";
        }

        try {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            return factory.newXMLGregorianCalendar(iso);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(XMLGregorianCalendar xmlGregorianCalendar) {
        Date date = toDate(xmlGregorianCalendar);
        return toString(date);
    }

    public static String toString(Date date) {
        if(date == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String toyMdHsString(Date date) {
        if(date == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String toyMdHsSString(Date date) {
        if(date == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = formatter.format(date);
        return dateString;
    }
}
