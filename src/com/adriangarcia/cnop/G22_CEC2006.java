package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G22_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: x1 <br>
 * Subject to: -x1 + x2^0.6 + x3^0.6 + x4^0.6 <= 0 <br>
 * x5 - 100000 * x8 + 1 * 10^7 = 0 <br>
 * x6 + 100000 * x8 - 100000 * x9 = 0 <br>
 * x7 + 100000 * x9 - 5 * 10^7 = 0 <br>
 * x5 + 100000 * x10 - 3.3 * 10^7 = 0 <br>
 * x6 + 100000 * x11 - 4.4 * 10^7 = 0 <br>
 * x7 + 100000 * x12 - 6.6 * 10^7 = 0 <br>
 * x5 - 120 * x2 * x13 = 0 <br>
 * x6 - 80 * x3 * x14 = 0 <br>
 * x7 - 40 * x4 * x15 = 0 <br>
 * x8 - x11 + x16 = 0 <br>
 * x9 - x12 + x17 = 0 <br>
 * -x18 + ln(x10 - 100) = 0 <br>
 * -x19 + ln(-x8 + 300) = 0 <br>
 * -x20 + ln(x16) = 0 <br>
 * -x21 + ln(-x9 + 400) = 0 <br>
 * -x22 + ln(x17) = 0 <br>
 * -x8 - x10 + x1 * x18 - x13 * x19 + 400 = 0 <br>
 * x8 - x9 - x11 + x14 * x20 - x14 * x21 + 400 = 0 <br>
 * x9 - x12 - 4.60517 * x15 + x15 * x22 + 100 = 0 <br> 
 * where: (0,20000);ran{2:4,0:1*10^6};ran{5:7,0:4*10^7};(100,299.99);(100,399.99);(100.01,300);(100,400);(100,600);ran{13:15,0:500};(0.01,300);(0.01,400);ran{18:22,-4.7:6.25}
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G22_CEC2006 extends Problem{

    public G22_CEC2006() {
        this.setBestKnownValue(236.4309755040); 
        this.setNameProblem("G22 CEC2006");
        this.setFunction("x1");                   //Función objetivo
        this.setOrderVariables("iter{x,1,22}");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,20000);ran{2:4,0:1*10^6};ran{5:7,0:4*10^7};"
                + "(100,299.99);(100,399.99);(100.01,300);(100,400);(100,600);"
                + "ran{13:15,0:500};(0.01,300);(0.01,400);ran{18:22,-4.7:6.25}"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{-x1 + x2^0.6 + x3^0.6 + x4^0.6 <= 0};"
                + "{x5 - 100000 * x8 + 1 * 10^7 = 0};"
                + "{x6 + 100000 * x8 - 100000 * x9 = 0};"
                + "{x7 + 100000 * x9 - 5 * 10^7 = 0};"
                + "{x5 + 100000 * x10 - 3.3 * 10^7 = 0};"
                + "{x6 + 100000 * x11 - 4.4 * 10^7 = 0};"
                + "{x7 + 100000 * x12 - 6.6 * 10^7 = 0};"
                + "{x5 - 120 * x2 * x13 = 0};"
                + "{x6 - 80 * x3 * x14 = 0};"
                + "{x7 - 40 * x4 * x15 = 0};"
                + "{x8 - x11 + x16 = 0};"
                + "{x9 - x12 + x17 = 0};"
                + "{-x18 + ln(x10 - 100) = 0};"
                + "{-x19 + ln(-x8 + 300) = 0};"
                + "{-x20 + ln(x16) = 0};"
                + "{-x21 + ln(-x9 + 400) = 0};"
                + "{-x22 + ln(x17) = 0};"
                + "{-x8 - x10 + x1 * x18 - x13 * x19 + 400 = 0};"
                + "{x8 - x9 - x11 + x14 * x20 - x14 * x21 + 400 = 0};"
                + "{x9 - x12 - 4.60517 * x15 + x15 * x22 + 100 = 0}";
        this.detectConstraints(constraints);
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(50);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.005);    // Se establece el tamaño de paso.        
        this.configurator.setNc(24);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.915);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(102);     // Frecuencia de reproducción
        this.configurator.setEvaluations(20000);       // Número de evaluaciones   

        return this.configurator;
    }

}
