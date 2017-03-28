/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import edu.wctc.jls.exeption.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

/**
 *
 * @author Jennifer
 */
public class ConnectionPoolWineDao implements IWineDao {

    private static final String WINE_ID_COL = "wine_id";
    private static final String WINE_NAME_COL = "wine_name";
    private static final String DATE_COL = "date_added";
    private static final String WINE_PRICE_COL = "wine_price";
    private static final String WINE_IMAGE_URL = "wine_img_url";
    private static final double DEFAULT_WINE_PRICE = 0.00;
    private static final int MIN_RECORDS = 1;
    private DataSource ds;
    private DbAccessor db;

    /**
     * Constructor to setup Connection pool looking at my perfect final from
     * advanced java, constructors do not need validation
     *
     * @param ds datasource
     * @param db database
     */
    public ConnectionPoolWineDao(DataSource ds, DbAccessor db) {
        this.ds = ds;
        this.db = db;
    }

    /**
     * overrides from IWineDao deletes a wine by id
     *
     * @param tableName- name of db table to be modified
     * @param wineIdCol- the column being queried/modified
     * @param wineId - the id of the wine we are working with
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final int deleteWineById(String tableName, String wineIdCol,
            Object wineId) throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || wineIdCol.isEmpty() || wineIdCol == null) {
            throw new InvalidParameterException();
        }
        //db.openConnection(driverClass, url, userName, password);
        db.openConnection(ds);
        int recordsDeleted = db.deleteById(tableName, wineIdCol, wineId);
        db.closeConnection();
        return recordsDeleted;
    }

    /**
     * overrides from IWineDao
     *
     * @param wineTableName - name of the table from wine db
     * @param wineIdColName - the column name to get the wine id from
     * @param wineId - the id of the wine we want to get
     * @return Wine
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final Wine retrieveWine(String wineTableName, String wineIdColName, String wineId) throws ClassNotFoundException,
            SQLException, InvalidParameterException {
        // db.openConnection(driverClass, url, userName, password);

        if (wineTableName.isEmpty() || wineTableName == null || wineId.isEmpty() || wineId == null || wineIdColName.isEmpty() || wineIdColName == null) {
            throw new InvalidParameterException();
        }
        db.openConnection(ds);

        Map<String, Object> rawRec = db.getSingleRecord(
                wineTableName, wineIdColName, wineId);

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

        Object urlObj = rawRec.get(WINE_IMAGE_URL) == null ? "" : rawRec.get(WINE_IMAGE_URL).toString();

        wine.setWineImgUrl((urlObj.toString()));

        db.closeConnection();

        return wine;
    }

    /**
     * overrides from IWineDao to return a wine list
     *
     * @param tableName - the name of the table to get the list from
     * @param maxRecords - number of max records to return from the db
     * @return List<Wine>
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final List<Wine> getWineList(String tableName, int maxRecords)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        //   db.openConnection(driverClass, url, userName, password);

        if (tableName.isEmpty() || tableName == null || maxRecords < MIN_RECORDS) {
            throw new InvalidParameterException();
        }

        db.openConnection(ds);

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

            Object priceObj = rawRec.get(WINE_PRICE_COL);
            //playing around with this. If the price is null (or any other paramemeter for that matter), nothing comes back in the list
            // currently with jquery validation, it is impossible to save a record from the view without all the needed paramemters.  This is one way to ensure that a
            //wine obj has a "valid" price and the that list is visible. Still working on these kinks.... need to look at checking that in the controller
            //update- 3/28- i did look at in in the controller, and I added logic there to prevent user saving if any of the inputs are null. Still like this though, because
            // what if somehow there is a "null"" price in the database? this will let the list come back anyways.
            if (priceObj == null) {
                // priceObj = DEFAULT_NULL_PRICE;
                //(Double.parseDouble(priceObj.toString()));
                wine.setWinePrice(DEFAULT_WINE_PRICE);
                // throw new InvalidParameterException();
            } else {
                wine.setWinePrice(Double.parseDouble(priceObj.toString()));
            }

            String imageUrl = rawRec.get(WINE_IMAGE_URL) == null ? "" : rawRec.get(WINE_IMAGE_URL).toString();

            wine.setWineImgUrl(imageUrl);

            records.add(wine);
        }
        db.closeConnection();

        return records;
    }

    /**
     * overrides from IWineDao
     *
     * @param wineTableName name of the table in the db - what if i change it?!
     * @param colNames names of the columns in the db
     * @param colValues values for the columns
     * @param wineIdColName col name for the id col of wine
     * @param wineId id of wine being updated
     * @return int
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InvalidParameterException
     */
    @Override
    public final int updateWineById(String wineTableName, List<String> colNames,
            List<Object> colValues, String wineIdColName, Object wineId)
            throws SQLException, ClassNotFoundException, InvalidParameterException {
        if (wineTableName.isEmpty() || wineTableName == null || colNames.isEmpty() || colNames == null || colValues.isEmpty() || colValues == null || wineIdColName.isEmpty() | wineIdColName == null
                || wineId == null) {
            throw new InvalidParameterException();
        }
        int updatedRecords = 0;
        //   db.openConnection(driverClass, url, userName, password);
        db.openConnection(ds);
        updatedRecords = db.updateRecord(wineTableName, colNames,
                colValues, wineIdColName, wineId);
        db.closeConnection();
        return updatedRecords;
    }

