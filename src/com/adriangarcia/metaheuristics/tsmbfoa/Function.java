package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

/**
 *
 * @author Adrian
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
    public Function() {  
        //...
    }
    
    /**
     * Método constructor para parsear la función.
     * @param function Un String que es una función: 
     * Ejemplo: (N+2)*D*d^2
     * @param variables Un String que contiene las 
     * variables de diseño ordenadas:
     * Ejemplo: d,D,N
     */
    public Function(String function, String[] variables) {        
        this.variables = variables;
        this.expressionOF = this.loadFunction(function, this.variables, false);
    }
    
    public void setFuncion(String function, String[] variables) {        
        this.setVariables(variables);
        this.setExpressionOF(this.loadFunction(function, this.getVariables(), false));
    }
        
    public double evaluateObjectiveFunction(double[] values) {
        this.values = values;
        this.setExpressionOF(this.setArguments(this.getExpressionOF(), false));
        return getExpressionOF().calculate();        
    } 
    
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
                              
//                            System.out.println(expression.getExpressionString());
//                            System.out.println("p: " + expression.getArgument("p").getArgumentValue()
//                                            + " q: " + expression.getArgument("q").getArgumentValue()
//                                            + " r: " + expression.getArgument("r").getArgumentValue()
//                            );
                            g1 = expression.calculate();
                            if (g1 < g) {                                
                                val1 = p;
                                val2 = q;
                                val3 = r;
                                g = g1;
//                                System.out.println(g);
                            }
                            
                        }
                    }
                }
                
                 expression.setArgumentValue(this.getArgs()[0], val1); 
                 expression.setArgumentValue(this.getArgs()[1], val2); 
                 expression.setArgumentValue(this.getArgs()[2], val3); 
                 
//                 System.out.println("A: " + expression.getExpressionString());
//                System.out.println("p: " + expression.getArgument("p").getArgumentValue()
//                                + " q: " + expression.getArgument("q").getArgumentValue()
//                                + " r: " + expression.getArgument("r").getArgumentValue()
//                );
                 
            }
            
            return expression;
        } else {
            this.COD_ERROR = 1;            
            this.error = "El número de variables no coincide con el número de valores a evaluar.";
            return null;
        }
    }
    
    public void setConstraints(String[][] constraintsD, String[][] constraintsI) {                      
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
        
    public double evaluateConstraints(String type, int index) {        
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
     * Verifica si las variables coinciden con lo que contiene la función
     * @param inVariables
     * @return 
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
           
    public String getError(){
        return this.error;
    }
    
    public void printError(){
        if (this.COD_ERROR == 0) 
            System.out.println(this.error);  
        else System.err.println(this.error);       
    }

    /**
     * @return the COD_ERROR
     */
    public byte getCOD_ERROR() {
        return COD_ERROR;
    }

    /**
     * @return the variables
     */
    public String[] getVariables() {
        return variables;
    }
    
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
    public Expression getExpressionOF() {
        return expressionOF;
    }

    /**
     * @param expressionOF the expressionOF to set
     */
    public void setExpressionOF(Expression expressionOF) {
        this.expressionOF = expressionOF;        
    }

    /**
     * @return the expressionConstEquality
     */
    public ArrayList<Expression> getExpressionConstEquality() {
        return expressionConstEquality;
    }

    /**
     * @param expressionConstEquality the expressionConstEquality to set
     */
    public void setExpressionConstEquality(ArrayList<Expression> expressionConstEquality) {
        this.expressionConstEquality = expressionConstEquality;
    }

    /**
     * @return the expressionConstInequality
     */
    public ArrayList<Expression> getExpressionConstInequality() {
        return expressionConstInequality;
    }

    /**
     * @param expressionConstInequality the expressionConstInequality to set
     */
    public void setExpressionConstInequality(ArrayList<Expression> expressionConstInequality) {
        this.expressionConstInequality = expressionConstInequality;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(String[] variables) {
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
    public void setArgs(String[] args) {
        this.args = args;
    }
    
}
