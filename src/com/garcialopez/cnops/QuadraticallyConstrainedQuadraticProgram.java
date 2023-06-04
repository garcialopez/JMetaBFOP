/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * QuadraticallyConstrainedQuadraticProgram class that creates a test CNOP. This
 * CNOP has as objective function:<br>https://doi.org/10.1080/00986449208936033<br>
 *
 * Minimize: x1^4 - 14 * x1^2 + 24 * x1 - x2^2 <br>
 * Subject to: -x1 + x2 - 8 <= 0 <= 0 <br> x2 - x1^2 - 2 * x1 + 2 <= 0 <br>
 * where: (-8,10),(0,10)
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class QuadraticallyConstrainedQuadraticProgram extends CNOP {

    /**
     * Constructor for the QuadraticallyConstrainedQuadraticProgram class.
     * Initializes the specific properties of the CNOP for the
     * Tension/compression spring problem. Sets the problem name, best known
     * value, optimization type, objective function, variable order, and
     * variable ranges. It also adds the constraints to the Constraints object.
     *
     */
    public QuadraticallyConstrainedQuadraticProgram() {
        this.setNameProblem("Quadratically constrained quadratic program");
        this.setBestKnownValue(-118.7048);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("x1^4 - 14 * x1^2 + 24 * x1 - x2^2");
        this.setOrderVariables("x1;x2");
        this.setVariableRange("(-8,10);(0,10)");
        Constraints constraints = new Constraints();
        constraints.add("-x1 + x2 - 8 <= 0");
        constraints.add("x1 - 10 <= 0");
        constraints.add("-x2 <= 0");
        constraints.add("x2 - x1^2 - 2 * x1 + 2 <= 0");
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
            x[indexFO] = Math.pow(x[0], 4) - 14 * Math.pow(x[0], 2) + 24 * x[0] - Math.pow(x[1], 2);

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[4];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -x[0] + x[1] - 8;
            constraintsIneq[1] = x[0] - 10;
            constraintsIneq[2] = -x[1];
            constraintsIneq[3] = x[1] - Math.pow(x[0], 2) - 2 * x[0] + 2;

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
