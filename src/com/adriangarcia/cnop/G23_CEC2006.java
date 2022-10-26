package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;
/**
 * G23_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: -9 * x5 - 15 * x8 + 6 * x1 + 16 * x2 + 10 * (x6 + x7) <br>
 * Subject to: x9 * x3 + 0.02 * x6 - 0.025 * x5 <= 0 <br>
 * x9 * x4 + 0.02 * x7 - 0.015 * x8 <= 0 <br>
 * x1 + x2 - x3 - x4 = 0 <br>
 * 0.03 * x1 + 0.01 * x2 - x9 * (x3 + x4) = 0 <br>
 * x3 + x6 - x5 = 0 <br>
 * x4 + x7 - x8 = 0 <br> 
 * where: (0,300),(0,300),(0,100),(0,200),(0,100),(0,300),(0,100),(0,200),(0.01,0.03)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
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
