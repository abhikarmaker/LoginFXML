package assignment4.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:database/AccountsDB;create=true";
    public static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupUsers();
    }
    
    
    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creating Table Users in Database
     */
    void setupUsers() {
        String TABLE_NAME = "USERS";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER NOT NULL "
                        + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
                        + "(START WITH 99, INCREMENT BY 1),\n"
                        + "fname varchar(255),\n"
                        + "mname varchar(255),\n"
                        + "lname varchar(255),\n"
                        + "phonenumber varchar(255),\n"
                        + "email varchar(255),\n"
                        + "date date,\n"
                        + "accountName varchar(255),\n"
                        + "pass varchar(255),\n"
                        + "cpass varchar(255),\n"
                        + "active boolean\n"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        } finally {
        }
    }
    
    /**
     * 
     * @param query
     * @return 
     */
    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
            //result.close();
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler " + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }
    
    /**
     * 
     * @param qu
     * @return 
     */
    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Excption at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
}
