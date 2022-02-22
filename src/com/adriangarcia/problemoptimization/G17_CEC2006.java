/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adriangarcia.problemoptimization;

import com.adriangarcia.metaheuristics.tsmbfoa.Configurator;
import com.adriangarcia.metaheuristics.tsmbfoa.Problem;

/**
 * 
 * @author José Adrian
 */
public class G17_CEC2006 extends Problem{

    public G17_CEC2006() {
        String fx1 = "if(x1 >= 0 & x1 < 300, 30 * x1, 31 * x1)";
        String fx2 = "iff(x2 >= 0 & x2 < 100, 28 * x2; x2 >= 100 & x2 < 200, 29 * x2; x2 >= 200 & x2 < 1000, 30 * x2 )";
        
        this.setBestKnownValue(8853.53967480648); 
        this.setNameProblem("G17 CEC2006");
        this.setFunction(fx1 + " + " + fx2);                   //Función objetivo
        this.setOrderVariables("x1,x2,x3,x4,x5,x6");             //Orden de sus variables        
        this.setObj(Problem.MINIMIZATION);                      // Objetivo -> min o max        
        this.setRankVariable("(0,400),(0,1000),(340,420),(340,420),(-1000,1000),(0,0.5236)"); //Se inserta el rango para cada variable.
        //Se aplica el metodo para detectar las restricciones        
//        this.detectConstraints("{(-1 * x1) + 300 - ((x3*x4)/131.078) * cos(1.48477 - x6) + ((0.90798 * x3^2)/131.078) * cos(1.47588) = 0}"
//                + ";{(-1 * x2) - ((x3 *x4)/131.078) * sin(1.48477 + x6) + ((0.90798 * x4^2)/131.078) * cos(1.47588) = 0}"
//                + ";{(-1 * x5) - ((x3*x4)/131.078) * sin(1.48477 + x6) + ((0.90798 * x4^3)/131.078) * sin(1.47588) = 0}"
//                + ";{200 - ((x3*x4)/131.078) * sin(1.48477 - x6) + ((0.90798 * x3^2)/131.078) * sin(1.47588) = 0}");
        this.detectConstraints("{(-1*x1)+300-(((x3*x4)/131.078)*cos(1.48477-x6))+(((0.90798*x3*x3)/131.078)*cos(1.47588)) = 0}"
                + ";{(-1*x2)-(((x3*x4)/131.078)*cos(1.48477+x6))+(((0.90798*x4*x4)/131.078)*cos(1.47588)) = 0}"
                + ";{(-1*x5)-(((x3*x4)/131.078)*sin(1.48477+x6))+(((0.90798*x4*x4)/131.078)*sin(1.47588)) = 0}"
                + ";{200-(((x3*x4)/131.078)*sin(1.48477-x6))+(((0.90798*x3*x3)/131.078)*sin(1.47588)) = 0}");
//        
    }
    
    @Override
    public Configurator getRecommendedSetting() {
        this.configurator = new Configurator();
        /**
         * Inicia la calibración de parámetros del algoritmo...
         */
        this.configurator.setSb(14);            /* Se establece la cantidad de bacterias 
                                                    para la población inicial.         */
        this.configurator.setStepSize(0.00004);    // Se establece el tamaño de paso.        
        this.configurator.setNc(7);            /* Se establece el número de ciclos         
                                                   quimiotáxicos.                     */
        this.configurator.setScalingFactor(1.95);      // Se establece el factor de escalamiento.      
        this.configurator.setBacteriaReproduce(1);          /* Se establece el número de bacterias 
                                                   a reproducir.                      */
        this.configurator.setRepcycle(100);     // Frecuencia de reproducción
        this.configurator.setEvaluations(30000);       // Número de evaluaciones   

        return this.configurator;
    }

}
