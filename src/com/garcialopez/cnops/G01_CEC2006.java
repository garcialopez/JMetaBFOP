package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G01_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: 5 * sum{x,1,4,xi} - 5 * sum{x,1,4,xi^2} - sum{x,5,13,xi} <br>
 * Subject to: 2 * x1 + 2 * x2 + x10 + x11 - 10 <= 0 <br> 2 * x1 + 2 * x3 + x10
 * + x12 - 10 <= 0 <br> 2 * x2 + 2 * x3 + x11 + x12 - 10 <= 0 <br> -8 * x1 + x10
 * <= 0 <br> -8 * x2 + x11 <= 0 <br> -8 * x3 + x12 <= 0 <br> -2 * x4 - x5 + x10
 * <= 0 <br> -2 * x6 - x7 + x11 <= 0 <br> -2 * x8 - x9 + x12 <= 0 <br> where:
 * ran{1:9,0:1};ran{10:12,0:100};(0,1)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G01_CEC2006 extends CNOP {

    /**
     * Constructor for the G01_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G01_CEC2006() {
        this.setNameProblem("G01 CEC2006");
        this.setBestKnownValue(-15.0000000000);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("5 * sum{x,1,4,xi} - 5 * sum{x,1,4,xi^2} - sum{x,5,13,xi}");
        this.setOrderVariables("var{x,1-13}");
        this.setVariableRange("ran[1-9:(0,1)];ran[10-12:(0,100)];(0,1)");
        Constraints constraints = new Constraints();

        constraints.add("2 * x1 + 2 * x2 + x10 + x11 - 10 <= 0");
        constraints.add("2 * x1 + 2 * x3 + x10 + x12 - 10 <= 0");
        constraints.add("2 * x2 + 2 * x3 + x11 + x12 - 10 <= 0");
        constraints.add("-8 * x1 + x10 <= 0");
        constraints.add("-8 * x2 + x11 <= 0");
        constraints.add("-8 * x3 + x12 <= 0");
        constraints.add("-2 * x4 - x5 + x10 <= 0");
        constraints.add("-2 * x6 - x7 + x11 <= 0");
        constraints.add("-2 * x8 - x9 + x12 <= 0");

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
            double s1 = 0;
            double s2 = 0;
            double s3 = 0;

            for (int i = 0; i < 4; i++) {
                s1 += x[i];
                s2 += (Math.pow(x[i], 2));
            }

            for (int i = 4; i < 13; i++) {
                s3 += x[i];
            }

            x[indexFO] = 5 * s1 - 5 * s2 - s3;

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[9];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = 2.0 * x[0] + 2.0 * x[1] + x[9] + x[10] - 10.0;
            constraintsIneq[1] = 2.0 * x[0] + 2.0 * x[2] + x[9] + x[11] - 10.0;
            constraintsIneq[2] = 2.0 * x[1] + 2.0 * x[2] + x[10] + x[11] - 10.0;
            constraintsIneq[3] = -8.0 * x[0] + x[9];
            constraintsIneq[4] = -8.0 * x[1] + x[10];
            constraintsIneq[5] = -8.0 * x[2] + x[11];
            constraintsIneq[6] = -2.0 * x[3] - x[4] + x[9];
            constraintsIneq[7] = -2.0 * x[5] - x[6] + x[10];
            constraintsIneq[8] = -2.0 * x[7] - x[8] + x[11];

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0, 0, 0, 0, 0, 0, 0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<="};

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
