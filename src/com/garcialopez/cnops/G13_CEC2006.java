package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G13_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: exp(x1 * x2 * x3 * x4 * x5) <br>
 * Subject to: x1^2 + x2^2 + x3^2 + x4^2 + x5^2 - 10 = 0 <br>
 * x2 * x3 - 5 * x4 * x5 = 0 <br>
 * x1^3 + x2^3 + 1 = 0 <br>
 * where: (-2.3,2.3);(-2.3,2.3);ran{3:5,-3.2:3.2}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G13_CEC2006 extends CNOP {

    /**
     * Constructor for the G13_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G13_CEC2006() {
        this.setNameProblem("G13 CEC2006");
        this.setBestKnownValue(0.0539415140);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("exp(x1 * x2 * x3 * x4 * x5)");
        this.setOrderVariables("x1;x2;x3;x4;x5");
        this.setVariableRange("(-2.3,2.3);(-2.3,2.3);ran[3-5:(-3.2,3.2)]");
        Constraints constraints = new Constraints();

        constraints.add("x1^2 + x2^2 + x3^2 + x4^2 + x5^2 - 10 = 0");
        constraints.add("x2 * x3 - 5 * x4 * x5 = 0");
        constraints.add("x1^3 + x2^3 + 1 = 0");        

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
            x[indexFO] = Math.exp((x[0] * x[1] * x[2] * x[3] * x[4]));            

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = {};           

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[3];            
            
            constraintsEqua[0] = x[0] * x[0] + x[1] * x[1] + x[2] * x[2] + x[3] * x[3] + x[4] * x[4] - 10.0;
            constraintsEqua[1] = x[1] * x[2] - 5.0 * x[3] * x[4];
            constraintsEqua[2] = Math.pow(x[0],3) + Math.pow(x[1],3) + 1.0;
            
            double[] rightSideEqua = {0, 0, 0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction
}
