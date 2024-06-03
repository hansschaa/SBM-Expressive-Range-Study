/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

import java.util.StringJoiner;

/**
 *
 * @author Hans
 */
public class LevelData {

    public char[][] level;
    public String filename;
    public int totalTiles;
    //Alturas del nivel comenzando desde 0 (mas arriba) hasta 14 (la mas abajo, donde se ubica el suelo)
    public int[] heights;
    public int[] enemiesPos;
    public float emptySpacePercentage;
    public float negativeSpacePercentage;
    public float interestingElementsPercentaje;
    public float significantJumpsCount;
    public float leniency;
    public float linearity;
    public float avgEnemiesCompression;
    public float enemyCount;
    public float density;
    
    
    public LevelData(char[][] level, String filename) {
        this.level = level;
        this.filename = filename;
        this.heights = Utils.GetHeightArray(level);
        this.enemiesPos = Utils.GetEnemiesXArray(level); 
        this.totalTiles = Utils.GetTotalLevelTiles(level);
    }

    void ComputeMetrics() {
        emptySpacePercentage = MetricFactory.GetEmptySpaceFrecuency(this);
        negativeSpacePercentage = MetricFactory.GetNegativeSpace(this);
        interestingElementsPercentaje = MetricFactory.GetInterestingTiles(this);
        significantJumpsCount = MetricFactory.GetSignificantJumps(this);
        leniency = MetricFactory.GetLeniency(this);
        linearity = MetricFactory.GetLinearity(this);
        avgEnemiesCompression = MetricFactory.GetEnemiesCompression(this);
        enemyCount = Utils.CountLevelChars(this.level,new char[]{'E', 'S'} );
        density = MetricFactory.GetDensity(this);
    }
    
    public void ShowMetrics(){
        System.out.println("Metrics:");
        System.out.println("-> Empty Space: " + emptySpacePercentage);
        System.out.println("-> Negative Space: " + negativeSpacePercentage);
        System.out.println("-> Interesting Tiles: " + interestingElementsPercentaje);
        System.out.println("-> Significant Jumps: " + significantJumpsCount);
        System.out.println("-> Leniency: " + leniency);
        System.out.println("-> Linearity: " + linearity);
        System.out.println("-> Enemies Compression: " + avgEnemiesCompression);
        System.out.println("-> Enemies Count: " + enemyCount);
        System.out.println("-> Density: " + density);
    }
    
    public void ShowLevel(){
        Utils.PrintLevel(level);
    }

    public String GetData() {
        StringJoiner joiner = new StringJoiner(";");
        
        String resultado = filename.replace("Level_", "");
        resultado = resultado.replace("level_", "");
        resultado = resultado.replace(".txt", "");
        
        joiner.add(String.valueOf(resultado))
            .add(String.valueOf(emptySpacePercentage))
            .add(String.valueOf(negativeSpacePercentage))
            .add(String.valueOf(interestingElementsPercentaje))
            .add(String.valueOf(significantJumpsCount))
            .add(String.valueOf(linearity))
            .add(String.valueOf(leniency))
            .add(String.valueOf(avgEnemiesCompression))
            .add(String.valueOf(enemyCount))
            .add(String.valueOf(density));
        var exData = joiner.toString().replace('.', ',');
        return exData;
    }

    void Normalize() {
        
        //Empty Space
        emptySpacePercentage/=MetricFactory.maxEmptySpacePercentage;
        
        //Negative Space
        negativeSpacePercentage/=MetricFactory.maxNegativeSpacePercentage;
        
        //Interesting tiles
        interestingElementsPercentaje/=MetricFactory.maxInterestingElementsPercentaje;
        
        //Significant jumps
        significantJumpsCount/=MetricFactory.maxSignificantJumpsCount;
        
        //Leniency
        leniency /= MetricFactory.maxLeniency;
        
        //Linearity
        linearity/=MetricFactory.maxLinearity;
        linearity=1 - linearity;
        
        //Avg enemies
        avgEnemiesCompression/=MetricFactory.maxAvgEnemiesCompression;
        
        //Enemy Count
        enemyCount/=MetricFactory.maxEnemyCount;
        
        //Density
        density/=MetricFactory.maxDensity;
                
    }
    
    void Normalize2() {
        
        //Empty Space
        //emptySpacePercentage/=MetricFactory.maxEmptySpacePercentage;
        emptySpacePercentage = (emptySpacePercentage - MetricFactory.emptySpacePercentage.min) / 
                (MetricFactory.emptySpacePercentage.max - MetricFactory.emptySpacePercentage.min);
        
        //Negative Space
        //negativeSpacePercentage/=MetricFactory.maxNegativeSpacePercentage;
        negativeSpacePercentage = (negativeSpacePercentage - MetricFactory.negativeSpacePercentage.min) / 
                (MetricFactory.negativeSpacePercentage.max - MetricFactory.negativeSpacePercentage.min);
        
        //Interesting tiles
        //interestingElementsPercentaje/=MetricFactory.maxInterestingElementsPercentaje;
        interestingElementsPercentaje = (interestingElementsPercentaje - MetricFactory.interestingElementsPercentaje.min) / 
                (MetricFactory.interestingElementsPercentaje.max - MetricFactory.interestingElementsPercentaje.min);
        
        //Significant jumps
        //significantJumpsCount/=MetricFactory.maxSignificantJumpsCount;
        significantJumpsCount = (significantJumpsCount - MetricFactory.significantJumpsCount.min) / 
                (MetricFactory.significantJumpsCount.max - MetricFactory.significantJumpsCount.min);
        
        //Leniency
        //leniency /= MetricFactory.maxLeniency;
        leniency = (leniency - MetricFactory.leniency.min) / 
                (MetricFactory.leniency.max - MetricFactory.leniency.min);
        //leniency = 1 - leniency;
        
        //Linearity
        //linearity/=MetricFactory.maxLinearity;
        linearity = (linearity - MetricFactory.linearity.min) / 
                (MetricFactory.linearity.max - MetricFactory.linearity.min);
        linearity=1 - linearity;
        
        //Avg enemies
        //avgEnemiesCompression/=MetricFactory.maxAvgEnemiesCompression;
        avgEnemiesCompression = (avgEnemiesCompression - MetricFactory.avgEnemiesCompression.min) / 
                (MetricFactory.avgEnemiesCompression.max - MetricFactory.avgEnemiesCompression.min);
        
        //Enemy Count
        //enemyCount/=MetricFactory.maxEnemyCount;
        enemyCount = (enemyCount - MetricFactory.enemyCount.min) / 
                (MetricFactory.enemyCount.max - MetricFactory.enemyCount.min);
        
        //Density
        //density/=MetricFactory.maxDensity;
        density = (density - MetricFactory.density.min) / 
                (MetricFactory.density.max - MetricFactory.density.min);
                
    }
}
