package com.adriangarcia.metaheuristics.tsmbfoa;


/**
 *
 * @author Adrian
 */
public class Population {

    private final NumberRandom numberRandom;
    private String description;
    private double[][] bacteriumMatix;  //Matriz de la población de bacterias
    private int sb = 40;            // Número de población de bacterias [10,500]
    private double stepSize = 0.001;   //(R)Tamaño de paso estático [0,1]

    public Population() {
        this.numberRandom = new NumberRandom();
    }

    public double[][] startPopulation(Problem problem) {
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
    
    
    public void sortPopulation(int numberVariables) {
        double[] aux = new double[numberVariables * 2 + 2];
        for (int i = 0; i < this.bacteriumMatix.length; i++) {
            
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
     * Función que devuelve una descripción de la población 
     * de cada ejecución.    
     * 
     * @return un valor {@code String}
     * 
     * <p>La invocación es la siguiente:</p>
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
     * @return the bacteriumMatix
     */
    public double[][] getBacteriumMatix() {
        return this.bacteriumMatix;
    }

    /**
     * @param bacteriumMatix the bacteriumMatix to set
     */
    public void setBacteriumMatix(double[][] bacteriumMatix) {
        this.bacteriumMatix = bacteriumMatix;
    }    
    
    /**
     * @return the sb
     */
    public int getSb() {
        return sb;
    }

    /**
     * @param sb the sb to set
     */
    public void setSb(int sb) {
        this.sb = sb;
    }
    
    /**
     * @return the stepSize
     */    
    public double getStepSize() {
        return stepSize;
    }

    /**
     * @param stepSize the stepSize to set
     */
    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }
    

}
