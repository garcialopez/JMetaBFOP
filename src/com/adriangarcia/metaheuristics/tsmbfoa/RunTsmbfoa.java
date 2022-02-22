package com.adriangarcia.metaheuristics.tsmbfoa;

import com.adriangarcia.statistics.Statistics;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Adrian
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

    private void adjustFunction(Problem problem) {
        // Se parsea para evaluar la función objetivo
        //Se establece el orden de las variable (x1, x2, x3, ..., xn).
        this.function.setFuncion(problem.getFunction(), problem.getOrderVariables());
        
        if (problem.getArguments()!=null) {
            this.function.setArgs(problem.getArguments());
        }
        
        if (problem.getConstraintsInequality() != null || problem.getConstraintsEquality() != null) {
            this.function.setConstraints(problem.getConstraintsInequality(), problem.getConstraintsEquality());
        }
        
        
        
    }

    /**
     * 
     * @param problem
     * @param bacterium 
     * @param debug 
     */          
    public void run(Problem problem, Configurator bacterium, boolean debug, boolean debugResults) {
        
        if(problem.getExecutions() > 0 && problem.getExecutions() <= 30){
            System.out.println("Iniciando...");
            //Se hace depuraciones por partes si el debug esta habilitado
            if (debug){
                System.out.println("Preparando el problema " + problem.getNameProblem()
                        + "\npara " + (problem.getExecutions()) + " ejecuciones."
                        + "\ncon los siguientes parámetros:\n"                                
                        + "\t\t  Parameter calibration\n"                
                        + "\t\tBacteria:\t\t" + bacterium.getSb() + "\n"
                        + "\t\tStepSize:\t\t" + bacterium.getStepSize() + "\n" 
                        + "\t\tChemotoxic cycles:\t" + bacterium.getNc() + "\n"
                        + "\t\tBacteria to reproduce:  " + bacterium.getBacteriaReproduce() + "\n"
                        + "\t\tScaling factor:\t\t" + bacterium.getScalingFactor() + "\n"
                        + "\t\tEvaluations:\t\t" + bacterium.getEvaluations() + "\n"
                        + "\t\tReproduction frequency: " + bacterium.getRepcycle() + "\n");
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
            if (debug) System.out.println("Inicia TS-MBFOA.");
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
                    System.out.println("Inicia la ejecución " + (i+1));
                    System.out.println("Creando población...");
                }
                
                // Se inicializa la población de bacterias
                bacterium.startPopulation(problem);                
                //se evalua la función objetivo, las restricciones y se asigna a la matriz de la población
                bacterium.setBacteriumMatix(this.mechanism.evaluationFyC(bacterium, problem, this.function));                
                // Se ordena la población
                bacterium.sortPopulation(problem.getNumberAssignedVariable());
                // Se reinician los contadores
                this.tsmbfoa.setGeneration(0);
                this.tsmbfoa.setCount(0);
                
                //se calcula la posicion de la FO
                int positionOF = bacterium.getBacteriumMatix()[0].length-2;
                
                //Se hace depuraciones por partes si el debug esta habilitado
                if (debug)  System.out.println("Inicia Forrajeo por generaciones.");
                // Inicia forrajeo
                while(this.tsmbfoa.getInstruction(bacterium)){
                    //incremento de la generación
                    this.tsmbfoa.increaseGeneration(1);
                    //Se hace depuraciones por partes si el debug esta habilitado
                    if (debug){
                        System.out.println("\n> Inicia la generación "+ this.tsmbfoa.getGeneration()
                                + " de la ejecución " + (i+1)
                                + "\n> Inicia el proceso quimiotaxico.");
                    }
                    //Proceso quimiotaxico                
                    this.tsmbfoa.chemotaxis(bacterium, problem, this.function);
                    //actualizamos contador
                    this.tsmbfoa.increaseCount((bacterium.getSb() * bacterium.getNc()));
                    if (debug)  System.out.println("> Inicia el proceso Agrupamiento y reproducción.");
                    //Reproducción
                    this.tsmbfoa.reproduction(bacterium);    
                    if (debug)  System.out.println("> Ocurre la eliminación-dispersión.");
                    //eliminacion-dispersion
                    this.tsmbfoa.eliminationDispersal(bacterium, problem, this.function);
                    // incrementa contador
                    this.tsmbfoa.increaseCount(1);
                    if (debug)  System.out.println("> Se actualiza el tamaño de paso.");
                    //actualizando tamaño de paso estático
                    this.tsmbfoa.updateStepSize(bacterium, problem);
                    
                    if ((i+1) == medianExecution) {
                        //System.out.println("ESTE ES: " + this.tsmbfoa.getCount());
                        problem.setConvergenceMedia(
                                this.tsmbfoa.getCount()
                                , this.st.bestRow(
                                        bacterium.getBacteriumMatix()
                                        , positionOF
                                )[positionOF]
                        );                                                
                    }
                    int x = bacterium.getBacteriumMatix()[0].length-1;
                                        
                    if (bacterium.getBacteriumMatix()[0][x] == 0) {
                        
                        sperformace = st.successPerformance(bacterium.getBacteriumMatix(), problem.getBestKnownValue());
                        
                        if ((bp == 0) && (sperformace==1)) {
                            sccp[i] = this.tsmbfoa.getCount();
                            bp = 1;
                        }
                        
                        
                    }
                    
                        problem.setConvergenceBestValueAUX(
                                this.tsmbfoa.getCount()
                                , this.st.bestRow(
                                            bacterium.getBacteriumMatix()
                                            , positionOF
                                    )[positionOF]
                        );                                                                                                      
                    
                }//Termina forrajeo                                                
                
                if (debug) {
                    System.out.println("\nTermina el forrajeo de bacteria con " 
                            + this.tsmbfoa.getGeneration() + " generaciones.");
                }                                                
                if(problem.getExecutions() > 1){                     
                    switch(problem.getObj()){
                        case Problem.MINIMIZATION:
                            this.bestValue = this.st.bestRow(bacterium.getBacteriumMatix()
                                    , bacterium.getBacteriumMatix()[0].length-2);                            
                            break;
                        case Problem.MAXIMIZATION:
                            this.bestValue = this.st.worstRow(bacterium.getBacteriumMatix()
                                    , bacterium.getBacteriumMatix()[0].length-2);
                            break;
                        default:
                                System.err.println("No se puede guardar los resultados, si es min o max.");
                    }                    
                    problem.addBestResults(this.bestValue);                                                            
                    
                    if (debugResults) {
                        System.out.println("Guardando el mejor valor encontrado : " + this.bestValue[position]);
                    }
                    
                    //Se guarda la convergencia de la mejor solución en caso de la iteración ser mayor a 1                                           
                        if (this.bestValue[positionOF] < bestValueAux && this.bestValue[positionOF+1] == 0) { 
                            System.out.println("ENTRE");
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
                    System.out.println("Información generado por la ejecución " 
                            + (i+1) 
                            + ":\n"
                            + bacterium.getDescription());
                    System.out.println("<<< Termina la ejecución " + (i+1) + " con " 
                            + this.tsmbfoa.getGeneration() + " generaciones >>>\n");
                }                
            } // Termina las ejecuciones 
            
            //Se añade el tiempo en segundos
            problem.setTimeSeconds(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()-timeAux));
            
            if (debug) System.out.println("Terminan las ejecuciones establecidas.");
            
            if(problem.getExecutions() == 1){                
                for (double[] bacteriumMatix : bacterium.getBacteriumMatix()) {
                    problem.addBestResults(bacteriumMatix);
                }                
                
                if(debug){
                    System.out.println("Al ser 1 ejecución independiente se evaluan los"
                        + "\nresultados con la unica población generada.");
                }
            }
            
            if (debugResults) {
                System.out.println("Mejores resultados encontrado por cada generación "
                        + "en cada ejecución del TS-BFOA:");
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
                System.out.println("\nCalculado estadisticas...");
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
            
            System.out.println("\nTS-MBOFA finalizado.");
            
        }else {                        
            System.err.println("El número de ejecuciones debe ser entre [1 y 30]");
        }              
    }
    
    private void cleanMatrix(double[][] obj, int newLength){
        aux = new double[obj.length][newLength + 2];        
        for (int i = 0; i < obj.length; i++) {            
            for (int j = 0; j < obj[i].length; j++) {                
                aux[i][j] = obj[i][j];                        
                if (i == newLength) {
                    i = obj[i].length;
                }
                
            }
            
        }
    }

}
