package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G02_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: -abs((sum{x,1,20,cos(xi)^4} - 2 * prod{x,1,20,cos(xi)^2}) /
 * (sqrt(sum{x,1,20,i*xi^2}))) <br>
 * Subject to: 0.75 - prod{x,1,20,xi} <= 0 <br> sum{x,1,20,xi} - 7.5 * 20 <= 0
 * <br> where: ran{1:20,0:10}
 *
 * <br> This class inherits the mechanisms of the CNOP class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G02_CEC2006 extends CNOP {

    /**
     * Constructor for the G02_CEC2006 class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G02_CEC2006() {
        this.setNameProblem("G02 CEC2006");
        this.setBestKnownValue(-0.8036191042);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("-abs((sum{x,1,20,cos(xi)^4 - 2}  * prod{x,1,20,cos(xi)^2}) / (sqrt(sum{x,1,20,i*xi^2})))");
        this.setOrderVariables("var{x,1-20}");
        this.setVariableRange("ran[1-20:(0,10)]");
        Constraints constraints = new Constraints();

        constraints.add("0.75 - prod{x,1,20,xi} <= 0");
        constraints.add("sum{x,1,20,xi} - 7.5 * 20 <= 0");

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
            double dividend;
            double divisor = 0.;
            double s1 = 0.0;
            double p1 = 1.0;

            // VAR FOR CONSTR
            double prodC = 1.0;
            double sumC = 0.0;

            for (int i = 0; i < 20; i++) {
                divisor += ((i + 1) * Math.pow(x[i], 2)); //for divisor
                s1 += (Math.pow(Math.cos(x[i]), 4));
                p1 *= (Math.pow(Math.cos(x[i]), 2));

                // FOR CONSTRAIN
                prodC = prodC * x[i];
                sumC += x[i];
            }

            dividend = s1 - 2.0 * p1;
            divisor = Math.sqrt(divisor);

            x[indexFO] = -Math.abs(dividend / divisor);

            //Create a vector of size n inequality constraints (if applicable)
            double[] constraintsIneq = new double[2];

            //In each position of the vector, evaluate the constraint.                                               
            constraintsIneq[0] = 0.75 - prodC;
            constraintsIneq[1] = sumC - 7.5 * 20;

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0, 0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<=", "<="};

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
