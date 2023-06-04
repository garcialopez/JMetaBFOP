package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G18_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -0.5 * (x1 * x4 - x2 * x3 + x3 * x9 - x5 * x9 + x5 * x8 - x6 * x7)
 * <br>
 * Subject to: x3^2 + x4^2 - 1 <= 0 <br> x9^2 - 1 <= 0 <br> x5^2 + x6^2 - 1 <= 0
 * <br> x1^2 + (x2 - x9)^2 - 1 <= 0 <br> (x1 - x5)^2 + (x2 - x6)^2 - 1 <= 0 <br>
 * (x1 - x7)^2 + (x2 - x8)^2 - 1 <= 0 <br> (x3 - x5)^2 + (x4 - x6)^2 - 1 <= 0
 * <br> (x3 - x7)^2 + (x4 - x8)^2 - 1 <= 0 <br> x7^2 + (x8 - x9)^2 - 1 <= 0 <br>
 * x2 * x2 - x1 * x4 <= 0 <br> -x3 * x9 <= 0 <br> x5 * x9 <= 0 <br> x6 * x7 - x5
 * * x8 <= 0 <br> where: ran{1:8,-10:10};(0,20)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G18_CEC2006 extends CNOP {

    /**
     * Constructor for the G18_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G18_CEC2006() {
        this.setNameProblem("G18 CEC2006");
        this.setBestKnownValue(-0.8660254038);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-0.5 * (x1 * x4 - x2 * x3 + x3 * x9 - x5 * x9 + x5 * x8 - x6 * x7)");
        this.setOrderVariables("var{x,1-9}");
        this.setVariableRange("ran[1-8:(-10.0,10.0)];(0,20)");
        Constraints constraints = new Constraints();
        
        constraints.add("x3^2 + x4^2 - 1 <= 0");
        constraints.add("x9^2 - 1 <= 0");
        constraints.add("x5^2 + x6^2 - 1 <= 0");
        constraints.add("x1^2 + (x2 - x9)^2 - 1 <= 0");
        constraints.add("(x1 - x5)^2 + (x2 - x6)^2 - 1 <= 0");
        constraints.add("(x1 - x7)^2 + (x2 - x8)^2 - 1 <= 0");
        constraints.add("(x3 - x5)^2 + (x4 - x6)^2 - 1 <= 0");
        constraints.add("(x3 - x7)^2 + (x4 - x8)^2 - 1 <= 0");
        constraints.add("x7^2 + (x8 - x9)^2 - 1 <= 0");
        constraints.add("x2 * x2 - x1 * x4 <= 0");
        constraints.add("-x3 * x9 <= 0");
        constraints.add("x5 * x9 <= 0");
        constraints.add("x6 * x7 - x5 * x8 <= 0");
        
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
            x[indexFO] = -0.5 * (x[0] * x[3] - (x[1] * x[2]) + (x[2] * x[8]) - (x[4] * x[8]) + (x[4] * x[7]) - (x[5] * x[6]));

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[13];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = - 1.0 + x[2] *x[2] + x[3] * x[3];
            constraintsIneq[1] = - 1.0 + x[8] * x[8];
            constraintsIneq[2] = - 1.0 + x[4] * x[4] + x[5] * x[5];
            constraintsIneq[3] = - 1.0 + x[0] * x[0] + (x[1] - x[8]) * (x[1] - x[8]);
            constraintsIneq[4] = - 1.0 +  (x[0] - x[4]) * (x[0] - x[4]) + (x[1] - x[5]) * (x[1] - x[5]);
            constraintsIneq[5] = - 1.0 + (x[0] - x[6]) * (x[0] - x[6]) + (x[1] - x[7]) * (x[1] - x[7]);
            constraintsIneq[6] = - 1.0 + (x[2] - x[4]) * (x[2] - x[4]) + (x[3] - x[5]) * (x[3] - x[5]);
            constraintsIneq[7] = - 1.0 + (x[2] - x[6]) * (x[2] - x[6]) + (x[3] - x[7]) * (x[3] - x[7]);
            constraintsIneq[8] = - 1.0 + x[6] * x[6] + (x[7] - x[8]) * (x[7] - x[8]);
            
            constraintsIneq[9] = x[1] * x[1] - x[0] * x[3];
            constraintsIneq[10] = -x[2] * x[8];
            constraintsIneq[11] = x[4] * x[8];
            constraintsIneq[12] = x[5] * x[6] - x[4] * x[7];

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<="};

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
