package com.adriangarcia.cnop;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * G02_CEC2006 class that creates a test CNOP. 
 * This CNOP has as objective function:<br><br>
 * 
 * Minimize: -abs((sum{x,1,20,cos(xi)^4} - 2 * prod{x,1,20,cos(xi)^2}) / (sqrt(sum{x,1,20,i*xi^2}))) <br>
 * Subject to: 0.75 - prod{x,1,20,xi} <= 0 <br> 
 *             sum{x,1,20,xi} - 7.5 * 20 <= 0 <br>              
 * where: ran{1:20,0:10}
 * 
 * <br> This class inherits the mechanisms of the Problem class.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G02_CEC2006 extends Problem{

    public G02_CEC2006() {
        this.setBestKnownValue(-0.8036191042); 
        this.setNameProblem("G02 CEC2006");
        //this.setFunction("-abs(((cos(x1)^4 + cos(x2)^4 + cos(x3)^4 + cos(x4)^4 + cos(x5)^4 + cos(x6)^4 + cos(x7)^4 + cos(x8)^4 + cos(x9)^4 + cos(x10)^4 + cos(x11)^4 + cos(x12)^4 + cos(x13)^4 + cos(x14)^4 + cos(x15)^4 + cos(x16)^4 + cos(x17)^4 + cos(x18)^4 + cos(x19)^4 + cos(x20)^4) - 2 * (cos(x1)^2 * cos(x2)^2 * cos(x3)^2 * cos(x4)^2 * cos(x5)^2 * cos(x6)^2 * cos(x7)^2 * cos(x8)^2 * cos(x9)^2 * cos(x10)^2 * cos(x11)^2 * cos(x12)^2 * cos(x13)^2 * cos(x14)^2 * cos(x15)^2 * cos(x16)^2 * cos(x17)^2 * cos(x18)^2 * cos(x19)^2 * cos(x20)^2)) / (sqrt((1 * x1^2 + 2 * x2^2 + 3 * x3^2 + 4 * x4^2 + 5 * x5^2 + 6 * x6^2 + 7 * x7^2 + 8 * x8^2 + 9 * x9^2 + 10 * x10^2 + 11 * x11^2 + 12 * x12^2 + 13 * x13^2 + 14 * x14^2 + 15 * x15^2 + 16 * x16^2 + 17 * x17^2 + 18 * x18^2 + 19 * x19^2 + 20 * x20^2))))");                   //Función objetivo       
        this.setFunction("-abs((sum{x,1,20,cos(xi)^4} - 2 * prod{x,1,20,cos(xi)^2}) / (sqrt(sum{x,1,20,i*xi^2})))");                   //Función objetivo       
        this.setOrderVariables("iter{x,1,20}"); //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        //this.setRankVariable("(0,10),(0,10),(0,10),(0,10),(0,10),"
        //                   + "(0,10),(0,10),(0,10),(0,10),(0,10),"
        //                   + "(0,10),(0,10),(0,10),(0,10),(0,10),"
        //                   + "(0,10),(0,10),(0,10),(0,10),(0,10)"); //Se inserta el rango para cada variable.
        this.setRankVariable("ran{1:20,0:10}");
        //Se aplica el metodo para detectar las restricciones        
        //this.detectConstraints("{0.75 - (x1 * x2 * x3 * x4 * x5 * x6 * x7 * x8 * x9 * x10 * x11 * x12 * x13 * x14 * x15 * x16 * x17 * x18 * x19 * x20) <= 0};"
        //        + "{(x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8 + x9 + x10 + x11 + x12 + x13 + x14 + x15 + x16 + x17 + x18 + x19 + x20) - 7.5 * 20 <= 0}");
        this.detectConstraints("{0.75 - prod{x,1,20,xi} <= 0};"
                + "{sum{x,1,20,xi} - 7.5 * 20 <= 0}");
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(15);            /* Se establece la cantidad de bacterias 
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
