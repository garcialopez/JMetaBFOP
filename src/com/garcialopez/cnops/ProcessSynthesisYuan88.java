package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * ProcessSynthesisMINLP class that creates a test CNOP. This CNOP has as
 * objective function:<br><br>
 *
 * Minimize: (y1 - 1)^2 + (y2 - 2)^2 + (y3 - 1)^2 - log10(y4 + 1) + (x1 - 1)^2 +
 * (x2 - 2)^2 + (x3 - 3)^2 <br>
 * Subject to: y1 + y2 + y3 + x1 + x2 + x3 <= 5 <br> y3^2 + x1^2 + x2^2 + x3^2
 * <= 5.5 <br> y1 + x1 <= 1.2 <br> y2 + x2 <= 1.8 <br> y3 + x3 <= 2.5 <br> y4 +
 * x1 <= 1.2 <br> y2^2 + x2^2 <= 1.64 <br> y3^2 + x3^2 <= 4.25 <br> y2^2 + x3^2
 * <= 4.64 <br> where: (0, 1.2);(0, 1.8);(0, 2.5);ran{4:7,0:1}
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class ProcessSynthesisYuan88 extends CNOP {

    /**
     * Constructor for the ProcessSynthesisYuan88 class. Initializes the
     * specific properties of the CNOP for the Tension/compression spring
     * problem. Sets the problem name, best known value, optimization type,
     * objective function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public ProcessSynthesisYuan88() {
        this.setNameProblem("Process synthesis MINLP de Yuan et al (1988)");
        this.setBestKnownValue(4.579582);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("(y1 - 1)^2 + (y2 - 2)^2 + (y3 - 1)^2 - log10(y4 + 1) + (x1 - 1)^2 + (x2 - 2)^2 + (x3 - 3)^2");
        this.setOrderVariables("x1;x2;x3;y1;y2;y3;y4");
        this.setVariableRange("(0, 1.2);(0, 1.8);(0, 2.5);{0,1};{0,1};{0,1};{0,1}");
        Constraints constraints = new Constraints();
        
        constraints.add("y1 + y2 + y3 + x1 + x2 + x3 <= 5");
        constraints.add("y3^2 + x1^2 + x2^2 + x3^2 <= 5.5");
        constraints.add("y1 + x1 <= 1.2");
        constraints.add("y2 + x2 <= 1.8");
        constraints.add("y3 + x3 <= 2.5");
        constraints.add("y4 + x1 <= 1.2");
        constraints.add("y2^2 + x2^2 <= 1.64");
        constraints.add("y3^2 + x3^2 <= 4.25");
        constraints.add("y2^2 + x3^2 <= 4.64");
        
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

            x[indexFO] = Math.pow(x[3] - 1, 2) //
                    + Math.pow(x[4] - 2.0, 2) //
                    + Math.pow(x[5] - 1.0, 2) //
                    - Math.log(x[6] + 1.0) //
                    + Math.pow(x[0] - 1.0, 2)
                    + Math.pow(x[1] - 2.0, 2)
                    + Math.pow(x[2] - 3.0, 2);

            double[] constraintsIneq = new double[9];

            constraintsIneq[0] = x[3] + x[4] + x[5] + x[0] + x[1] + x[2];
            constraintsIneq[1] = Math.pow(x[5], 2) + Math.pow(x[0], 2) + Math.pow(x[1], 2) + Math.pow(x[2], 2);
            constraintsIneq[2] = x[3] + x[0];
            constraintsIneq[3] = x[4] + x[1];
            constraintsIneq[4] = x[5] + x[2];
            constraintsIneq[5] = x[6] + x[0];
            constraintsIneq[6] = Math.pow(x[4], 2) + Math.pow(x[1], 2);
            constraintsIneq[7] = Math.pow(x[5], 2) + Math.pow(x[2], 2);
            constraintsIneq[8] = Math.pow(x[4], 2) + Math.pow(x[2], 2);

            double[] rightSideIneq = {5, 5.5, 1.2, 1.8, 2.5, 1.2, 1.64, 4.25, 4.64};
            String[] comparatorIneq = {"<=", "<=", "<=", "<=", "<=", "<=", "<=", "<=", "<="};

            double[] constraintsEqua = {};
            double[] rightSideEqua = {};

            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }

        return values;
    }

}
