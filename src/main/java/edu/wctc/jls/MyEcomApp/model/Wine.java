/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import edu.wctc.jls.exeption.InvalidParameterException;
import java.util.Date;
import java.util.Objects;

/**
 * This is a domain/entity object reprsenting wine.
 *
 * @author Jennifer
 */
public class Wine {

    private String wineName;
    private Integer wineID;
    private double winePrice;
    private String wineImgUrl;
    private Date dateAdded;
    private static final int MIN_VALUE = 0;

    /**
     * Default Constructor
     */
    public Wine() {
    }

    /**
     * getter for wineName.
     *
     * @return String
     */
    public final String getWineName() {
        return wineName;
    }

    /**
     * setter for wineName. Name may not be empty or null
     *
     * @param wineName
     * @throws InvalidParameterException
     */
    public final void setWineName(String wineName) {
        if (wineName.isEmpty() || wineName == null) {
            throw new InvalidParameterException();
        }
        this.wineName = wineName;
    }

    /**
     * getter for wineID
     *
     * @return Integer
     */
    public final Integer getWineID() {
        return wineID;
    }

    /**
     * setter for wine id. May not be less than 0
     *
     * @param wineID
     * @throws InvalidParameterException
     */
    public final void setWineID(Integer wineID) {
        if (wineID < MIN_VALUE) {
            throw new InvalidParameterException();
        }
        this.wineID = wineID;
    }

    /**
     * getter for winePrice
     *
     * @return double
     */
    public final double getWinePrice() {

        return winePrice;
    }

    /**
     * setter for wine price. May not be less than 0
     *
     * @param winePrice
     * @throws InvalidParameterException
     */
    public final void setWinePrice(double winePrice) {

        this.winePrice = winePrice;
    }

    /**
     * getter for wine img url
     *
     * @return String
     */
    public final String getWineImgUrl() {
        ImageFileHelper imgHelper = new ImageFileHelper(wineImgUrl);
        wineImgUrl = imgHelper.wineImgFileChecker(wineImgUrl);
        return wineImgUrl;
    }

    /**
     * setter for wine img url.May not be empty or null
     *
     * @param wineImgUrl
     * @throws InvalidParameterException
     */
    public final void setWineImgUrl(String wineImgUrl) {
        if (wineImgUrl.isEmpty() || wineImgUrl == null) {
            throw new InvalidParameterException();
        }

        this.wineImgUrl = wineImgUrl;
    }

    /**
     * getter for date added.
     *
     * @return Date
     */
    public final Date getDateAdded() {
        return dateAdded;
    }

    /**
     * setter for dateAdded. May not be null.
     *
     * @param dateAdded
     * @throws InvalidParameterException
     */
    public final void setDateAdded(Date dateAdded) {
        if (dateAdded == null) {
            throw new InvalidParameterException();
        }
        this.dateAdded = dateAdded;
    }

    /**
     * Overridden hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.wineName);
        hash = 67 * hash + Objects.hashCode(this.wineID);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.winePrice) ^ (Double.doubleToLongBits(this.winePrice) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.wineImgUrl);
        hash = 67 * hash + Objects.hashCode(this.dateAdded);
        return hash;
    }

    /**
     * Overriden equals
     *
     * @param obj
     * @return
     */
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
        final Wine other = (Wine) obj;
        if (Double.doubleToLongBits(this.winePrice) != Double.doubleToLongBits(other.winePrice)) {
            return false;
        }
        if (!Objects.equals(this.wineName, other.wineName)) {
            return false;
        }
        if (!Objects.equals(this.wineImgUrl, other.wineImgUrl)) {
            return false;
        }
        if (!Objects.equals(this.wineID, other.wineID)) {
            return false;
        }
        if (!Objects.equals(this.dateAdded, other.dateAdded)) {
            return false;
        }
        return true;
    }

    /**
     * overriden toString
     *
     * @return String
     */
    @Override
    public final String toString() {
        return "Wine{" + "wineID=" + wineID + ", wineName=" + wineName + ", winePrice=" + winePrice + ", wineImgUrl=" + wineImgUrl + ", dateAddes=" + dateAdded + '}';
    }

}
