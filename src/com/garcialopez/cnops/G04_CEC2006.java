package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G04_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: 5.3578547 * x3^2 + 0.8356891 * x1 * x5 + 37.293239 * x1 - 40792.141
 * <br>
 * Subject to: 85.334407 + 0.0056858 * x2 * x5 + 0.0006262 * x1 * x4 - 0.0022053
 * * x3 * x5 - 92 <= 0 <br> -85.334407 - 0.0056858 * x2 * x5 - 0.0006262 * x1 *
 * x4 + 0.0022053 * x3 * x5 <= 0 <br> 80.51249 + 0.0071317 * x2 * x5 + 0.0029955
 * * x1 * x2 + 0.0021813 * x3^2 - 110 <= 0 <br> -80.51249 - 0.0071317 * x2 * x5
 * - 0.0029955 * x1 * x2 - 0.0021813 * x3^2 + 90 <= 0 <br> 9.300961 + 0.0047026
 * * x3 * x5 + 0.0012547 * x1 * x3 + 0.0019085 * x3 * x4 - 25 <= 0 <br>
 * -9.300961 - 0.0047026 * x3 * x5 - 0.0012547 * x1 * x3 - 0.0019085 * x3 * x4 +
 * 20 <= 0 <br> where: (78,102),(33,45),(27,45),(27,45),(27,45)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G04_CEC2006 extends CNOP {

    /**
     * Constructor for the G04_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G04_CEC2006() {
        this.setNameProblem("G04 CEC2006");
        this.setBestKnownValue(-30665.5386717834);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("5.3578547 * x3^2 + 0.8356891 * x1 * x5 + 37.293239 * x1 - 40792.141");
        this.setOrderVariables("x1;x2;x3;x4;x5");
        this.setVariableRange("(78,102);(33,45);(27,45);(27,45);(27,45)");
        Constraints constraints = new Constraints();

        constraints.add("85.334407 + 0.0056858 * x2 * x5 + 0.0006262 * x1 * x4 - 0.0022053 * x3 * x5 - 92 <= 0");
        constraints.add("-85.334407 - 0.0056858 * x2 * x5 - 0.0006262 * x1 * x4 + 0.0022053 * x3 * x5 <= 0");
        constraints.add("80.51249 + 0.0071317 * x2 * x5 + 0.0029955 * x1 * x2 + 0.0021813 * x3^2 - 110 <= 0");
        constraints.add("-80.51249 - 0.0071317 * x2 * x5 - 0.0029955 * x1 * x2 - 0.0021813 * x3^2 + 90 <= 0");
        constraints.add("9.300961 + 0.0047026 * x3 * x5 + 0.0012547 * x1 * x3 + 0.0019085 * x3 * x4 - 25 <= 0");
        constraints.add("-9.300961 - 0.0047026 * x3 * x5 - 0.0012547 * x1 * x3 - 0.0019085 * x3 * x4 + 20 <= 0");

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
            x[indexFO] = 5.3578547 * (x[2] * x[2]) + 0.8356891 * x[0] * x[4] + 37.293239 * x[0] - 40792.141;

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[6];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = 85.334407 + 0.0056858 * x[1] * x[4] + 0.0006262 * x[0] * x[3] - 0.0022053 * x[2] * x[4] - 92;
            constraintsIneq[1] = -85.334407 - 0.0056858 * x[1] * x[4] - 0.0006262 * x[0] * x[3] + 0.0022053 * x[2] * x[4];
            constraintsIneq[2] = 80.51249 + 0.0071317 * x[1] * x[4] + 0.0029955 * x[0] * x[1] + 0.0021813 * (x[2] * x[2]) - 110;
            constraintsIneq[3] = -80.51249 - 0.0071317 * x[1] * x[4] - 0.0029955 * x[0] * x[1] - 0.0021813 * (x[2] * x[2]) + 90;
            constraintsIneq[4] = 9.300961 + 0.0047026 * x[2] * x[4] + 0.0012547 * x[0] * x[2] + 0.0019085 * x[2] * x[3] - 25;
            constraintsIneq[5] = -9.300961 - 0.0047026 * x[2] * x[4] - 0.0012547 * x[0] * x[2] - 0.0019085 * x[2] * x[3] + 20;

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0, 0, 0, 0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<=", "<=", "<=", "<=", "<="};

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
