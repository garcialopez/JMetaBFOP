package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G03_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -(sqrt(10)) * prod{x,1,10,xi} <br>
 * Subject to: sum{x,1,10,xi^2} - 1 = 0 <br>
 * where: ran{1:10,0:1}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G03_CEC2006 extends CNOP {

    /**
     * Constructor for the G03_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G03_CEC2006() {
        this.setNameProblem("G03 CEC2006");
        this.setBestKnownValue(-1.0005001000);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-(sqrt(10))^10 * prod{x,1,10,xi}");
        this.setOrderVariables("var{x,1-10}");
        this.setVariableRange("ran[1-10:(0,1)]");
        Constraints constraints = new Constraints();

        constraints.add("sum{x,1,10,xi^2} - 1 = 0");

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

            double p1 = 1.0;
            double s1C = 0.0;

            for (int i = 0; i < 10; i++) {
                p1 *= x[i];
                s1C += (x[i] * x[i]);
            }

            // Include the coding of the objective function, where each variable
            // is represented by x from position 0 to the number of variables. 
            x[indexFO] = - Math.pow(Math.sqrt(10), 10) * p1;

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
            double[] constraintsEqua = new double[1];

            constraintsEqua[0] = s1C - 1.;

            double[] rightSideEqua = {0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
