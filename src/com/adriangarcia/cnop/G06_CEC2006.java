package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G06_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: (x1 - 10)^3 + (x2 - 20)^3 <br>
 * Subject to: -(x1 - 5)^2 - (x2 - 5)^2 + 100 <= 0 <br> 
 *             (x1 - 6)^2 + (x2 - 5)^2 - 82.81 <= 0 <br>  
 * where: (13.0,100.0),(0,100.0)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G06_CEC2006 extends Problem{

    public G06_CEC2006() {
        this.setBestKnownValue(-6961.8138755802); 
        this.setNameProblem("G06 CEC2006");
        this.setFunction("(x1 - 10)^3 + (x2 - 20)^3");                   //Función objetivo
        this.setOrderVariables("x1,x2");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(13.0,100.0),(0,100.0)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{-(x1 - 5)^2 - (x2 - 5)^2 + 100 <= 0};{(x1 - 6)^2 + (x2 - 5)^2 - 82.81 <= 0}";
        this.detectConstraints(constraints);
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
