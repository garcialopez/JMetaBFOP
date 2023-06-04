package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G07_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: x1^2 + x2^2 + x1 * x2 - 14 * x1 - 16 * x2 + (x3 - 10)^2 + 4 * (x4 -
 * 5)^2 + (x5 - 3)^2 + 2 * (x6 - 1)^2 + 5 * x7^2 + 7 * (x8 - 11)^2 + 2 * (x9 -
 * 10)^2 + (x10 - 7)^2 + 45 <br>
 * Subject to: -105 + 4 * x1 + 5 * x2 - 3 * x7 + 9 * x8 <= 0 <br> 10 * x1 - 8 *
 * x2 - 17 * x7 + 2 * x8 <= 0 <br> -8 * x1 + 2 * x2 + 5 * x9 - 2 * 10 - 12 <= 0
 * <br> 3 * (x1 - 2)^2 + 4 * (x2 - 3)^2 + 2 * x3^2 -7 * x4 - 120 <= 0 <br> 5 *
 * x1^2 + 8 * x2 + (x3 - 6)^2 - 2 * x4 - 40 = 0 <br>
 * x1^2 + 2 * (x2 - 2)^2 - 2 * x1 * x2 + 14 * x5 - 6 * x6 <= 0 <br> 0.5 * (x1 -
 * 8)^2 + 2.0 * (x2 - 4)^2 + (3.0 * x5^2) - x6 - 30.0 <= 0 <br> -3 * x1 + 6 * x2
 * + 12 * (x9 - 8)^2 - 7 * x10 <= 0 <br> where: ran{1:10,-10:10}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G07_CEC2006 extends CNOP {

    /**
     * Constructor for the G07_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G07_CEC2006() {
        this.setNameProblem("G07 CEC2006");
        this.setBestKnownValue(24.3062090681);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("x1^2 + x2^2 + x1 * x2 - 14 * x1 - 16 * x2 + (x3 - 10)^2 + 4 * (x4 - 5)^2 + (x5 - 3)^2 + 2 * (x6 - 1)^2 + 5 * x7^2 + 7 * (x8 - 11)^2 + 2 * (x9 - 10)^2 + (x10 - 7)^2 + 45");
        this.setOrderVariables("var{x,1-10}");
        this.setVariableRange("ran[1-10:(-10,10)]");
        Constraints constraints = new Constraints();

        constraints.add("-105 + 4 * x1 + 5 * x2 - 3 * x7 + 9 * x8 <= 0");
        constraints.add("10 * x1 - 8 * x2 - 17 * x7 + 2 * x8 <= 0");
        constraints.add("-8 * x1 + 2 * x2 + 5 * x9 - 2 * x[10] - 12 <= 0");
        constraints.add("3 * (x1 - 2)^2 + 4 * (x2 - 3)^2 + 2 * x3^2 -7 * x4 - 120 <= 0");
        constraints.add("5 * x1^2 + 8 * x2 + (x3 - 6)^2 - 2 * x4 - 40 <= 0");
        constraints.add("x1^2 + 2 * (x2 - 2)^2 - 2 * x1 * x2 + 14 * x5 - 6 * x6 <= 0");
        constraints.add("0.5 * (x1 - 8)^2 + 2.0 * (x2 - 4)^2 + (3.0 * x5^2) - x6 - 30.0 <= 0");
        constraints.add("-3 * x1 + 6 * x2 + 12 * (x9 - 8)^2 - 7 * x10 <= 0");

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
            x[indexFO] = Math.pow(x[0], 2) + Math.pow(x[1], 2) + x[0] * x[1] - 14. * x[0] - 16. * x[1] + Math.pow((x[2] - 10), 2)
                    + 4. * Math.pow((x[3] - 5), 2) + Math.pow((x[4] - 3), 2) + 2. * Math.pow((x[5] - 1), 2) + 5. * Math.pow(x[6], 2)
                    + 7. * Math.pow((x[7] - 11), 2) + 2. * Math.pow((x[8] - 10), 2) + Math.pow((x[9] - 7), 2) + 45.;

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[8];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -105. + 4. * x[0] + 5. * x[1] - 3. * x[6] + 9. * x[7];
            constraintsIneq[1] = 10. * x[0] - 8. * x[1] - 17. * x[6] + 2. * x[7];
            constraintsIneq[2] = -8. * x[0] + 2. * x[1] + 5. * x[8] - 2. * x[9] - 12.;
            constraintsIneq[3] = 3.0 * (x[0] - 2.0) * (x[0] - 2.0) + 4.0 * (x[1] - 3.0) * (x[1] - 3.0) + 2.0 * x[2] * x[2] - 7.0 * x[3] - 120.0;
            constraintsIneq[4] = 5.0 * x[0] * x[0] + 8.0 * x[1] + (x[2] - 6.0) * (x[2] - 6.0) - 2.0 * x[3] - 40.0;
            constraintsIneq[5] = x[0] * x[0] + 2.0 * (x[1] - 2.0) * (x[1] - 2.0) - 2.0 * x[0] * x[1] + 14.0 * x[4] - 6.0 * x[5];
            constraintsIneq[6] = 0.5 * (x[0] - 8.0) * (x[0] - 8.0) + 2.0 * (x[1] - 4.0) * (x[1] - 4.0) + (3.0 * (x[4] * x[4])) - x[5] - 30.0;
            constraintsIneq[7] = -3.0 * x[0] + 6.0 * x[1] + 12.0 * (x[8] - 8.0) * (x[8] - 8.0) - 7.0 * x[9];

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0, 0, 0, 0, 0, 0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<=", "<=", "<=", "<=", "<=", "<=", "<="};

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
