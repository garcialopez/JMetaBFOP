package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G06_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: (x1 - 10)^3 + (x2 - 20)^3 <br>
 * Subject to: -(x1 - 5)^2 - (x2 - 5)^2 + 100 <= 0 <br> (x1 - 6)^2 + (x2 - 5)^2
 * - 82.81 <= 0 <br> where: (13.0,100.0),(0,100.0)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G06_CEC2006 extends CNOP {

    /**
     * Constructor for the G06_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G06_CEC2006() {
        this.setNameProblem("G06 CEC2006");
        this.setBestKnownValue(-6961.8138755802);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("(x1 - 10)^3 + (x2 - 20)^3");
        this.setOrderVariables("x1;x2");
        this.setVariableRange("(13.0,100.0);(0,100.0)");
        Constraints constraints = new Constraints();

        constraints.add("-(x1 - 5)^2 - (x2 - 5)^2 + 100 <= 0");
        constraints.add("(x1 - 6)^2 + (x2 - 5)^2 - 82.81 <= 0");

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
            x[indexFO] = Math.pow((x[0] - 10),3) + Math.pow((x[1] - 20),3);

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -Math.pow((x[0] - 5),2) - Math.pow((x[1] - 5),2) + 100;
            constraintsIneq[1] = Math.pow((x[0] - 6),2) + Math.pow((x[1] - 5),2) - 82.81;

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
