package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;
import java.util.Arrays;

import org.mariuszgromada.math.mxparser.Expression;

/**
 *
 * @author Adrian
 */
public class Problem {
    public static final String MINIMIZATION = "min";
    public static final String MAXIMIZATION = "max";
    public static final String INEQUALITY = "inequality";
    public static final String EQUALITY = "equality";
    private final String[] statisticsName = {"Best","Mean","Median","St.d","Worst","FeasibleRate", "SuccessRate", "SuccessPerformance"};
    
    private String nameProblem = "";
    private String[] arguments;
    private String obj = Problem.MINIMIZATION;
    private String function = "";
    private String[] orderVariables = {};
    private String temp = "";
    private int numberAssignedVariable = 0;    
    private int numberVariableOF = 0; 
    private double srv = 0;
    private String[][] constraintsEquality;
    private String[][] constraintsInequality;
    private double[][] rankVariable;
    private int executions = 1;
    private ArrayList<double[]> results;
    private ArrayList<Double> statistic;
    
    private long timeSeconds;
    
    private Converter converter;
    
    //Variables para ser usada en los Problemas de optimización
    public Configurator configurator;
    private double bestKnownValue;
    
    
    private int advance = 0;

    /**
     * 
     */
    public Problem() {
        this.results = new ArrayList();
        this.statistic = new ArrayList();
        this.converter = new Converter();        
    }
    
    protected void newResults(){
        this.results = new ArrayList();        
    }
    
    protected void newStatistic(){
        this.statistic = new ArrayList();        
    }
    
    protected void newConverter(){
        this.converter = new Converter(); 
    }
    
    /**
     * 
     * @param obj
     */
    protected void addStatistic(double obj){
        this.statistic.add(obj);
    }
    
    public double[] getStatistic(){
        double[] statistics = new double[this.statistic.size()];
        for (int i = 0; i < statistics.length; i++) 
            statistics[i] = this.statistic.get(i);                     
        return statistics;
    }

    /**
     * 
     * @param obj 
     */
    protected void addBestResults(double[] obj){
        /*byte j = 0, sizeVector = (byte) ((obj.length - 2) / 2);
        double[] aux = new double[sizeVector + 2];        
        
        for (int i = 0; i < obj.length; i++) {
            if(i < sizeVector || i >= (sizeVector * 2)){
                aux[j] = obj[i];
                j++;
            }else{
                i = sizeVector * 2 - 1;
            }
        }                
        this.results.add(aux);*/
        int sizeVector = this.getNumberVariableOF();
        
        double[] aux = new double[sizeVector + 2];        
                  
        for (int i = 0; i < sizeVector; i++) {
            aux[i] = obj[i];
            
        }
        
        aux[sizeVector] = obj[obj.length-2];
        aux[sizeVector + 1] = obj[obj.length-1];
        this.results.add(aux);
    }
    
    /**
     * 
     * @return 
     */
    public double[][] getBestResults(){        
        double[][] bestResultsArrays = new double[this.results.size()][];
        for (int i = 0; i < bestResultsArrays.length; i++) 
            bestResultsArrays[i] = this.results.get(i);                     
        return bestResultsArrays;
    }
    
    /**
     * @return the nameProblem
     */
    public String getNameProblem() {
        return nameProblem;
    }

