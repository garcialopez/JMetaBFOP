package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.Arrays;

/**
 * 
 * <b>Mechanism</b> class is in charge of performing external techniques 
 * to fulfill the CNOP optimization. In this class, the mathematical 
 * evaluation and the mechanisms that generate the rotation angles of 
 * the TS-MBFOA chemotaxis are performed.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Mechanism {
    // Private variable of the NumberRandom class to generate random numbers.
    private NumberRandom numberRandom;
    String val = "";
    
    /**
     * Empty constructor.
     */ 
    protected Mechanism() {
    }    
    
    /**
     * Method that returns a vector of double random algorithms 
     * for the spinning of bacteria in chemotaxis.
     * 
     * @param numberVariables size of objective function variables
     * @return an angle vetor with numberVariables size
     */
    protected double[] generateAngles(int numberVariables) {
        //NumberRandom class is instantiated to generate random numbers between ranges. 
        numberRandom = new NumberRandom(); 
        //An accumulator variable is created of angles i ^ 2
        double accum = 0.0;  
        /* The vector is created to to store the random modules with sizes 
         * with sizes of the number of variables of the objective function.
         */
        double[] angles = new double[numberVariables]; 
                                                       
        for (int i = 0; i < angles.length; i++) { // Inicia for i
            // Se genera un número aleatorio entre -1 y 1 y se asigna a 
            // angles en su posición i
            angles[i] = numberRandom.getRandomRankUnif(-1.0, 1.0);   
            // Se hace la suma de acumm con angles en su posición i 
            // elevado al cuadrado
            accum +=  (angles[i] * angles[i]);
        } // Termina for i        
        // Se calcula la raiz cuadrada de accum
        double root = Math.sqrt(accum);
        // Se itera el vector de angles y en su posicón i se evalua la posición 
        // dividiendola entre la raiz cuadrada de accum.
        for (int i = 0; i < angles.length; i++) 
            angles[i] /=  root;
        //Termino for y devuelve el vector de angulos
        return angles;
    }       
    
    /**
     * This function evaluates the mathematical expression of the CNOP 
     * objective function, also identifies the type of constraints, 
     * and evaluates them taking into account the sum of constraint violations. 
     * It finishes by returning a matrix with size Sb X number of variables.
     * 
     * @param configurator object of the class Configurator
     * @param problem object of the class Problem
     * @param function object of the class Function
     * @return  a matrix with size Sb X number of variables, 
     * in it the value of the objective function already evaluated.
     */
    protected double[][] evaluationFyC(Configurator configurator, Problem problem, Function function) {
        int varNumber = problem.getNumberAssignedVariable();
        double[] values;
        double svr;
        double resultC;
        for (int i = 0; i < configurator.getBacteriumMatix().length; i++) {
            svr = 0;
            values = Arrays.copyOfRange(configurator.getBacteriumMatix()[i], 0, varNumber);
            configurator.getBacteriumMatix()[i][varNumber * 2] = function.evaluateObjectiveFunction(values);    
            
            if (problem.getConstraintsInequality() != null || problem.getConstraintsEquality() != null) {                            

                if (problem.getConstraintsInequality().length > 0) {
                    for (int constrD = 0; constrD < problem.getConstraintsInequality().length; constrD++) {
                        resultC = function.evaluateConstraints("inequality", constrD);  
                        problem.getConstraintsInequality()[constrD][4] = String.valueOf(resultC);
                        
                        this.val = problem.getConstraintsInequality()[constrD][2];
                        
                        if (val.equals(">=") || val.equals(">")) {
                            svr += Math.max(0, resultC + Double.parseDouble(problem.getConstraintsInequality()[constrD][1])); 
                        }else if(val.equals("<=") || val.equals("<")){                            
                            svr += Math.max(0, resultC - Double.parseDouble(problem.getConstraintsInequality()[constrD][1])); 
                        }
                        
                        
                        //System.out.println("SVR IGUALDAD: " + svr);
                        
                    }
                }

                if (problem.getConstraintsEquality().length > 0) {                
                    for (int constrI = 0; constrI < problem.getConstraintsEquality().length; constrI++) {
                        resultC = function.evaluateConstraints("equality", constrI);
                        problem.getConstraintsEquality()[constrI][4] = String.valueOf(resultC);
                        svr += Math.max(0, Math.abs(resultC)-0.0001);   
                        //System.out.println("SVR DESIGUALDAD: " + svr);
                    }
                }            
                configurator.getBacteriumMatix()[i][varNumber * 2 + 1] = svr;
            }else configurator.getBacteriumMatix()[i][varNumber * 2 + 1] = Double.NaN;
        }
        return configurator.getBacteriumMatix();
    }
}
