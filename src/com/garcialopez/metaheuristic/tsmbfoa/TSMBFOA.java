package com.garcialopez.metaheuristic.tsmbfoa;

import com.garcialopez.metaheuristic.MetaheuristicBase;
import com.garcialopez.metaheuristic.Population;
import com.garcialopez.metaheuristic.Statistics;
import com.garcialopez.optimizationmodel.CNOP;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * The TSMBFOA class represents the Bacterial Foraging Optimization Algorithm
 * with a
 *
 * Two Swin (TSMBFOA). It extends the MetaheuristicBase class.
 *
 * The TSMBFOA class encapsulates the parameters and methods specific to the
 * TSMBFOA algorithm.
 *
 * It includes properties for the population size (sb), the number of
 * chemotactic steps (nc),
 *
 * the number of bacteria to reproduce (bacteriaReproduce), the reproduction
 * cycle (repcycle),
 *
 * the scaling factor (scalingFactor), the step size, and the CNOP instance.
 *
 * The TSMBFOA class also inherits properties and methods from the
 * MetaheuristicBase class,
 *
 * which provides common functionalities for metaheuristic algorithms.
 *
 * This class is intended to be extended by concrete TSMBFOA implementations
 * that define
 *
 * the specific behavior of the algorithm.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class TSMBFOA extends MetaheuristicBase {

    private int sb = 50;
    private int nc = 24;
    private int bacteriaReproduce = 1;
    private int repcycle = 100;
    private double scalingFactor = 1.9;
    private double stepSize;
    private final CNOP cnop;
    private final boolean debug;

    private double[][] individuals;

    public TSMBFOA(CNOP cnop, boolean debug) {
        
        this.cnop = cnop;
        this.debug = debug;
    }

    /**
     * Method returns the stored value for the sb parameter of the TS-MBFOA.
     *
     * @return value of parameter Sb
     */
    public int getSb() {
        return sb;
    }

    /**
     * Method to assign the number of bacteria in the population.<br>
     * The number of the population is determined between [10, 500].
     *
     * @param sb the sb to set
     */
    public void setSb(int sb) {
        this.sb = sb;
    }

    /**
     * Method returns the stored value for the Nc parameter of the TS-MBFOA.
     *
     * @return value of parameter Nc
     */
    public int getNc() {
        return nc;
    }

    /**
     * Method that assigns value to the Nc parameter of the TS-MBFOA.<br>
     * the number of chemotaxis cycles [1,Sb]
     *
     * @param nc the nc to set
     */
    public void setNc(int nc) {
        this.nc = nc;
    }

    /**
     * Method returns the stored value for the Sr parameter of the TS-MBFOA.
     *
     * @return value of parameter bacteriaReproduce (Sr)
     */
    public int getBacteriaReproduce() {
        return bacteriaReproduce;
    }

    /**
     * Method that assigns value to the Sr parameter of the TS-MBFOA.<br>
     * (sr) the number of bacteria to reproduce [1, Sb/2]
     *
     * @param bacteriaReproduce the bacteriaReproduce (Sr) to set
     */
    public void setBacteriaReproduce(int bacteriaReproduce) {
        this.bacteriaReproduce = bacteriaReproduce;
    }

    /**
     * Method returns the stored value for the RepClycle parameter of the
     * TS-MBFOA.
     *
     * @return value of parameter repcycle
     */
    public int getRepcycle() {
        return repcycle;
    }

    /**
     * Method that assigns value to the RepCycle parameter of the TS-MBFOA.<br>
     * The reproduction frequency [1, GMAX/2]
     *
     * @param repcycle the repcycle to set
     */
    public void setRepcycle(int repcycle) {
        this.repcycle = repcycle;
    }

    /**
     * Method returns the stored value for the &beta; parameter of the TS-MBFOA.
     *
     * @return value of parameter scalingFactor (&beta;)
     */
    public double getScalingFactor() {
        return scalingFactor;
    }

    /**
     * Method that assigns value to the &beta; parameter of the TS-MBFOA.<br>
     * (&beta;) the scaling factor [0,2]
     *
     * @param scalingFactor the scalingFactor (&beta;) to set
     */
    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    /**
     * Method returns the stored value for the stepSize parameter of the
     * TS-MBFOA.
     *
     * @return value of parameter stepSize (R)
     */
    public double getStepSize() {
        return stepSize;
    }

    /**
     * Method that assigns value to the (R) parameter of the TS-MBFOA.<br>
     * Static step size [0,1].
     *
     * @param stepSize the stepSize to set
     */
    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public double[][] getIndividuals() {
        return individuals;
    }

    @Override
    public void setIndividuals(double[][] individuals) {
        this.individuals = individuals;
    }

    @Override
    public void run() {

        // Validate if the number of iterations is between [1 - 30].
        if (this.getExecutions() >= 1 && this.getExecutions() <= 30) {
            // Code to be executed if the number of iterations is between 1 and 30

            Statistics st = new Statistics();
            this.iniResults();

            System.out.println("Starting TS-MBFOA...");

            if (this.debug) {
                System.out.println(printParameters());
            }

            ProcessTSMBFOA tsmbfoa = new ProcessTSMBFOA();

            // Checking the median of the executions
            int medianExecution = (this.getExecutions() > 1) ? (int) this.getExecutions() / 2 : 1;
            double bestValueAux = Double.POSITIVE_INFINITY;
            boolean flag = true;
            List<Double> x_auxConver = new ArrayList();
            List<Double> y_auxConver = new ArrayList();

            int[] sccp = new int[this.getExecutions()];

            // Calculate the position of the objective function
            int positionOF = cnop.getNumberVariable() * 2;
            int positionSVR = cnop.getNumberVariable() * 2 + 1;

            long timeAux = System.nanoTime();

            //Se incluye el porcentaje de avance del algoritmo
            int percentageIncrease = (int) (100 / this.getExecutions());
            this.setAdvance(1);

            //medimos el tiempo de inicio
            timeAux = System.nanoTime();
            for (int i = 0; i < this.getExecutions(); i++) {

                //Variables to calculate performance.
                int bp = 0;
                int sperformace = 0;

                //Se hace depuraciones por partes si el debug esta habilitado
                if (this.debug) {
                    System.out.println("Start execution: " + (i + 1));
                    System.out.println("Generating population...");
                }

                // The bacteria population is initialized.
                this.individuals = Population.startPopulation(this.cnop, this.sb, true);

                // The objective function and constraints are evaluated, and assigned to the population matrix.
                this.individuals = cnop.evaluateObjectiveFunction(this.individuals);

                // We sort the population based on feasibility rules
                this.individuals = Population.sortPopulation(this.individuals);

                if (debug) {
                    System.out.println("Initial population sorted.\n"
                            + this.getDescription(this.getIndividuals(), cnop.getNumberVariable()));
                }

                // Auxiliary counters
                int count = 0;
                this.setGmax(0);

                if (this.debug) {
                    System.out.println("Bacterial foraging begins.");
                }

                /**
                 * Starting TS-MBFOA...
                 */
                // Calculate current evaluations for the stopping condition
                while ((count + (this.getSb() * this.getNc())) <= this.getEvaluations()) {   //stopping condition                 
                    
                    //Generation increment
                    this.incrementGmax(1);

                    if (this.debug) {
                        System.out.println("\n> Start of generation " + this.getGmax()
                                + " of execution " + (i + 1)
                                + "\n> Starts the chemotaxic process...");
                    }

                    //Chemotactic process
                    tsmbfoa.chemotaxis(this.cnop, this);

                    count += this.getSb() * this.getNc();
                    if (this.debug) {
                        System.out.println("> Starts the process of grouping and reproduction.");
                    }
                    //reproduction
                    tsmbfoa.reproduction(this);
                    if (debug) {
                        System.out.println("> Elimination-dispersion.");
                    }
                    // Elimination-dispersion
                    tsmbfoa.eliminationDispersal(cnop, this);
                    // Increment counter
                    count++;
                    if (this.debug) {
                        System.out.println("> Step size is updated.");
                    }
                    // Updating static step size
                    tsmbfoa.updateStepSize(this.cnop, this);

                    if ((i + 1) == medianExecution) {

                        this.addBestSolutionConvergenceMedia(
                                count, this.individuals[0][positionOF]
                        );
                    }

                    if (this.individuals[0][positionSVR] == 0) {
                        sperformace = st.successPerformance(this.individuals, this.cnop.getBestKnownValue());
                        if ((bp == 0) && (sperformace == 1)) {
                            sccp[i] = count;
                            bp = 1;
                        }
                    }

                    x_auxConver.add(Double.parseDouble(String.valueOf(count)));
                    y_auxConver.add(this.individuals[0][positionOF]);

                } //Close while the evaluation -> Bacterial foraging

                if (debug) {
                    System.out.println("\nTerminates bacterial foraging with "
                            + this.getGmax() + " generations.");

//                    System.out.println(this.getDescription(this.getIndividuals(), cnop.getNumberVariable()));
                } 

                if (this.getExecutions() > 1) {
                    double[] aux = {};
                    switch (this.cnop.getType()) {
                        case CNOP.MINIMIZATION:
                            aux = this.individuals[0];

                            break;
                        case CNOP.MAXIMIZATION:
                            aux = this.individuals[this.getSb() - 1];
                            break;
                        default:
                            System.err.println("Results cannot be saved, if min or max.");
                    }

                    this.addBestResults(aux);
                    //Se guarda la convergencia de la mejor solución en caso de la iteración ser mayor a 1                                                               

                    if (aux[positionOF] < bestValueAux && aux[positionSVR] == 0) {
                        this.clearConvergenceBestSolution();
                        bestValueAux = aux[positionOF];
                        this.addBestSolutionConvergence(x_auxConver, y_auxConver);
                        x_auxConver = new ArrayList();
                        y_auxConver = new ArrayList();
                    } else {
                        x_auxConver = new ArrayList();
                        y_auxConver = new ArrayList();
                    }

                } else {

                    for (double[] individual : this.individuals) {
                        this.addBestResults(individual);
                    }

                    if (debug) {
                        System.out.println("As there is 1 independent execution, the results are"
                                + "\nevaluated with the only population generated.");
                    }

                }

                //se calcula el porcentaje de avance
                if (this.getAdvance() + percentageIncrease - 1 >= 100) {
                    this.setAdvance(99);
                } else {
                    this.setAdvance(this.getAdvance() + percentageIncrease - 1);
                }

            } // close for Executions

            //Se añade el tiempo en segundos
            this.setTimeSeconds(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - timeAux));
            if (debug) {
                System.out.println("Final Results.\n" + this.getDescription(Population.sortPopulation(this.getBestResults()), cnop.getNumberVariable()));
            }
            //Mejor valor dependiendo el objetivos de max o min
            this.addStatistic(
                    (cnop.getType().equals(CNOP.MINIMIZATION))
                    ? st.best(this.getBestResults(), positionOF)
                    : st.worst(this.getBestResults(), positionOF)
            );

            //Media
            this.addStatistic(st.mean(this.getBestResults(), positionOF));

            //Mediana
            this.addStatistic(st.median(this.getBestResults(), positionOF));

            //Desviación estandar
            this.addStatistic(st.standardDeviation(this.getBestResults(), positionOF));

            //Peor valor
            this.addStatistic(
                    (cnop.getType().equals(cnop.MINIMIZATION))
                    ? st.worst(this.getBestResults(), positionOF)
                    : st.best(this.getBestResults(), positionOF)
            );

            //Tasa de factibilidad
            this.addStatistic(st.feasibleRate(this.getBestResults(), positionSVR));
            //Tasa de éxito
            this.addStatistic(st.successRate(this.getBestResults(), positionOF, cnop.getBestKnownValue()));

            int pd = 0;
            double pscp = 0;

            for (int i = 0; i < sccp.length; i++) {
                if (sccp[i] > 0) {
                    pd++;
                    pscp = pscp + sccp[i];
                }

            }

            if (pscp > 0 && pd > 0) {
                pscp = pscp / pd;
            } else {
                pscp = 0;
            }

            if (pscp > 0) {
                this.addStatistic(Math.floor(pscp * this.getExecutions()) / pd);
            } else {
                this.addStatistic(0);
            }

            if (debug) {
                System.out.println("\nCalculating statistics...");
                for (int i = 0; i < this.getStatistic().length; i++) {
                    System.out.println(this.getStatisticsName()[i] + ": " + this.getStatistic()[i]);
                }
            }
            if (debug) {
                System.out.println("Time: " + this.getTimeSeconds() + "\n");
            }

            if (this.getAdvance() < 100) {
                this.setAdvance(100);
            }

            System.out.println("JMetaBFOP completed.");

        } else {
            System.err.println("The number of iterations should be between [1 - 30.]");
        }// close else

    }//close run

    /**
     *
     * Prints the parameter values in a formatted string.
     * <br>
     *
     * @return the formatted string with parameter values
     */
    private String printParameters() {
        String line = "-".repeat(37);
        String format = "Preparing CNOP: %s for %d iterations.%n"
                + "|%s|%n"
                + "| Parameter calibration for TS-MBFOA: |%n"
                + "|%s|%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "| %-23s | %9s |%n"
                + "|%s|%n";

        return String.format(format, cnop.getNameProblem(), this.getExecutions(),
                line,
                line,
                "Bacteria", this.getSb(),
                "StepSize", this.getStepSize(),
                "Chemotoxic cycles", this.getNc(),
                "Bacteria to reproduce", this.getBacteriaReproduce(),
                "Scaling factor", this.getScalingFactor(),
                "Evaluations", this.getEvaluations(),
                "Reproduction frequency", this.getRepcycle(),
                line);
    }

}
