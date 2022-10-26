package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * DesignReinforcedConcreteBeam class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: 29.4*x1 + 18*x2 <br>
 * Subject to: (x1 - (0.2458 * x1^2)) / x2 >= 6 <br> 
 * where: (0,115.8),(0.00001,30)
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class DesignReinforcedConcreteBeam extends Problem{

    public DesignReinforcedConcreteBeam() {
        this.setBestKnownValue(376.2919); 
        this.setNameProblem("Design of a reinforced concrete beam");
        this.setFunction("29.4*x1 + 18*x2");                   //Función objetivo
        this.setOrderVariables("x1,x2");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,115.8),(0.00001,30)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones        
        this.detectConstraints("{(x1 - (0.2458 * x1^2)) / x2 >= 6}");
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(14);
                                     
        this.configurator.setStepSize(0.0005);
        this.configurator.setNc(7);            
                                                  
        this.configurator.setScalingFactor(1.95);     
        this.configurator.setBacteriaReproduce(1);   
                                                  
        this.configurator.setRepcycle(100);    
        this.configurator.setEvaluations(20000);  

        return this.configurator;
    }

}
