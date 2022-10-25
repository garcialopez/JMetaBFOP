package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G01_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: 5 * sum{x,1,4,xi} - 5 * sum{x,1,4,xi^2} - sum{x,5,13,xi} <br>
 * Subject to: 2 * x1 + 2 * x2 + x10 + x11 - 10 <= 0 <br> 
 *             2 * x1 + 2 * x3 + x10 + x12 - 10 <= 0 <br> 
 *             2 * x2 + 2 * x3 + x11 + x12 - 10 <= 0 <br> 
 *             -8 * x1 + x10 <= 0 <br> 
 *             -8 * x2 + x11 <= 0 <br> 
 *             -8 * x3 + x12 <= 0 <br> 
 *             -2 * x4 - x5 + x10 <= 0 <br> 
 *             -2 * x6 - x7 + x11 <= 0 <br> 
 *             -2 * x8 - x9 + x12 <= 0 <br> 
 * where: ran{1:9,0:1};ran{10:12,0:100};(0,1)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G01_CEC2006 extends Problem{

    public G01_CEC2006() {
        this.setBestKnownValue(-15.0000000000); 
        this.setNameProblem("G01 CEC2006");
        //this.setFunction("5 * (x1 + x2 + x3 + x4) - 5 * (x1^2 + x2^2 + x3^2 + x4^2) - (x5 + x6 + x7 + x8 + x9 + x10 + x11 + x12 + x13)");                   //Función objetivo
        this.setFunction("5 * sum{x,1,4,xi} - 5 * sum{x,1,4,xi^2} - sum{x,5,13,xi}");                   //Función objetivo
        //this.setOrderVariables("x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13");             //Orden de sus variables        
        this.setOrderVariables("iter{x,1,13}");   //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);              // Objetivo -> min o max        
        //this.setRankVariable("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),"
        //                   + "(0,100),(0,100),(0,100),(0,1)"); //Se inserta el rango para cada variable.
        this.setRankVariable("ran{1:9,0:1};ran{10:12,0:100};(0,1)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones        
        this.detectConstraints("{2 * x1 + 2 * x2 + x10 + x11 - 10 <= 0};"
                             + "{2 * x1 + 2 * x3 + x10 + x12 - 10 <= 0};"
                             + "{2 * x2 + 2 * x3 + x11 + x12 - 10 <= 0};"
                             + "{-8 * x1 + x10 <= 0};{-8 * x2 + x11 <= 0};"
                             + "{-8 * x3 + x12 <= 0};"
                             + "{-2 * x4 - x5 + x10 <= 0};"
                             + "{-2 * x6 - x7 + x11 <= 0};"
                             + "{-2 * x8 - x9 + x12 <= 0}"
        );
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(13);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.0005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(5);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(20000);       // Número de evaluaciones   

        return this.configurator;
    }

}
