/*package FileAnalyzer;

import Connections.ConnectionInfo;
import Constants.ConnectionType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import Connections.Database;

import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.StringJoiner;

public class CsvFileAnalyzer implements FileAnalyzer {
    
    private final Connection database;
    ConnectionInfo connectionInfo = new ConnectionInfo("root","0000","3306","dbms");
    
    public CsvFileAnalyzer(Connection database) {
        this.database = database;
    }


    @Override
    public long analyseFile(File file, String tableName) {
        long rowsInserted = 0;
        database = Database.createConnection(ConnectionType.MySql, connectionInfo);
        
        try (
                FileWriter fileWriter = new FileWriter(new File("D:\\My_Work_Area\\DatabaseManager\\test\\test.sql"))
        ) {
            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> records = reader.readAll();
            String columnsNamesPart = "(" + String.join(",", records.get(0)) + ")";
            records.remove(0);
            for (String[] recordFields : records) {
                String insertStmt = getTableInsertStmt(tableName, columnsNamesPart, recordFields);
                boolean insertOperationResult = database.executeInsertStmt(insertStmt);
                if (insertOperationResult) {
                    writeExecutedSqlStatementsIntoFile(fileWriter, insertStmt);
                    rowsInserted++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } finally {
            database.closeStatement();
            database.closeConnection();
        }
        
        return rowsInserted;
    }
    
    private String getTableInsertStmt(String tableName, String columnsNamesPart, String[] fieldsValues) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String value : fieldsValues) {
            stringJoiner.add("'" + value + "'");
        }
        return "insert into " +
                tableName +
                " " +
                columnsNamesPart +
                " values(" +
                stringJoiner +
                ")";
    }
    
    private void writeExecutedSqlStatementsIntoFile(FileWriter fileWriter, String insertStmt) {
        try {
            fileWriter.write(insertStmt + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}*/
