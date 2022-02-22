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
public class C03_CEC2010 extends Problem{

    public C03_CEC2010() {
        this.setBestKnownValue(0); 
        this.setNameProblem("C03 CEC2010");
        this.setFunction("sum{x,1,10,(100 * (((xi-oi)^2 - (x(i+1)-o(i+1)))^2) + ((xi-oi) - 1)^2)}");                   //Función objetivo
        this.setOrderVariables("iter{x,1,10};iter{o,1,10}");
        this.setRankVariable("ran{1:20, -5.12:5.12};");
        this.detectConstraints("{sum{x,1,10,(((xi-oi) - (x(i+1)-o(i+1)))^2)} = 0}");
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(14);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(7);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(20000);       // Número de evaluaciones   

        return this.configurator;
    }

}
