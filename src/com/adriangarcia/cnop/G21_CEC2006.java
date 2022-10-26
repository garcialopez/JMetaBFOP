package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G21_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: x1 <br>
 * Subject to: -x1 + 35 * x2^0.6 + 35 * x3^0.6 <= 0 <br>
 * -300 * x3 + 7500 * x5 - 7500 * x6 - 25 * x4 * x5 + 25 * x4 * x6 + x3 * x4 = 0 <br>
 * 100 * x2 + 155.365 * x4 + 2500 * x7 - x2 * x4 - 25 * x4 * x7 - 15536.5 = 0 <br>
 * -x5 + ln(-x4 + 900) = 0 <br>
 * -x6 + ln(x4 + 300) = 0 <br>
 * -x7 + ln(-2 * x4 + 700) = 0 <br>
 * where: iter{x,1,7}
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G21_CEC2006 extends Problem{

    public G21_CEC2006() {
        this.setBestKnownValue(193.7245100700); 
        this.setNameProblem("G21 CEC2006");
        this.setFunction("x1");                   //Función objetivo
        this.setOrderVariables("iter{x,1,7}");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,1000),(0,40),(0,40),(100,300),(6.3,6.7),(5.9,6.4),(4.5,6.25)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{-x1 + 35 * x2^0.6 + 35 * x3^0.6 <= 0};"
                + "{-300 * x3 + 7500 * x5 - 7500 * x6 - 25 * x4 * x5 + 25 * x4 * x6 + x3 * x4 = 0};"
                + "{100 * x2 + 155.365 * x4 + 2500 * x7 - x2 * x4 - 25 * x4 * x7 - 15536.5 = 0};"
                + "{-x5 + ln(-x4 + 900) = 0};"
                + "{-x6 + ln(x4 + 300) = 0};"
                + "{-x7 + ln(-2 * x4 + 700) = 0};";
        this.detectConstraints(constraints);
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(28);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(15);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.83);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(19990);       // Número de evaluaciones   

        return this.configurator;
    }

}
