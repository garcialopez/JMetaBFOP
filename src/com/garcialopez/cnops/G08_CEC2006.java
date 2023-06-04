package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G08_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -(sin(2 * pi * x1)^3) * sin(2 * pi * x2) / ((x1^3) * (x1 + x2))
 * <br>
 * Subject to: x1^2 - x2 + 1 <= 0 <br> 1 - x1 + (x2 - 4)^2 <= 0 <br> where:
 * (0,10),(0,10)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G08_CEC2006 extends CNOP {

    /**
     * Constructor for the G08_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G08_CEC2006() {
        this.setNameProblem("G08 CEC2006");
        this.setBestKnownValue(-0.0958250415);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-(sin(2 * pi * x1)^3) * sin(2 * pi * x2) / ((x1^3) * (x1 + x2))");
        this.setOrderVariables("x1;x2");
        this.setVariableRange("(0,10);(0,10)");
        Constraints constraints = new Constraints();

        constraints.add("x1^2 - x2 + 1 <= 0");
        constraints.add("1 - x1 + (x2 - 4)^2 <= 0");

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
            x[indexFO] = -Math.pow(Math.sin(2 * Math.PI * x[0]), 3) * Math.sin(2 * Math.PI * x[1]) / (Math.pow(x[0], 3) * (x[0] + x[1]));

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = (x[0] * x[0]) - x[1] + 1;
            constraintsIneq[1] = 1 - x[0] + ((x[1] - 4) * (x[1] - 4));

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<="};

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
