
package edu.wctc.jls.MyEcomApp.model;

import edu.wctc.jls.exeption.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import javax.sql.DataSource;

/**
 * A low-level JDBC class which represents an implementation of a DbAccessor
 * 
 * Specifically for a MySql Database
 * @author Jennifer
 */
public class MySqlDbAccessor implements DbAccessor {

    private Connection conn;
    private Statement stmt;
    private ResultSet resultSet;
    private PreparedStatement prepStmt;

    /**
     *  opens a connection to the server 
     * @param driverClass String driverclass Values 
     * @param url String url value 
     * @param userName String username value needed to access the database 
     * @param password String password value needed to access the database 
     * @throws ClassNotFoundException
     * @throws SQLException 
     * @throws IllegalArgumentException
     */
    @Override
    public final void openConnection(String driverClass, String url, String userName,
            String password) throws ClassNotFoundException, SQLException {
        if (driverClass.isEmpty() || driverClass == null || userName.isEmpty()
                || userName == null || password.isEmpty() || password == null
                || url.isEmpty() || url == null) {
            throw new IllegalArgumentException();
        }

        Class.forName(driverClass);
        try{
        conn = DriverManager.getConnection(url, userName, password);
        }
         catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
    }

    /**
     * Open a connection using a connection pool configured on server.
     *
     * @param ds - a reference to a connection pool via a JNDI name, producing
     * this object. Typically done in a servlet using InitalContext object.
     * @throws SQLException - if ds cannot be established
     */
    @Override
    public final void openConnection(DataSource ds) throws SQLException {
        if(ds == null){
            throw new IllegalArgumentException(); 
        }
        try{
        conn = ds.getConnection();
        }
         catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
    }
    
/**
 * closes the connection to the server 
 * @throws SQLException 
 */
    @Override
    public final void closeConnection() throws SQLException {
        if (conn != null) {
            try{
            conn.close();        
        }
             catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
    }
    }

    /**
     * gets a single record from the database 
     * @param table table name to get the record from 
     * @param idColName column name where the id values are stored in the db 
     * @param recordId record id of the 
     * @return Map<String, Ojbect> a single record from the database 
     * @throws SQLException 
     */
    @Override
    public final Map<String, Object> getSingleRecord(String table, String idColName, String recordId) throws SQLException {
        if(table.isEmpty() || table == null || idColName.isEmpty() || idColName == null || recordId.isEmpty() || recordId == null){
        
        }
        String sql = "SELECT * FROM " + table + " WHERE " + idColName + " = ?";
try{
        prepStmt = conn.prepareStatement(sql);
        prepStmt.setObject(1, recordId);
        resultSet = prepStmt.executeQuery();

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> map = new LinkedHashMap<>();

        while (resultSet.next()) {
            for (int col = 1; col < colCount + 1; col++) {
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
                
            }
        }

        return map;
}
 catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
    }

