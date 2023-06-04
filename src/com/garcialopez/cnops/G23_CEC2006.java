package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G23_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -9 * x5 - 15 * x8 + 6 * x1 + 16 * x2 + 10 * (x6 + x7) <br>
 * Subject to: x9 * x3 + 0.02 * x6 - 0.025 * x5 <= 0 <br> x9 * x4 + 0.02 * x7 -
 * 0.015 * x8 <= 0 <br> x1 + x2 - x3 - x4 = 0 <br>
 * 0.03 * x1 + 0.01 * x2 - x9 * (x3 + x4) = 0 <br>
 * x3 + x6 - x5 = 0 <br>
 * x4 + x7 - x8 = 0 <br>
 * where:
 * (0,300),(0,300),(0,100),(0,200),(0,100),(0,300),(0,100),(0,200),(0.01,0.03)
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G23_CEC2006 extends CNOP {

    /**
     * Constructor for the CONSTRUCTOR class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G23_CEC2006() {
        this.setNameProblem("G23 CEC2006");
        this.setBestKnownValue(-400.0551000000);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-9 * x5 - 15 * x8 + 6 * x1 + 16 * x2 + 10 * (x6 + x7)");
        this.setOrderVariables("var{x,1-9}");
        this.setVariableRange("(0,300);(0,300);(0,100);(0,200);(0,100);(0,300);(0,100);(0,200);(0.01,0.03)");
        Constraints constraints = new Constraints();

        constraints.add("x9 * x3 + 0.02 * x6 - 0.025 * x5 <= 0");
        constraints.add("x9 * x4 + 0.02 * x7 - 0.015 * x8 <= 0");
        constraints.add("x1 + x2 - x3 - x4 = 0");
        constraints.add("0.03 * x1 + 0.01 * x2 - x9 * (x3 + x4) = 0");
        constraints.add("x3 + x6 - x5 = 0");
        constraints.add("x4 + x7 - x8 = 0");

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
            x[indexFO] = - 9.0 * x[4] - 15.0 * x[7] + 6.0 * x[0] + 16.0 * x[1] + 10.0 * (x[5] + x[6]);

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = x[8] * x[2] + 0.02 * x[5] - 0.025 * x[4];
            constraintsIneq[1] = x[8] * x[3] + 0.02 * x[6] - 0.015 * x[7];

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<="};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[4];
            constraintsEqua[0] = x[0] + x[1] - x[2] - x[3];
            constraintsEqua[1] = 0.03 * x[0] + 0.01 * x[1] - x[8] * (x[2] + x[3]);
            constraintsEqua[2] = x[2] + x[5] - x[4];
            constraintsEqua[3] = x[3] + x[6] - x[7];
            
            double[] rightSideEqua = {0,0,0,0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
