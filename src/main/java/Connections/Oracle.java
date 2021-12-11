package Connections;

import java.sql.*;

public class Oracle {
    protected static Connection instance;
    protected static Connection getInstance(ConnectionInfo connectionInfo){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            instance =DriverManager.getConnection("jdbc:oracle:thin:@localhost:" + connectionInfo.port + ":" +
                            connectionInfo.dbName, connectionInfo.username, connectionInfo.password);
            return instance;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