    /**
     *  gets a list of all the records from the database table indicated 
     * @param table the table in the database to get the record list from 
     * @param maxRecords the number of max records to get from the table 
     * @return List<Map<String, Object>> all the records from the database 
     * @throws SQLException 
     */
    @Override
    public final List<Map<String, Object>> getAllRecords(String table, int maxRecords)
            throws SQLException {
        if (table == null || table.isEmpty() || maxRecords < 1) {
            throw new IllegalArgumentException("error");
        }
        String sql = "";

        if (maxRecords > 0) {
            sql = "SELECT * FROM " + table + " LIMIT " + maxRecords;
        } else {
            sql = "SELECT * FROM " + table;
        }
try{
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colCount = rsmd.getColumnCount();

        List<Map<String, Object>> results = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, Object> map = new LinkedHashMap<>();

            for (int col = 1; col < colCount + 1; col++) {
                map.put(rsmd.getColumnName(col), resultSet.getObject(col));
                System.out.println(rsmd.getColumnName(col));
            }
            results.add(map);
        }
        return results;
}
 catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
        
    }

    /**
     * deletes record by id from the database
     * @param table table name to look for the record and delete from 
     * @param pkName the primary key name for the database 
     * @param id id of the object to be deleted 
     * @return int number of the records deleted 
     * @throws SQLException 
     */
    @Override
    public final int deleteById(String table, String pkName, Object id) throws
            SQLException {
        if (table == null || table.isEmpty() || pkName == null || pkName.isEmpty()
                || id == null) {
            throw new IllegalArgumentException("error");
        }
        int recordsDeleted = 0;
        String sql = "DELETE FROM  " + table + " WHERE " + pkName
                + " = ?";
try{ 
        prepStmt = conn.prepareStatement(sql);
        prepStmt.setObject(1, id);
        recordsDeleted = prepStmt.executeUpdate();
} 
catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}

        return recordsDeleted;
    }

    /**
     * updates a record in the database. 
     * @param tableName the table name to update a record in 
     * @param colNames list of the name of the oolumns in the table we need to update 
     * @param colValues list of the actual values to put into the column 
     * @param idColName the name of the identifying id column for the record 
     * @param idColValue the value of the id col 
     * @return int of updated record 
     * @throws SQLException 
     */
    @Override
    public final int updateRecord(String tableName, List<String> colNames,
            List<Object> colValues, String idColName, Object idColValue) throws SQLException {
        if (tableName == null || tableName.isEmpty() || colNames == null
                || colValues == null || idColName == null
                || idColName.isEmpty() || idColValue == null) {
            throw new IllegalArgumentException("error");
        }
        int recordsUpdated = 0;

        String sql = "UPDATE " + tableName + " SET ";

        StringJoiner sj = new StringJoiner(",");

        for (String colName : colNames) {
            sj.add(colName + " = ?");
        }
        sql += sj.toString();

        sql += " WHERE " + idColName + " = " + " ? ";
        try { 
        prepStmt = conn.prepareStatement(sql);

        for (int i = 0; i < colNames.size(); i++) {
            prepStmt.setObject(i + 1, colValues.get(i));
        }
        

        prepStmt.setObject(colNames.size() + 1, idColValue);
        recordsUpdated = prepStmt.executeUpdate();
        } 
        catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}
        return recordsUpdated;
    }

    /**
     * creates a new record in the database 
     * @param tableName the table name where a record is to be added 
     * @param colNames list of the columns in the table a record is being inserted in 
     * @param colValues list of the values to be inserted into the columns in the table
     * @return int value of the record inserted into the db 
     * @throws SQLException 
     * @throws InvalidParameterException
     */
    @Override
    public final int insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        if (tableName.isEmpty() || tableName == null || colNames.isEmpty() || colNames == null || colValues.isEmpty()
                || colValues == null) {
          throw new InvalidParameterException();
        }
        int recordsInserted = 0;

        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(",", "(", ")");

        for (String colName : colNames) {
            sj.add(colName);
        }
        sql += sj.toString();
        sql += " VALUES ";

        sj = new StringJoiner(",", "(", ")");

        for (Object colValue : colValues) {
            sj.add("?");
        }

        sql += sj.toString();
try{
        prepStmt = conn.prepareStatement(sql);

        for (int i = 0; i < colValues.size(); i++) {
            prepStmt.setObject(i + 1, colValues.get(i));
        }

        recordsInserted = prepStmt.executeUpdate();
}
 catch(SQLException e) {
    throw new SQLException(e.getMessage(), e.getCause()); 
}

        return recordsInserted;
    }
    
/**
 * overridden hashcode
 * @return 
 */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.conn);
        hash = 47 * hash + Objects.hashCode(this.stmt);
        hash = 47 * hash + Objects.hashCode(this.resultSet);
        hash = 47 * hash + Objects.hashCode(this.prepStmt);
        return hash;
    }
/**
 * overridden equals 
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
        final MySqlDbAccessor other = (MySqlDbAccessor) obj;
        if (!Objects.equals(this.conn, other.conn)) {
            return false;
        }
        if (!Objects.equals(this.stmt, other.stmt)) {
            return false;
        }
        if (!Objects.equals(this.resultSet, other.resultSet)) {
            return false;
        }
        if (!Objects.equals(this.prepStmt, other.prepStmt)) {
            return false;
        }
        return true;
    }
/**
 * obligatory toString
 * @return 
 */
    @Override
    public String toString() {
        return "MySqlDbAccessor{" + "conn=" + conn + ", stmt=" + stmt + ", resultSet=" + resultSet + ", prepStmt=" + prepStmt + '}';
    }
    
}
