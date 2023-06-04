package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G09_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: (x1 - 10)^2 + 5 * (x2 - 12)^2 + x3^4 + 3 * (x4 - 11)^2 + 10 * x5^6
 * + 7 * x6^2 + x7^4 - 4 * x6 * x7 - 10 * x6 - 8 * x7 <br>
 * Subject to: -127 + 2 * x1^2 + 3 * x2^4 + x3 + 4 * x4^2 + 5 * x5 <= 0 <br>
 * -282 + 7 * x1 + 3 * x2 + 10 * x3^2 + x4 - x5 <= 0 <br> -196 + 23 * x1 + x2^2
 * + 6 * x6^2 - 8 * x7 <= 0 <br> 4 * x1^2 + x2^2 - 3 * x1 * x2 + 2 * x3^2 + 5 *
 * x6 - 11 * x7 <= 0 <br> where:
 * (-10.0,10.0),(-10.0,10.0),(-10.0,10.0),(-10.0,10.0),(-10.0,10.0),(-10.0,10.0),(-10.0,10.0)
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G09_CEC2006 extends CNOP {

    /**
     * Constructor for the G09_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G09_CEC2006() {
        this.setNameProblem("G09 CEC2006");
        this.setBestKnownValue(680.6300573745);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("(x1 - 10)^2 + 5 * (x2 - 12)^2 + x3^4 + 3 * "
                + "(x4 - 11)^2 + 10 * x5^6  + 7 * x6^2 + x7^4 - 4 * "
                + "x6 * x7 - 10 * x6 - 8 * x7");
        this.setOrderVariables("x1;x2;x3;x4;x5;x6;x7");
        this.setVariableRange("(-10.0,10.0);(-10.0,10.0);(-10.0,10.0);(-10.0,10.0);(-10.0,10.0);(-10.0,10.0);(-10.0,10.0)");
        Constraints constraints = new Constraints();

        constraints.add("-127 + 2 * x1^2 + 3 * x2^4 + x3 + 4 * x4^2 + 5 * x5 <= 0");
        constraints.add("-282 + 7 * x1 + 3 * x2 + 10 * x3^2 + x4 - x5 <= 0");
        constraints.add("-196 + 23 * x1 + x2^2 + 6 * x6^2 - 8 * x7 <= 0");
        constraints.add("4 * x1^2 + x2^2 - 3 * x1 * x2 + 2 * x3^2 + 5 * x6 - 11 * x7 <= 0");

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
            x[indexFO] = Math.pow((x[0] - 10),2) + 5. * Math.pow((x[1] - 12),2) + Math.pow(x[2],4) + 3. * Math.pow((x[3] - 11),2) + 10. * Math.pow(x[4],6)  + 7. * Math.pow(x[5],2) + Math.pow(x[6],4) - 4. * x[5] * x[6] - 10.0 * x[5] - 8. * x[6];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[4];                                    
            
            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -127.0 + 2. * x[0] * x[0] + 3.0 * (Math.pow(x[1],4)) + x[2] + 4.0 * x[3] * x[3] + 5.0 * x[4];
            constraintsIneq[1] = -282. + 7. * x[0] + 3. * x[1] + 10. * Math.pow(x[2],2) + x[3] - x[4];
            constraintsIneq[2] = -196. + 23. * x[0] + Math.pow(x[1],2) + 6. * Math.pow(x[5],2) - 8. * x[6];
            constraintsIneq[3] = 4. * Math.pow(x[0],2) + Math.pow(x[1],2) - 3. * x[0] * x[1] + 2. * Math.pow(x[2],2) + 5. * x[5] - 11. * x[6];

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
