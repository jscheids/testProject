/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.exeption;

/**
 * Custom exception class for invalid parameters
 * @author Jennifer
 */
public class InvalidParameterException extends IllegalArgumentException {

 
    public static String MSG = "A parameter was null, empty or invalid.";
    
    public InvalidParameterException() {
        super(MSG);
    }

    public InvalidParameterException(String s) {
        super(MSG);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(MSG, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }  
}
