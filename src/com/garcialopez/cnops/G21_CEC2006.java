package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G21_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: x1 <br>
 * Subject to: -x1 + 35 * x2^0.6 + 35 * x3^0.6 <= 0 <br> -300 * x3 + 7500 * x5 -
 * 7500 * x6 - 25 * x4 * x5 + 25 * x4 * x6 + x3 * x4 = 0 <br>
 * 100 * x2 + 155.365 * x4 + 2500 * x7 - x2 * x4 - 25 * x4 * x7 - 15536.5 = 0
 * <br>
 * -x5 + ln(-x4 + 900) = 0 <br>
 * -x6 + ln(x4 + 300) = 0 <br>
 * -x7 + ln(-2 * x4 + 700) = 0 <br>
 * where: iter{x,1,7}
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G21_CEC2006 extends CNOP {

    /**
     * Constructor for the G21_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G21_CEC2006() {
        this.setNameProblem("G21 CEC2006");
        this.setBestKnownValue(193.7245100700);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("x1");        
        this.setOrderVariables("var{x,1-7}");
        this.setVariableRange("(0,1000.0);(0,40.0);(0,40.0);(100.0,300.0);(6.3,6.7);(5.9,6.4);(4.5,6.25)");
        Constraints constraints = new Constraints();

        constraints.add("-x1 + 35 * x2^0.6 + 35 * x3^0.6 <= 0");
        constraints.add("-300 * x3 + 7500 * x5 - 7500 * x6 - 25 * x4 * x5 + 25 * x4 * x6 + x3 * x4 = 0");
        constraints.add("100 * x2 + 155.365 * x4 + 2500 * x7 - x2 * x4 - 25 * x4 * x7 - 15536.5 = 0");
        constraints.add("-x5 + ln(-x4 + 900) = 0");
        constraints.add("-x6 + ln(x4 + 300) = 0");
        constraints.add("-x7 + ln(-2 * x4 + 700) = 0");

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
            x[indexFO] = x[0];

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[1];

            //In each position of the vector, evaluate the constraint.
            constraintsIneq[0] = -x[0] + 35.0 * Math.pow(x[1],0.6) + 35.0 * Math.pow(x[2],0.6);                                 

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<="};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[5];
            
            constraintsEqua[0] = (-300.0 * x[2]) + (7500.0 * x[4]) - (7500 * x[5]) -(25.0 * x[3] * x[4]) + (25.0 * x[3] * x[5]) + x[2] * x[3];                                 
            constraintsEqua[1] = 100.0 * x[1] + 155.365 * x[3] + 2500.0 * x[6] - x[1] * x[3] - 25.0 * x[3] * x[6] - 15536.5;                                                       
            constraintsEqua[2] = -x[4] + Math.log(-x[3] + 900.0);
            constraintsEqua[3] = -x[5] + Math.log(x[3] + 300.0);
            constraintsEqua[4] = -x[6] + Math.log(-2 * x[3] + 700.0);
            
            double[] rightSideEqua = {0,0,0,0,0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
