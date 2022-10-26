package com.adriangarcia.metaheuristics.tsmbfoa;

import com.adriangarcia.statistics.Statistics;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * <b>RunTsmbfoa</b> class is responsible for initiating the TS-MBFOA 
 * processes by fulfilling its generational cycles of the algorithm 
 * and applying the entire bacteria foraging process.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class RunTsmbfoa {

    private final Function function;    
    private final Mechanism mechanism;
    private final Tsmbfoa tsmbfoa;
    private final Statistics st;
    private double[] bestValue;    
    private double[][] aux;  
    private long timeAux;

    public RunTsmbfoa() {
        this.function = new Function();
        this.mechanism = new Mechanism();
        this.tsmbfoa = new Tsmbfoa();
        this.st = new Statistics();
    }

    /**
     * This method has two purposes: to parse the objective function with 
     * the help of the mXparser and to assign the constraints contained 
     * in the CNOP. 
     * 
     * @param problem object of the class Problem
     */
    private void adjustFunction(Problem problem) {
        // Parse to evaluate the objective function
        // The order of variables is established (x1, x2, x3, ..., xn).
        this.function.setFuncion(problem.getFunction(), problem.getOrderVariables());
        
        if (problem.getArguments()!=null) {
            this.function.setArgs(problem.getArguments());
        }
        
        if (problem.getConstraintsInequality() != null || problem.getConstraintsEquality() != null) {
            this.function.setConstraints(problem.getConstraintsInequality(), problem.getConstraintsEquality());
        }                        
    }

    /**
     * This method is responsible for implementing the algorithm. 
     * An instance of the RunTsmbfoa class and invoking the run method, 
     * triggers the execution of the TS-MBFOA processes.
     * 
     * @param problem object of the class Problem
     * @param configurator object of the class Configurator
     * @param debug set to true if you want the execution status to appear on 
     * the console and false if you do not want it to be activated. 
     * @param debugResults is set to true if you want the results to appear 
     * in the console and false if you do not want it to be activated. 
     */          
    public void run(Problem problem, Configurator configurator, boolean debug, boolean debugResults) {
        
        if(problem.getExecutions() > 0 && problem.getExecutions() <= 30){
            System.out.println("Starting...");
            //Se hace depuraciones por partes si el debug esta habilitado
            if (debug){
                System.out.println("Preparing CNOP: " + problem.getNameProblem()
                        + "\for  " + (problem.getExecutions()) + " executions."
                        + "\n"                                
                        + "\t\t  Parameter calibration\n"                
                        + "\t\tBacteria:\t\t" + configurator.getSb() + "\n"
                        + "\t\tStepSize:\t\t" + configurator.getStepSize() + "\n" 
                        + "\t\tChemotoxic cycles:\t" + configurator.getNc() + "\n"
                        + "\t\tBacteria to reproduce:  " + configurator.getBacteriaReproduce() + "\n"
                        + "\t\tScaling factor:\t\t" + configurator.getScalingFactor() + "\n"
                        + "\t\tEvaluations:\t\t" + configurator.getEvaluations() + "\n"
                        + "\t\tReproduction frequency: " + configurator.getRepcycle() + "\n");
            }                                      
            
            //en caso de ser mañor a 1 ejecución independiente
            int medianExecution = 1;
            if (problem.getExecutions() > 1) {
                medianExecution = (int) problem.getExecutions()/2;
            }
            problem.iniConvergenceMedia();
            problem.iniConvergenceBestValueAUX();                        
            double bestValueAux = Double.POSITIVE_INFINITY;
            
            
            //Se prepara los contenedores para los nuevos resultados
            problem.newResults();
            problem.newStatistic();
            problem.newConverter();
            int[] sccp = new int[problem.getExecutions()];
            
            //Se ajusta la expersión
            this.adjustFunction(problem);
            // Se calcula la posición de la FO
            int position = problem.getNumberVariableOF();                        
            
            /**
             * Inicia el TS-MBFOA...
             */
            
            //Se hace depuraciones por partes si el debug esta habilitado
            if (debug) System.out.println("JMetaBFOP starts.");
            this.timeAux = System.nanoTime();
            
            //Se incluye el porcentaje de avance del algoritmo
            int percentageIncrease = (int) (100 / problem.getExecutions());
            problem.setAdvance(1);
            
            //Número de ejecuciones
            for (int i = 0; i < problem.getExecutions(); i++) {
                //variables para calcular el performance
                int bp = 0;
                int sperformace = 0;
                
                //Se hace depuraciones por partes si el debug esta habilitado
                if (debug) {
                    System.out.println("Start execution " + (i+1));
                    System.out.println("Generating population...");
                }
                
                // Se inicializa la población de bacterias
                configurator.startPopulation(problem);                
                //se evalua la función objetivo, las restricciones y se asigna a la matriz de la población
                configurator.setBacteriumMatix(this.mechanism.evaluationFyC(configurator, problem, this.function));                
                // Se ordena la población
                configurator.sortPopulation(problem.getNumberAssignedVariable());
                // Se reinician los contadores
                this.tsmbfoa.setGeneration(0);
                this.tsmbfoa.setCount(0);
                
                //se calcula la posicion de la FO
                int positionOF = configurator.getBacteriumMatix()[0].length-2;
                
                //Se hace depuraciones por partes si el debug esta habilitado
                if (debug)  System.out.println("Initiates Foraging for generations.");
                // Inicia forrajeo
                while(this.tsmbfoa.getInstruction(configurator)){
                    //incremento de la generación
                    this.tsmbfoa.increaseGeneration(1);
                    //Se hace depuraciones por partes si el debug esta habilitado
                    if (debug){
                        System.out.println("\n> Start of generation "+ this.tsmbfoa.getGeneration()
                                + " of execution " + (i+1)
                                + "\n> Starts the chemotaxic process...");
                    }
                    //Proceso quimiotaxico                
                    this.tsmbfoa.chemotaxis(configurator, problem, this.function);
                    //actualizamos contador
                    this.tsmbfoa.increaseCount((configurator.getSb() * configurator.getNc()));
                    if (debug)  System.out.println("> Starts the process of grouping and reproduction.");
                    //Reproducción
                    this.tsmbfoa.reproduction(configurator);    
                    if (debug)  System.out.println("> Elimination-dispersion occurs.");
                    //eliminacion-dispersion
                    this.tsmbfoa.eliminationDispersal(configurator, problem, this.function);
                    // incrementa contador
                    this.tsmbfoa.increaseCount(1);
                    if (debug)  System.out.println("> Step size is updated.");
                    //actualizando tamaño de paso estático
                    this.tsmbfoa.updateStepSize(configurator, problem);
                    
                    if ((i+1) == medianExecution) {
                        //System.out.println("ESTE ES: " + this.tsmbfoa.getCount());
                        problem.setConvergenceMedia(
                                this.tsmbfoa.getCount()
                                , this.st.bestRow(
                                        configurator.getBacteriumMatix()
                                        , positionOF
                                )[positionOF]
                        );                                                
                    }
                    int x = configurator.getBacteriumMatix()[0].length-1;
                                        
                    if (configurator.getBacteriumMatix()[0][x] == 0) {
                        
                        sperformace = st.successPerformance(configurator.getBacteriumMatix(), problem.getBestKnownValue());
                        
                        if ((bp == 0) && (sperformace==1)) {
                            sccp[i] = this.tsmbfoa.getCount();
                            bp = 1;
                        }
                        
                        
                    }
                    
                        problem.setConvergenceBestValueAUX(
                                this.tsmbfoa.getCount()
                                , this.st.bestRow(
                                            configurator.getBacteriumMatix()
                                            , positionOF
                                    )[positionOF]
                        );                                                                                                      
                    
                }//Termina forrajeo                                                
                
                if (debug) {
                    System.out.println("\nTerminates bacterial foraging with " 
                            + this.tsmbfoa.getGeneration() + " generations.");
                }                                                
                if(problem.getExecutions() > 1){                     
                    switch(problem.getObj()){
                        case Problem.MINIMIZATION:
                            this.bestValue = this.st.bestRow(configurator.getBacteriumMatix()
                                    , configurator.getBacteriumMatix()[0].length-2);                            
                            break;
                        case Problem.MAXIMIZATION:
                            this.bestValue = this.st.worstRow(configurator.getBacteriumMatix()
                                    , configurator.getBacteriumMatix()[0].length-2);
                            break;
                        default:
                                System.err.println("Results cannot be saved, if min or max.");
                    }                    
                    problem.addBestResults(this.bestValue);                                                            
                    
                    if (debugResults) {
                        System.out.println("Saving the best value found: " + this.bestValue[position]);
                    }
                    
                    //Se guarda la convergencia de la mejor solución en caso de la iteración ser mayor a 1                                           
                        if (this.bestValue[positionOF] < bestValueAux && this.bestValue[positionOF+1] == 0) { 
                            //System.out.println("ENTRE");
                            problem.setConvergenceBestValue();
                            problem.iniConvergenceBestValueAUX();                                                
                            bestValueAux = this.bestValue[positionOF];                        
                        }
              
                    
                }
                //se calcula el porcentaje de avance
                if (problem.getAdvance()+percentageIncrease-1 >= 100) {
                    problem.setAdvance(99);
                }else problem.setAdvance(problem.getAdvance()+percentageIncrease-1);                                                                
                
                if (debug){
                    System.out.println("Information generated by execution " 
                            + (i+1) 
                            + ":\n"
                            + configurator.getDescription());
                    System.out.println("<<< Completion of run " + (i+1) + " with " 
                            + this.tsmbfoa.getGeneration() + " generations >>>\n");
                }                
            } // Termina las ejecuciones 
            
            //Se añade el tiempo en segundos
            problem.setTimeSeconds(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-timeAux));
            
            if (debug) System.out.println("The established executions end.");
            
            if(problem.getExecutions() == 1){                
                for (double[] bacteriumMatix : configurator.getBacteriumMatix()) {
                    problem.addBestResults(bacteriumMatix);
                }                
                
                if(debug){
                    System.out.println("As there is 1 independent execution, the results are"
                        + "\nevaluated with the only population generated.");
                }
            }
            
            if (debugResults) {
                System.out.println("Best results found for each generation in each run "
                        + "of the TS-MBFOA:");
                for (double[] bestResult : problem.getBestResults()) {
                    System.out.println(Arrays.toString(bestResult));
                }
           }
                                   
            
            //Mejor valor dependiendo el objetivos de max o min
            problem.addStatistic( 
                    (problem.getObj().equals(Problem.MINIMIZATION))
                        ? this.st.best(problem.getBestResults(), position)
                        :this.st.worst(problem.getBestResults(), position)
            );
            
            //Media
            problem.addStatistic(this.st.mean(problem.getBestResults(), position));
            
            //Mediana
            problem.addStatistic(this.st.median(problem.getBestResults(), position));
            
            //Desviación estandar
            problem.addStatistic(this.st.standardDeviation(problem.getBestResults(), position));
            
            //Peor valor
            problem.addStatistic( 
                    (problem.getObj().equals(Problem.MINIMIZATION))
                        ? this.st.worst(problem.getBestResults(), position)
                        :this.st.best(problem.getBestResults(), position)
            );
            
            //Tasa de factibilidad
            problem.addStatistic(this.st.feasibleRate(problem.getBestResults(), position+1));
            //Tasa de éxito
            problem.addStatistic(this.st.successRate(problem.getBestResults(), position, problem.getBestKnownValue()));
            
            int pd = 0;
            double pscp = 0;
            
            for (int i = 0; i < sccp.length; i++) {
                if (sccp[i] > 0) {
                    pd++;
                    pscp = pscp + sccp[i];
                }
                
            }
            
            if (pscp > 0 && pd > 0) {
                pscp = pscp/pd;
            }else pscp = 0;
            
            if (pscp>0) {
                problem.addStatistic(Math.floor(pscp*problem.getExecutions())/pd);
            } else {
                problem.addStatistic(0);
            }
            
            if (debugResults) {
                System.out.println("\nCalculating statistics...");
                for (int i = 0; i < problem.getStatistic().length; i++) {
                    System.out.println(problem.getStatisticsName()[i] + ": "+ problem.getStatistic()[i]);               
                }
            }
            if (debugResults) {
                System.out.println("Time: " + problem.getTimeSeconds());
            }
            
            if (problem.getAdvance() < 100) {
                problem.setAdvance(100);
            }
            
            System.out.println("\nJMetaBFOP completed.");
            
        }else {                        
            System.err.println("The number of executions must be between [1 and 30].");
        }              
    }
    
//    /**
//     * 
//     * @param obj 
//     * @param newLength 
//     */
//    private void cleanMatrix(double[][] obj, int newLength){
//        aux = new double[obj.length][newLength + 2];        
//        for (int i = 0; i < obj.length; i++) {            
//            for (int j = 0; j < obj[i].length; j++) {                
//                aux[i][j] = obj[i][j];                        
//                if (i == newLength) {
//                    i = obj[i].length;
//                }
//                
//            }
//            
//        }
//    }

}
