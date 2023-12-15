/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expressiverange;

/**
 *
 * @author Hans
 */
public class ExpressiveRange {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Get Level
        char[][] level = ImportTextFile.leerMatrizDesdeArchivo("level_1.txt");
        
        LevelData levelData = new LevelData(level);
        levelData.ComputeMetrics();

        levelData.ShowLevel();
        levelData.ShowMetrics();
        
        
    }
}
