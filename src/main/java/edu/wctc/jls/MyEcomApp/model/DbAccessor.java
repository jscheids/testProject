package edu.wctc.jls.MyEcomApp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * Interface/strategy class for database access
 * http://softwareengineering.stackexchange.com/questions/96947/why-should-i-declare-a-class-as-an-abstract-class
 * http://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
 * ""declared abstract bc it wouldn't make sense to instantiate it" 
 * @author Jennifer
 */
public interface DbAccessor {

    public abstract int deleteById(String tableName, String idColName, Object id)
            throws SQLException;

    public abstract int insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;

    public abstract void openConnection(DataSource ds) throws SQLException;

    public abstract List<Map<String, Object>> getAllRecords(String table,
            int maxRecords) throws SQLException;

    public abstract Map<String, Object> getSingleRecord(String table, String idColName, String recordId) throws SQLException;

    public abstract int updateRecord(String tableName, List<String> colNamesToSet,
            List<Object> colValues, String conditionColName, Object conditionColValue) throws SQLException;

    public abstract void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException,
            SQLException;

    public abstract void closeConnection() throws SQLException;
}
