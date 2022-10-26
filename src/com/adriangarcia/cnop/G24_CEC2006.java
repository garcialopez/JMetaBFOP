package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G24_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: -x1 - x2 <br>
 * Subject to: -2 * x1^4 + 8 * x1^3 - 8 * x1^2 + x2 - 2 <= 0 <br>
 * -4 * x1^4 + 32 * x1^3 - 88 * x1^2 + 96 * x1 + x2 - 36 <= 0 <br>
 * where: (0,3),(0,4)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G24_CEC2006 extends Problem{

    public G24_CEC2006() {
        this.setBestKnownValue(-5.5080132716); 
        this.setNameProblem("G24 CEC2006");
        this.setFunction("-x1 - x2");                   //Función objetivo
        this.setOrderVariables("x1,x2");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,3),(0,4)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones        
        this.detectConstraints("{-2 * x1^4 + 8 * x1^3 - 8 * x1^2 + x2 - 2 <= 0};"
                           + "{-4 * x1^4 + 32 * x1^3 - 88 * x1^2 + 96 * x1 + x2 - 36 <= 0}");
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
        this.configurator.setEvaluations(30000);       // Número de evaluaciones   

        return this.configurator;
    }

}
