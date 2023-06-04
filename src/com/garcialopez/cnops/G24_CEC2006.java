package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G24_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -x1 - x2 <br>
 * Subject to: -2 * x1^4 + 8 * x1^3 - 8 * x1^2 + x2 - 2 <= 0 <br> -4 * x1^4 + 32
 * * x1^3 - 88 * x1^2 + 96 * x1 + x2 - 36 <= 0 <br> where: (0,3),(0,4)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G24_CEC2006 extends CNOP {

    /**
     * Constructor for the G24_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G24_CEC2006() {
        this.setNameProblem("G24 CEC2006");
        this.setBestKnownValue(-5.5080132716);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-x1 - x2");
        this.setOrderVariables("x1;x2");
        this.setVariableRange("(0,3);(0,4)");
        Constraints constraints = new Constraints();

        constraints.add("-2 * x1^4 + 8 * x1^3 - 8 * x1^2 + x2 - 2 <= 0");
        constraints.add("-4 * x1^4 + 32 * x1^3 - 88 * x1^2 + 96 * x1 + x2 - 36 <= 0");

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
            x[indexFO] = -x[0] - x[1];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -2. * Math.pow(x[0], 4) + 8. * Math.pow(x[0], 3) - 8. * Math.pow(x[0], 2) + x[1] - 2.;
            constraintsIneq[1] = -4. * Math.pow(x[0], 4) + 32. * Math.pow(x[0], 3) - 88. * Math.pow(x[0], 2) + 96. * x[0] + x[1] - 36.;

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
