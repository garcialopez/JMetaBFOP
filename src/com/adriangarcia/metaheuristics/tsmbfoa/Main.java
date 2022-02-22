package com.adriangarcia.metaheuristics.tsmbfoa;

//import statistics.Statistics;

import com.adriangarcia.problemoptimization.*;
import java.util.Arrays;



/**
 *
 * @author Adrian
 */




///COMPARAR LOS DOS CODIGOS EN JAVA

public class Main {
    public static void main(String[] args) {
        /**
         *        Inicia la configuración...
         */
                
        
//        // Se instancia los objetos para el forrajeo                
//        
////        Problem problem = new Problem();
//        Configurator configurator = new Configurator();
//        
//        
//        /**
//         *        Inicia la calibración de parámetros del algoritmo...
//         */
//        
//        configurator.setSb(14);            /* Se establece la cantidad de bacterias 15
//                                           para la población inicial.         */               
//        configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.           0.0005
//        configurator.setNc(7);            /* Se establece el número de ciclos         12
//                                           quimiotáxicos.                     */      
//        configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      1.99  
//        configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
//                                           a reproducir.                      */
//        configurator.setRepcycle(100);     // Frecuencia de reproducción
//        
//        
//        configurator.setEvaluations(20000);       // Número de evaluaciones   10000
////        
////        /**
////         *        Inicia la configuración del problema...
////         */
////        
//////         //Se establece el nombre del problema  resolver
////        problem.setNameProblem("Resorte de Tensión/compresión");
////        // Se guarda la función objetivo
////        problem.setFunction("(N+2)*D*d^2");
////        problem.setOrderVariables("d,D,N");
////        // Objetivo -> min o max
////        problem.setObj(Problem.MINIMIZATION);
////        
////        //Mejor valor conocido
////        problem.setBestKnownValue(0.012681);
////                                               
////        /**
////         * Se inserta el rango para cada variable.
////         * Cada rango separado por coma entre (), {} o []
////         * y cada (), {} o [] separado por coma.
////         * Ejemplo: (-1,3),(2,3),(0.5,1.5)        
////         */
////        
////        problem.setRankVariable("(0.05, 2),(0.25, 1.3),(2, 15)"); //Expresiones regulares
////        
////        /**
////         * Se establecen las restricciones de igualdad (equality) 
////         * y desigualdad (inequality).
////         * La restricciones deben ser ingresadas entre {} y dentro de ellas debe incluir 
////         * separado por comas: la restricción, número resultante, 
////         * simbolo comparativo (<,>,=,<=,>=) y  tipo de restricción (equality, inequality).
////         * Cada restricción se debe separar con el simbolo: ;
////         * Ejemplo: 
////         * {1-(D^3*N/71785*d^4),0,<=,inequality}|{1-(D^3*N/71785*d^4),0,<=,equality}
////         */
////        
////   //terminos de una restricción
////        String constraints = "{1-(D^3*N)/(71785*d^4), 0, <=, inequality};"
////                           + "{((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1/(5108*(d^2)))-1,0,<=,inequality};"
////                           + "{1-(140.45*d/((D^2)*N)),0,<=,inequality};"
////                           + "{((D+d)/1.5)-1,0,<=,inequality}";
////        //Se aplica el metodo para detectar las restricciones
////        problem.detectConstraints(constraints);
        
     
        
        
            Problem problem = new G12_CEC2006();
            Configurator configurator = problem.getRecommendedSetting();
                       
            
        //Se establece el número de ejecuciónes
        
        //documentar porque 30 es el maximo
        problem.setExecutions(30);
        /**
         *      Termina la configuración...
         */
        
            RunTsmbfoa stst = new RunTsmbfoa();
            stst.run(problem, configurator, true, true);        
          
//        System.out.println(Arrays.toString(problem.getConvergenceBestValue()[0]));
//        System.out.println(Arrays.toString(problem.getConvergenceBestValue()[1]));
                                
                    
    }
    
    
    
}

