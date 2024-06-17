/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expressiverange;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    
        String directory = "Levels"; // Directory containing the files
        File folder = new File(directory);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                int cont = 0;
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        char[][] level = ImportTextFile.leerMatrizDesdeArchivo(directory + "\\" + file.getName());

                        LevelData levelData = new LevelData(level, (cont+1)+ "");
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
        String[] levelDataList = new String[importedLevels.size()];
        //levelDataList[0] = csvFields;
        for(int i = 0 ; i < importedLevels.size(); i++){
            importedLevels.get(i).Normalize2();
            levelDataList[i] = importedLevels.get(i).GetData();
        }
        
         // Ordenar el ArrayList por el campo Id
        Collections.sort(importedLevels, new Comparator<LevelData>() {
            @Override
            public int compare(LevelData o1, LevelData o2) {
                return Integer.compare(Integer.parseInt(o1.filename), Integer.parseInt(o2.filename));
            }
        });
        
        ImportTextFile.exportDataToCSV(levelDataList, "data.csv");
    }
}
