package Connections;

import java.sql.*;

public class SQLite{
    protected static Connection instance;
    protected static Connection getInstance(ConnectionInfo connectionInfo){
        try{
            Class.forName("org.sqlite.JDBC");
            instance = DriverManager.getConnection("jdbc:sqlite:D:\\name.db");
            return instance;
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }
}
