package Connections;

import Constants.ConnectionType;
import Exceptions.DatabaseException;
import Logger.Logger;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.sql.Connection;
import java.util.Scanner;

public class Database{

    public static ConnectionInfo readProfile(String id){
        String username = null;
        String password = null;
        String port = null;
        String dbName = null;
        File inputFile = new File("./Profiles.txt");
        try{
            Scanner in = new Scanner(inputFile);
            while(in.hasNextLine()){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                if(id.equals(stringArray[0])) {
                    username = stringArray[1];
                    password = stringArray[2];
                    port = stringArray[3];
                    dbName = stringArray[4];
                }
            }
            Logger.logInfo("Successfully read profile");
            return new ConnectionInfo(username,password,port,dbName);
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profile");
            System.out.println("FileNotFoundException: " + ex.getMessage());
            return null;
        }
    }
    public static void showProfiles(){
        File inputFile = new File("./Profiles.txt");
        try {
            Scanner in = new Scanner(inputFile);
            for(int i = 0;in.hasNextLine();i++){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                System.out.println((i+1) + "- " + stringArray[4]);
            }
            Logger.logInfo("Successfully read profiles");
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profiles");
            System.out.println("FileNotFoundException: " + ex.getMessage());
        }
    }
    public static void createProfile(String info){
        File inputFile = new File("./Profiles.txt");
        String[] stringArray = null;
        String index = null;
        try {
            Scanner in = new Scanner(inputFile);
            while (in.hasNextLine()) {
                String string = in.nextLine();
                stringArray = string.split(",");
            }
            index = stringArray[0];
            int i = Integer.parseInt(index);
            i +=1;
            index = Integer.toString(i);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Profiles.txt",true));
            writer.append("\n");
            writer.append(index + "," + info);
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /*public static void editProfile(String id, String info){
        File inputFile = new File("./Profiles.txt");
        try{
            Scanner in = new Scanner(inputFile);
            while(in.hasNextLine()){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                if(id.equals(stringArray[0])) {
                }
            }
            Logger.logInfo("Successfully read profile");
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profile");
            System.out.println("FileNotFoundException: " + ex.getMessage());
        }
    }*/

        public static Connection createConnection(String connectionType,ConnectionInfo connectionInfo){
        try {
            if (connectionType.equals(ConnectionType.MySql)) {
                Logger.logInfo("MySql connection created");
                return MySQL.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Sqlite)) {
                Logger.logInfo("Sqlite connection created");
                return SQLite.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Oracle)) {
                Logger.logInfo("Oracle connection created");
                return Oracle.getInstance(connectionInfo);
            }
                Logger.logError("Failed to create connection");
                throw new DatabaseException("Unsupported Schema");
        }
        catch (DatabaseException ex) {
            ex.getMessage();
            return null;
        }
    }
    public static void takeSnapshot(String connectionType){
            if (connectionType.equals(ConnectionType.MySql)){
                Logger.logInfo("Successfully taken Snapshot");
                MySQL.takeSnapshot();
            }
    }
    public static void importSnapshot(String connectionType, String s){
            if (connectionType.equals(ConnectionType.MySql)){
                Logger.logInfo("Successfully imported Snapshot");
                MySQL.importSnapshot(s);
            }
    }
    public static void closeConnection(String connectionType){
            if(connectionType.equals(ConnectionType.MySql)){
                Logger.logInfo("Closed Connection");
                MySQL.closeConnection();
            }
    }

    public static boolean executeInsertStmt(String connectionType, String insertStmt){
            if (connectionType.equals(ConnectionType.MySql)){
                MySQL.executeInsertStmt(insertStmt);
                return true;
            }
            return false;
    }

    public static boolean executeDeleteStatement(String connectionType,String tableName, int id){
            if (connectionType.equals(ConnectionType.MySql)){
                MySQL.deleteStatement(tableName, id);
                return true;
            }
            return false;
    }

    public static void displayTables(String connectionType){
            if(connectionType.equals(ConnectionType.MySql)){
                MySQL.displayTables();
            }
    }
    /*public static void exportData(String connectionType){
        if (connectionType.equals(ConnectionType.MySql)){
            MySQL.exportData();
        }
    }*/
    public static void executeSelectStatement(String connectionType, String tableName){
        if (connectionType.equals(ConnectionType.MySql)){
            MySQL.selectStatement(tableName);
        }
    }
}
