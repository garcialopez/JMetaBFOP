package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * ProcessSynthesisMINLP class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: (y1 - 1)^2 + (y2 - 2)^2 + (y3 - 1)^2 - log10(y4 + 1) + (x1 - 1)^2 + (x2 - 2)^2 + (x3 - 3)^2 <br>
 * Subject to: y1 + y2 + y3 + x1 + x2 + x3 <= 5 <br>
 * y3^2 + x1^2 + x2^2 + x3^2 <= 5.5 <br>
 * y1 + x1 <= 1.2 <br>
 * y2 + x2 <= 1.8 <br>
 * y3 + x3 <= 2.5 <br>
 * y4 + x1 <= 1.2 <br>
 * y2^2 + x2^2 <= 1.64 <br>
 * y3^2 + x3^2 <= 4.25 <br>
 * y2^2 + x3^2 <= 4.64 <br> 
 * where: (0, 1.2);(0, 1.8);(0, 2.5);ran{4:7,0:1}
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class ProcessSynthesisMINLP extends Problem{
    
    public ProcessSynthesisMINLP() {
        this.setBestKnownValue(4.579582); 
        this.setNameProblem("Process synthesis MINLP");
        this.setFunction("(y1 - 1)^2 + (y2 - 2)^2 + (y3 - 1)^2 - log10(y4 + 1) + (x1 - 1)^2 + (x2 - 2)^2 + (x3 - 3)^2");                   //Función objetivo
        this.setOrderVariables("iter{x,1,3};iter{y,1,4}");                   //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                                // Objetivo -> min o max        
        this.setRankVariable("(0, 1.2);(0, 1.8);(0, 2.5);ran{4:7,0:1}"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{y1 + y2 + y3 + x1 + x2 + x3 <= 5};"
                           + "{y3^2 + x1^2 + x2^2 + x3^2 <= 5.5};"
                           + "{y1 + x1 <= 1.2};"
                           + "{y2 + x2 <= 1.8};"
                           + "{y3 + x3 <= 2.5};"
                           + "{y4 + x1 <= 1.2};"
                           + "{y2^2 + x2^2 <= 1.64};"
                           + "{y3^2 + x3^2 <= 4.25};"
                           + "{y2^2 + x3^2 <= 4.64}";
        this.detectConstraints(constraints);
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
