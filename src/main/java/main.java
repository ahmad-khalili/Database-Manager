import Connections.Database;
import Constants.ConnectionType;
import Connections.*;

import java.sql.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        //Database connection = new Connection();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter username: ");
        String user = sc.nextLine();

        System.out.println("Enter password: ");
        String pass = sc.nextLine();

        System.out.println("Enter port number: ");
        String port = sc.nextLine();

        System.out.println("Enter the database's name: ");
        String dbName = sc.nextLine();

        ConnectionInfo connectionInfo = new ConnectionInfo(user, pass, port, dbName);

        System.out.println("Select database type: \n" +
                "1- MySql\n" + "2- Sqlite\n" + "3- Oracle" + "\nChoice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case (1):
                connection = connection.createConnection(ConnectionType.MySql, connectionInfo);
        }
        //Database.exportData(ConnectionType.MySql);
        connection.displayTables(ConnectionType.MySql);
        connection.closeConnection(ConnectionType.MySql);
    }
}
