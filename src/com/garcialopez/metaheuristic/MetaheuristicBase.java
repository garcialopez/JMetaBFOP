package com.garcialopez.metaheuristic;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class for metaheuristic algorithms.
 * <br>
 * This class provides common functionality and data structures used by
 * metaheuristic algorithms. It includes methods for managing statistics,
 * storing results, and accessing convergence data.
 * <br>
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class MetaheuristicBase {

    private final String[] statisticsName = {"Best", "Mean", "Median", "St.d", "Worst", "FeasibleRate", "SuccessRate", "SuccessPerformance"};
    private int evaluations = 30000;
    private int executions = 1;
    private int gmax = 0;

    private List<double[]> bestResultsAux;
    private double[][] bestResults;
    private List<Double> statistics;

    private List<Double> y_bestSolutionConvergenceMedia;
    private List<Double> x_convergenceMedia;

    private List<Double> y_bestSolutionConvergence;
    private List<Double> x_convergence;

    private double[][] individuals;

    private long timeSeconds;
    private int advance = 0;

    /**
     * Initializes the result data structures.
     */
    public void iniResults() {
        this.bestResultsAux = new ArrayList();
        this.statistics = new ArrayList();

        this.y_bestSolutionConvergenceMedia = new ArrayList();
        this.x_convergenceMedia = new ArrayList();

        this.y_bestSolutionConvergence = new ArrayList();
        this.x_convergence = new ArrayList();
    }

    /**
     * Returns the number of evaluations.
     *
     * @return the number of evaluations.
     */
    public int getEvaluations() {
        return evaluations;
    }

    /**
     * Sets the number of evaluations.
     *
     * @param evaluations the number of evaluations to set.
     */
    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;
    }

    /**
     * Returns the number of executions.
     *
     * @return the number of executions.
     */
    public int getExecutions() {
        return executions;
    }

    /**
     * Sets the number of executions.
     *
     * @param executions the number of executions to set.
     */
    public void setExecutions(int executions) {
        this.executions = executions;
    }

    /**
     * Returns the best results.
     *
     * @return the best results.
     */
    public double[][] getBestResults() {
        this.bestResults = new double[bestResultsAux.size()][];

        for (int i = 0; i < bestResults.length; i++) {
            bestResults[i] = bestResultsAux.get(i);
        }

        return bestResults;
    }

    /**
     * Adds the best results.
     *
     * @param bestResults the best results to add.
     */
    public void addBestResults(double[] bestResults) {
        this.bestResultsAux.add(bestResults);
    }

    /**
     * Adds a statistic.
     *
     * @param obj the statistic to add.
     */
    protected void addStatistic(double obj) {
        this.statistics.add(obj);
    }

    /**
     * Returns the statistics as an array of real numbers.
     *
     * @return the statistics.
     */
    public double[] getStatistic() {
        double[] sts = new double[this.statistics.size()];
        for (int i = 0; i < sts.length; i++) {
            sts[i] = this.statistics.get(i);
        }
        return sts;
    }

    /**
     * Method that returns the names of the statistics applied.
     *
     * @return the statisticsName
     */
    public String[] getStatisticsName() {
        return statisticsName;
    }

    /**
     * Returns the convergence data for the mean best solution.
     *
     * @return the convergence data for the mean best solution.
     */
    public double[][] getConvergenceMedia() {
        double[][] bestSolutionConvergence = new double[2][this.y_bestSolutionConvergenceMedia.size()];

        for (int i = 0; i < bestSolutionConvergence[0].length; i++) {
            bestSolutionConvergence[0][i] = this.x_convergenceMedia.get(i);
            bestSolutionConvergence[1][i] = this.y_bestSolutionConvergenceMedia.get(i);
        }

        return bestSolutionConvergence;
    }

    /**
     * Adds a data point to the convergence data for the mean best solution.
     *
     * @param x the x-coordinate of the data point.
     * @param y the y-coordinate of the data point.
     */
    public void addBestSolutionConvergenceMedia(double x, double y) {
        this.x_convergenceMedia.add(x);
        this.y_bestSolutionConvergenceMedia.add(y);
    }

    /**
     * Returns the convergence data for the best solution.
     *
     * @return the convergence data for the best solution.
     */
    public double[][] getConvergence() {
        double[][] bestSolutionConvergence = new double[2][this.y_bestSolutionConvergence.size()];

        for (int i = 0; i < bestSolutionConvergence[0].length; i++) {
            bestSolutionConvergence[0][i] = this.x_convergence.get(i);
            bestSolutionConvergence[1][i] = this.y_bestSolutionConvergence.get(i);
        }

        return bestSolutionConvergence;
    }

    /**
     * Adds a data point to the convergence data for the best solution.
     *
     * @param x the x-coordinate of the data point.
     * @param y the y-coordinate of the data point.
     */
    public void addBestSolutionConvergence(List<Double> x, List<Double> y) {
        this.x_convergence = x;
        this.y_bestSolutionConvergence = y;
    }

    /**
     * Clears the convergence data for the best solution.
     */
    public void clearConvergenceBestSolution() {
        this.x_convergence = new ArrayList();
        this.y_bestSolutionConvergence = new ArrayList();
    }

    /**
     * Returns data to plot the best results.
     *
     * @return the data for plotting the best results.
     */
    public double[][] getDataGraphicsBestResults() {

        int size = this.getBestResults().length;
        int sizeCol = this.getBestResults()[0].length;

        double[][] data = new double[2][size];

        for (int i = 0; i < size; i++) {
            data[0][i] = i + 1;
            data[1][i] = this.getBestResults()[i][sizeCol - 2];
        }

        return data;
    }

    /**
     * Returns the individuals.
     *
     * @return the individuals.
     */
    public double[][] getIndividuals() {
        return individuals;
    }

    /**
     * Sets the individuals.
     *
     * @param individuals the individuals to set.
     */
    public void setIndividuals(double[][] individuals) {
        this.individuals = individuals;
    }

    /**
     * Returns the time in seconds.
     *
     * @return the time in seconds.
     */
    public long getTimeSeconds() {
        return timeSeconds;
    }

    /**
     * Sets the time in seconds.
     *
     * @param timeSeconds the time in seconds to set.
     */
    public void setTimeSeconds(long timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    /**
     * Returns the advance.
     *
     * @return the advance.
     */
    public int getAdvance() {
        return advance;
    }

    /**
     * Sets the advance.
     *
     * @param advance the advance to set.
     */
    public void setAdvance(int advance) {
        this.advance = advance;
    }

    /**
     * Method to run the metaheuristic algorithm.
     */
    public void run() {
        // Placeholder method. Implement the algorithm logic here.
    }

    /**
     * Returns a description of the individual.
     *
     * @param individual the individual.
     * @param numberVar the number of variables.
     * @return the description of the individual.
     */
    public String getDescription(double[][] individual, int numberVar) {
        StringBuilder descriptionBuilder = new StringBuilder();
        int objFunPosition = individual[0].length - 2;
        int constraintPosition = individual[0].length - 1;
        int matrixSize = individual.length;

        descriptionBuilder.append("Objective function, variables information and sum of constraint violations.\n");

        for (int i = 0; i < matrixSize; i++) {

            descriptionBuilder.append("");
            descriptionBuilder.append((i + 1)).append(" -->\t");

            for (int j = 0; j < numberVar; j++) {
                descriptionBuilder.append(individual[i][j]).append("\t");
            }

            descriptionBuilder.append(individual[i][objFunPosition]).append("\t");
            descriptionBuilder.append(individual[i][constraintPosition]).append("\t");
            descriptionBuilder.append("\n");
        }

        return descriptionBuilder.toString();
    }

    /**
     * Returns the maximum number of generations.
     *
     * @return the maximum number of generations.
     */
    public int getGmax() {
        return gmax;
    }

    /**
     * Increments the maximum number of generations by the specified value.
     *
     * @param i the value to increment.
     */
    public void incrementGmax(int i) {
        this.gmax += i;
    }

    /**
     * Sets the maximum number of generations.
     *
     * @param gmax the maximum number of generations to set.
     */
    public void setGmax(int gmax) {
        this.gmax = gmax;
    }

}