    /**
     * @param nameProblem the nameProblem to set
     */
    public void setNameProblem(String nameProblem) {
        this.nameProblem = nameProblem;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {                
        this.function = this.converter.extendFunction(function);
        //this.function = function;
        
    }

    /**
     * @return the numberAssignedVariable
     */
    public int getNumberAssignedVariable() {
        return numberAssignedVariable;
    }

    /**
     * @param numberAssignedVariable the numberAssignedVariable to set
     */
    protected void setNumberAssignedVariable(int numberAssignedVariable) {
        this.numberAssignedVariable = numberAssignedVariable;
    }

    /**
     * @return the rankVariable
     */
    public double[][] getRankVariable() {
        return rankVariable;
    }

    /**
     * @param rankVariable the rankVariable to set
     */
    public void setRankVariable(String rankVariable) {
        if (!rankVariable.isEmpty()) {                   
            // String rank = "{0.5, 2},(0.25, 1.3),(2, 15)"; 
            rankVariable = this.converter.extendRankVariables(rankVariable);
            String ran[] = rankVariable.replaceAll("[\\s\\()\\[\\]]", "").split(",");
            double[][] auxRank = new double[getNumberAssignedVariable()][2];                        
            byte row = 0;        
            double x = Double.parseDouble(ran[0]);
            for (int i = 0; i < ran.length; i+=2) {            
                //auxRank[row][0] = Double.parseDouble(ran[i]);
                //auxRank[row][1] = Double.parseDouble(ran[i+1]);
                auxRank[row][0] = new Expression(ran[i]).calculate();
                auxRank[row][1] = new Expression(ran[i+1]).calculate();
                row++;
            }
            this.rankVariable = auxRank; 
        }else{
            System.out.println("Variables vacías");
        }
    }

    /**
     * @return the orderVariables
     */
    public String[] getOrderVariables() {
        return orderVariables;
    }

    /**
     * @param varOrder
     */
    public void setOrderVariables(String varOrder) {
        if (!varOrder.isEmpty()) {                    
            this.orderVariables = this.converter.extendVariables(varOrder);              
            // Se guarda el número de variables de la función objetivo
            this.numberVariableOF = new Expression(function).getMissingUserDefinedArguments().length;
            this.setNumberAssignedVariable(this.getOrderVariables().length);
        }
    }

    /**
     * @return the constraintsEquality
     */
    public String[][] getConstraintsEquality() {
        return constraintsEquality;
    }

//    /**
//     * @param constraintsI the constraintsEquality to set
//     */
//    public void setConstraintsI(String[][] constraintsI) {
//        this.constraintsEquality = constraintsI;
//    }

    /**
     * @return the constraintsInequality
     */
    public String[][] getConstraintsInequality() {
        return constraintsInequality;
    }

//    /**
//     * @param constraintsInequality the constraintsInequality to set
//     */
//    public void setConstraintsInequality(String[][] constraintsInequality) {
//        this.constraintsInequality = constraintsInequality;
//    }
    
    public void detectConstraints(String constraints){  //Expresión regular                       
        
        if (!constraints.isEmpty()) {                            
            String[] constraintsAux = constraints.split(";");
            constraints = "";
            for (int i = 0; i < constraintsAux.length; i++) {
                constraintsAux[i] = this.converter.extendFunction(constraintsAux[i]);                
                constraintsAux[i] = "{" + constraintsAux[i].substring(1,  constraintsAux[i].length()-1) + "}";            
                constraints += constraintsAux[i];
                constraints += (i < constraintsAux.length-1)?";":"";
            }

            //System.out.println(constraints);

            constraintsAux = constraints.replaceAll("[\\{}\\s]", "").split("[\\s\\;]");
            String[] constraintsTemp = null;
            int equalityCounter = 0, inequalityCounter = 0;     



            for (int i = 0; i < constraintsAux.length; i++) {                                 
                constraintsAux[i] = organizeconstraints(constraintsAux[i]);           
                constraintsTemp = constraintsAux[i].split(",");            
                switch(constraintsTemp[2]){
                    case "<": case ">": case "<=": case ">=":
                        constraintsAux[i] += ",".concat(Problem.INEQUALITY);                    
                        break;
                    case "=":
                        constraintsAux[i] += "," + Problem.EQUALITY;                    
                        break;
                    default: //Mandar un Error de restricción                    
                        System.out.println("Default case");
                        break;
                }            
            }                

            for (String constraintsAux1 : constraintsAux) {
                //System.out.println("Index = "+i +" -> "+constraintsAux[i]);
                constraintsTemp = constraintsAux1.split(",");                                                

                switch (constraintsTemp[3]) {
                    case Problem.INEQUALITY:
                        inequalityCounter++;
                        break;
                    case Problem.EQUALITY:
                        equalityCounter++;
                        break;
                }
            }

                this.constraintsEquality = new String[equalityCounter][5];
                this.constraintsInequality = new String[inequalityCounter][5];

                equalityCounter = 0;
                inequalityCounter = 0;
            for (String constraintsAux1 : constraintsAux) {
                constraintsTemp = constraintsAux1.split(",");            

                switch (constraintsTemp[3]) {
                    case Problem.INEQUALITY:                                        
                        System.arraycopy(constraintsTemp, 0,constraintsInequality[inequalityCounter], 0,constraintsTemp.length);
                        inequalityCounter++;
                        break;
                    case Problem.EQUALITY:
                        System.arraycopy(constraintsTemp, 0,constraintsEquality[equalityCounter], 0,constraintsTemp.length);
                        equalityCounter++;
                        break;
                }
            }
        }     
    }

    /**
     * @return the obj
     */
    public String getObj() {
        return obj;
    }

    /**
     * @param obj the obj to set
     */
    public void setObj(String obj) {
        this.obj = obj;
    }

    /**
     * @return the executions
     */
    public int getExecutions() {
        return executions;
    }

    /**
     * @param executions the executions to set
     */
    public void setExecutions(int executions) {
        this.executions = executions;
    }
    
    public Configurator getRecommendedSetting() {
        return this.configurator;
    }

    /**
     * @return the bestKnownValue
     */
    public double getBestKnownValue() {
        return bestKnownValue;
    }

    /**
     * @param bestKnownValue the bestKnownValue to set
     */
    public void setBestKnownValue(double bestKnownValue) {
        this.bestKnownValue = bestKnownValue;
    }

    /**
     * @return the statisticsName
     */
    public String[] getStatisticsName() {
        return statisticsName;
    }
    
    private String organizeconstraints(String constraints){
        this.temp = "";
        if (constraints.contains(">=")) 
            this.temp = ">=";
        else if (constraints.contains("<="))
            this.temp = "<=";
        else if (constraints.contains("<"))
            this.temp = "<";
        else if (constraints.contains(">"))
            this.temp = ">";
        else if (constraints.contains("="))
            this.temp = "=";
                
        return constraints.replaceAll(this.temp, ",").concat(",").concat(this.temp);
        
    }

    /**
     * @return the numberVariableOF
     */
    public int getNumberVariableOF() {
        return numberVariableOF;
    }

    /**
     * @return the timeSeconds
     */
    public long getTimeSeconds() {
        return timeSeconds;
    }

    /**
     * @param timeSeconds the timeSeconds to set
     */
    protected void setTimeSeconds(long timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    /**
     * @return the arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * @param constraintsValuate the arguments to set
     */
    protected void setConstraintsValuate(String constraintsValuate) {        
        this.arguments = this.converter.extendConstraintsValuate(constraintsValuate);
    }

    /**
     * @return the advance
     */
    public int getAdvance() {
        return advance;
    }

    /**
     * @param advance the advance to set
     */
    public void setAdvance(int advance) {
        this.advance = advance;
    }

//    /**
//     * @return the srv
//     */
//    public double getSrv() {
//        return srv;
//    }
//
//    /**
//     * @param srv the srv to set
//     */
//    public void setSrv(double srv) {
//        this.srv = srv;
//    }
    
    public double[][] getDataGraphicsBestResults(){
        
        int size = this.getBestResults().length;
        int sizeCol = this.getBestResults()[0].length;
        
        double[][] data = new double[2][size]; 
        
        for (int i = 0; i < size; i++) {
            data[0][i] = i+1;
            data[1][i] = this.getBestResults()[i][sizeCol-2];
        }        
        
        return data;
    }
    
    
    private ArrayList<Integer> convergenceOMediaEval = new ArrayList();
    private ArrayList<Double> convergenceMediaBest = new ArrayList();
    
    protected void iniConvergenceMedia(){
        convergenceOMediaEval = new ArrayList();
        convergenceMediaBest = new ArrayList();
    }
    
    protected void setConvergenceMedia(int number, double best){
        convergenceOMediaEval.add(number);
        convergenceMediaBest.add(best);
    }
    
    public double[][] getConvergenceMedia(){
        double[][] convergenceMediaEval = new double[2][this.convergenceMediaBest.size()];        
        
        for (int i = 0; i < convergenceMediaEval[0].length; i++) {
            convergenceMediaEval[0][i] = convergenceOMediaEval.get(i);
            convergenceMediaEval[1][i] = convergenceMediaBest.get(i);                        
        }                                
        
        return convergenceMediaEval;
    }
    //----------------------------------------------------------------------------------------------------
    
    private ArrayList<Integer> convergenceBestValueEvalAUX = new ArrayList();
    private ArrayList<Double> convergenceBestValuetAUX = new ArrayList();
    
    protected void iniConvergenceBestValueAUX(){
        convergenceBestValueEvalAUX = new ArrayList();
        convergenceBestValuetAUX = new ArrayList();                        
    }
       
    protected void setConvergenceBestValueAUX(int number, double best){
        convergenceBestValueEvalAUX.add(number);
        convergenceBestValuetAUX.add(best);
    }
    
    //----------------------------------------------------------------------------------------------------
    
    
    private ArrayList<Integer> convergenceBestValueEval;
    private ArrayList<Double> convergenceBestValuet;
        
    protected void setConvergenceBestValue(){ 
        convergenceBestValueEval = null;
        convergenceBestValuet = null;
        convergenceBestValueEval = convergenceBestValueEvalAUX;
        convergenceBestValuet = convergenceBestValuetAUX;                
    }
       
    public double[][] getConvergenceBestValue(){
       double[][] convergenceBestValue = new double[2][this.convergenceBestValuet.size()];                       
       
        for (int i = 0; i < convergenceBestValue[0].length; i++) {
            convergenceBestValue[0][i] = this.convergenceBestValueEval.get(i);
            convergenceBestValue[1][i] = this.convergenceBestValuet.get(i);                        
        }        
        //Arrays.sort(convergenceBestValue[1]);
        return convergenceBestValue;
    }
          
    
}
