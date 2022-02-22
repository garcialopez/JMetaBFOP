package com.adriangarcia.problemoptimization;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 *
 * @author Adrian
 */
public class TensionCompressionSpring extends Problem {
    
    public TensionCompressionSpring() {
        this.setBestKnownValue(0.012681);                   //Mejor valor conocido
        this.setNameProblem("Tension/compression spring"); //Nombre del problema        
        this.setFunction("(N+2)*D*d^2");                   //Función objetivo
        this.setOrderVariables("d,D,N");                   //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                                // Objetivo -> min o max        
        this.setRankVariable("(0.05, 2),(0.25, 1.3),(2, 15)"); //Se inserta el rango para cada variable.        
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{1-(D^3*N)/(71785*d^4) <= 0};"
                           + "{((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1/(5108*(d^2)))-1 <= 0};"
                           + "{1-(140.45*d/((D^2)*N)) <= 0};"
                           + "{((D+d)/1.5)-1 <= 0}";
        //Se aplica el metodo para detectar las restricciones
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
        this.configurator.setStepSize(0.15);    // Se establece el tamaño de paso.        
        this.configurator.setNc(12);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.9);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(10000);       // Número de evaluaciones   

        return this.configurator;
    }

}
