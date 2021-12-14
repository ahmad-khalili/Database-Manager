/*package org.mmo.analyzer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.mmo.db.DatabaseManager;

import java.io.*;
import java.util.List;
import java.util.StringJoiner;

public class CsvFileAnalyzer implements FileAnalyzer {
    
    private final DatabaseManager databaseManager;
    
    public CsvFileAnalyzer(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public long analyseFile(File file, String tableName) {
        long rowsInserted = 0;
        databaseManager.connect();
        
        try (
                FileWriter fileWriter = new FileWriter(new File("D:\\My_Work_Area\\DatabaseManager\\test\\test.sql"))
        ) {
            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> records = reader.readAll();
            String columnsNamesPart = "(" + String.join(",", records.get(0)) + ")";
            records.remove(0);
            for (String[] recordFields : records) {
                String insertStmt = getTableInsertStmt(tableName, columnsNamesPart, recordFields);
                boolean insertOperationResult = databaseManager.executeInsertStmt(insertStmt);
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
            databaseManager.closeStatement();
            databaseManager.closeConnection();
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
    
} */
