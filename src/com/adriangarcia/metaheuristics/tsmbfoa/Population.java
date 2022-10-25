package com.adriangarcia.metaheuristics.tsmbfoa;


/**
 * <b>Population</b> class is the initial process of the TS-MBFOA, 
 * where the initial random population is created based on uniform 
 * distribution and using the NumberRandom class. This class also 
 * has the mechanism to sort the solution space for the CNOP based 
 * on feasibility rules.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Population {

    private final NumberRandom numberRandom;
    private String description;
    private double[][] bacteriumMatix;  
    private int sb = 40;            
    private double stepSize = 0.001;  
    
    /**
     * Constructor method that instantiates an object of the NumberRandom class.
     */
    public Population() {
        this.numberRandom = new NumberRandom();
    }
    
    /**
     * Method that takes care of starting the population of bacteria 
     * with size Sb X number of variables.
     * 
     * @param problem object of the class Problem
     * @return Matrix representing the solution space for the CNOP.
     */
    protected double[][] startPopulation(Problem problem) {
        this.bacteriumMatix = new double[this.sb][problem.getNumberAssignedVariable() * 2 + 2];
        for (int i = 0; i < this.sb; i++) {
            for (int j = 0; j < problem.getNumberAssignedVariable(); j++) {
                if (problem.getRankVariable() != null) {
                    this.bacteriumMatix[i][j] = this.numberRandom.getRandomRankUnif(problem.getRankVariable()[j][0], problem.getRankVariable()[j][1]);
                    this.bacteriumMatix[i][problem.getNumberAssignedVariable() + j] = this.numberRandom.getRandomRankUnif(problem.getRankVariable()[j][0], problem.getRankVariable()[j][1]);// * this.stepSize;
                } else {
                    this.bacteriumMatix[i][j] = this.numberRandom.getRandomUnif();
                    this.bacteriumMatix[i][problem.getNumberAssignedVariable() + j] = this.numberRandom.getRandomUnif();
                }
                
            }
        }
        return this.bacteriumMatix;
    }
    
    
    /**
     * This method uses an ordering technique based on feasibility rules. 
     * 
     * @param numberVariables of CNOP design variables
     */
    protected void sortPopulation(int numberVariables) {
        double[] aux = new double[numberVariables * 2 + 2];
        for (double[] bacteriumMatix1 : this.bacteriumMatix) {
            for (int j = 0; j < this.bacteriumMatix.length - 1; j++) {
                
                if ((this.bacteriumMatix[j][numberVariables * 2 + 1] == 0) && (this.bacteriumMatix[j + 1][numberVariables * 2 + 1] == 0)) {
                    if (this.bacteriumMatix[j][numberVariables * 2 + 1] > this.bacteriumMatix[j + 1][numberVariables * 2 + 1]) {
                        for (int k = 0; k < this.bacteriumMatix[j].length; k++) {
                            aux[k] = this.bacteriumMatix[j][k];
                            this.bacteriumMatix[j][k] = this.bacteriumMatix[j + 1][k];
                            this.bacteriumMatix[j + 1][k] = aux[k];
                        }
                    }
                }
                if ((this.bacteriumMatix[j][numberVariables * 2 + 1] > 0) && (this.bacteriumMatix[j + 1][numberVariables * 2 + 1] > 0)) {
                    if (this.bacteriumMatix[j][numberVariables * 2 + 1] > this.bacteriumMatix[j + 1][numberVariables * 2 + 1]) {
                        for (int k = 0; k < this.bacteriumMatix[j].length; k++) {
                            aux[k] = this.bacteriumMatix[j][k];
                            this.bacteriumMatix[j][k] = this.bacteriumMatix[j + 1][k];
                            this.bacteriumMatix[j + 1][k] = aux[k];
                        }
                    }
                }
                if ((this.bacteriumMatix[j][numberVariables * 2 + 1]) > 0 && (this.bacteriumMatix[j + 1][numberVariables * 2 + 1] == 0)) {
                    for (int k = 0; k < this.bacteriumMatix[j].length; k++) {
                        aux[k] = this.bacteriumMatix[j][k];
                        this.bacteriumMatix[j][k] = this.bacteriumMatix[j + 1][k];
                        this.bacteriumMatix[j + 1][k] = aux[k];
                    }
                }
            }
        }
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
    public String getDescription(){         
        this.description = "";  
        byte sizeVector = (byte) ((this.bacteriumMatix[0].length - 2) / 2);
        for (int i = 0; i < this.bacteriumMatix.length; i++) {  
            this.description += (i+1) + "\t";
            for (int j = 0; j < this.bacteriumMatix[i].length; j++) {
                if (j < sizeVector || j >= (sizeVector*2)) {
                    this.description += this.bacteriumMatix[i][j] + "\t";
                }else {
                    j = sizeVector*2 - 1;
                }                
            }
            this.description += "\n";            
        }
        return this.description;
    }
    
    /**
     * Method that returns a matrix; the number of columns represents 
     * the number of CNOP variables to be solved. The number of rows 
     * represents the number of assigned Sb bacteria, which is a matrix 
     * of all the solutions for that CNOP.
     * 
     * @return the bacteriumMatix
     */
    public double[][] getBacteriumMatix() {
        return this.bacteriumMatix;
    }

    /**
     * With this method the CNOP solution matrix can assigned. 
     * Commonly used to replace a solution space by a more optimal one.
     * @param bacteriumMatix the bacteriumMatix to set
     */
    public void setBacteriumMatix(double[][] bacteriumMatix) {
        this.bacteriumMatix = bacteriumMatix;
    }    
    
    /**
     * Method returns the stored value for the sb parameter 
     * of the TS-MBFOA.
     * @return value of parameter Sb
     */
    public int getSb() {
        return sb;
    }

    /**
     * Method to assign the number of bacteria in the population.<br>
     * The number of the population is determined between [10, 500].
     * 
     * @param sb the sb to set
     */
    public void setSb(int sb) {
        this.sb = sb;
    }
    
    /**
     * Method returns the stored value for the stepSize parameter 
     * of the TS-MBFOA.
     * @return value of parameter stepSize (R)
     */
    public double getStepSize() {
        return stepSize;
    }

    /**
     * Method that assigns value to the (R) parameter of the TS-MBFOA.<br>
     * Static step size [0,1].
     * 
     * @param stepSize the stepSize to set
     */
    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }
    

}
