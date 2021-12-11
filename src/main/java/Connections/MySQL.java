package Connections;

import java.sql.*;

public class MySQL{
    protected static Connection instance;
    protected static Connection getInstance(ConnectionInfo connectionInfo){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            instance =DriverManager.getConnection("jdbc:mysql://localhost:" + connectionInfo.port + "/"
                    + connectionInfo.dbName,connectionInfo.username, connectionInfo.password);
            return instance;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    protected static void exportData(){
        String[] filePath = new String[]{"/bin/ln","-s","C:\\Program Files\\MySQL\\MySQL\\Server\\s+8.0\\bin -u root -p  dbms"};
        String executeCmd = filePath + "C:\\dump.sql";
        try{
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int status = runtimeProcess.waitFor();
            if (status == 0){
                System.out.println("Success");
            }
            else{
                System.out.println("Fail");
            }
        }
        catch (Exception ex){
            System.out.println("exception: " + ex.getMessage());
        }
    }

    protected static void displayTables(){
        try {
            ResultSet rs = null;
            DatabaseMetaData meta = (DatabaseMetaData) instance.getMetaData();
            rs = meta.getTables(null, null, null, new String[]{
                    "TABLE"
            });
            int count = 0;
            System.out.println("Table Names: ");
            while(rs.next()){
                String tableName = rs.getString("TABLE_NAME");
                System.out.println(tableName);
                count++;
            }
            System.out.println(count + " Rows in set ");
        }
        catch (SQLException ex){

        }
    }

    protected static void closeMySql(){
        try{
            instance.close();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
