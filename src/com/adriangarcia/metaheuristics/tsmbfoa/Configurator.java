package com.adriangarcia.metaheuristics.tsmbfoa;

public class Configurator extends Population{
        
    private int nc = 24;                 // Número de ciclos quimiotáxicos [1,sb]
    private int evaluations = 30000;           // Número de evaluaciones  [15,000 ->  30,000] 
    private int bacteriaReproduce = 1; //(sr)Número de bacterias a reproducir [1, sb/2]
    private int repcycle = 100;       // Frecuencia de reproducción [1,gmax/2]
    private double scalingFactor = 1.9;    //(B)Factor de escalamiento [0,2] (beta) 
    private String description;
    

    public Configurator() {
        //...
    }   

    /**
     * @return the nc
     */
    public int getNc() {
        return nc;
    }

    /**
     * @param nc the nc to set
     */
    public void setNc(int nc) {
        this.nc = nc;
    }

    /**
     * @return the scalingFactor
     */
    public double getScalingFactor() {
        return scalingFactor;
    }

    /**
     * @param scalingFactor the scalingFactor to set
     */
    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    /**
     * @return the bacteriaReproduce
     */
    public int getBacteriaReproduce() {
        return bacteriaReproduce;
    }

    /**
     * @param bacteriaReproduce the bacteriaReproduce to set
     */
    public void setBacteriaReproduce(int bacteriaReproduce) {
        this.bacteriaReproduce = bacteriaReproduce;
    }

    /**
     * @return the repcycle
     */
    public int getRepcycle() {
        return repcycle;
    }

    /**
     * @param repcycle the repcycle to set
     */
    public void setRepcycle(int repcycle) {
        this.repcycle = repcycle;
    }   

    /**
     * @return the evaluations
     */
    public int getEvaluations() {
        return evaluations;
    }

    /**
     * @param evaluations the evaluations to set
     */
    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;        
    }       
    
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
