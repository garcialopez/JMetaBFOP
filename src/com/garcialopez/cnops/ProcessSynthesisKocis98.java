package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * Process Synthesis Kocis 98 class that creates a test CNOP. This CNOP has as
 * objective function:<br><br>
 *
 * Minimize: y + 2 * x <br>
 * Subject to: x^2 - y + 1.25 <= 0 <br> x + y <= 1.6 <br> <br>
 * where: (0,1.6);{0, 1}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class ProcessSynthesisKocis98 extends CNOP {

    /**
     * Constructor for the ProcessSynthesisKocis98 class. Initializes the
     * specific properties of the CNOP for the Tension/compression spring
     * problem. Sets the problem name, best known value, optimization type,
     * objective function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public ProcessSynthesisKocis98() {
        this.setNameProblem("Process synthesis MINLP de Kocis and Grossmann (1998)");
        this.setBestKnownValue(2.0);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("y + 2 * x");
        this.setOrderVariables("x;y");
        this.setVariableRange("(0,1.6);{0, 1}");
        Constraints constraints = new Constraints();

        constraints.add("x^2 - y + 1.25 <= 0");
        constraints.add("x + y <= 1.6");

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

            x[indexFO] = x[1] + 2.0 * x[0];

            double[] constraintsIneq = new double[2];

            constraintsIneq[0] = -Math.pow(x[0], 2) - x[1] + 1.25;
            constraintsIneq[1] = x[0] + x[1];

            double[] rightSideIneq = {0, 1.6};
            String[] comparatorIneq = {"<=", "<="};

            double[] constraintsEqua = {};
            double[] rightSideEqua = {};

            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }

        return values;
    }

}
