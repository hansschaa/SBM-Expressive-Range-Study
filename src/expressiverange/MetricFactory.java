/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

/**
 *
 * @author Hans
 */
public class MetricFactory {
    
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
        float coinandblockscount = Utils.CountLevelChars(levelData.level, new char[]{'B','C', 'E'});
        return coinandblockscount/levelData.totalTiles;
    }
    
    public static int GetSignificantJumps(LevelData levelData){
        
        int significantJumps = 0;
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
        significantJumps+= Utils.CountLevelChars(levelData.level, 'E');

        return significantJumps;
    }
    
    //Power ups:1
    //Cañones, tubos de flores y espacios tienen un valor de −0.5
    //Tortugas y grump: -1
    //Spiny: -1.5
    public static int GetLeniency(LevelData levelData){
        int leniency = 0;
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
}
