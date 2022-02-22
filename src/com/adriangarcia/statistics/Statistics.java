package com.adriangarcia.statistics;

/**
 *
 * @author Adrian
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
     * 
     * @param obj
     * @return 
     */
    public double mean(double[] obj){        
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }  
        return this.summation(obj)/obj.length;
    }   // End mean   
    
    /**
     * 
     * @param obj
     * @return 
     */
    public double[] mean(double[][] obj){                           
            return this.vectorEvaluate(obj, this.OPTION_MEAN);
    }   //End mean   
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
     */
    public double mean(double[][] obj, int column){        
        return this.optionEvaluate(obj, column, this.OPTION_MEAN);
    }   // End mean      
                                                                                
    /**
     * 
     * @param obj
     * @return 
     */
    public double standardDeviation(double[] obj) {    
        if (obj == null){
            System.err.println("Error: empty vector.");
            return Double.NaN;
        }        
        return Math.sqrt((this.distanceMeanAbs(obj, this.mean(obj))/obj.length));
    }   //End standardDeviation
    
    /**
     * 
     * @param obj
     * @return 
     */
    public double[] standardDeviation(double[][] obj) {
        return this.vectorEvaluate(obj, this.OPTION_STANDARD_DEVIATION);
    }   //End standardDeviation
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
     */
    public double standardDeviation(double[][] obj, int column) {                                                    
        return this.optionEvaluate(obj, column, this.OPTION_STANDARD_DEVIATION);
    }   // End standardDeviation

    /**
     * 
     * @param obj
     * @return 
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
     * 
     * @param obj
     * @return 
     */
    public double[] median(double[][] obj){                                 
        return this.vectorEvaluate(obj, this.OPTION_MEDIAN);
    }   // End median
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
     */
    public double median(double[][] obj, int column){                          
        return this.optionEvaluate(obj, column, this.OPTION_MEDIAN);
    }   // End median               

    /**
     * 
     * @param obj
     * @return 
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
     * 
     * @param obj
     * @return 
     */
    public double[] best(double[][] obj){              
        return this.vectorEvaluate(obj, this.OPTION_BEST);
    }   // End best
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
     */
    public double best(double[][] obj, int column){  
        return this.optionEvaluate(obj, column, this.OPTION_BEST);
    }    // End best
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
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
     * 
     * @param obj
     * @return 
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
     * 
     * @param obj
     * @return 
     */
    public double[] worst(double[][] obj){               
        return this.vectorEvaluate(obj, this.OPTION_WORST);
    }   // End worst
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
     */
    public double worst(double[][] obj, int column){         
        return this.optionEvaluate(obj, column, this.OPTION_WORST);
    }   // End worst
         
    /**
     * 
     * @param obj
     * @param column
     * @return 
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
     * 
     * @param obj
     * @return 
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
     * 
     * @param obj
     * @return 
     */
    public double summation(double[] obj){
        double summation = 0;
        for (int i = 0; i < obj.length; i++)            
            summation += obj[i];
        return summation;
    }   // End summation
    
    /**
     * 
     * @param obj
     * @param column
     * @return 
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
     * 
     * @param obj
     * @param column
     * @param bestKnownValue
     * @return 
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
     * 
     * @param obj
     * @param column
     * @param bestKnownValue
     * @return 
     */
    public int successPerformance(double[][] obj, double bestKnownValue){                                                   
        return (obj[0][obj[0].length-2] - (bestKnownValue) <= 0.0001)? 1 : 0;        
    }
        
    /**
     * 
     * @param obj
     * @param mean
     * @return 
     */
    private double distanceMeanAbs(double[] obj, double mean){
        this.vectorDouble = new double[obj.length];
        for (int i = 0; i < obj.length; i++) {
            this.vectorDouble[i] = Math.pow(Math.abs(obj[i] - mean),2);            
        }
        return summation(this.vectorDouble);
    }   // End distanceMeanAbs
    
    /**
     * mean : 0
     * standardDeviation: 1
     * median: 2
     * best: 3
     * worst: 4
     * @param obj
     * @param option
     * @return 
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
     * @param option
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
    
    public double[] getColumns(double[][] obj, int column){
        this.vectorDouble = new double[obj.length];
        
        for (int i = 0; i < obj.length; i++) {
            this.vectorDouble[i] = obj[i][column];
        }      
        return this.vectorDouble;
    }
    
//    public double[][] getColumns(double[][] obj, int columnX, int columnY){
//        double[][] array = new double[obj.length][2];
//        
//        for (int i = 0; i < obj.length; i++) {
//            array[i][0] = obj[i][columnX];
//            array[i][1] = obj[i][columnY];
//        }      
//        return array;
//    }
    
}
