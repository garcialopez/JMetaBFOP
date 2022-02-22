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
public class C01_CEC2010 extends Problem{

    public C01_CEC2010() {
        this.setBestKnownValue(-0.7473104); 
        this.setNameProblem("C01 CEC2010");
        this.setFunction("-(abs((sum{x,1,10,cos(xi-oi)^4} - 2 * prod{x,1,10,cos(xi-oi)^2}) / sqrt(sum{x,1,10, i * (xi-oi)^2}) ))");                   //Función objetivo      
        this.setOrderVariables("iter{x,1,10};iter{o,1,10}");
        this.setRankVariable("ran{1:20, 0:10};");
        this.detectConstraints("{0.75 - prod{x,1,10,(xi - oi)} <= 0};{sum{x,1,10,(xi - oi)} - 7.5 * 10 <= 0}");
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(30);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(14);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(15000);       // Número de evaluaciones   

        return this.configurator;
    }

}
