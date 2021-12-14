package Connections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.opencsv.CSVWriter;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class MySQL{
    protected static ConnectionInfo connectionInfo1;
    protected static Connection instance;
    protected static Statement statement;
    protected static Connection getInstance(ConnectionInfo connectionInfo){
        try{
            connectionInfo1 = connectionInfo;
            Class.forName("com.mysql.cj.jdbc.Driver");
            instance =DriverManager.getConnection("jdbc:mysql://localhost:" + connectionInfo.port + "/"
                    + connectionInfo.dbName,connectionInfo.username, connectionInfo.password);
            return instance;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static void takeSnapshot() {
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\" -u" +
                    connectionInfo1.username + " -p" +
                    connectionInfo1.password + " " + connectionInfo1.dbName + " > C:\\Users\\ahmad\\Documents\\" + connectionInfo1.dbName +".sql"});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void exportData(Connection connection){
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\" -u" +
                            connectionInfo1.username + " -p" +
                            connectionInfo1.password + " " + connectionInfo1.dbName + " > C:\\Users\\ahmad\\Documents\\" + connectionInfo1.dbName +".sql"});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void importSnapshot(String s) {
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql\" -u" +
                            connectionInfo1.username + " -p" +
                            connectionInfo1.password + " " + connectionInfo1.dbName + " < C:\\Users\\ahmad\\Documents\\" + s});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        protected static void displayTables(){
        try {
            DatabaseMetaData meta = instance.getMetaData();
            ResultSet rs = meta.getTables(null, null,null,new String[]{"TABLE"});
            while(rs.next()){
                System.out.println(rs.getString("TABLE_NAME"));
            }
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
    //INSERT STATEMENT
    public static boolean executeInsertStmt(String insertStmt) {
        try {
            statement = instance.createStatement();
            int recordInserted = statement.executeUpdate(insertStmt);
            return recordInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //SELECT STATEMENT(Shahd)
    public static void selectStatement(String tableName){
        try {
            PreparedStatement ps = instance.prepareStatement("SELECT * from " + tableName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    //UPDATE STATEMENT(Farah)
    public void updateStatement(Integer id){
        String query=" UPDATE tableName SET = ? = ? where id = ?" ;
        try{
            Statement stmt = instance.createStatement();
            // execute the delete statement
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //DELETE STATEMENT(Farah)
    public static void deleteStatement(String tableName, int id){
        String sql = "DELETE FROM " + tableName + " where id = ?";
        try{
            PreparedStatement stmt = instance.prepareStatement(sql);
            // set the corresponding param
            stmt.setInt(1, id);
            // execute the delete statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Connection Closed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
<<<<<<< HEAD



}
=======
    //UPDATE STATEMENT(Farah)
    public static void updateStatement(String updateStmt){

    }



            public void deleteRow(Integer id) {
                String sql = "DELETE FROM tableName where id = ?";

                try{
                     PreparedStatement stmt = instance.prepareStatement(sql);
                    // set the corresponding param
                    stmt.setInt(1, id);
                    // execute the delete statement
                    stmt.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }



            public void deleteColumn( String column) {
                String query = "ALTER TABLE tableName DROP column = ?";
                try{
                    Statement stmt = instance.createStatement();
                    // set the corresponding param
                    // execute the delete statement
                    stmt.executeUpdate(query);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            public void deleteTable(){
                String sql = "DROP TABLE tableName";

                try{
                    Statement stmt = instance.createStatement();
                    // execute the delete statement
                    stmt.executeUpdate(sql);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

        }

        public void renameTable() throws SQLException {
            ResultSet result = null;

            String newTableName = result.getString("new_name");
            String query= "RENAME TABLE tableName TO newTableName";

            try{
                Statement stmt = instance.createStatement();

                // execute the delete statement
                stmt.executeUpdate(query);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            }

            public void updateTable(Integer id){
               String query=" UPDATE tableName SET = ? = ? where id = ?" ;
                try{
                    Statement stmt = instance.createStatement();
                    // execute the delete statement
                    stmt.executeUpdate(query);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }



            }


    }


>>>>>>> 4329446a53fecd87248f04608c847db7ca07ee7f
