/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.jls.MyEcomApp.model;

import java.sql.SQLException;
import java.util.List;

/**
 * Database Access Object interface 
 * @author Jennifer
 */
public interface IWineDao {

    public abstract List<Wine> getWineList(String tableName, int maxRecords)
            throws ClassNotFoundException, SQLException;

    public abstract Wine retrieveWine(String wineTableName, String wineIdColName, String wineId) throws ClassNotFoundException,
            SQLException;

    public abstract int deleteWineById(String tableName, String wineIdColName,
            Object wineId) throws ClassNotFoundException, SQLException;

    public int addWine(String tableName, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException;

    public int updateWineById(String wineTableName, List<String> colNames,
            List<Object> colValues, String wineIdColName, Object wineId) throws SQLException, ClassNotFoundException;

    public abstract DbAccessor getDb();

    public abstract void setDb(DbAccessor db);

    
}
