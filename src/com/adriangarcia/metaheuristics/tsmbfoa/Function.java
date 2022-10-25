package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

/**
 * <b>Function</b> class the xxxx class is in charge of evaluating the 
 * mathematical expressions, it uses the mXparse library to do this function.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Function {
    private Expression expressionOF;
    private String error = "";
    private byte COD_ERROR = 0;
    private String[] variables;
    private double[] values;
    private ArrayList<Expression> expressionConstEquality;
    private ArrayList<Expression> expressionConstInequality;
    private ArrayList<Expression> expressionConstants;
    private String[] args;    
    
    /**
     * Método constructor vacio, para despues
     * parsear la Expresión.
     */
    protected Function() {  
        //...
    }
    
    /**
     * Constructor method for parsing the function.
     * @param function A String that is a function: <br>
     * e.g: (N+2)*D*d^2
     * @param variables A String containing the 
     * ordered design variables:<br>
     * e.g: d,D,N
     */
    protected Function(String function, String[] variables) {        
        this.variables = variables;
        this.expressionOF = this.loadFunction(function, this.variables, false);
    }
    
    /**
     * 
     * @param function function
     * @param variables variables
     */
    protected void setFuncion(String function, String[] variables) {        
        this.setVariables(variables);
        this.setExpressionOF(this.loadFunction(function, this.getVariables(), false));
    }
        
    /**
     * 
     * @param values values
     * @return double
     */
    protected double evaluateObjectiveFunction(double[] values) {
        this.values = values;
        this.setExpressionOF(this.setArguments(this.getExpressionOF(), false));
        return getExpressionOF().calculate();        
    } 
    
    /**
     * 
     * @param expression expression
     * @param flag flag
     * @return --
     */
      private Expression setArguments(Expression expression, boolean flag){                    
        if (this.getVariables().length == this.values.length) {            
            this.COD_ERROR = 0;
            this.error = "No hay error.";                        
            
            for (int i = 0; i < this.values.length; i++) 
                expression.setArgumentValue(getVariables()[i], this.values[i]);
            
            if (this.getArgs() != null && flag) {
                //System.out.println("a: " + expression.getExpressionString());
                double g = expression.calculate();
                double g1;
                
                int[] rank = new int[2];
                rank[0] = Integer.parseInt(this.args[this.args.length-1].split(":")[0]);
                rank[1] = Integer.parseInt(this.args[this.args.length-1].split(":")[1]);
                int val1 = 1, val2 = 1, val3 = 1;
                for (int p = rank[0]; p <= rank[1]; p++) {  
                    expression.setArgumentValue("p", p);
                    for (int q = rank[0]; q <= rank[1]; q++) { 
                        expression.setArgumentValue("q", q);
                        for (int r = rank[0]; r <= rank[1]; r++) { 
                            expression.setArgumentValue("r", r);                             
                              
                            g1 = expression.calculate();
                            if (g1 < g) {                                
                                val1 = p;
                                val2 = q;
                                val3 = r;
                                g = g1;
                            }
                            
                        }
                    }
                }
                
                 expression.setArgumentValue(this.getArgs()[0], val1); 
                 expression.setArgumentValue(this.getArgs()[1], val2); 
                 expression.setArgumentValue(this.getArgs()[2], val3);                  
                 
            }
            
            return expression;
        } else {
            this.COD_ERROR = 1;            
            this.error = "El número de variables no coincide con el número de valores a evaluar.";
            return null;
        }
    }
    
      /**
       * 
       * @param constraintsD constraintsD
       * @param constraintsI constraintsI
       */
    protected void setConstraints(String[][] constraintsD, String[][] constraintsI) {                      
        if(constraintsD != null){
            this.setExpressionConstInequality((ArrayList<Expression>) new ArrayList());
            for (String[] constraintsD1 : constraintsD){
                this.getExpressionConstInequality().add(this.loadFunction(constraintsD1[0], this.variables, true));                                                
            } 
                
        }        
        if(constraintsI != null){
            this.setExpressionConstEquality((ArrayList<Expression>) new ArrayList());
            for (String[] constraintsI1 : constraintsI) 
                this.getExpressionConstEquality().add(this.loadFunction(constraintsI1[0], this.variables, true));
        }        
    }
        
    /**
     * 
     * @param type type
     * @param index index
     * @return double
     */
    protected double evaluateConstraints(String type, int index) {        
            this.COD_ERROR = 0;
            this.error = "No hay error.";            
            switch(type){                                 
                case Problem.INEQUALITY:           
                    this.getExpressionConstInequality().set(index, 
                                this.setArguments(this.getExpressionConstInequality().get(index), true
                            )
                    );                                   
                    //System.out.println(this.getExpressionConstInequality().get(index).getExpressionString());
                    return this.getExpressionConstInequality().get(index).calculate();
                case Problem.EQUALITY:                    
                    this.getExpressionConstEquality().set(index, 
                                this.setArguments(this.getExpressionConstEquality().get(index), true
                            )
                    );
                    return this.getExpressionConstEquality().get(index).calculate();   
                default: 
                    this.COD_ERROR = 1;
                    this.error = "Restricción no encontrada.";
                    return Double.NaN;
            }               
    }
        
    
    /**
     * Check if the variables match what the function contains.
     * @param inVariables in Variables
     * @return true or false
     */
    private boolean checkVariables(Expression expressionFO, String[] inVariables){   
        boolean check = true;                
        for (int i = 0; i < inVariables.length - 1; i++) {        
            for (int j = i + 1; j < inVariables.length; j++) {
                if (inVariables[i].equals(inVariables[j])) {
                    this.COD_ERROR = 1;
                    this.error = "************************ Error:\n"
                             + "Duplicado de variables.\n"
                             + "Verifiqué su entrada.";
                    return false;
                }                
            }
        }
        return check;
    }
    
    /**
     * 
     * @return Error
     */
    protected String getError(){
        return this.error;
    }
    
    /**
     * print Error
     */
    protected void printError(){
        if (this.COD_ERROR == 0) 
            System.out.println(this.error);  
        else System.err.println(this.error);       
    }

    /**
     * @return the COD_ERROR
     */
    protected byte getCOD_ERROR() {
        return COD_ERROR;
    }

    /**
     * @return the variables
     */
    protected String[] getVariables() {
        return variables;
    }
    
    /**
     * 
     * @param function function
     * @param variables variables
     * @param flag flag
     * @return Expression
     */
    private Expression loadFunction(String function, String[] variables, boolean flag){
        Expression expressionFOAux = new Expression(function);                                 
        if (expressionFOAux.checkLexSyntax()) {
            if (this.checkVariables(expressionFOAux, variables)) {
                this.COD_ERROR = 0;
                this.error = "No hay error.";                 
                for (String variable : variables){
                    expressionFOAux.addArguments(new Argument(variable, 0.0));
                }
                                
                if (this.getArgs() != null && flag) {
                    for (int i = 1; i < this.getArgs().length-1; i++) {                        
                        expressionFOAux.addArguments(new Argument(this.getArgs()[i], 1));
                    }                    
                }                
                
            } else {
                if(this.COD_ERROR == 0){
                    this.COD_ERROR = 1;
                    this.error = "Error: Incluye variables que no contiene la función";
                }
            }                      
        } else {
            this.COD_ERROR = 1;
            this.error = "La sintaxis de la funcón no es correcta.";
        }                
        return expressionFOAux;
    }

    /**
     * @return the expressionOF
     */
    protected Expression getExpressionOF() {
        return expressionOF;
    }

    /**
     * @param expressionOF the expressionOF to set
     */
    protected void setExpressionOF(Expression expressionOF) {
        this.expressionOF = expressionOF;        
    }

    /**
     * @return the expressionConstEquality
     */
    protected ArrayList<Expression> getExpressionConstEquality() {
        return expressionConstEquality;
    }

    /**
     * @param expressionConstEquality the expressionConstEquality to set
     */
    protected void setExpressionConstEquality(ArrayList<Expression> expressionConstEquality) {
        this.expressionConstEquality = expressionConstEquality;
    }

    /**
     * @return the expressionConstInequality
     */
    protected ArrayList<Expression> getExpressionConstInequality() {
        return expressionConstInequality;
    }

    /**
     * @param expressionConstInequality the expressionConstInequality to set
     */
    protected void setExpressionConstInequality(ArrayList<Expression> expressionConstInequality) {
        this.expressionConstInequality = expressionConstInequality;
    }

    /**
     * @param variables the variables to set
     */
    protected void setVariables(String[] variables) {
        this.variables = variables;
    }

    /**
     * @return the args
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    protected void setArgs(String[] args) {
        this.args = args;
    }
    
}
