/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

/**
 *
 * @author Hans
 */
public class Utils {
    public static int[] GetHeightArray(char[][] level){
        int[] levelHeights = new int[level[0].length];
        
        for(int j = 0 ; j < level[0].length; j++){
            for(int i = 0 ; i < level.length; i++){
                if(level[i][j] == 'X'){
                    levelHeights[j] = i;
                    break;
                }
            }
        }
        return levelHeights;
    }
    
    public static void PrintLevel(char[][] level){
        for (char[] fila : level) {
            for (char caracter : fila) {
                System.out.print(caracter + " ");
            }
            System.out.println();
        }
    }
    
    public static int CountLevelChars(char[][] level, char levelChar){
        int charCount = 0;
        
        for (char[] fila : level) {
            for (char caracter : fila) {
                if(caracter==levelChar)
                    charCount++;
            }
        }
        
        return charCount;
    }
    
    public static int CountLevelChars(char[][] level, char[] levelChars){
        int charCount = 0;
        
        for (char[] fila : level) {
            for (char caracter : fila) {
                for(char levelChar : levelChars)
                    if(caracter==levelChar){
                        charCount++;
                        break;
                    }    
            }
        }
        
        return charCount;
    }
    
    public static int GetTotalLevelTiles(char[][] level){
        return level.length*level[0].length;
    }

    static int[] GetEnemiesXArray(char[][] level) {
        
        int[] enemiesArray = new int[level[0].length];
        
        for(int j = 0 ; j < level[0].length; j++){
            for(int i = 0 ; i < level.length; i++){
                if(level[i][j] == 'E' || level[i][j] == 'S'){
                    enemiesArray[j] = 1;
                    break;
                }
            }
        }
        
        return enemiesArray;
    }
}
