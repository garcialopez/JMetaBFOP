/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adriangarcia.problemoptimization;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * 
 * @author José Adrian
 */
public class G09_CEC2006 extends Problem{

    public G09_CEC2006() {
        this.setBestKnownValue(680.6300573745); 
        this.setNameProblem("G09 CEC2006");
        this.setFunction("(x1 - 10)^2 + 5 * (x2 - 12)^2 + x3^4 + 3 * "
                + "(x4 - 11)^2 + 10 * x5^6  + 7 * x6^2 + x7^4 - 4 * "
                + "x6 * x7 - 10 * x6 - 8 * x7");   //Función objetivo
        this.setOrderVariables("x1,x2,x3,x4,x5,x6,x7");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(-10.0,10.0),(-10.0,10.0),(-10.0,10.0),"
                + "(-10.0,10.0),(-10.0,10.0),(-10.0,10.0),(-10.0,10.0)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{-127 + 2 * x1^2 + 3 * x2^4 + x3 + 4 * x4^2 + 5 * x5 <= 0};"
                + "{-282 + 7 * x1 + 3 * x2 + 10 * x3^2 + x4 - x5 <= 0};"
                + "{-196 + 23 * x1 + x2^2 + 6 * x6^2 - 8 * x7 <= 0};"
                + "{4 * x1^2 + x2^2 - 3 * x1 * x2 + 2 * x3^2 + 5 * x6 - 11 * x7 <= 0}";
        this.detectConstraints(constraints);
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(20);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(9);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(20000);       // Número de evaluaciones   

        return this.configurator;
    }

}
