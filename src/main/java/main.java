import Authentication.Lab1Viewer;
import Connections.Database;
import Constants.ConnectionType;
import Connections.*;
import Logger.Logger;
import Authentication.Lab1Frame;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Lab1Viewer.loginAuth();
        String startCommand = sc.next();
        if (startCommand.equals("start")) {
            if (Lab1Frame.userType.equals("1")) {

                Database database = new Database();
                ConnectionInfo connectionInfo = null;

                System.out.println("1- Choose existing profile\n" +
                        "2- Create new Profile\n" +
                        "Choice: ");
                int choice1 = sc.nextInt();

                if (choice1 == 1) {
                    database.showProfiles();
                    System.out.println("Choice: ");
                    String choice2 = sc.next();
                    connectionInfo = database.readProfile(choice2);
                }
                if (choice1 == 2) {
                    System.out.println("Enter connection info in this format (user,password,port,name): ");
                    String info = sc.next();
                    database.createProfile(info);
                }

                System.out.println("Select database type: \n" +
                        "1- MySql\n" + "2- Sqlite\n" + "3- Oracle" + "\nChoice: ");
                int choice3 = sc.nextInt();
                if (choice3 == 1) {
                    database.createConnection(ConnectionType.MySql, connectionInfo);
                    System.out.println("1- View Tables\n" +
                            "2- Take Snapshot\n" +
                            "3- Import Data\n" +
                            "4- Restore Snapshot\n" +
                            "5- Export Data\n" +
                            "6- Edit Profile\n" +
                            "7- Remove Profile\n" +
                            "8- Exit");
                    int choice4 = sc.nextInt();
                    if (choice4 == 1) {
                        database.displayTables(ConnectionType.MySql);
                        System.out.println("1- Select Table\n" +
                                "2- Delete Row\n" +
                                "3- Update Table\n" +
                                "4- Drop Table");
                        int choiceTables = sc.nextInt();
                            System.out.println("Enter Table Name: ");
                            String table = sc.next();
                            if (choiceTables == 1) {
                                database.executeSelectStatement(ConnectionType.MySql, table);
                            }
                            if (choiceTables == 2){
                                database.executeSelectStatement(ConnectionType.MySql, table);
                                System.out.println("Choose row's id: ");
                                int choiceRows = sc.nextInt();
                                database.executeDeleteStatement(ConnectionType.MySql,table,choiceRows);
                            }
                            if (choiceTables == 4){
                                database.executeDropStatement(ConnectionType.MySql,table);
                            }
                    }
                    if (choice4 == 2) {
                        database.takeSnapshot(ConnectionType.MySql);
                    }
                    if (choice4 == 4){
                        database.importSnapshot(ConnectionType.MySql,"dbms.sql");
                    }
                    if (choice4 == 5) {

                    }
            /*if (choice2 == 4){
                database.exportData(ConnectionType.MySql);
            }*/
                    if (choice4 == 7) {
                        database.closeConnection(ConnectionType.MySql);
                    }
                }
                if (choice3 == 2) {
                    System.out.println("Not supported yet");
                }
                if (choice3 == 3) {
                    System.out.println("Not supported yet");
                }
                System.out.println(Lab1Frame.userType);
                Logger.SaveLogs();
            }
        }
    }
}