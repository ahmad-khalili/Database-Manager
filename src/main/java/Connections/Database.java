package Connections;

import Constants.ConnectionType;

import java.sql.Connection;

public abstract class Database implements Connection{
        public static Connection createConnection(String connectionType, ConnectionInfo connectionInfo){
            if (connectionType.equals(ConnectionType.MySql)){
                return MySQL.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Sqlite)){
                return SQLite.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Oracle)){
                return Oracle.getInstance(connectionInfo);
            }
            return null;
    }
    public static void exportData(String connectionType){
            if (connectionType.equals(ConnectionType.MySql)){
                MySQL.exportData();
            }
    }
    public static void closeConnection(String connectionType){
            if(connectionType.equals(ConnectionType.MySql)){
                MySQL.closeMySql();
            }
    }
    public static void displayTables(String connectionType){
            if(connectionType.equals(ConnectionType.MySql)){
                MySQL.displayTables();
            }
    }
}
