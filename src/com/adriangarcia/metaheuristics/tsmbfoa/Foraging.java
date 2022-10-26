package com.adriangarcia.metaheuristics.tsmbfoa;

/**
 * 
 * <b>Foraging</b> provides an interface for defining the process of bacterial 
 * foraging: chemotaxis, grouping, reproduction and elimination-dispersal. 
 * If you implement the Foraging object it can be used while building 
 * the <b>Tsmbfoa</b> object. <br>
 * 
 * This interface can be used to include other BFO-based algorithms. 
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */

public interface Foraging {
    /**     
     * In the chemotaxis method the chemotaxis process of TS-MBFOA is performed, 
     * where two swims are interleaved, in each cycle one exploitation or 
     * exploration swim is performed. 
     * 
     * @param configurator object of the class Configurator
     * @param problem object of the class Problem
     * @param function object of the class Function
     */
    public abstract void chemotaxis(Configurator configurator, Problem problem, Function function);
    
    /**
     * The grouping method is a TS-MBFOA process, which is applied in the 
     * middle cycle of the chemotaxic process.
     */
    public abstract void grouping();
    
    /**
     * In the reproduction method, the bacteria are ordered based on the 
     * constraint management technique, eliminating the worst Sb - Sr 
     * bacteria and duplicating the best ones.
     * 
     * @param configurator object of the class Configurator
     */
    public abstract void reproduction(Configurator configurator);
    
    /**
     * In the eliminationDispersal method, the worst bacterium is eliminated 
     * from the population based on feasibility rules and a 
     * new one is randomly generated.
     * 
     * @param configurator object of the class Configurator
     * @param problem object of the class Problem
     * @param function object of the class Function 
     */
    public abstract void eliminationDispersal(Configurator configurator, Problem problem, Function function);
    
    /**
     * The updateStepSize method updates the TS-MBFOA step size.
     * 
     * @param configurator object of the class Configurator
     * @param problem object of the class Problem      
     */
    public abstract void updateStepSize(Configurator configurator, Problem problem);
}

