package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.Arrays;

/**
 *
 * @author José Adrian García López
 * @email joseadrian_g97@hotmail.com
 */
public class Mechanism {
    // Variable privada de la clase NumberRandom para generar números aleatorios
    private NumberRandom numberRandom;
    String val = "";
    
    // Método constructor vacio, sin parámetros    
    public Mechanism() {
    }    
    
    /**
     * Método que devuelve un vector de algulos 
     * aleatorios doubles para el giro de bacterias en chemotaxis
     * @param numberVariables de variables de la función objetivo
     * @return un vetor de angulos con tamaño de numberVariables
     */
    public double[] generateAngles(int numberVariables) {
        numberRandom = new NumberRandom(); /* Se crea el objeto de la clase 
                                              NumberRandom para generar números 
                                              aleatorios entre rangos.        */
        double accum = 0.0;                /* Se crea una variable acumuladora 
                                              de angles_i ^ 2.                */
        double[] angles = new double[numberVariables]; 
                                           /* Se crea el vector para
                                              alamcenar los algulos aleatorios 
                                              con tamaños del número de variables
                                              de la función objetivo.         */               
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
    
    public double[][] evaluationFyC(Configurator configurator, Problem problem, Function function) {
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
