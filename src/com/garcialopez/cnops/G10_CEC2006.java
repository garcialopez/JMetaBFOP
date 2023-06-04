package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G10_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: x1 + x2 + x3 <br>
 * Subject to: -1 + 0.0025 * (x4 + x6) <= 0 <br> -1 + 0.0025 * (x5 + x7 - x4) <=
 * 0 <br> -1 + 0.01 * (x8 - x5) <= 0 <br> -x1 * x6 + 833.33252 * x4 + 100 * x1 -
 * 83333.333 <= 0 <br> -x2 * x7 + 1250 * x5 + x2 * x4 - 1250 * x4 <= 0 <br> -x3
 * * x8 + 1250000 + x3 * x5 - 2500 * x5 <= 0 <br> where:
 * (100,10000);ran{2:3,1000:10000};ran{4:8,10:1000}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G10_CEC2006 extends CNOP {

    /**
     * Constructor for the G10_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G10_CEC2006() {
        this.setNameProblem("G10 CEC2006");
        this.setBestKnownValue(7049.2480205286);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("x1 + x2 + x3");
        this.setOrderVariables("var{x,1-8}");
        this.setVariableRange("(100.0,10000.0);ran[2-3:(1000.0,10000.0)];ran[4-8:(10.0,1000.0)]");
        Constraints constraints = new Constraints();

        constraints.add("-1 + 0.0025 * (x4 + x6) <= 0");
        constraints.add("-1 + 0.0025 * (x5 + x7 - x4) <= 0");
        constraints.add("-1 + 0.01 * (x8 - x5) <= 0");
        constraints.add("-x1 * x6 + 833.33252 * x4 + 100 * x1 - 83333.333 <= 0");
        constraints.add("-x2 * x7 + 1250 * x5 + x2 * x4 - 1250 * x4 <= 0");
        constraints.add("-x3 * x8 + 1250000 + x3 * x5 - 2500 * x5 <= 0");

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
            x[indexFO] = x[0] + x[1] + x[2];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[6];            
                                                
            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -1.0 + 0.0025 * (x[3] + x[5]);
            constraintsIneq[1] = -1.0 + 0.0025 * (x[4] + x[6] - x[3]);
            constraintsIneq[2] = -1.0 + 0.01 * (x[7] - x[4]);
            constraintsIneq[3] = -x[0] * x[5] + 833.33252 * x[3] + 100.0 * x[0] - 83333.333;
            constraintsIneq[4] = -x[1] * x[6] + 1250.0 * x[4] + x[1] * x[3] - 1250.0 * x[3];
            constraintsIneq[5] = -x[2] * x[7] + 1250000.0 + x[2] * x[4] - 2500.0 * x[4];

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
