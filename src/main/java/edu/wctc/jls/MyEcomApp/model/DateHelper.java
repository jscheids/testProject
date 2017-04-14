/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * THis is a data helper class to separate out responsibilities. If this was a
 * real application, it would have additional date/time methods
 *
 * @author Jennifer
 */
public class DateHelper {

    private String formattedDate;
    private DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Method to get the current date Current date currently used to record time
     * wine added to db and nothing else
     *
     * @return String
     */
    public String getCurrentDate() {
        LocalDateTime currentDate;
        currentDate = LocalDateTime.now();
        formattedDate = dateTimeFormatter.format(currentDate);
        return formattedDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.formattedDate);
        hash = 17 * hash + Objects.hashCode(this.dateTimeFormatter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DateHelper other = (DateHelper) obj;
        if (!Objects.equals(this.formattedDate, other.formattedDate)) {
            return false;
        }
        if (!Objects.equals(this.dateTimeFormatter, other.dateTimeFormatter)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DateHelper{" + "formattedDate=" + formattedDate + ", dateTimeFormatter=" + dateTimeFormatter + '}';
    }
    

}
