/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

/**
 *
 * @author Hans
 */
public class LevelData {

    public char[][] level;
    public int totalTiles;
    //Alturas del nivel comenzando desde 0 (mas arriba) hasta 14 (la mas abajo, donde se ubica el suelo)
    public int[] heights;
    public float emptySpacePercentage;
    public float negativeSpacePercentage;
    public float interestingElementsPercentaje;
    public int significantJumpsCount;
    public int leniency;
    public float linearity;
    
    public LevelData(char[][] level) {
        this.level = level;
        this.heights = Utils.GetHeightArray(level);
        this.totalTiles = Utils.GetTotalLevelTiles(level);
    }

    void ComputeMetrics() {
        emptySpacePercentage = MetricFactory.GetEmptySpaceFrecuency(this);
        negativeSpacePercentage = MetricFactory.GetNegativeSpace(this);
        interestingElementsPercentaje = MetricFactory.GetInterestingTiles(this);
        significantJumpsCount = MetricFactory.GetSignificantJumps(this);
        leniency = MetricFactory.GetLeniency(this);
        linearity = MetricFactory.GetLinearity(this);
    }
    
    public void ShowMetrics(){
        System.out.println("Metrics:");
        System.out.println("-> Empty Space: " + emptySpacePercentage);
        System.out.println("-> Negative Space: " + negativeSpacePercentage);
        System.out.println("-> Interesting Tiles: " + interestingElementsPercentaje);
        System.out.println("-> Significant Jumps: " + significantJumpsCount);
        System.out.println("-> Leniency: " + leniency);
        System.out.println("-> Linearity: " + linearity);
    }
    
    public void ShowLevel(){
        Utils.PrintLevel(level);
    }
}
