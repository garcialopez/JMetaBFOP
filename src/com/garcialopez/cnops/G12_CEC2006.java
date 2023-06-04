package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G12_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -(100 - (x1 - 5)^2 - (x2 - 5)^2 - (x3 - 5)^2)/100 <br>
 * Subject to: val{g1,p,q,r,1:9} <br>
 * where: ran{1:3,0:10}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G12_CEC2006 extends CNOP {

    /**
     * Constructor for the G12_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G12_CEC2006() {
        this.setNameProblem("G12 CEC2006");
        this.setBestKnownValue(-1.0000000000);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-(100 - (x1 - 5)^2 - (x2 - 5)^2 - (x3 - 5)^2)/100.0");
        this.setOrderVariables("x1;x2;x3");
        this.setVariableRange("ran[1-3:(0,10)]");
        Constraints constraints = new Constraints();

        constraints.add("(x1-p)^2 + (x2 - q)^2 + (x3 - r)^2 - 0.0625 <= 0");

        this.setConstraints(constraints);
    }

    /**
     * Evaluates the objective function for the given input values. Overrides
     * the base class method.
     *
     * @param values The input values to evaluate the objective function on.
     * @return The evaluated objective function values for the given input
     * values.
     */
    @Override
    public double[][] evaluateObjectiveFunction(double[][] values) {

        int sizeValues = values[0].length;
        int indexFO = sizeValues - 2;
        int indexSVR = sizeValues - 1;

        for (double[] x : values) {

            // Include the coding of the objective function, where each variable
            // is represented by x from position 0 to the number of variables. 
            x[indexFO] = - (100.0 - Math.pow(x[0] - 5.0, 2) - Math.pow(x[1] - 5.0, 2) - Math.pow(x[2] - 5.0, 2)) / 100.0;

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[1];

            //In each position of the vector, evaluate the constraint.            
            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double g = Math.pow((x[0] - 1.0),2) + Math.pow((x[1] - 1.0),2) + Math.pow((x[2] - 1.0),2)- 0.0625;

            for (int p = 1; p <= 9; p++) {
                for (int q = 1; q <= 9; q++) {
                    for (int r = 1; r <= 9; r++) {
                        double gt = Math.pow((x[0] - p),2) + Math.pow((x[1] - q),2) + Math.pow((x[2] - r),2)- 0.0625;
                        
                        if (gt < g) {
                            g = gt;
                        }                       
                    }
                }
            }
            
            constraintsIneq[0] = g;
            
            double[] rightSideIneq = {0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<="};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = {};
            double[] rightSideEqua = {};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
