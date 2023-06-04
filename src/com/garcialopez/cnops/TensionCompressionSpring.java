package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * TensionCompressionSpring class that creates a test CNOP. This CNOP has as
 * objective function:<br><br>
 *
 * Minimize: (N+2)*D*d^2 <br>
 * Subject to: 1-(D^3*N)/(71785*d^4) <= 0 <br>
 * ((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1/(5108*(d^2)))-1 <= 0 <br>
 * 1-(140.45*d/((D^2)*N)) <= 0 <br> ((D+d)/1.5)-1 <= 0 <br> where: (0.05,
 * 2),(0.25, 1.3),(2, 15)
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class TensionCompressionSpring extends CNOP {

    /**
     * Constructor for the TensionCompressionSpring class. Initializes the
     * specific properties of the CNOP for the Tension/compression spring
     * problem. Sets the problem name, best known value, optimization type,
     * objective function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public TensionCompressionSpring() {
        this.setNameProblem("Tension/compression spring");
        this.setBestKnownValue(0.012681);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("(N+2)*D*d^2");
        this.setOrderVariables("d;D;N");
        this.setVariableRange("(0.05, 2);(0.25, 1.3);(2, 15)");
        Constraints constraints = new Constraints();
        constraints.add("1-(D^3*N)/(71785*d^4) <= 0");
        constraints.add("((4*(D^2)-d*D)/(12566*(D*(d^3)-(d^4)))) + (1/(5108*(d^2)))-1 <= 0");
        constraints.add("1-(140.45*d/((D^2)*N)) <= 0");
        constraints.add("((D+d)/1.5)-1 <= 0");
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

            x[indexFO] = (x[2] + 2) * x[1] * Math.pow(x[0], 2);

            double[] constraintsIneq = new double[4];

            constraintsIneq[0] = 1.0 - (Math.pow(x[1], 3) * x[2]) / (71785.0 * Math.pow(x[0], 4));
            constraintsIneq[1] = ((4.0 * (Math.pow(x[1], 2)) - x[0] * x[1]) / (12566.0 * (x[1] * (Math.pow(x[0], 3)) - (Math.pow(x[0], 4))))) + (1.0 / (5108.0 * (Math.pow(x[0], 2)))) - 1.0;
            constraintsIneq[2] = 1.0 - (140.45 * x[0] / ((Math.pow(x[1], 2)) * x[2]));
            constraintsIneq[3] = ((x[1] + x[0]) / 1.5) - 1.0;

            double[] rightSideIneq = {0, 0, 0, 0};
            String[] comparatorIneq = {"<=", "<=", "<=", "<="};

            double[] constraintsEqua = {};
            double[] rightSideEqua = {};

            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }

        return values;
    }

}
