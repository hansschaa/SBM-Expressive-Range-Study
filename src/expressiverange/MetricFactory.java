/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

import java.util.ArrayList;

/**
 *
 * @author Hans
 */
public class MetricFactory {
  
    public static float maxEmptySpacePercentage;
    public static float maxNegativeSpacePercentage;
    public static float maxInterestingElementsPercentaje;
    public static float maxSignificantJumpsCount;
    public static float maxLeniency;
    public static float minLeniency;
    public static float maxLinearity;
    public static float maxAvgEnemiesCompression;
    public static float maxEnemyCount;
    public static float maxDensity;
    
    //Metrics
    public static MetricMinMax emptySpacePercentage = new MetricMinMax();
    public static MetricMinMax negativeSpacePercentage = new MetricMinMax();
    public static MetricMinMax interestingElementsPercentaje = new MetricMinMax();
    public static MetricMinMax significantJumpsCount = new MetricMinMax();
    public static MetricMinMax leniency = new MetricMinMax();
    public static MetricMinMax linearity = new MetricMinMax();
    public static MetricMinMax avgEnemiesCompression = new MetricMinMax();
    public static MetricMinMax enemyCount = new MetricMinMax();
    public static MetricMinMax density = new MetricMinMax();
    
    public static float GetEmptySpaceFrecuency(LevelData levelData){
        float emptySpaceCount = 0;

        for (int i = 0; i < levelData.level.length; i++) {
            for (int j = 0; j < levelData.level[i].length; j++) {
                if (levelData.level[i][j] == '-') {
                    emptySpaceCount++;
                }
            }
        }

        return emptySpaceCount/levelData.totalTiles;
    }
    
    //Max tile space when Mario jumps is 4
    public static float GetNegativeSpace(LevelData levelData){
        float negativeSpaceCount = 0;
        
        //Total Negative Space 
        for (int i = 0; i < levelData.heights.length; i++) {
            if(levelData.heights[i] != 0)
                negativeSpaceCount+=4;
        }
        
        var blockCount = Utils.CountLevelChars(levelData.level, 'B');
        negativeSpaceCount+=4*blockCount;
        negativeSpaceCount-= blockCount;
        
        return (negativeSpaceCount/levelData.totalTiles);
    }
    
    public static float GetInterestingTiles(LevelData levelData){
        float coinandblockscount = Utils.CountLevelChars(levelData.level, new char[]{'B','C', 'E', 'P', 'S'});
        return coinandblockscount/levelData.totalTiles;
    }
    
    public static float GetSignificantJumps(LevelData levelData){
        
        float significantJumps = 0;
        boolean flag = false;

        //Holes
        for(int height : levelData.heights){
            if(height==0 && !flag){
                significantJumps++;
                flag = true;
            }
            
            else if(height!= 0 && flag){
                flag = false;
            } 
        }

        //Fix last hole
        significantJumps-=1;
        
        //Add enemies
        significantJumps+= Utils.CountLevelChars(levelData.level, new char[]{'E', 'S'});

        return significantJumps;
    }
    
    //Power ups:1
    //Cañones, tubos de flores y espacios tienen un valor de −0.5
    //Tortugas y grump: -1
    //Spiny: -1.5
    public static float GetLeniency(LevelData levelData){
        float leniency = 0;
        leniency += GetCharLeniency(levelData, 'E', -1);
        leniency += GetCharLeniency(levelData, 'S', -1.5f);
        leniency += GetCharLeniency(levelData, 'P', 1);
        leniency += GetHoleLeniency(levelData);
   
        return leniency; 
    }
    
    public static float GetCharLeniency(LevelData levelData, char character, float value){
        float charLeniency = 0;
        
        for(int i = 0; i < levelData.level.length; i++){
            for(int j = 0 ; j < levelData.level[0].length; j++){
                if(levelData.level[i][j] == character)
                    charLeniency+=value;
            }
        }
        
        return charLeniency;
    }
    
    public static float GetHoleLeniency(LevelData levelData){
        float holeLeniency = 0;
        
        for(int i = 0; i < levelData.heights.length; i++){
            if(levelData.heights[i] == 0)
                holeLeniency-=.5f;
        }
        
        return holeLeniency;
    }

    static float GetLinearity(LevelData levelData) {
        
        float sumaDiferencias = 0;

        for (int i = 0; i < levelData.heights.length - 1; i++) {
            sumaDiferencias += Math.abs(levelData.heights[i] - levelData.heights[i + 1]);
        }

        float linealidad = sumaDiferencias / (levelData.heights.length - 1);
        
        
        return linealidad;
    }

    static float GetEnemiesCompression(LevelData levelData) {
        int margin = 5;
        float totalEnemies = Utils.CountLevelChars(levelData.level, new char[]{'E','S'});
        float totalCompression = 0;
        if(totalEnemies == 0)
            return totalCompression;
        
        for (int j = 0; j < levelData.enemiesPos.length; j++) {
            if (levelData.enemiesPos[j] == 1) {
  
                var bottomLimit = Math.max(0, j-margin);
                var upperLimit = Math.min( j+margin,levelData.enemiesPos.length);

                for(int jj = bottomLimit; jj < upperLimit ; jj++){
                    if(jj != j && levelData.enemiesPos[jj] == 1)
                    {
                        totalCompression += Math.abs(j-jj);
                    }    
                }
            }
        }

        return totalCompression/ totalEnemies;
    }

