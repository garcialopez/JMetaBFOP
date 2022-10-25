package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * PressureVessel class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: (0.6224* x1 * x3 * x4) + (1.7781*x2*x3^2) + (3.1661*x1^2*x4) + (19.84*x1^2*x3) <br>
 * Subject to: (-1 * x1) + (0.0193*x3) <= 0 <br> 
 * (-1 * x2) + (0.00954*x3) <= 0 <br> 
 * (-3.1416 * x3^2 * x4) - ((4/3) * 3.1416 * x3^3) + 1296000 <= 0 <br> 
 * x4 - 240 <= 0 <br> 
 * where: (1, 99),(1, 99),(10, 200),(10,200)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class PressureVessel extends Problem{

    public PressureVessel() {
        this.setBestKnownValue(6059.946); 
        this.setNameProblem("Pressure Vessel");
        this.setFunction("(0.6224* x1 * x3 * x4) + (1.7781*x2*x3^2) + (3.1661*x1^2*x4) + (19.84*x1^2*x3)");                   //Función objetivo
        this.setOrderVariables("x1,x2,x3,x4");                   //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                                // Objetivo -> min o max        
        this.setRankVariable("(1, 99),(1, 99),(10, 200),(10,200)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones
        String constraints = "{(-1 * x1) + (0.0193*x3) <= 0};"
                           + "{(-1 * x2) + (0.00954*x3) <= 0};"
                           + "{(-3.1416 * x3^2 * x4) - ((4/3) * 3.1416 * x3^3) + 1296000 <= 0};"
                           + "{x4 - 240 <= 0}";
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
        this.configurator.setEvaluations(15000);       // Número de evaluaciones   

        return this.configurator;
    }
    
}
