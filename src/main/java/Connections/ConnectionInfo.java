package Connections;

import Constants.ConnectionType;

public class ConnectionInfo {
    String username;
    String password;
    String port;
    String dbName;

    public ConnectionInfo(String name, String passcode, String portNumber, String databaseName){
        username = name;
        password = passcode;
        port = portNumber;
        dbName = databaseName;
    }
}
