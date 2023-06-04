package com.garcialopez.metaheuristic.tsmbfoa;

import com.garcialopez.optimizationmodel.CNOP;

/**
 *
 * <b>Foraging</b> provides an interface for defining the process of bacterial
 * foraging: chemotaxis, grouping, reproduction and elimination-dispersal. If
 * you implement the Foraging object it can be used while building the
 * <b>Tsmbfoa</b> object. <br>
 *
 * This interface can be used to include other BFO-based algorithms.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public interface Foraging {

    /**
     * Performs the chemotaxis process in the TS-MBFOA algorithm.
     *
     * This method interleaves two types of swims, exploitation and exploration,
     * in each cycle. The chemotaxis process is a key step in the TS-MBFOA
     * algorithm, where the bacteria perform movements and update their
     * positions based on exploration and exploitation strategies.
     *
     * @param cnop the CNOP instance containing the problem information.
     * @param bacteria the TSMBFOA instance representing the bacteria
     * population.
     */
    public abstract void chemotaxis(CNOP cnop, TSMBFOA bacteria);

    /**
     * Applies the grouping method in the TS-MBFOA algorithm.
     *
     * The grouping method is a process specific to the TS-MBFOA algorithm, and
     * it is executed in the middle cycle of the chemotaxis process. This method
     * performs the grouping of bacteria based on certain criteria or rules
     * defined in the algorithm. The purpose of grouping is to promote
     * collaboration and exchange of information between bacteria to enhance the
     * exploration and exploitation capabilities.
     */
    public abstract void grouping();

    /**
     * Applies the reproduction method in the TS-MBFOA algorithm.
     *
     * In the reproduction method, the bacteria are ordered based on a
     * constraint management technique. The worst Sb - Sr bacteria are
     * eliminated, and the best ones are duplicated. This process aims to
     * improve the population by removing less fit bacteria and replicating the
     * more fit ones.
     *
     * @param bacteria the population of bacteria
     */
    public abstract void reproduction(TSMBFOA bacteria);

    /**
     * Applies the elimination and dispersal method in the TS-MBFOA algorithm.
     *
     * In the elimination and dispersal method, the worst bacterium is
     * eliminated from the population based on feasibility rules, and a new
     * bacterium is randomly generated to replace it. This process helps
     * maintain diversity in the population and prevent stagnation.
     *
     * @param cnop the CNOP problem instance
     * @param bacteria the population of bacteria
     */
    public abstract void eliminationDispersal(CNOP cnop, TSMBFOA bacteria);

    /**
     * Updates the step size in the TS-MBFOA algorithm.
     *
     * The updateStepSize method adjusts the step size of each bacterium in the
     * population based on the algorithm-specific rules. The step size
     * determines the distance and direction of each bacterium's movement in the
     * search space.
     *
     * @param cnop the CNOP problem instance
     * @param bacteria the population of bacteria
     */
    public abstract void updateStepSize(CNOP cnop, TSMBFOA bacteria);
}