    public static float GetDensity(LevelData levelData) {
        float density = 0;
        for(int j = 0; j < levelData.level[0].length; j++){
           for(int i =  levelData.level.length-1; i >0 ; i--){
               if(levelData.level[i][j] == 'X'){
                   density += GetCountColDensity(levelData.level, i,j);
                   break;
               }
           }
       }
          
       return density;
    }
    
    static int GetCountColDensity(char[][] level, int i, int j){
        int colCountDensity = 0;
        
        for(int ii = i-1 ; level[ii][j]== 'X'; ii--){
            colCountDensity++;
        }
        
        return colCountDensity;
    }
    
    public static void ComputeMaximumMinimum(ArrayList<LevelData> importedLevels){
        
        var firstElement = importedLevels.get(0);
        
        maxEmptySpacePercentage = firstElement.emptySpacePercentage;
        maxNegativeSpacePercentage = firstElement.negativeSpacePercentage;
        maxInterestingElementsPercentaje = firstElement.interestingElementsPercentaje;
        maxSignificantJumpsCount = firstElement.significantJumpsCount;
        maxLinearity = firstElement.linearity;
        maxLeniency = firstElement.leniency;
        minLeniency = firstElement.leniency;
        maxAvgEnemiesCompression = firstElement.avgEnemiesCompression;
        maxEnemyCount = firstElement.enemyCount;
        maxDensity = firstElement.density;
        
        // Itera sobre la lista para encontrar el máximo valor del campo específico
        for (LevelData levelData : importedLevels) {
            if (levelData.emptySpacePercentage > maxEmptySpacePercentage) {
                maxEmptySpacePercentage = levelData.emptySpacePercentage;
            }
            if (levelData.negativeSpacePercentage > maxNegativeSpacePercentage) {
                maxNegativeSpacePercentage = levelData.negativeSpacePercentage;
            }
            if (levelData.interestingElementsPercentaje > maxInterestingElementsPercentaje) {
                maxInterestingElementsPercentaje = levelData.interestingElementsPercentaje;
            }
            if (levelData.significantJumpsCount > maxSignificantJumpsCount) {
                maxSignificantJumpsCount = levelData.significantJumpsCount;
            }
            if (levelData.linearity > maxLinearity) {
                maxLinearity = levelData.linearity;
            }
            if (levelData.leniency > maxLeniency) {
                maxLeniency = levelData.leniency;
            }
            if (levelData.leniency < minLeniency) {
                minLeniency = levelData.leniency;
            }

            if (levelData.avgEnemiesCompression > maxAvgEnemiesCompression) {
                maxAvgEnemiesCompression = levelData.avgEnemiesCompression;
            }
            if (levelData.enemyCount > maxEnemyCount) {
                maxEnemyCount = levelData.enemyCount;
            }
            if (levelData.density > maxDensity) {
                maxDensity = levelData.density;
            }
        }
        
        // Leniency fix
        for (LevelData levelData : importedLevels) {
            levelData.leniency += Math.abs(minLeniency);
        }
        
        maxLeniency += Math.abs(minLeniency);
     
    }
    
    public static void ComputeMaximumMinimum2(ArrayList<LevelData> importedLevels){
    
        InitMetrics();

        // Itera sobre la lista para encontrar el máximo valor del campo específico
        for (LevelData levelData : importedLevels) {

            CheckMetric(emptySpacePercentage,levelData.emptySpacePercentage );
            CheckMetric(negativeSpacePercentage,levelData.negativeSpacePercentage);
            CheckMetric(interestingElementsPercentaje,levelData.interestingElementsPercentaje);
            CheckMetric(significantJumpsCount,levelData.significantJumpsCount);
            CheckMetric(linearity,levelData.linearity);
            CheckMetric(leniency,levelData.leniency);
            CheckMetric(avgEnemiesCompression,levelData.avgEnemiesCompression);
            CheckMetric(enemyCount,levelData.enemyCount);
            CheckMetric(density,levelData.density);
        }
        
        // Leniency fix
        for (LevelData levelData : importedLevels) {
            levelData.leniency += Math.abs(leniency.min);
        }
        
        leniency.max += Math.abs(leniency.min);
        
        System.out.println("Max: " + leniency.max);
        System.out.println("Min: " + leniency.min);
    }
    
    public static void CheckMetric(MetricMinMax metricMinMax, float value){
        if (value > metricMinMax.max) 
            metricMinMax.max = value;

        if(value < metricMinMax.min)
            metricMinMax.min = value;
    }
    
    public static void InitMetrics(){
        emptySpacePercentage.min = Float.MAX_VALUE;
        emptySpacePercentage.max = Float.MIN_VALUE;
        
        negativeSpacePercentage.min = Float.MAX_VALUE;
        negativeSpacePercentage.max = Float.MIN_VALUE;
        
        interestingElementsPercentaje.min = Float.MAX_VALUE;
        interestingElementsPercentaje.max = Float.MIN_VALUE;
        
        significantJumpsCount.min = Float.MAX_VALUE;
        significantJumpsCount.max = Float.MIN_VALUE;
        
        leniency.min = Float.MAX_VALUE;
        leniency.max = Float.MIN_VALUE;
        
        linearity.min = Float.MAX_VALUE;
        linearity.max = Float.MIN_VALUE;
        
        avgEnemiesCompression.min = Float.MAX_VALUE;
        avgEnemiesCompression.max = Float.MIN_VALUE;
        
        enemyCount.min = Float.MAX_VALUE;
        enemyCount.max = Float.MIN_VALUE;
        
        density.min = Float.MAX_VALUE;
        density.max = Float.MIN_VALUE;
    }
}
