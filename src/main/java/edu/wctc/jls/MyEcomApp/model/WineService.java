package edu.wctc.jls.MyEcomApp.model;

import edu.wctc.jls.exeption.InvalidParameterException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * A service class used to manage the Dao interface object.
 *
 * @author Jennifer
 */
public class WineService {

    private IWineDao wineDao;
    private static final int MIN_RECORDS = 1;
    private static final String DAO_EXCEPTION_MSG = "wine dao is null";

    /**
     * Constructor requires dao object.
     *
     * @param wineDao
     * @throws InvalidParameterException
     */
    public WineService(IWineDao wineDao) {
        if (wineDao == null) {
            throw new InvalidParameterException(DAO_EXCEPTION_MSG);
        }
        setWineDao(wineDao);
    }

    /**
     * returns a list of wines from the table- calls dao objects retrive wine
     * list method
     *
     * @param tableName- name of the table we want to work with
     * @param maxRecords max number of of records to get from the table
     * @return List<Wine>
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    public final List<Wine> retrieveWineList(String tableName, int maxRecords)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (tableName.isEmpty() || tableName == null || maxRecords < MIN_RECORDS) {
            throw new InvalidParameterException();
        }
        return wineDao.getWineList(tableName, maxRecords);
    }

    /**
     * calls the wine dao's retrieve Wine method to return a single wine
     *
     * @param wineTableName tablename in the database
     * @param wineIdColName colname of wine id in db
     * @param wineId id of the wine to retrieve
     * @return Wine- a single wine object
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    public final Wine retrieveWine(String wineTableName, String wineIdColName, String wineId) throws ClassNotFoundException,
            SQLException, InvalidParameterException {
        if (wineTableName.isEmpty() || wineTableName == null || wineIdColName.isEmpty() || wineIdColName == null || wineId.isEmpty() || wineId == null) {
            throw new InvalidParameterException();
        }
        return wineDao.retrieveWine(wineTableName, wineIdColName, wineId);
    }

    /**
     * calls the wine dao's delete wine by id method
     *
     * @param wineTableName table name to work with in the database
     * @param wineIdColName column name for the id value to be deleted
     * @param wineId- the id of the wine to be deleted
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    public final int deleteWineById(String wineTableName, String wineIdColName,
            String wineId) throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (wineTableName.isEmpty() || wineTableName == null || wineIdColName.isEmpty() || wineIdColName == null
                || wineId == null) {
            throw new InvalidParameterException();
        }
        return wineDao.deleteWineById(wineTableName, wineIdColName, wineId);
    }

    /**
     * calls the wineDao's update wine by id method. Updates a wine by the id
     * passed in.
     *
     * @param wineTableName table name for the database
     * @param colNames list of column names to work with
     * @param colValues list of column values to work with
     * @param wineIdColName the column name for wine id so we can find the right
     * wine
     * @param wineId the id of the wine to be updated in the database
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    public final int updateWineById(String wineTableName, List<String> colNames,
            List<Object> colValues, String wineIdColName, Object wineId)
            throws ClassNotFoundException, SQLException, InvalidParameterException {
        if (wineTableName.isEmpty() || wineTableName == null || colNames.isEmpty() || colNames == null
                || colValues.isEmpty() || colValues == null || wineId == null) {
            throw new InvalidParameterException();
        }

        return wineDao.updateWineById(wineTableName, colNames, colValues,
                wineIdColName, wineId);
    }

    /**
     * returns the wine dao's add wine method
     *
     * @param wineTableName
     * @param wineTableColNames
     * @param wineTableColValues
     * @return int
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InvalidParameterException
     */
    public final int addNewWine(String wineTableName, List<String> wineTableColNames,
            List<Object> wineTableColValues) throws ClassNotFoundException,
            SQLException, InvalidParameterException {
        if (wineTableName.isEmpty() || wineTableName == null || wineTableColNames.isEmpty() || wineTableColNames == null
                || wineTableColValues.isEmpty() || wineTableColValues == null) {
            throw new InvalidParameterException();
        }
        return wineDao.addWine(wineTableName, wineTableColNames,
                wineTableColValues);
    }

    /**
     * getter for the wine data access strategy object
     *
     * @return dao object
     */
    public final IWineDao getWineDao() {
        return wineDao;
    }

    /**
     * setter for the dao object
     *
     * @param wineDao the data access strategy object
     * @throws InvalidParameterException
     */
    public final void setWineDao(IWineDao wineDao) throws InvalidParameterException {
        if (wineDao == null) {
            throw new InvalidParameterException(DAO_EXCEPTION_MSG);
        }
        this.wineDao = wineDao;
    }

    /**
     * overriding hashcode
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.wineDao);
        return hash;
    }

    /**
     * overriding equals
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
        final WineService other = (WineService) obj;
        if (!Objects.equals(this.wineDao, other.wineDao)) {
            return false;
        }
        return true;
    }

    /**
     * obligatory toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return "WineService{" + "wineDao=" + wineDao + '}';
    }

}
