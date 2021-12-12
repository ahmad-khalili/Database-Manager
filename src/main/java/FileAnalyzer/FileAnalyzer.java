package org.mmo.analyzer;

import java.io.File;

public interface FileAnalyzer {
    
    long analyseFile(File file, String tableName);
    
}
