/* package org.mmo.analyzer;

import org.mmo.db.DatabaseManager;

import java.io.*;

public class SqlFileAnalyzer implements FileAnalyzer {
    
    private final DatabaseManager databaseManager;
    
    public SqlFileAnalyzer(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public long analyseFile(File file, String tableName) {
        long rowsInserted = 0;
        databaseManager.connect();
        
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            rowsInserted = (int) fileReader.lines()
                    .filter(databaseManager::executeInsertStmt)
                    .count();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            databaseManager.closeStatement();
            databaseManager.closeConnection();
        }
        
        return rowsInserted;
    }
    
} */
