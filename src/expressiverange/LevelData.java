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
    public int significantJumpsCount;
    public int leniency;
    public float linearity;
    public float avgEnemiesCompression;
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
        System.out.println("-> Density: " + density);
    }
    
    public void ShowLevel(){
        Utils.PrintLevel(level);
    }

    public String GetData() {
        StringJoiner joiner = new StringJoiner(";");
        joiner.add(String.valueOf(filename))
            .add(String.valueOf(emptySpacePercentage))
            .add(String.valueOf(negativeSpacePercentage))
            .add(String.valueOf(interestingElementsPercentaje))
            .add(String.valueOf(significantJumpsCount))
            .add(String.valueOf(linearity))
            .add(String.valueOf(leniency))
            .add(String.valueOf(avgEnemiesCompression))
            .add(String.valueOf(density));
        var exData = joiner.toString().replace('.', ',');
        return exData;
    }
}
