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
public class G23_CEC2006 extends Problem{

    public G23_CEC2006() {
        this.setBestKnownValue(-400.0551000000); 
        this.setNameProblem("G23 CEC2006");
        this.setFunction("-9 * x5 - 15 * x8 + 6 * x1 + 16 * x2 + 10 * (x6 + x7)");                   //Función objetivo
        this.setOrderVariables("iter{x,1,9}");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,300),(0,300),(0,100),(0,200),(0,100),(0,300),(0,100),(0,200),(0.01,0.03)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{x9 * x3 + 0.02 * x6 - 0.025 * x5 <= 0};"
                + "{x9 * x4 + 0.02 * x7 - 0.015 * x8 <= 0};"
                + "{x1 + x2 - x3 - x4 = 0};"
                + "{0.03 * x1 + 0.01 * x2 - x9 * (x3 + x4) = 0};"
                + "{x3 + x6 - x5 = 0};"
                + "{x4 + x7 - x8 = 0}";
        this.detectConstraints(constraints);
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(19);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0000012);    // Se establece el tamaño de paso.        
        this.configurator.setNc(7);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.77);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(4);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(150);     // Frecuencia de reproducción
        this.configurator.setEvaluations(17300);       // Número de evaluaciones   

        return this.configurator;
    }

}
