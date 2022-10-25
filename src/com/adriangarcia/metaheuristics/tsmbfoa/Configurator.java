package com.adriangarcia.metaheuristics.tsmbfoa;

/**
 * <b>Configurator</b> class creates a new configuration of the TS-MBFOA 
 * own parameters. <br> With its methods, the following are adjusted.
 * <ol>
 * <li> Sb is the number of bacteria </li>
 * <li> Nc is the number of chemotaxis cycles </li>
 * <li>&beta; is the scaling factor </li>
 * <li> R is the stepsize </li>
 * <li> Sr is the number of bacteria to reproduce</li>
 * <li> Repcycle is the reproduction frequency </li>
 * <li> GMAX is the number of generations</li>
 * </ol>
 * 
 * This class inherits the mechanisms of the Population class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */


public class Configurator extends Population{

    private int nc = 24;               
    private int evaluations = 30000;  
    private int bacteriaReproduce = 1; 
    private int repcycle = 100;        
    private double scalingFactor = 1.9; 
    private String description;        //Information presented 
    
    /**
     * Constructor method to create a new instance of the Configurator class.
     * <br>
     * Does not receive parameters.
     */
    public Configurator() {        
    }   

    /**
     * Method returns the stored value for the Nc parameter of the TS-MBFOA.
     * @return value of parameter Nc
     */
    public int getNc() {
        return nc;
    }

    /**
     * Method that assigns value to the Nc parameter of the TS-MBFOA.<br>
     * the number of chemotaxis cycles [1,Sb]
     * @param nc the nc to set
     */
    public void setNc(int nc) {
        this.nc = nc;
    }

    /**
     * Method returns the stored value for the &beta; parameter of the TS-MBFOA.
     * @return value of parameter scalingFactor (&beta;)
     */
    public double getScalingFactor() {
        return scalingFactor;
    }

    /**
     * Method that assigns value to the &beta; parameter of the TS-MBFOA.<br>
     * (&beta;) the scaling factor [0,2]
     * @param scalingFactor the scalingFactor (&beta;) to set
     */
    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    /**
     * Method returns the stored value for the Sr parameter of the TS-MBFOA.
     * @return value of parameter bacteriaReproduce (Sr)
     */
    public int getBacteriaReproduce() {
        return bacteriaReproduce;
    }

    /**
     * Method that assigns value to the Sr parameter of the TS-MBFOA.<br>
     * (sr) the number of bacteria to reproduce [1, Sb/2]
     * @param bacteriaReproduce the bacteriaReproduce (Sr) to set
     */
    public void setBacteriaReproduce(int bacteriaReproduce) {
        this.bacteriaReproduce = bacteriaReproduce;
    }

    /**
     * Method returns the stored value for the RepClycle parameter of the TS-MBFOA.
     * @return value of parameter repcycle
     */
    public int getRepcycle() {
        return repcycle;
    }

    /**
     * Method that assigns value to the RepCycle parameter of the TS-MBFOA.<br>
     * The reproduction frequency [1, GMAX/2]
     * @param repcycle the repcycle to set
     */
    public void setRepcycle(int repcycle) {
        this.repcycle = repcycle;
    }   

    /**
     * Method returns the stored value for the GMAX parameter of the TS-MBFOA.
     * @return value of parameter evaluations (GMAX)
     */
    public int getEvaluations() {
        return evaluations;
    }

    /**
     * Method that assigns value to the GMAX parameter of the TS-MBFOA.<br>
     * The number of generations [10000, 30000] 
     * @param evaluations the evaluations (GMAX) to set
     */
    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;        
    }       
    
    /**
     * Method that returns a description of the population of each run.    
     * 
     * @return value {@code String}
     * 
     * <p>The invocation is as follows:</p> <br>
     * <pre>{@code object.getDescription();}</pre>
     * 
     */
    @Override
    public String getDescription(){
        this.description = "";
        String asterisks = "";
        for (int i = 0; i < 60; i++) 
            asterisks += "*";                
        
        this.description += "\n" + asterisks + "\n"
                + "\t\t  Information presented\n"
                + asterisks + "\n"
                + "\t\tColumn 1: Number of bacteria\n"
                + "\t\tColumn 2 to " + ((this.getBacteriumMatix()[0].length - 2) / 2 + 1)
                + ": Variable values\n"
                + "\t\tColumn " + ((this.getBacteriumMatix()[0].length - 2) / 2 + 2)
                + ": Objective function evaluated\n"
                + "\t\tColumn " + ((this.getBacteriumMatix()[0].length - 2) / 2 + 3)
                + ": Constraints violation sum\n"
                + asterisks + "\n"
                + super.getDescription()+"\n"
                + asterisks + "\n";
                
        return this.description;
    }
            
}
