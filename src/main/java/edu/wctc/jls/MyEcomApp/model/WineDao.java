/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import edu.wctc.jls.exeption.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * wineDao for not connection pooled connection to the wine database 
 *
 * @author Jennifer
 */
public class WineDao implements IWineDao {

    private DbAccessor db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    private static final String WINE_ID_COL = "wine_id";
    private static  final String WINE_NAME_COL = "wine_name";
    private static  final String DATE_COL = "date_added";
    private static  final String WINE_PRICE_COL = "wine_price";
    private static  final String WINE_IMAGE_URL = "wine_img_url";

    /**
     * Constructor for the Wine Dao class.
     *
     * @param db= database access object
     * @param driverClass string of the driver class value
     * @param url String url to the database
     * @param userName String value of the username needed to access the
     * database
     * @param password String value for the password needed to access the
     * database
     * @throws InvalidParameterException
     */
    public WineDao(DbAccessor db, String driverClass, String url, String userName, String password) {
        if (db == null || driverClass.isEmpty() || driverClass == null || url.isEmpty() || url == null || userName.isEmpty() || userName == null || password.isEmpty() || password == null) {
            throw new InvalidParameterException();
        }
        setDb(db);
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }

    /**
     * overrides the wine dao's delete wine by id method to delete a wine by the
     * id passed in
     *
     * @param tableName table name in the database to get the wine from
     * @param idColName name of the column that for the wine id
     * @param wineId id of the wine to delete from the db
     * @return int number of records deleted
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final int deleteWineById(String tableName, String idColName,
            Object wineId) throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || idColName.isEmpty() || idColName == null || wineId == null) {
            throw new InvalidParameterException();
        }
        db.openConnection(driverClass, url, userName, password);
        int recordsDeleted = db.deleteById(tableName, idColName, wineId);
        db.closeConnection();
        return recordsDeleted;
    }

    /**
     * overrides wine dao's retrieve wine method to get a single wine by the id
     *
     * @param tableName table name in the database to get the wine from
     * @param idColName name of the column that for the wine id
     * @param wineId id of the wine to get from the db
     * @return Wine a wine as indicated by the id passed in
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final Wine retrieveWine(String tableName, String idColName, String wineId) throws ClassNotFoundException,
            SQLException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || idColName.isEmpty() || idColName == null || wineId == null) {
            throw new InvalidParameterException();
        }
        db.openConnection(driverClass, url, userName, password);

        Map<String, Object> rawRec = db.getSingleRecord(
                tableName, idColName, wineId);

        Wine wine = new Wine();

        Object objId = rawRec.get(WINE_ID_COL);
        Integer id = (Integer) objId;
        wine.setWineID(id);

        Object objName = rawRec.get(WINE_NAME_COL);
        String wineName = (objName != null) ? objName.toString() : "";
        wine.setWineName(wineName);

        Object objDateAdded = rawRec.get(DATE_COL);
        Date dateAdded = (objDateAdded != null) ? (Date) objDateAdded : null;
        wine.setDateAdded(dateAdded);

        Object object = rawRec.get(WINE_PRICE_COL) == null ? "" : rawRec.get(WINE_PRICE_COL).toString();
        wine.setWinePrice(Double.parseDouble(object.toString()));
        String imageUrl = rawRec.get(WINE_IMAGE_URL) == null ? "" : rawRec.get(WINE_IMAGE_URL).toString();
        wine.setWineImgUrl(imageUrl);

        db.closeConnection();

        return wine;
    }

    /**
     * overrides wine dao's get wine list method to get a list of wines from the
     * indicated table
     *
     * @param tableName name of the table to get the list from
     * @param maxRecords number of max records to return from the query
     * @return List<Wine> a list of wines
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final List<Wine> getWineList(String tableName, int maxRecords)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (maxRecords < 1 || tableName.isEmpty() || tableName == null) {
            throw new InvalidParameterException();
        }
        db.openConnection(driverClass, url, userName, password);

        List<Wine> records = new ArrayList<>();
        List<Map<String, Object>> rawData = db.getAllRecords(tableName,
                maxRecords);

        for (Map<String, Object> rawRec : rawData) {
            Wine wine = new Wine();

            Object objId = rawRec.get(WINE_ID_COL);
            Integer wineId = (Integer) objId;
            wine.setWineID(wineId);

            Object objName = rawRec.get(WINE_NAME_COL);
            //check for null to be safe
            String wineName = (objName != null) ? objName.toString() : "";
            wine.setWineName(wineName);

            Object objDateAdded = rawRec.get(DATE_COL);
            //works a lot better if we check for null date 
            Date dateAdded = (objDateAdded != null) ? (Date) objDateAdded : null;
            wine.setDateAdded(dateAdded);

            Object priceObj = rawRec.get(WINE_PRICE_COL) == null ? "" : rawRec.get(WINE_PRICE_COL).toString();
            if(priceObj == null){
                throw new InvalidParameterException("Price is missing in db"); 
            }
            wine.setWinePrice(Double.parseDouble(priceObj.toString()));

            String imageUrl = rawRec.get(WINE_IMAGE_URL) == null ? "" : rawRec.get(WINE_IMAGE_URL).toString();
         
                wine.setWineImgUrl(imageUrl);
            

            records.add(wine);
        
        db.closeConnection();

       
    }
         return records;
    }

    /**
     * overrides the wine dao's update wine method- does it by wine id
     *
     * @param tableName name of the table being worked in
     * @param colNames list of the column names values are being updated in
     * @param colValues list of the values to update within the aforementioned
     * cols
     * @param wineIdColName column name of wine id in the table
     * @param wineId id of the wine to update
     * @return int updated records
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InvalidParameterException
     */
    @Override
    public final int updateWineById(String tableName, List<String> colNames,
            List<Object> colValues, String wineIdColName, Object wineId)
            throws SQLException, ClassNotFoundException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || colNames.isEmpty() || colNames == null || colValues.isEmpty() || colValues == null
                || wineId == null || wineIdColName.isEmpty() || wineIdColName == null) {
            throw new InvalidParameterException();
        }
        int updatedRecords = 0;
        db.openConnection(driverClass, url, userName, password);
        updatedRecords = db.updateRecord(tableName, colNames,
                colValues, wineIdColName, wineId);
        db.closeConnection();
        return updatedRecords;
    }

    /**
     * overrides I wine dao's addwine method to add a new wine to the database
     *
     * @param tableName- table name in the db to add the wine to
     * @param wineTableColNames list of column names in the table we want to
     * work with
     * @param wineTableColValues list of values to put into the aformentioned
     * cols
     * @return int (id of wine added to db)
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final int addWine(String tableName, List<String> wineTableColNames, List<Object> wineTableColValues)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || wineTableColNames.isEmpty() || wineTableColNames == null
                || wineTableColValues.isEmpty() || wineTableColValues == null) {
            throw new InvalidParameterException();
        }

        int wine_Added = 0;
        db.openConnection(driverClass, url, userName, password);
        wine_Added = db.insertRecord(
                tableName, wineTableColNames, wineTableColValues);
        db.closeConnection();

        return wine_Added;
    }

    /**
     * getter for the database Access object
     *
     * @return DbAccessor
     */
    public final DbAccessor getDb() {
        return db;
    }

    /**
     * setter for database accessor object
     *
     * @param db a database access object
     * @throws InvalidParameterException
     */
    public final void setDb(DbAccessor db) throws InvalidParameterException {
        if (db == null) {
            throw new InvalidParameterException();
        }
        this.db = db;
    }

    /**
     * getter for the driver class object
     *
     * @return the driver class
     */
    public final String getDriverClass() {
        return driverClass;
    }

    /**
     * setter for the driver class object
     *
     * @param driverClass
     * @throws InvalidParameterException
     *
     */
    public final void setDriverClass(String driverClass) throws InvalidParameterException {
        if (driverClass == null) {
            throw new InvalidParameterException();
        }
        this.driverClass = driverClass;
    }

    public final String getUrl() {
        return url;
    }

    /**
     * setter for the url for the database to be accessed
     *
     * @param url
     * @throws InvalidParameterException
     */
    public final void setUrl(String url) throws InvalidParameterException {
        if (url.isEmpty() || url == null) {
            throw new InvalidParameterException();
        }
        this.url = url;
    }

    /**
     * getter for the userName to access the database with
     *
     * @return String
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * setter for the username to access the database with
     *
     * @param userName
     * @throws InvalidParameterException
     */
    public final void setUserName(String userName) throws InvalidParameterException {
        if (userName.isEmpty() || userName == null) {
            throw new InvalidParameterException();
        }
        this.userName = userName;
    }

    /**
     * setter for the password to access the database with
     *
     * @return String
     */
    public final String getPassword() {
        return password;
    }

    /**
     * getter for the password to access the database with
     *
     * @param password
     * @throws InvalidParameterException
     */
    public final void setPassword(String password) throws InvalidParameterException {
        if (password.isEmpty() || password == null) {
            throw new InvalidParameterException();
        }
        this.password = password;
    }

    /**
     * obligatory toString method
     *
     * @return String
     */
    @Override
    public String toString() {
        return "WineDao{" + "db=" + db + ", driverClass=" + driverClass + ", url=" + url + ", userName=" + userName + ", password=" + password + ", WINE_ID_COL=" + WINE_ID_COL + ", WINE_NAME_COL=" + WINE_NAME_COL + ", DATE_COL=" + DATE_COL + ", WINE_PRICE_COL=" + WINE_PRICE_COL + ", WINE_IMAGE_URL=" + WINE_IMAGE_URL + '}';
    }

    @Override
    public final int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.driverClass);
        hash = 97 * hash + Objects.hashCode(this.url);
        hash = 97 * hash + Objects.hashCode(this.userName);
        hash = 97 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WineDao other = (WineDao) obj;
        if (!Objects.equals(this.driverClass, other.driverClass)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
