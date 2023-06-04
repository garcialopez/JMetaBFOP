package com.garcialopez.metaheuristic;

/**
 * 
 * <b>Statistics</b> class is in charge of calculating the basic statistics:<br>  
 * 
 * <ol>
 * <li>Best value</li>
 * <li>Mean</li>
 * <li>Median</li>
 * <li>Standard deviation</li>
 * <li>Worst value</li>
 * <li>Feasibility rate</li>
 * <li>Success rate</li>
 * <li>Successful performance</li>
 * </ol>
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Statistics {
        
    private double[] vectorDouble, aux;  
    /**
     * OPTION
     */
    private final int OPTION_MEAN = 0;
    private final int OPTION_STANDARD_DEVIATION = 1;
    private final int OPTION_MEDIAN = 2;
    private final int OPTION_BEST = 3;
    private final int OPTION_WORST = 4;    

    /**
     * Constructor method
     */
    public Statistics() {
        //...           
    }        
    
    /**
     * This method generates the mean of a vector.
     * @param obj real number vector
     * @return mean of a given vector
     */
    public double mean(double[] obj){        
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }  
        return this.summation(obj)/obj.length;
    }   // End mean   
    
    /**
     * This method generates an mean vector of a matrix.
     * @param obj real number matrix
     * @return mean vector of a given matrix
     */
    public double[] mean(double[][] obj){                           
            return this.vectorEvaluate(obj, this.OPTION_MEAN);
    }   //End mean   
    
    /**
     * This method calculates the mean of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the mean
     * @return mean of a given vector
     */
    public double mean(double[][] obj, int column){        
        return this.optionEvaluate(obj, column, this.OPTION_MEAN);
    }   // End mean      
                                                                                
    /**
     * This method generates the standardDeviation of a vector.
     * @param obj real number vector
     * @return standardDeviation of a given vector
     */
    public double standardDeviation(double[] obj) {    
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }        
        return Math.sqrt((this.distanceMeanAbs(obj, this.mean(obj))/obj.length));
    }   //End standardDeviation
    
    /**
     * This method generates an standardDeviation vector of a matrix.
     * @param obj real number matrix
     * @return standardDeviation vector of a given matrix
     */
    public double[] standardDeviation(double[][] obj) {
        return this.vectorEvaluate(obj, this.OPTION_STANDARD_DEVIATION);
    }   //End standardDeviation
    
    /**
     * This method calculates the standardDeviation of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the standardDeviation
     * @return standardDeviation of a given vector
     */
    public double standardDeviation(double[][] obj, int column) {                                                    
        return this.optionEvaluate(obj, column, this.OPTION_STANDARD_DEVIATION);
    }   // End standardDeviation

    /**
     * This method generates the median of a vector.
     * @param obj real number vector
     * @return median of a given vector
     */
    public double median(double[] obj){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }
        obj = this.bubble(obj);                         
        int size = obj.length, position = size/2;        
        if (size%2 == 0)
            return (obj[position-1] + obj[position])/2;        
        return obj[position];
    }   // End median
    
    /**
     * This method generates an median vector of a matrix.
     * @param obj real number matrix
     * @return median vector of a given matrix
     */
    public double[] median(double[][] obj){                                 
        return this.vectorEvaluate(obj, this.OPTION_MEDIAN);
    }   // End median
    
    /**
     * This method calculates the median of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the median
     * @return median of a given vector
     */
    public double median(double[][] obj, int column){                          
        return this.optionEvaluate(obj, column, this.OPTION_MEDIAN);
    }   // End median               

    /**
     * This method generates the best of a vector.
     * @param obj real number vector
     * @return best of a given vector
     */
    public double best(double[] obj){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }
        obj = bubble(obj);        
        return obj[0];
    }   //End best
    
    /**
     * This method generates an best vector of a matrix.
     * @param obj real number matrix
     * @return best vector of a given matrix
     */
    public double[] best(double[][] obj){              
        return this.vectorEvaluate(obj, this.OPTION_BEST);
    }   // End best
    
    /**
     * This method calculates the best of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the best
     * @return best of a given vector
     */
    public double best(double[][] obj, int column){  
        return this.optionEvaluate(obj, column, this.OPTION_BEST);
    }    // End best
    
    /**
     * This method calculates the best Row of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the best Row
     * @return best Row of a given vector
     */
    public double[] bestRow(double[][] obj, int column){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return null;
        }
        double best = this.best(obj, column);
        for (double[] obj1 : obj) {
            if (obj1[column] == best){                
                return obj1;  
            }
        }
        return null;
    }
                                                                    
    /**
     * This method generates the worst of a vector.
     * @param obj real number vector
     * @return worst of a given vector
     */  
    public double worst(double[] obj){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }
        obj = bubble(obj);              
        return obj[obj.length-1];
    }   // End worst
    
    /**
     * This method generates an worst vector of a matrix.
     * @param obj real number matrix
     * @return worst vector of a given matrix
     */
    public double[] worst(double[][] obj){               
        return this.vectorEvaluate(obj, this.OPTION_WORST);
    }   // End worst
    
    /**
     * This method calculates the worst of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the worst
     * @return worst of a given vector
     */
    public double worst(double[][] obj, int column){         
        return this.optionEvaluate(obj, column, this.OPTION_WORST);
    }   // End worst
         
    /**
     * This method calculates the worst Row of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the worst Row
     * @return worst Row of a given vector
     */
    public double[] worstRow(double[][] obj, int column){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return null;
        }
        double best = this.worst(obj, column);
        for (double[] obj1 : obj) {
            if (obj1[column] == best){                
                return obj1;                                    
            } 
                
        }
        return null;
    }
    
    /**
     * Bubble method sorting a vector.
     * @param obj real number vector
     * @return sorted vector
     */
    private double[] bubble(double[] obj){
        double assistant;
        for (int i = 0; i < obj.length-1; i++) {    
            for (int j = (i+1); j < obj.length; j++) {
                if (obj[j] < obj[i]) {
                    assistant = obj[i];
                    obj[i] = obj[j];
                    obj[j] = assistant;
                }
            }
        }        
        return obj;
    }   // End bubble
    
    /**
     * Method that sums all elements of a vector.
     * @param obj real number vector
     * @return vector sum
     */
    public double summation(double[] obj){
        double summation = 0;
        for (int i = 0; i < obj.length; i++)            
            summation += obj[i];
        return summation;
    }   // End summation
    
    /**
     * This method calculates the feasible Rate of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the feasible Rate
     * @return feasible Rate of a given vector
     */
    public double feasibleRate(double[][] obj, int column){
        int count = 0;
                
        for (double[] obj1 : obj) {            
            if (obj1[column] == 0) {
                count++;                
            }
        }                
        return (count > 0)? (100 * count) / obj.length: 0;        
    }
    
    /**
     * This method calculates the success Rate of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param column column number to calculate the success Rate
     * @param bestKnownValue best known value of CNOP
     * @return success Rate of a given vector
     */
    public double successRate(double[][] obj, int column, double bestKnownValue){
        int count = 0;
        for (double[] obj1 : obj) {            
            if (obj1[column] - (bestKnownValue) <= 0.0001) {
                count++;                
            }
        }                        
        return (count > 0)? (100 * count) / obj.length : 0;        
    }
    
    /**
     * This method calculates the success Performance of a specified column given a matrix.
     * 
     * @param obj real number matrix
     * @param bestKnownValue best known value of CNOP
     * @return success Performance of a given vector
     */
    public int successPerformance(double[][] obj, double bestKnownValue){                                                   
        return (obj[0][obj[0].length-2] - (bestKnownValue) <= 0.0001)? 1 : 0;        
    }
        
    /**
     * This method calculates the distance between the vector 
     * values and the mean.
     * 
     * @param obj real number vector
     * @param mean mean of a vector
     * @return real value representing the distance
     */
    private double distanceMeanAbs(double[] obj, double mean){
        this.vectorDouble = new double[obj.length];
        for (int i = 0; i < obj.length; i++) {
            this.vectorDouble[i] = Math.pow(Math.abs(obj[i] - mean),2);            
        }
        return summation(this.vectorDouble);
    }   // End distanceMeanAbs
    
    /**
     * Method to calculate the chosen statistics and to be 
     * able to reuse storage vectors. 
     * 
     * @param obj real number vector
     * @param option :mean : 0, standardDeviation: 1, median: 2, best: 3, worst: 4
     * @return real value
     */
    private double[] vectorEvaluate(double[][]obj, int option){ 
        try {
            this.aux = new double[obj[0].length];
            this.vectorDouble = new double[obj.length];
            for (int i = 0; i < obj[0].length; i++) {
                for (int j = 0; j < obj.length; j++)                     
                    this.vectorDouble[j] = obj[j][i];
                this.aux[i] = evaluate(this.vectorDouble, option);            
            } 
            return this.aux;
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error Array Index Out Of Bounds Exception: empty vector.");
            return new double[]{};
        }        
               
    }   // End vector
    
    /**
     * mean : 0
     * standardDeviation: 1
     * median: 2
     * best: 3
     * worst: 4
     * @param obj 
     * @param column
     * @param option
     * @return 
     */
    private double optionEvaluate(double[][] obj, final int column, final int option){
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        } 
        try {
            this.vectorDouble = new double[obj.length];        
            for (int i = 0; i < obj.length; i++)             
                this.vectorDouble[i] = obj[i][column];   
            return this.evaluate(vectorDouble, option);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Invalid arrayIndex" + e.getMessage());
            return Double.NaN;
        }
    }   // End option
    
    /**
     * 
     * @param option option to evaluate
     * @return 
     */
    private double evaluate(double[] vectorDouble, final int option){
        switch(option){
            case 0: return this.mean(vectorDouble);
            case 1: return this.standardDeviation(vectorDouble);
            case 2: return this.median(vectorDouble);
            case 3: return this.best(vectorDouble);
            case 4: return this.worst(vectorDouble);                
            default: System.err.println("Error: option invalid.");
        }
        return Double.NaN;
    } // End evaluate     
    
    /**
     * 
     * @param obj real number vector
     * @param column column vector to obtain
     * @return column vector 
     */
    public double[] getColumns(double[][] obj, int column){
        this.vectorDouble = new double[obj.length];
        
        for (int i = 0; i < obj.length; i++) {
            this.vectorDouble[i] = obj[i][column];
        }      
        return this.vectorDouble;
    } 
    
}
