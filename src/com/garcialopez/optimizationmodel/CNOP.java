package com.garcialopez.optimizationmodel;

import com.garcialopez.parser.ParseCNOP;
import java.util.Arrays;
import java.util.List;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;

/**
 * CNOP (Constraint Numerica Optimization Problem) class represents a constraint
 * optimization problem.
 * <br>
 * It provides methods and attributes to define and manage a CNOP.
 * <br>
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class CNOP {

    /* Non-Commercial Use Confirmation */
    boolean isCallSuccessful = License.iConfirmNonCommercialUse("JMetaBFOP");

    /* Verification if use type has been already confirmed */
    boolean isConfirmed = License.checkIfUseTypeConfirmed();

    public static final String MINIMIZATION = "Minimization";
    public static final String MAXIMIZATION = "Maximization";

    private String nameProblem;
    private double bestKnownValue;
    private String type = CNOP.MINIMIZATION;
    private String function;
    private int numberVariable = 0;
    private int numberVariableOF = 0;
    private List<String> orderVariables;
    private double[][] variableRange;
    private boolean[] isContinuousVariable;

    private String[][] constraintsEquality;
    private String[][] constraintsInequality;

    private double sumConstraintViolation = 0;

    //Parameter solo para parser
    private ParseCNOP parseCNOP;

    private int sizeCInequality;
    private int sizeCEquality;

    /**
     * Empty constructor method for object instance.
     */
    public CNOP() {
    }

    /**
     * Method returning the problem name.
     *
     * @return the nameProblem
     */
    public String getNameProblem() {
        return nameProblem;
    }

    /**
     * Method that establishes the problem name.
     *
     * @param nameProblem the nameProblem to set
     */
    public void setNameProblem(String nameProblem) {
        this.nameProblem = nameProblem;
    }

    /**
     * Method that returns the best known value for CNOP.
     *
     * @return the bestKnownValue
     */
    public double getBestKnownValue() {
        return bestKnownValue;
    }

    /**
     * Method that sets the best known value for the CNOP.
     *
     * @param bestKnownValue the bestKnownValue to set
     */
    public void setBestKnownValue(double bestKnownValue) {
        this.bestKnownValue = bestKnownValue;
    }

    /**
     * Method that returns the optimization goal:<br>
     * Can be:<br><br>
     * Problem.MINIMIZATION<br>
     * Problem.MAXIMIZATION<br>
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Method that sets the optimization objective:
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method that returns the objective function of the problem.
     *
     * @return the function
     */
    public String getFunction() {
        return function;
    } //close getFunction

    /**
     * Method that establishes the objective function of the problem.<br><br>
     * These may be of different types such as:<br>
     * 29.4*x1 + 18*x2<br>
     * 5 * sum{x,1,4,xi}<br>
     * sum{x,1,4,xi^2} - sum{x,5,13,xi}<br>
     * sum{x,1,4,xi^2} - prod{x,5,13,xi}<br>
     * x1<br>
     * -(100 - (x1 - 5)^2 - (x2 - 5)^2 - (x3 - 5)^2)/100<br>
     * and more...<br><br>
     *
     * the purpose is that mXparse is in charge of evaluating and substituting
     * directly the values for each variable.
     *
     * <br>Syntax allowed for summations and products is:<br> <br>
     *
     * summation: sum{var, from, to, f(x1,x2,...,xn)} <br>
     * Product: prod{var, from, to, f(x1,x2,...,xn)}
     *
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = Transform.extendFunction(function);
    } //close setFunction

    /**
     * Method returning the assigned variable number.
     *
     * @return the numberAssignedVariable
     */
    public int getNumberVariable() {
        return numberVariable;
    }

    /**
     * Method setting the assigned variable number.
     *
     * @param numberVariable
     */
    protected void setNumberVariable(int numberVariable) {
        this.numberVariable = numberVariable;
    }

    /**
     * Method to return the number of CNOP variables.
     *
     * @return the number Variable OF
     */
    public int getNumberVariableOF() {
        return numberVariableOF;
    }

    /**
     * Method that returns the order of the CNOP design variables.
     *
     * @return the orderVariables
     */
    public List<String> getOrderVariables() {
        return orderVariables;
    }

    /**
     * Method that assigns the order of the CNOP design variables.<br><br>
     *
     * e.g: x1;x2;x3;x4;x5;x6;x7<br><br>
     *
     * also, the following abbreviation can be used:<br><br>
     *
     * if the variables input is:<br>
     * p1; p2; var{x,1-3}; var{y,1-4}; p1; p2<br><br>
     *
     * conversion will return <br>
     * [p1, p2, x1, x2, x3, y1, y2, y3, y4, p1, p2]<br><br>
     *
     * all elements inside the iteration are separated by commas (;) and outside
     * the iteration are separated by semicolons (;).
     *
     * @param varOrder the orderVariables
     */
    public void setOrderVariables(String varOrder) {
        this.orderVariables = Transform.extendVariables(varOrder);

        // Se guarda el número de variables de la función objetivo
        this.numberVariableOF = new Expression(this.function).getMissingUserDefinedArguments().length;
        this.setNumberVariable(this.getOrderVariables().size());

    }

    /**
     * Method that returns the range of CNOP design variables.
     *
     * @return the rankVariable
     */
    public double[][] getVariableRange() {
        return variableRange;
    }

    /**
     * Method that establishes the range of CNOP design variables.
     *
     * The variableRange parameter accepts the following formats:
     *
     * - Continuous variables: Use parentheses to define the range. For example,
     * (0.5,2.5) represents a variable range from 0.5 to 2.5.
     *
     * - Discrete variables: Use curly braces to define the set of values. For
     * example, {0,1} represents a discrete variable with two possible values, 0
     * and 1. Multiple values can be separated by commas, e.g.,
     * {0,1,2,3,4,...,n}.
     *
     * - Multiple variables: Use the function "ran" followed by the range
     * specification in square brackets. For example, ran[3-7:(0,1.2)]
     * represents 5 continuous variables with a range from 0 to 1.2.
     *
     * The conversion of the variableRange parameter will return the
     * corresponding ranges for all variables.
     *
     * Example usage: - For continuous and discrete variables: (0, 1.2);(0,
     * 1.8);(0, 2.5);{0,1};{0,1};{0,1};{0,1} - For multiple variables:
     * ran[1-9:(0,1)];ran[10-12:(0,100)];(0,1) - For a combination of multiple
     * variables: ran[1-9:(0,1)];ran[10-12:(0,100)];{0,1}
     *
     * @param variableRange the string representation of variable ranges
     */
    public void setVariableRange(String variableRange) {
        this.variableRange = Transform.createRanges(variableRange);
        this.setIsContinuousVariable(Transform.CONTINUOUS);
    }

    /**
     *
     * Returns an array indicating whether each variable is continuous or
     * discrete.
     *
     * @return an array of boolean values indicating the continuity of each
     * variable
     */
    public boolean[] isContinuousVariable() {
        return isContinuousVariable;
    }

    /**
     *
     * Sets the array indicating whether each variable is continuous or
     * discrete.
     *
     * @param isContinuousVariable an array of boolean values indicating the
     * continuity of each variable
     */
    public void setIsContinuousVariable(boolean[] isContinuousVariable) {
        this.isContinuousVariable = isContinuousVariable;
    }

    /**
     * Method that returns equality constraints.
     *
     * @return the constraintsEquality
     */
    public String[][] getConstraintsEquality() {
        return constraintsEquality;
    }

    /**
     * Method that returns inequality constraints.
     *
     * @return the constraintsInequality
     */
    public String[][] getConstraintsInequality() {
        return constraintsInequality;
    }

    /**
     *
     * Sets the constraints for the CNOP.
     *
     * @param constraints an instance of the Constraints class containing the
     * inequality and equality constraint matrices
     */
    public void setConstraints(Constraints constraints) {

        this.constraintsInequality = constraints.getInequalityMatrix();
        this.sizeCInequality = this.constraintsInequality.length;
        this.constraintsEquality = constraints.getEqualityMatrix();

        this.sizeCEquality = this.constraintsEquality.length;

    }

    /**
     *
     * Parses the CNOP function and sets up the constraints using the specified
     * function expression, order of variables, inequality constraints, and
     * equality constraints. This method is used when the CNOP is provided as a
     * text string and utilizes the mxParser library for parsing and evaluation.
     */
    public void parserCNOP() {

        this.parseCNOP = new ParseCNOP();

        this.parseCNOP.setExpressionOF(this.function, this.orderVariables);
        this.parseCNOP.setConstraints(this.constraintsInequality, this.constraintsEquality);

    }

    /**
     *
     * Evaluates the objective function using the provided values and returns a
     * matrix of objective function values.
     * <br>
     *
     * @param values The matrix of variable values to be evaluated.
     * @return A matrix representing the evaluated objective function values.
     */
    public double[][] evaluateObjectiveFunction(double[][] values) {
        int varNumber = this.getNumberVariable();
        int sizeValues = values[0].length;
        int indexFO = sizeValues - 2;
        int indexSVR = sizeValues - 1;

        for (double[] value : values) {
            double[] valuesAux = Arrays.copyOfRange(value, 0, varNumber);
            value[indexFO] = this.parseCNOP.evaluateOF(valuesAux);
            value[indexSVR] = this.getSumConstraintViolation(valuesAux,
                    constraintsInequality,
                    constraintsEquality
            );
        }

        return values;
    }

    /**
     *
     * Calculates the sum of constraint violations for the given variable values
     * and constraint matrices.
     *
     * @param values The array of variable values.
     * @param constraintsInequality The matrix of inequality constraints (parse
     * with mxParser).
     * @param constraintsEquality The matrix of equality constraints (parse with
     * mxParser).
     * @return The sum of constraint violations.
     */
    public double getSumConstraintViolation(double[] values,
            String[][] constraintsInequality,
            String[][] constraintsEquality) {

        this.sumConstraintViolation = 0;
        double svrAux = 0;
        String comparator;
        double rightSide;
        double resultC;

        if (constraintsInequality != null || constraintsEquality != null) {

            if (sizeCInequality > 0) {

                for (int i = 0; i < sizeCInequality; i++) {
                    resultC = this.parseCNOP.evaluateConstraintsInequality(i, values);

                    comparator = constraintsInequality[i][1];

                    rightSide = Double.parseDouble(constraintsInequality[i][2]);

                    svrAux += calculateSVR(resultC, comparator, rightSide);
                }

            }

            if (sizeCEquality > 0) {

                for (int i = 0; i < sizeCEquality; i++) {
                    resultC = this.parseCNOP.evaluateConstraintsEquality(i, values);
                    rightSide = Double.parseDouble(constraintsEquality[i][2]);
                    svrAux += calculateSVR(resultC, "=", rightSide);
                }
            }
            this.sumConstraintViolation = svrAux;
        } else {
            this.sumConstraintViolation = Double.NaN;
        }

        return sumConstraintViolation;
    }

    /**
     *
     * Calculates the sum of constraint violations for the given constraint
     * parameters.
     *
     * @param constraintsIneq The array of inequality constraint values.
     * @param constraintsEqua The array of equality constraint values.
     * @param rightSideIneq The array of right side values for inequality
     * constraints.
     * @param rightSideEqua The array of right side values for equality
     * constraints.
     * @param comparatorIneq The array of inequality constraint comparators
     * ("<", "<=", ">", ">=", or "=").
     * @return The sum of constraint violations.
     */
    public double getSumConstraintViolation(
            double[] constraintsIneq,
            double[] constraintsEqua,
            double[] rightSideIneq,
            double[] rightSideEqua,
            String[] comparatorIneq) {

        this.sumConstraintViolation = 0;
        double svrAux = 0;
        String comparator;
        double rightSide;

        if (constraintsIneq != null || constraintsEqua != null) {

            if (constraintsIneq.length > 0) {

                for (int i = 0; i < constraintsIneq.length; i++) {
                    double resultC = constraintsIneq[i];

                    comparator = comparatorIneq[i];
                    rightSide = rightSideIneq[i];

                    svrAux += calculateSVR(resultC, comparator, rightSide);
                }

            }

            if (constraintsEqua.length > 0) {

                for (int i = 0; i < constraintsEqua.length; i++) {
                    double resultC = constraintsEqua[i];
                    rightSide = rightSideEqua[i];
                    svrAux += calculateSVR(resultC, "=", rightSide);
                }
            }
            this.sumConstraintViolation = svrAux;
        } else {
            this.sumConstraintViolation = Double.NaN;
        }

        return sumConstraintViolation;
    }

    /**
     *
     * Calculates the constraint violation for a single constraint based on the
     * given result, comparator, and right side.
     *
     * @param resultC The result of the constraint evaluation.
     *
     * @param comparator The comparator ("<", "<=", ">", ">=", or "=") used for
     * the constraint.
     *
     * @param rightSide The right side value of the constraint.
     *
     * @return The constraint violation value.
     */
    private double calculateSVR(double resultC, String comparator, double rightSide) {
        double violation = 0.0;

        switch (comparator) {
            case ">=":
            case ">":
                violation = Math.max(0, resultC + rightSide);
                break;
            case "<=":
            case "<":
                violation = Math.max(0, resultC - rightSide);
                break;
            case "=":
                violation = Math.max(0, Math.abs(resultC) - 0.0001);
                break;
        }

        return violation;
    }

}
