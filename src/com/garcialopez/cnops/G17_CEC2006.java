package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G17_CEC2006 class that creates a test CNOP.
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G17_CEC2006 extends CNOP {

    /**
     * Constructor for the G17_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G17_CEC2006() {
        this.setNameProblem("G17 CEC2006");
        this.setBestKnownValue(8853.53967480648);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("if((x1 >= 0 & x1 < 300)? 30 * x1 : 31 * x1) + if((x2 >= 0 & x2 < 100)? 28 * x2 : if((x2 >= 100 & x2 < 200)? 29 * x2: if((x2 >= 200 & x2 < 1000)? 30 * x2 )))");
        this.setOrderVariables("x1;x2;x3;x4;x5;x6");
        this.setVariableRange("(0.0,400.0);(0.0,1000.0);(340.0,420.0);(340.0,420.0);(-1000.0,1000.0);(0.0,0.5236)");
        Constraints constraints = new Constraints();

        constraints.add("(-1*x1)+300-(((x3*x4)/131.078)*cos(1.48477-x6))+(((0.90798*x3*x3)/131.078)*cos(1.47588)) = 0");
        constraints.add("(-1*x2)-(((x3*x4)/131.078)*cos(1.48477+x6))+(((0.90798*x4*x4)/131.078)*cos(1.47588)) = 0");
        constraints.add("(-1*x5)-(((x3*x4)/131.078)*sin(1.48477+x6))+(((0.90798*x4*x4)/131.078)*sin(1.47588)) = 0");
        constraints.add("200-(((x3*x4)/131.078)*sin(1.48477-x6))+(((0.90798*x3*x3)/131.078)*sin(1.47588)) = 0");

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
            
            double f1 = Double.NaN;
            double f2 = Double.NaN;
            
            if (x[0] >= 0 && x[0] < 300) {
                f1 = 30 * x[0];
            }else if (x[0] >= 300 && x[0] < 400) {
                f1 = 31 * x[0];
            }
            
            if (x[1] >= 0 && x[1] < 100) {
                f2 = 28 * x[1];
            } else if (x[1] >= 100 && x[1] < 200) {
                f2 = 29 * x[1];
            } else if (x[1] >= 200 && x[1] < 1000) {
                f2 = 30 * x[1];
            }
            
            
            // Include the coding of the objective function, where each variable
            // is represented by x from position 0 to the number of variables. 
            x[indexFO] = f1 + f2;

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = {};

            //In each position of the vector, evaluate the constraint.

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[4];
          
            constraintsEqua[0] = -1. * x[0] + 300.-((x[2]*x[3])/131.078) * Math.cos(1.48477 - x[5]) + ((0.90798*x[2]*x[2])/131.078)*Math.cos(1.47588);
            constraintsEqua[1] = -1. * x[1] - ((x[2]*x[3])/131.078)*Math.cos(1.48477+x[5])+((0.90798*x[3]*x[3])/131.078)*Math.cos(1.47588);
            constraintsEqua[2] = -1. * x[4] - ((x[2]*x[3])/131.078)*Math.sin(1.48477+x[5])+((0.90798*x[3]*x[3])/131.078)*Math.sin(1.47588);
            constraintsEqua[3] = 200.0 - ((x[2]*x[3])/131.078)*Math.sin(1.48477-x[5])+((0.90798*x[2]*x[2])/131.078)*Math.sin(1.47588);
            
            double[] rightSideEqua = {0,0,0,0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
