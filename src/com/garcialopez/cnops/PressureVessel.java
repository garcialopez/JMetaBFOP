package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * PressureVessel class that creates a test CNOP. This CNOP has as objective
 * function:<br>https://ijssst.info/Vol-17/No-43/paper5.pdf<br>
 *
 * Minimize: (0.6224* x1 * x3 * x4) + (1.7781*x2*x3^2) + (3.1661*x1^2*x4) +
 * (19.84*x1^2*x3) <br>
 * Subject to: (-1 * x1) + (0.0193*x3) <= 0 <br> (-1 * x2) + (0.00954*x3) <= 0
 * <br> (-3.1416 * x3^2 * x4) - ((4/3) * 3.1416 * x3^3) + 1296000 <= 0 <br> x4 -
 * 240 <= 0 <br> where: (1, 99),(1, 99),(10, 200),(10,200)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class PressureVessel extends CNOP {

    /**
     * Constructor for the PressureVessel class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public PressureVessel() { 
        this.setNameProblem("PressureVessel");
        this.setBestKnownValue(5896.94890);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("0.6224 * x1 * x3 * x4 + 1.7781 * x2 * x3^2  + 3.1661 * x1^2 * x4  + 19.84 * x1^2 * x3");
        this.setOrderVariables("x1;x2;x3;x4");
        this.setVariableRange("(0.0625,99*0.0625);(0.0625,99*0.0625);(10, 200);(10,200)");
        Constraints constraints = new Constraints();
        constraints.add("(-1 * x1) + (0.0193*x3) <= 0");
        constraints.add("(-1 * x2) + (0.00954*x3) <= 0");
        constraints.add("(-3.1416 * x3^2 * x4) - ((4/3) * 3.1416 * x3^3) + 1296000 <= 0");
        constraints.add("x4 - 240 <= 0");
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
            x[indexFO] = 0.6224 * x[0] * x[2] * x[3]
                    + 1.7781 * x[1] * Math.pow(x[2], 2)
                    + 3.1661 * Math.pow(x[0], 2) * x[3]
                    + 19.84 * Math.pow(x[0], 2) * x[2];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[4];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -x[0] + 0.0193 * x[2];
            constraintsIneq[1] = -x[1] + 0.00954 * x[2];
            constraintsIneq[2] = -Math.PI * Math.pow(x[2], 2) * x[3] - (4.0 / 3.0) * Math.PI * Math.pow(x[2], 3) + 1296000;
            constraintsIneq[3] = x[3] - 240 - 0;

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0, 0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<=", "<=", "<="};

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
