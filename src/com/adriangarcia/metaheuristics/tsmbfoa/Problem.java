package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;

import org.mariuszgromada.math.mxparser.Expression;

/**
 * <b>Problem</b> class where all the characteristics of the input CNOP 
 * problem object are defined.  
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
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
     * Empty constructor method for object instance.
     */
    public Problem() {
        this.results = new ArrayList();
        this.statistic = new ArrayList();
        this.converter = new Converter();        
    }
    
    /**
     * Start a new list for results.
     */
    protected void newResults(){
        this.results = new ArrayList();        
    }
    
    /**
     * Start a new list for statistics.
     */
    protected void newStatistic(){
        this.statistic = new ArrayList();        
    }
    
    /**
     * Starts a new list for conversions.
     */
    protected void newConverter(){
        this.converter = new Converter(); 
    }
    
    /**
     * Method of adding statistics.
     * @param obj statistics
     */
    protected void addStatistic(double obj){
        this.statistic.add(obj);
    }
    
    /**
     * Method that returns a vector of real numbers representing the statistics.
     * 
     * @return statistics 
     */
    public double[] getStatistic(){
        double[] statistics = new double[this.statistic.size()];
        for (int i = 0; i < statistics.length; i++) 
            statistics[i] = this.statistic.get(i);                     
        return statistics;
    }

    /**
     * Method that adds the best results found.
     * 
     * @param obj best value
     */
    protected void addBestResults(double[] obj){
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
     * Method that returns the best results found.
     * @return best value
     */
    public double[][] getBestResults(){        
        double[][] bestResultsArrays = new double[this.results.size()][];
        for (int i = 0; i < bestResultsArrays.length; i++) 
            bestResultsArrays[i] = this.results.get(i);                     
        return bestResultsArrays;
    }
    
    /**
     * Method returning the problem name.
     * 
     * @return the nameProblem
     */
    public String getNameProblem() {
        return nameProblem;
    }

    /**
     * Method that establishes the problem name.
     * 
     * @param nameProblem the nameProblem to set
     */
    public void setNameProblem(String nameProblem) {
        this.nameProblem = nameProblem;
    }

    /**
     * Method that returns the objective function of the problem.
     * 
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * Method that establishes the objective function of the problem.<br><br>
     * These may be of different types such as:<br>
     * 29.4*x1 + 18*x2<br>
     * 5 * sum{x,1,4,xi}<br>
     * sum{x,1,4,xi^2} - sum{x,5,13,xi}<br>
     * sum{x,1,4,xi^2} - prod{x,5,13,xi}<br>
     * x1<br>
     * -(100 - (x1 - 5)^2 - (x2 - 5)^2 - (x3 - 5)^2)/100<br>
     * and more...<br><br>
     * 
     * the purpose is that mXparse is in charge of evaluating and substituting 
     * directly the values for each variable.
     * 
     * <br>Syntax allowed for summations and products is:<br> <br>
     * 
     * summation: sum{var, from, to, f(x1,x2,...,xn)} <br>
     * Product: prod{var, from, to, f(x1,x2,...,xn)}
     * 
     * @param function the function to set
     */
    public void setFunction(String function) {                
        this.function = this.converter.extendFunction(function);
        //this.function = function;
        
    }

    /**
     * Method returning the assigned variable number.
     * 
     * @return the numberAssignedVariable
     */
    public int getNumberAssignedVariable() {
        return numberAssignedVariable;
    }

    /**
     * Method setting the assigned variable number.
     * @param numberAssignedVariable the numberAssignedVariable to set
     */
    protected void setNumberAssignedVariable(int numberAssignedVariable) {
        this.numberAssignedVariable = numberAssignedVariable;
    }

    /**
     * Method that returns the range of CNOP design variables.
     * @return the rankVariable
     */
    public double[][] getRankVariable() {
        return rankVariable;
    }

    /**
     * Method that establishes the range of CNOP design variables.<br><br>
     * 
     * e.g: (0,1.4),(0,1.2),(3.25,4),(0.5,2)<br><br>
     * 
     * also, the following abbreviation can be used:<br><br>
     * 
     * (0,1.2);(0,1.8);ran{3:7,0:1.2};ran{8:10,0.25:2}<br><br>
     * 
     * conversion will return <br>
     * (0,1.2),(0,1.8),(0,1.2),(0,1.2),(0,1.2),(0,1.2),(0,1.2),(0.25,2),(0.25,2),(0.25,2)<br><br>
     * 
     * all elements within the iteration are separated by commas (,) 
     * and ranges by colons (:) and outside the iteration are 
     * separated by semicolons (;).
     * 
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
     * Method that returns the order of the CNOP design variables.
     * @return the orderVariables
     */
    public String[] getOrderVariables() {
        return orderVariables;
    }

    /**
     * Method that assigns the order of the CNOP design variables.<br><br>
     * 
     * e.g: x1,x2,x3,x4,x5,x6,x7<br><br>
     * 
     * also, the following abbreviation can be used:<br><br>
     * 
     * if the variables input is:<br>
     * p1; p2; iter{x,1,3}; iter{y,1,4}; p1; p2<br><br>
     * 
     * conversion will return <br>
     * [p1,  p2,  x1,  x2,  x3,  y1,  y2,  y3,  y4,  p1,  p2]<br><br>
     * 
     * all elements inside the iteration are separated by commas (,) 
     * and outside the iteration are separated by semicolons (;).
     * 
     * @param varOrder the orderVariables
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
     * Method that returns equality constraints.
     * @return the constraintsEquality
     */
    public String[][] getConstraintsEquality() {
        return constraintsEquality;
    }

    /**
     * Method that returns inequality constraints.
     * @return the constraintsInequality
     */
    public String[][] getConstraintsInequality() {
        return constraintsInequality;
    }


    /**
     * Method that receives the restrictions of the CNOP, these can be of 
     * two types, equality or inequality. The correct syntax to 
     * include them is the following: <br><br>
     * 
     * [opening brace][mathematical expression][conditional operator][results][closing brace][semicolon]<br><br>
     * 
     * e.g:<br>
     * {-127 + 2 * x1^2 + 3 * x2^4 + x3 + 4 * x4^2 + 5 * x5 <= 0}<br><br>
     * 
     * if there are more than one restriction they can be separated by semicolons [;]<br>
     * {y1 + x1 <= 1.2}; {y2 + x2 <= 1.8}; {y2^2 + x2^2 <= 1.64}; ...; [n-constraints] <br>
     * 
     * @param constraints constraints
     */
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
     * Method that returns the optimization goal:<br>
     * Can be:<br><br>
     * 
     * Problem.MINIMIZATION<br>
     * Problem.MAXIMIZATION<br>
     * 
     * @return the obj
     */
    public String getObj() {
        return obj;
    }

    /**
     * Method that sets the optimization objective:
     * @param obj the obj to set
     */
    public void setObj(String obj) {
        this.obj = obj;
    }

    /**
     * Method that returns the number of independent executions of the algorithm.
     * @return the executions
     */
    public int getExecutions() {
        return executions;
    }

    /**
     * Method that sets the number of independent executions of the TS-MBFOA. 
     * This must be an enter qua number from 1 to 30.
     * @param executions the executions to set
     */
    public void setExecutions(int executions) {
        this.executions = executions;
    }
    
    /**
     * Method returning the recommended parameter settings.
     * @return configurator
     */
    public Configurator getRecommendedSetting() {
        return this.configurator;
    }

    /**
     * Method that returns the best known value for CNOP.
     * @return the bestKnownValue
     */
    public double getBestKnownValue() {
        return bestKnownValue;
    }

    /**
     * Method that sets the best known value for the CNOP.
     * @param bestKnownValue the bestKnownValue to set
     */
    public void setBestKnownValue(double bestKnownValue) {
        this.bestKnownValue = bestKnownValue;
    }

    /**
     * Method that returns the names of the statistics applied.
     * @return the statisticsName
     */
    public String[] getStatisticsName() {
        return statisticsName;
    }
    
    /**
     * Method organizing constraints.
     * @param constraints constraints
     * @return constraints organized
     */
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
     * Method to return the number of CNOP variables.
     * @return the number Variable OF
     */
    public int getNumberVariableOF() {
        return numberVariableOF;
    }

    /**
     * Method that returns the time in seconds in which the configured execution finished.
     * @return the time Seconds
     */
    public long getTimeSeconds() {
        return timeSeconds;
    }

    /**
     * Method that sets the time in seconds in which the configured execution is finished.
     * @param timeSeconds the timeSeconds to set
     */
    protected void setTimeSeconds(long timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    /**
     * Method that returns the arguments of the CNOP function.
     * @return the arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * ---
     * @param constraintsValuate the arguments to set
     */
    protected void setConstraintsValuate(String constraintsValuate) {        
        this.arguments = this.converter.extendConstraintsValuate(constraintsValuate);
    }

    /**
     * Method returning TS-MBFOA execution progress.
     * @return the advance
     */
    public int getAdvance() {
        return advance;
    }

    /**
     * Method sets the TS-MBFOA execution progress.
     * @param advance the advance to set
     */
    protected void setAdvance(int advance) {
        this.advance = advance;
    }
   
    /**
     * Method that returns data to plot the best results.
     * @return Data Graphics Best Results
     */
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
    
    /**
     * Method that starts the convergence graph garbage cans. 
     */
    protected void iniConvergenceMedia(){
        convergenceOMediaEval = new ArrayList();
        convergenceMediaBest = new ArrayList();
    }
    
    /**
     * Method that sets data to plot the best results of the average of runs.
     * @param number values
     * @param best best values
     */
    protected void setConvergenceMedia(int number, double best){
        convergenceOMediaEval.add(number);
        convergenceMediaBest.add(best);
    }
    
    /**
     * Method that returns data to plot the best results of the mean number of runs.
     * @return Convergence Media
     */
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
    
    /**
     * Start Convergence Best Value AUX
     */
    protected void iniConvergenceBestValueAUX(){
        convergenceBestValueEvalAUX = new ArrayList();
        convergenceBestValuetAUX = new ArrayList();                        
    }
    
    /**
     * Sets Convergence Best Value AUX
     * @param number value aux
     * @param best best value aux
     */
    protected void setConvergenceBestValueAUX(int number, double best){
        convergenceBestValueEvalAUX.add(number);
        convergenceBestValuetAUX.add(best);
    }
    
    //----------------------------------------------------------------------------------------------------
    
    
    private ArrayList<Integer> convergenceBestValueEval;
    private ArrayList<Double> convergenceBestValuet;
    
    /**
     * Sets Convergence Best Value 
     */
    protected void setConvergenceBestValue(){ 
        convergenceBestValueEval = null;
        convergenceBestValuet = null;
        convergenceBestValueEval = convergenceBestValueEvalAUX;
        convergenceBestValuet = convergenceBestValuetAUX;                
    }
    
    /**
     * get Convergence Best Value
     * @return Convergence Best Value
     */
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
