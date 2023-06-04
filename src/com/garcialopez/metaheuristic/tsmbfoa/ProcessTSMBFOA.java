package com.garcialopez.metaheuristic.tsmbfoa;

import com.garcialopez.metaheuristic.NRandom;
import com.garcialopez.metaheuristic.Population;
import com.garcialopez.optimizationmodel.CNOP;
import java.util.Arrays;

/**
 * <b>ProcessTSMBFOA</b> class allows the creation of an object to initiate the
 * bacteria foraging process for CNOP optimization. This class is implemented by
 * Foraging.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class ProcessTSMBFOA implements Foraging {

    private NRandom nRandom;

    public ProcessTSMBFOA() {
    }

    @Override
    public void chemotaxis(CNOP cnop, TSMBFOA bacteria) {
        this.nRandom = new NRandom();
        int sizeIndividuals = bacteria.getIndividuals().length;
        int sizeValues = bacteria.getIndividuals()[0].length;
        int indexFO = sizeValues - 2;
        int indexSVR = sizeValues - 1;
        int numberVariables = cnop.getNumberVariable();

        int middle = (int) (bacteria.getNc() / 2.0);

        double[][] newBacterium = new double[1][numberVariables * 2 + 2];
        double[] angles = new double[numberVariables];
        double[] stem = new double[numberVariables * 2 + 2];       //vastago
        boolean flag;

        for (int b = 0; b < bacteria.getSb(); b++) { //for b
            flag = true;
            for (int c = 0; c < bacteria.getNc(); c++) {   //start chemotaxis - for c                
                if (flag) {
                    angles = this.generateAngles(numberVariables);
                }

                int[] v = this.offspring(sizeIndividuals, b);

                //preguntar si en bact1 es b o v1
                double[] bact1 = Arrays.copyOf(bacteria.getIndividuals()[v[0]], bacteria.getIndividuals()[v[0]].length);
                double[] bact2 = Arrays.copyOf(bacteria.getIndividuals()[v[1]], bacteria.getIndividuals()[v[1]].length);
                double[] bact3 = Arrays.copyOf(bacteria.getIndividuals()[v[2]], bacteria.getIndividuals()[v[2]].length);

                for (int m = 0; m < numberVariables; m++) {
                    if (cnop.isContinuousVariable()[m]) {
                        stem[m] = bact1[m] + (bacteria.getScalingFactor() - 1.0) * (bact2[m] - bact3[m]);
                    } else {
                        stem[m] = (int) (bact1[m] + (bacteria.getScalingFactor() - 1.0) * (bact2[m] - bact3[m]));
                    }
                }

                for (int k = 0; k < numberVariables; k++) { //for k   
                    // if we are in the middle of the chemotactic cycles
                    if (c > middle || c < middle) {
                        // preguntamos si es par o impar
                        if (c % 2 == 0) {
                            newBacterium[0][k] = stem[k];
                            newBacterium[0][k + numberVariables] = bacteria.getIndividuals()[b][k + numberVariables];
                        } else {

                            if (cnop.isContinuousVariable()[k]) {
                                newBacterium[0][k] = bacteria.getIndividuals()[b][k] + bacteria.getIndividuals()[b][k + numberVariables] * angles[k];
                                newBacterium[0][k + numberVariables] = bacteria.getIndividuals()[b][k + numberVariables];
                            } else {
                                newBacterium[0][k] = (int) (bacteria.getIndividuals()[b][k] + bacteria.getIndividuals()[b][k + numberVariables] * angles[k]);
                                newBacterium[0][k + numberVariables] = (int) bacteria.getIndividuals()[b][k + numberVariables];
                            }

                        }
                    } else { // si mitad == c entonces ocurre el AGRUPAMIENTO

                        if (cnop.isContinuousVariable()[k]) {
                            newBacterium[0][k] = bacteria.getIndividuals()[b][k] + bacteria.getScalingFactor() * (bacteria.getIndividuals()[0][k] - bacteria.getIndividuals()[b][k]);
                            newBacterium[0][k + numberVariables] = bacteria.getIndividuals()[0][k + numberVariables];
                        } else {
                            newBacterium[0][k] = (int) (bacteria.getIndividuals()[b][k] + bacteria.getScalingFactor() * (bacteria.getIndividuals()[0][k] - bacteria.getIndividuals()[b][k]));
                            newBacterium[0][k + numberVariables] = (int) bacteria.getIndividuals()[0][k + numberVariables];
                        }

                    }

                    // Se valida que la nueva bacteria este dentro de los rangos de las variables
                    if (cnop.isContinuousVariable()[k]) {

                        if (newBacterium[0][k] < cnop.getVariableRange()[k][0]) {
                            //System.out.println("revaso menor");
                            newBacterium[0][k] = (cnop.getVariableRange()[k][0] * 2.0 - newBacterium[0][k]);
                        }
                        if (newBacterium[0][k] > cnop.getVariableRange()[k][1]) {
                            //System.out.println("revaso mayor");
                            newBacterium[0][k] = (cnop.getVariableRange()[k][1] * 2.0 - newBacterium[0][k]);
                        }
                        if (newBacterium[0][k] < cnop.getVariableRange()[k][0] || newBacterium[0][k] > cnop.getVariableRange()[k][1]) {
                            newBacterium[0][k] = this.nRandom.getRandomRankUnif(cnop.getVariableRange()[k][0], cnop.getVariableRange()[k][1]);
                            //System.out.println("revaso ambos");
                        }

                    } else {

                        double[] varAux = cnop.getVariableRange()[k];
                        boolean isExist = false;

                        for (int i = 0; i < varAux.length; i++) {
                            if (varAux[i] == newBacterium[0][k]) {
                                isExist = true;
                                break;
                            }
                        }

                        if (isExist == false) {

                            if (newBacterium[0][k] < cnop.getVariableRange()[k][0]) {
                                //System.out.println("revaso menor");
                                newBacterium[0][k] = (int) (cnop.getVariableRange()[k][0] * 2.0 - newBacterium[0][k]);

                            }
                            if (newBacterium[0][k] > cnop.getVariableRange()[k][1]) {
                                //System.out.println("revaso mayor");
                                newBacterium[0][k] = (int) (cnop.getVariableRange()[k][1] * 2.0 - newBacterium[0][k]);
                            }
                            if (newBacterium[0][k] < cnop.getVariableRange()[k][0] || newBacterium[0][k] > cnop.getVariableRange()[k][1]) {

                                int indice;
                                indice = nRandom.getNetxInt(cnop.getVariableRange()[k].length);
                                newBacterium[0][k] = cnop.getVariableRange()[k][indice];
                                //System.out.println("revaso ambos");
                            }
                        }

                    }

                }// for k

                newBacterium = cnop.evaluateObjectiveFunction(newBacterium);

                // Despues de la evaluación en la función objetivo de la nueva bacteria, 
                // se compara con la bacteria en proceso usando reglas de factibilidad
                if (newBacterium[0][indexSVR] == 0 && bacteria.getIndividuals()[b][indexSVR] == 0) {
                    if (newBacterium[0][indexFO] < bacteria.getIndividuals()[b][indexFO]) {
                        flag = false;
                        bacteria.getIndividuals()[b] = Arrays.copyOf(newBacterium[0], newBacterium[0].length);
                    } else {
                        flag = true;
                    }
                }
                if (newBacterium[0][indexSVR] > 0 && bacteria.getIndividuals()[b][indexSVR] > 0) {
                    if (newBacterium[0][indexSVR] < bacteria.getIndividuals()[b][indexSVR]) {
                        flag = false;
                        bacteria.getIndividuals()[b] = Arrays.copyOf(newBacterium[0], newBacterium[0].length);
                    } else {
                        flag = true;
                    }
                }
                if (newBacterium[0][indexSVR] == 0 && bacteria.getIndividuals()[b][indexSVR] > 0) {
                    flag = false;
                    bacteria.getIndividuals()[b] = Arrays.copyOf(newBacterium[0], newBacterium[0].length);
                } else {
                    flag = true;
                }

            } //for c    
            bacteria.setIndividuals(Population.sortPopulation(bacteria.getIndividuals()));
        }//for b

    }

    @Override
    public void grouping() {

    }

    @Override
    public void reproduction(TSMBFOA bacteria) {
        if (bacteria.getGmax() % bacteria.getRepcycle() == 0) {
            int row = bacteria.getIndividuals().length;
            int column = bacteria.getIndividuals()[0].length;

            for (int y = (row - bacteria.getBacteriaReproduce()); y < row; y++) {
                bacteria.getIndividuals()[y] = Arrays.copyOf(bacteria.getIndividuals()[((row - 1) - y)], column);
            }
            bacteria.setIndividuals(Population.sortPopulation(bacteria.getIndividuals()));
        }
    }

    @Override
    public void eliminationDispersal(CNOP cnop, TSMBFOA bacteria) {
        this.nRandom = new NRandom();
        int numberVar = cnop.getNumberVariable();
        int sizeInd = bacteria.getIndividuals().length;

        double[] angles = this.generateAngles(numberVar);
        double[][] bacter = new double[1][numberVar * 2 + 2];

        for (int j = 0; j < numberVar; j++) {

            if (cnop.isContinuousVariable()[j]) {
                bacter[0][j] = this.nRandom.getRandomRankUnif(cnop.getVariableRange()[j][0], cnop.getVariableRange()[j][1]);
                bacter[0][numberVar + j] = angles[j];
            } else {
                int indice = this.nRandom.getNetxInt(cnop.getVariableRange()[j].length);
                bacter[0][j] = cnop.getVariableRange()[j][indice];
                bacter[0][numberVar + j] = (int) angles[j];
            }

        }

        bacter = cnop.evaluateObjectiveFunction(bacter);
        bacteria.getIndividuals()[sizeInd - 1] = Arrays.copyOf(bacter[0], bacter[0].length);
        bacteria.setIndividuals(Population.sortPopulation(bacteria.getIndividuals()));
    }

    @Override
    public void updateStepSize(CNOP cnop, TSMBFOA bacteria) {
        this.nRandom = new NRandom();
        int sizeVar = cnop.getNumberVariable();
        for (int i = 0; i < bacteria.getSb(); i++) {
            for (int j = 0; j < sizeVar; j++) {

                if (cnop.isContinuousVariable()[j]) {
                    bacteria.getIndividuals()[i][sizeVar + j] = this.nRandom.getRandomRankUnif(cnop.getVariableRange()[j][0], cnop.getVariableRange()[j][1] * bacteria.getStepSize());
                } else {
                    int indice = this.nRandom.getNetxInt(cnop.getVariableRange()[j].length);
                    bacteria.getIndividuals()[i][sizeVar + j] = cnop.getVariableRange()[j][indice];
                }

            }
        }
    }

    /**
     * Generates a vector of random angles for the rotation of bacteria in
     * chemotaxis.
     * <br>
     * The generateAngles method creates a vector of random angles, which are
     * used to determine the rotation of bacteria during the chemotaxis process.
     * <br>
     *
     * @param numberVariables the size of the objective function variables
     * @return a vector of angles with the specified number of variables
     */
    private double[] generateAngles(int numberVariables) {
        this.nRandom = new NRandom();
        //An accumulator variable is created of angles i ^ 2
        double accum = 0.0;
        /* The vector is created to to store the random modules with sizes 
         * with sizes of the number of variables of the objective function.
         */
        double[] angles = new double[numberVariables];

        for (int i = 0; i < angles.length; i++) { // Inicia for i
            // Se genera un número aleatorio entre -1 y 1 y se asigna a 
            // angles en su posición i
            angles[i] = nRandom.getRandomRankUnif(-1.0, 1.0);
            // Se hace la suma de acumm con angles en su posición i 
            // elevado al cuadrado
            accum += (angles[i] * angles[i]);
        } // Termina for i        
        // Se calcula la raiz cuadrada de accum
        double root = Math.sqrt(accum);
        // Se itera el vector de angles y en su posicón i se evalua la posición 
        // dividiendola entre la raiz cuadrada de accum.
        for (int i = 0; i < angles.length; i++) {
            angles[i] /= root;
        }
        //Termino for y devuelve el vector de angulos
        return angles;
    }

    /**
     * Generates three random positions for offspring selection.
     * <br>
     * The offspring method generates three random positions within the
     * specified limit, ensuring that they are different from the "different"
     * position.
     * <br>
     *
     * @param limit the upper limit of the positions
     * @param different the position to be excluded
     * @return an array of three random positions
     */
    private int[] offspring(int limit, int different) {
        //restamos 1 para el control de posiciones
        limit = limit - 1;

        int[] pos = new int[3];

        pos[0] = this.nRandom.getRandomRankUnif(0, limit);
        pos[1] = this.nRandom.getRandomRankUnif(0, limit);
        pos[2] = this.nRandom.getRandomRankUnif(0, limit);

        //evaluamos para que sean diferentes
        while (pos[0] == different) {
            pos[0] = this.nRandom.getRandomRankUnif(0, limit);
        }

        while ((pos[1] == different) || (pos[1] == pos[0])) {
            pos[1] = this.nRandom.getRandomRankUnif(0, limit);
        }

        while ((pos[2] == different) || (pos[2] == pos[1]) || (pos[2] == pos[0])) {
            pos[2] = this.nRandom.getRandomRankUnif(0, limit);
        }

        return pos;
    }

}
