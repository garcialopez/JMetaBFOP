package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G05_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: 3 * x1 + 0.000001 * x1^3 + 2 * x2 + (0.000002 / 3) * x2^3 <br>
 * Subject to: -x4 + x3 - 0.55 <= 0 <br> -x3 + x4 - 0.55 <= 0 <br> 1000 *
 * sin(-x3 - 0.25) + 1000 * sin(-x4 - 0.25) + 894.8 - x1 = 0 <br>
 * 1000 * sin(x3 - 0.25) + 1000 * sin(x3 - x4 - 0.25) + 894.8 - x2 = 0 <br>
 * 1000 * sin(x4 - 0.25) + 1000 * sin(x4 - x3 - 0.25) + 1294.8 = 0 <br>
 * where: (0,1200),(0,1200),(-0.55,0.55),(-0.55,0.55)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G05_CEC2006 extends CNOP {

    /**
     * Constructor for the G05_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G05_CEC2006() {
        this.setNameProblem("G05 CEC2006");
        this.setBestKnownValue(5126.4967140071);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("3 * x1 +  0.000001 * x1^3 + 2 * x2 + (0.000002 / 3) * x2^3");
        this.setOrderVariables("x1;x2;x3;x4");
        this.setVariableRange("(0,1200);(0,1200);(-0.55,0.55);(-0.55,0.55)");
        Constraints constraints = new Constraints();

        constraints.add("-x4 + x3 - 0.55 <= 0");
        constraints.add("-x3 + x4 - 0.55 <= 0");
        constraints.add("1000 * sin(-x3 - 0.25) + 1000 * sin(-x4 - 0.25) + 894.8 - x1 = 0");
        constraints.add("1000 * sin(x3 - 0.25) + 1000 * sin(x3 - x4 - 0.25) + 894.8 - x2 = 0");
        constraints.add("1000 * sin(x4 - 0.25) + 1000 * sin(x4 - x3 - 0.25) + 1294.8 = 0");

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
            x[indexFO] = 3.0 * x[0] + 0.000001 * Math.pow(x[0], 3) + 2.0 * x[1] + (0.000002 / 3.0) * Math.pow(x[1], 3);

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -x[3] + x[2] - 0.55;
            constraintsIneq[1] = -x[2] + x[3] - 0.55;

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<="};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[3];
            constraintsEqua[0] = 1000.0 * Math.sin(-x[2] - 0.25) + 1000.0 * Math.sin(-x[3] - 0.25) + 894.8 - x[0];
            constraintsEqua[1] = 1000.0 * Math.sin(x[2] - 0.25) + 1000.0 * Math.sin(x[2] - x[3] - 0.25) + 894.8 - x[1];
            constraintsEqua[2] = 1000.0 * Math.sin(x[3] - 0.25) + 1000.0 * Math.sin(x[3] - x[2] - 0.25) + 1294.8;

            double[] rightSideEqua = {0, 0, 0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
