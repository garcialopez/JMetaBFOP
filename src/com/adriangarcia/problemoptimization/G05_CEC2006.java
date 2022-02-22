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
public class G05_CEC2006 extends Problem{

    public G05_CEC2006() {
        this.setBestKnownValue(5126.4967140071); 
        this.setNameProblem("G05 CEC2006");
        this.setFunction("3 * x1 +  0.000001 * x1^3 + 2 * x2 + (0.000002 / 3) * x2^3");                   //Función objetivo
        this.setOrderVariables("x1,x2,x3,x4");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,1200),(0,1200),(-0.55,0.55),(-0.55,0.55)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{-x4 + x3 - 0.55 <= 0};{-x3 + x4 - 0.55 <= 0};{1000 * sin(-x3 - 0.25) + 1000 * sin(-x4 - 0.25) + 894.8 - x1 = 0};{1000 * sin(x3 - 0.25) + 1000 * sin(x3 - x4 - 0.25) + 894.8 - x2 = 0};{1000 * sin(x4 - 0.25) + 1000 * sin(x4 - x3 - 0.25) + 1294.8 = 0}";
        this.detectConstraints(constraints);
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(15);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(7);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(15000);       // Número de evaluaciones   

        return this.configurator;
    }

}
