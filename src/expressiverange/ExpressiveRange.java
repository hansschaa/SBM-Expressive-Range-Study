/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expressiverange;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Hans
 */
public class ExpressiveRange {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<LevelData> importedLevels = new ArrayList<>();
        String csvFields = "ID;Empty Spaces;Negative Space;Interesting Elements;Significant Jumps;Linearity;Leniency;Avg Enemy Comp;Enemy Count;Density";
    
        String directory = "Levels"; // Directory containing the files
        File folder = new File(directory);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                int cont = 0;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        char[][] level = ImportTextFile.leerMatrizDesdeArchivo(directory + "\\" + file.getName());

                        LevelData levelData = new LevelData(level, file.getName());
                        levelData.ComputeMetrics();
                        importedLevels.add(levelData);
                        cont++;
                    }
                }
            }
        } else {
            System.out.println("The folder does not exist or is not a valid directory.");
        }

        //Normalize
        MetricFactory.ComputeMaximumMinimum2(importedLevels);
        
        
        //Export LevelData to .csv
        String[] levelDataList = new String[importedLevels.size()+1];
        levelDataList[0] = csvFields;
        for(int i = 0 ; i < importedLevels.size(); i++){
            importedLevels.get(i).Normalize2();
            
            levelDataList[i+1] = importedLevels.get(i).GetData();
        }
        
        ImportTextFile.exportDataToCSV(levelDataList, "data.csv");
    }
}
