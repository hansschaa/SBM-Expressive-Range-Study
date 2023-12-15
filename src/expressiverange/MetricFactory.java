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
}
