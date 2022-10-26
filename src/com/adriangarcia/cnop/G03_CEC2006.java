package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G03_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: -(sqrt(10)) * prod{x,1,10,xi} <br>
 * Subject to: sum{x,1,10,xi^2} - 1 = 0 <br>              
 * where: ran{1:10,0:1}
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G03_CEC2006 extends Problem{

    public G03_CEC2006() {
        this.setBestKnownValue(-1.0005001000); 
        this.setNameProblem("G03 CEC2006");
        this.setFunction("-(sqrt(10)) * prod{x,1,10,xi}");                   //Función objetivo
        this.setOrderVariables("iter{x,1,10}"); //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("ran{1:10,0:1}"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{sum{x,1,10,xi^2} - 1 = 0}";
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
        this.configurator.setNc(14);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(20000);       // Número de evaluaciones   

        return this.configurator;
    }

}
