/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * THis is a data helper class to separate out responsibilities. If this was a real application, it would have 
 * additional date/time methods
 * @author Jennifer
 */
public class DateHelper {
     private String formattedDate;
       private  DateTimeFormatter dateTimeFormatter = 
            DateTimeFormatter.ofPattern("yyyy-MM-dd"); 

   /**
    * Method to get the current date 
    * Current date currently used to record time wine added to db and nothing else
    * @return String
    */
    public String getCurrentDate() {
        LocalDateTime currentDate;
       currentDate = LocalDateTime.now();
       formattedDate =  dateTimeFormatter.format(currentDate); 
        return formattedDate;
    }

      
}
