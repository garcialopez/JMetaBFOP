package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G04_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: 5.3578547 * x3^2 + 0.8356891 * x1 * x5 + 37.293239 * x1 - 40792.141 <br>
 * Subject to: 85.334407 + 0.0056858 * x2 * x5 + 0.0006262 * x1 * x4 - 0.0022053 * x3 * x5 - 92 <= 0 <br> 
 *             -85.334407 - 0.0056858 * x2 * x5 - 0.0006262 * x1 * x4 + 0.0022053 * x3 * x5 <= 0 <br>  
 *              80.51249 + 0.0071317 * x2 * x5 + 0.0029955 * x1 * x2 + 0.0021813 * x3^2 - 110 <= 0 <br> 
 *              -80.51249 - 0.0071317 * x2 * x5 - 0.0029955 * x1 * x2 - 0.0021813 * x3^2 + 90 <= 0 <br>  
 *              9.300961 + 0.0047026 * x3 * x5 + 0.0012547 * x1 * x3 + 0.0019085 * x3 * x4 - 25 <= 0 <br>  
 *             -9.300961 - 0.0047026 * x3 * x5 - 0.0012547 * x1 * x3 - 0.0019085 * x3 * x4 + 20 <= 0 <br>  
 * where: (78,102),(33,45),(27,45),(27,45),(27,45)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G04_CEC2006 extends Problem{

    public G04_CEC2006() {
        this.setBestKnownValue(-30665.5386717834); 
        this.setNameProblem("G04 CEC2006");
        this.setFunction("5.3578547 * x3^2 + 0.8356891 * x1 * x5 + 37.293239 * x1 - 40792.141");                   //Función objetivo
        this.setOrderVariables("x1,x2,x3,x4,x5");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(78,102),(33,45),(27,45),(27,45),(27,45)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{85.334407 + 0.0056858 * x2 * x5 + 0.0006262 * x1 * x4 - 0.0022053 * x3 * x5 - 92 <= 0};"
                + "{-85.334407 - 0.0056858 * x2 * x5 - 0.0006262 * x1 * x4 + 0.0022053 * x3 * x5 <= 0};"
                + "{80.51249 + 0.0071317 * x2 * x5 + 0.0029955 * x1 * x2 + 0.0021813 * x3^2 - 110 <= 0};"
                + "{-80.51249 - 0.0071317 * x2 * x5 - 0.0029955 * x1 * x2 - 0.0021813 * x3^2 + 90 <= 0};"
                + "{9.300961 + 0.0047026 * x3 * x5 + 0.0012547 * x1 * x3 + 0.0019085 * x3 * x4 - 25 <= 0};"
                + "{-9.300961 - 0.0047026 * x3 * x5 - 0.0012547 * x1 * x3 - 0.0019085 * x3 * x4 + 20 <= 0}";
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
