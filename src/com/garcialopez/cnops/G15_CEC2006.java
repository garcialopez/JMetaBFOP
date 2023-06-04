package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G15_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: 1000 - x1^2 - 2 * x2^2 -x3^2 - x1 * x2 - x1 * x3 <br>
 * Subject to: x1^2 + x2^2 + x3^2 - 25 = 0 <br>
 * 8 * x1 + 14 * x2 + 7 * x3 - 56 = 0<br>
 * where: ran{1:3,0:10}
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G15_CEC2006 extends CNOP {

    /**
     * Constructor for the G15_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G15_CEC2006() {
        this.setNameProblem("G15 CEC2006");
        this.setBestKnownValue(961.7150222899);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("1000 - x1^2 - 2 * x2^2 -x3^2 - x1 * x2 - x1 * x3");
        this.setOrderVariables("x1;x2;x3");
        this.setVariableRange("ran[1-3:(0,10)]");
        Constraints constraints = new Constraints();

        constraints.add("x1^2 + x2^2 + x3^2 - 25 = 0");
        constraints.add("8 * x1 + 14 * x2 + 7 * x3 - 56 = 0");

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
            x[indexFO] = 1000 - (x[0] * x[0]) - 2 * (x[1] * x[1]) - (x[2] * x[2]) - x[0] * x[1] - x[0] * x[2];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = {};

            //In each position of the vector, evaluate the constraint.            
            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[2];
            constraintsEqua[0] = (x[0] * x[0]) + (x[1] * x[1]) + (x[2] * x[2]) - 25;
            constraintsEqua[1] = 8 * x[0] + 14 * x[1] + 7 * x[2] - 56;

            double[] rightSideEqua = {0, 0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