    /**
     * overrides from IWineDao method to add wine
     *
     * @param tableName - name of the table we want to add this wine item to
     * @param wineTableColNames - col names we need to add values
     * @param wineTableColValues list of values we want to add into the cols
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    @Override
    public final int addWine(String tableName, List<String> wineTableColNames, List<Object> wineTableColValues)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (wineTableColNames.isEmpty() || wineTableColNames == null || tableName.isEmpty() || tableName == null || wineTableColValues.isEmpty() || wineTableColValues == null) {
            throw new InvalidParameterException();
        }
        int wine_Added = 0;
        //  db.openConnection(driverClass, url, userName, password);
        db.openConnection(ds);
        wine_Added = db.insertRecord(
                tableName, wineTableColNames, wineTableColValues);
        db.closeConnection();

        return wine_Added;

    }

    /**
     * getter for the DatabaseStrategy object
     *
     * @return DatabaseStrategy Object
     */
    @Override
    public final DbAccessor getDb() {
        return db;
    }

    /**
     * Setter for the Database Strategy Object
     *
     * @param db
     * @throws InvalidParameterException
     */
    @Override
    public final void setDb(DbAccessor db) throws InvalidParameterException {
        if (db == null) {
            throw new InvalidParameterException();
        }
        this.db = db;
    }

    /**
     * getter for the datasource object
     *
     * @return DataSource
     */
    public final DataSource getDs() {
        return ds;
    }

    /**
     * setter for the datasource object
     *
     * @param ds
     * @throws InvalidParameterException
     */
    public final void setDs(DataSource ds) throws InvalidParameterException {
        if (ds == null) {
            throw new InvalidParameterException();
        }
        this.ds = ds;
    }

    public static void main(String[] args) throws Exception {

        // Sets up the connection pool and assigns it a JNDI name
        NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

            @Override
            public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException {
                return new InitialContextFactory() {

                    @Override
                    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
                        return new InitialContext() {

                            private Hashtable<String, DataSource> dataSources = new Hashtable<>();

                            @Override
                            public Object lookup(String name) throws NamingException {

                                if (dataSources.isEmpty()) { //init datasources
                                    MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
                                    ds.setURL("jdbc:mysql://localhost:3306/wine_store");
                                    ds.setUser("root");
                                    ds.setPassword("admin");
                                    // Association a JNDI name with the DataSource for our Database
                                    dataSources.put("jdbc/wine_store", ds);

                                    //add more datasources to the list as necessary
                                }

                                if (dataSources.containsKey(name)) {
                                    return dataSources.get(name);
                                }

                                throw new NamingException("Unable to find datasource: " + name);
                            }
                        };
                    }

                };
            }

        });

        // Find the connection pool and create the DataSource
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/wine_store");

        IWineDao dao = new ConnectionPoolWineDao(ds, new MySqlDbAccessor());

        //    List<Wine> authors = dao.getWineList("wine", 10);
        System.out.println(dao.getWineList("wine", 10));
    }

}
