/*package org.mmo.analyzer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mmo.db.DatabaseManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class JsonFileAnalyzer implements FileAnalyzer {
    
    private final DatabaseManager databaseManager;
    
    public JsonFileAnalyzer(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public long analyseFile(File file, String tableName) {
        long rowsInserted = 0;
        databaseManager.connect();
        
        try (
                FileWriter fileWriter = new FileWriter(new File("D:\\My_Work_Area\\DatabaseManager\\test\\test.sql"))
        ) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(file));
            for (Object object : jsonArray) {
                JSONObject record = (JSONObject) object;
                String insertStmt = getTableInsertStmt(tableName, record);
                boolean insertOperationResult = databaseManager.executeInsertStmt(insertStmt);
                if (insertOperationResult) {
                    writeExecutedSqlStatementsIntoFile(fileWriter, insertStmt);
                    rowsInserted++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            databaseManager.closeStatement();
            databaseManager.closeConnection();
        }
        
        return rowsInserted;
    }
    
    private String getTableInsertStmt(String tableName, JSONObject record) {
        StringBuilder insertStmt = new StringBuilder();
        insertStmt.append("insert into ")
                .append(tableName)
                .append(" ")
                .append(record.keySet().toString().replace('[', '(').replace(']', ')'))
                .append(" values(");
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Object value : record.values()) {
            stringJoiner.add("'" + value.toString() + "'");
        }
        insertStmt.append(stringJoiner).append(")");
        
        return insertStmt.toString();
    }
    
    private void writeExecutedSqlStatementsIntoFile(FileWriter fileWriter, String insertStmt) {
        try {
            fileWriter.write(insertStmt + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}*/
