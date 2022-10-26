package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G18_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: -0.5 * (x1 * x4 - x2 * x3 + x3 * x9 - x5 * x9 + x5 * x8 - x6 * x7) <br>
 * Subject to: x3^2 + x4^2 - 1 <= 0 <br>
 * x9^2 - 1 <= 0 <br>
 * x5^2 + x6^2 - 1 <= 0 <br>
 * x1^2 + (x2 - x9)^2 - 1 <= 0 <br>
 * (x1 - x5)^2 + (x2 - x6)^2 - 1 <= 0 <br>
 * (x1 - x7)^2 + (x2 - x8)^2 - 1 <= 0 <br>
 * (x3 - x5)^2 + (x4 - x6)^2 - 1 <= 0 <br>
 * (x3 - x7)^2 + (x4 - x8)^2 - 1 <= 0 <br>
 * x7^2 + (x8 - x9)^2 - 1 <= 0 <br>
 * x2 * x2 - x1 * x4 <= 0 <br>
 * -x3 * x9 <= 0 <br>
 * x5 * x9 <= 0 <br>
 * x6 * x7 - x5 * x8 <= 0 <br> 
 * where: ran{1:8,-10:10};(0,20)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G18_CEC2006 extends Problem{

    public G18_CEC2006() {
        this.setBestKnownValue(-0.8660254038); 
        this.setNameProblem("G18 CEC2006");
        this.setFunction("-0.5 * (x1 * x4 - x2 * x3 + x3 * x9 - x5 * x9 + x5 * x8 - x6 * x7)");      //Función objetivo
        this.setOrderVariables("iter{x,1,9}");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("ran{1:8,-10:10};(0,20)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{x3^2 + x4^2 - 1 <= 0};"
                + "{x9^2 - 1 <= 0};"
                + "{x5^2 + x6^2 - 1 <= 0};"
                + "{x1^2 + (x2 - x9)^2 - 1 <= 0};"
                + "{(x1 - x5)^2 + (x2 - x6)^2 - 1 <= 0};"
                + "{(x1 - x7)^2 + (x2 - x8)^2 - 1 <= 0};"
                + "{(x3 - x5)^2 + (x4 - x6)^2 - 1 <= 0};"
                + "{(x3 - x7)^2 + (x4 - x8)^2 - 1 <= 0};"
                + "{x7^2 + (x8 - x9)^2 - 1 <= 0};"
                + "{x2 * x2 - x1 * x4 <= 0};"
                + "{-x3 * x9 <= 0};"
                + "{x5 * x9 <= 0};"
                + "{x6 * x7 - x5 * x8 <= 0}";
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
        this.configurator.setNc(13);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(15000);       // Número de evaluaciones   

        return this.configurator;
    }

}
