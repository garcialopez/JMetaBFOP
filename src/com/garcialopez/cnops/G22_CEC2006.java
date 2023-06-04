package com.garcialopez.cnops;

import com.garcialopez.optimizationmodel.CNOP;
import com.garcialopez.optimizationmodel.Constraints;

/**
 * G22_CEC2006 class that creates a test CNOP. This CNOP has as objective
 * function:<br><br>
 *
 * Minimize: x1 <br>
 * Subject to: -x1 + x2^0.6 + x3^0.6 + x4^0.6 <= 0 <br> x5 - 100000 * x8 + 1 *
 * 10^7 = 0 <br>
 * x6 + 100000 * x8 - 100000 * x9 = 0 <br>
 * x7 + 100000 * x9 - 5 * 10^7 = 0 <br>
 * x5 + 100000 * x10 - 3.3 * 10^7 = 0 <br>
 * x6 + 100000 * x11 - 4.4 * 10^7 = 0 <br>
 * x7 + 100000 * x12 - 6.6 * 10^7 = 0 <br>
 * x5 - 120 * x2 * x13 = 0 <br>
 * x6 - 80 * x3 * x14 = 0 <br>
 * x7 - 40 * x4 * x15 = 0 <br>
 * x8 - x11 + x16 = 0 <br>
 * x9 - x12 + x17 = 0 <br>
 * -x18 + ln(x10 - 100) = 0 <br>
 * -x19 + ln(-x8 + 300) = 0 <br>
 * -x20 + ln(x16) = 0 <br>
 * -x21 + ln(-x9 + 400) = 0 <br>
 * -x22 + ln(x17) = 0 <br>
 * -x8 - x10 + x1 * x18 - x13 * x19 + 400 = 0 <br>
 * x8 - x9 - x11 + x14 * x20 - x14 * x21 + 400 = 0 <br>
 * x9 - x12 - 4.60517 * x15 + x15 * x22 + 100 = 0 <br>
 * where:
 * (0,20000);ran{2:4,0:1*10^6};ran{5:7,0:4*10^7};(100,299.99);(100,399.99);(100.01,300);(100,400);(100,600);ran{13:15,0:500};(0.01,300);(0.01,400);ran{18:22,-4.7:6.25}
 *
 * <br> This class inherits the mechanisms of the Problem class.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class G22_CEC2006 extends CNOP {

    /**
     * Constructor for the CONSTRUCTOR class. Initializes the specific
     * properties of the CNOP for the Tension/compression spring problem. Sets
     * the problem name, best known value, optimization type, objective
     * function, variable order, and variable ranges. It also adds the
     * constraints to the Constraints object.
     */
    public G22_CEC2006() {
        this.setNameProblem("G22 CEC2006");
        this.setBestKnownValue(236.4309755040);
        this.setType(CNOP.MINIMIZATION);
        this.setFunction("x1");
        this.setOrderVariables("var{x,1-22}");
        this.setVariableRange("(0,20000.0);ran[2-4:(0,1*10^6)];ran[5-7:(0,4*10^7)];(100,299.99);(100,399.99);(100.01,300);(100,400);(100,600);ran[13-15:(0,500)];(0.01,300);(0.01,400);ran[18-22:(-4.7,6.25)]");
        
        Constraints constraints = new Constraints();

        constraints.add("-x1 + x2^0.6 + x3^0.6 + x4^0.6 <= 0");
        constraints.add("x5 - 100000 * x8 + 1 * 10^7 = 0");
        constraints.add("x6 + 100000 * x8 - 100000 * x9 = 0");
        constraints.add("x7 + 100000 * x9 - 5 * 10^7 = 0");
        constraints.add("x5 + 100000 * x10 - 3.3 * 10^7 = 0");
        constraints.add("x6 + 100000 * x11 - 4.4 * 10^7 = 0");
        constraints.add("x7 + 100000 * x12 - 6.6 * 10^7 = 0");
        constraints.add("x5 - 120 * x2 * x13 = 0");
        constraints.add("x6 - 80 * x3 * x14 = 0");
        constraints.add("x7 - 40 * x4 * x15 = 0");
        constraints.add("x8 - x11 + x16 = 0");
        constraints.add("x9 - x12 + x17 = 0");
        constraints.add("-x18 + ln(x10 - 100) = 0");
        constraints.add("-x19 + ln(-x8 + 300) = 0");
        constraints.add("-x20 + ln(x16) = 0");
        constraints.add("-x21 + ln(-x9 + 400) = 0");
        constraints.add("-x22 + ln(x17) = 0");
        constraints.add("-x8 - x10 + x1 * x18 - x13 * x19 + 400 = 0");
        constraints.add("x8 - x9 - x11 + x14 * x20 - x14 * x21 + 400 = 0");
        constraints.add("x9 - x12 - 4.60517 * x15 + x15 * x22 + 100 = 0");

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
            constraintsIneq[0] = -x[0] + Math.pow(x[1],0.6) + Math.pow(x[2],0.6) + Math.pow(x[3],0.6);            

            // Create a vector of size n according to the number of inequality 
            // constraints for save the right side of the inequality constraints.
            double[] rightSideIneq = {0};

            // Create a vector of size n according to the number of inequality 
            // constraints for save the comparator of the inequality constraints.
            String[] comparatorIneq = {"<="};

            // Perform the previous steps, except for the comparator vector, 
            // in case there are equality constraints.
            double[] constraintsEqua = new double[19];
            constraintsEqua[0] = x[4] - 100000.0 * x[7] + 1.0 * Math.pow(10,7);
            constraintsEqua[1] = x[5] + 100000.0 * x[7] - 100000.0 * x[8];
            constraintsEqua[2] = x[6] + 100000.0 * x[8] - 5.0 * Math.pow(10,7);
            constraintsEqua[3] = x[4] + 100000.0 * x[9] - 3.3 * Math.pow(10,7);
            constraintsEqua[4] = x[5] + 100000.0 * x[10] - 4.4 * Math.pow(10,7);
            constraintsEqua[5] = x[6] + 100000.0 * x[11] - 6.6 * Math.pow(10,7);
            constraintsEqua[6] = x[4] - 120.0 * x[1] * x[12];
            constraintsEqua[7] = x[5] - 80.0 * x[2] * x[13];
            constraintsEqua[8] = x[6] - 40.0 * x[3] * x[14];
            constraintsEqua[9] = x[7] - x[10] + x[15];
            constraintsEqua[10] = x[8] - x[11] + x[16];
            constraintsEqua[11] = -x[17] + Math.log(x[9] - 100.0);
            constraintsEqua[12] = -x[18] + Math.log(-x[7] + 300.0);
            constraintsEqua[13] = -x[19] + Math.log(x[15]);
            constraintsEqua[14] = -x[20] + Math.log(-x[8] + 400.0);
            constraintsEqua[15] = -x[21] + Math.log(x[16]);
            constraintsEqua[16] = -x[7] - x[9] + x[12] * x[17] - x[12] * x[18] + 400.0;
            constraintsEqua[17] = x[7] - x[8] - x[10] + x[13] * x[19] - x[13] * x[20] + 400.0;
            constraintsEqua[18] = x[8] - x[11] - 4.60517 * x[14] + x[14] * x[21] + 100.0;             
            
            double[] rightSideEqua = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

            // Assign SVR
            x[indexSVR] = this.getSumConstraintViolation(constraintsIneq, constraintsEqua, rightSideIneq, rightSideEqua, comparatorIneq);

        }//close for 

        return values;
    } // close evaluateObjectiveFunction

}
