package com.garcialopez.metaheuristic;

import com.garcialopez.optimizationmodel.CNOP;
import java.util.Arrays;

/**
 * <b>Population</b> class is the initial process of the TS-MBFOA, where the
 * initial random population is created based on uniform distribution and using
 * the NumberRandom class. This class also has the mechanism to sort the
 * solution space for the CNOP based on feasibility rules.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Population {

    /**
     * Method that takes care of starting the population of individuals with
     * size sizeRow X number of variables the cnop.
     * <br>
     *
     * @param cnop the CNOP instance.
     * @param sizeRow the number of rows in the population matrix.
     * @param duplicateVariable flag indicating whether to create new positions
     * for step size. This applies only to certain algorithms, such as bacterial
     * foraging.
     * <br>
     * @return a matrix representing the solution space for the CNOP.
     */
    public static double[][] startPopulation(CNOP cnop, int sizeRow, boolean duplicateVariable) {
        final NRandom nRandom = new NRandom();

        int sizeColumns = 2; //for FO and SVR
        int variables = cnop.getNumberVariable();

        //Creating new positions for step size applies only to certain algorithms, such as bacterial foraging.
        sizeColumns += (duplicateVariable) ? (variables * 2) : variables;

        double[][] population = new double[sizeRow][sizeColumns];

        for (double[] population1 : population) {

            for (int j = 0; j < variables; j++) {

                if (cnop.isContinuousVariable()[j]) { // is continuous

                    population1[j] = nRandom.getRandomRankUnif(cnop.getVariableRange()[j][0], cnop.getVariableRange()[j][1]);

                    if (duplicateVariable) {
                        population1[j + variables] = nRandom.getRandomRankUnif(cnop.getVariableRange()[j][0], cnop.getVariableRange()[j][1]);
                    }

                } else { // is discrete

                    // We select one from the set
                    int indice;

                    indice = nRandom.getNetxInt(cnop.getVariableRange()[j].length);
                    population1[j] = cnop.getVariableRange()[j][indice];

                    if (duplicateVariable) {
                        indice = nRandom.getNetxInt(cnop.getVariableRange()[j].length);
                        population1[j + variables] = cnop.getVariableRange()[j][indice];
                    }

                }
            }
        }
        return population;
    }

    /**
     * This method sorts the population using an ordering technique based on
     * feasibility rules.
     *
     * @param individual the population matrix to be sorted.
     * @return the sorted population matrix.
     */
    public static double[][] sortPopulation(double[][] individual) {

        int objFunPosition = individual[0].length - 2;
        int constraintPosition = individual[0].length - 1;
        int matrixSize = individual.length;

        for (int i = 0; i < matrixSize; i++) {

            for (int j = 0; j < (matrixSize - 1); j++) {

                if ((individual[j][constraintPosition] == 0) && (individual[j + 1][constraintPosition] == 0)) {
                    if (individual[j][objFunPosition] > individual[j + 1][objFunPosition]) {
                        double[] temp = Arrays.copyOf(individual[j], individual[j].length);
                        individual[j] = Arrays.copyOf(individual[j + 1], individual[j + 1].length);
                        individual[j + 1] = Arrays.copyOf(temp, temp.length);
                    }
                }

                if ((individual[j][constraintPosition] > 0) && (individual[j + 1][constraintPosition] > 0)) {
                    if (individual[j][constraintPosition] > individual[j + 1][constraintPosition]) {
                        double[] temp = Arrays.copyOf(individual[j], individual[j].length);
                        individual[j] = Arrays.copyOf(individual[j + 1], individual[j + 1].length);
                        individual[j + 1] = Arrays.copyOf(temp, temp.length);
                    }
                }

                if ((individual[j][constraintPosition]) > 0 && (individual[j + 1][constraintPosition] == 0)) {
                    double[] temp = Arrays.copyOf(individual[j], individual[j].length);
                    individual[j] = Arrays.copyOf(individual[j + 1], individual[j + 1].length);
                    individual[j + 1] = Arrays.copyOf(temp, temp.length);
                }

            }// end for two

        } //end for main

        return individual;
    }//close sort

}
