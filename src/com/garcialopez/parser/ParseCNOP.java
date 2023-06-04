package com.garcialopez.parser;

import java.util.ArrayList;
import java.util.List;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

/**
 * The ParseCNOP class is responsible for parsing mathematical functions and
 * constraints using the mxparser library.
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class ParseCNOP {

    private List<String> variables;
    private Expression expressionOF;
    private ArrayList<Expression> expressionConstInequality;
    private ArrayList<Expression> expressionConstEquality;

    /**
     * Constructor method for parsing the function.
     */
    public ParseCNOP() {
        //...
    }

    /**
     * Sets the objective function expression and variables.
     *
     * @param expressionOF The objective function expression as a string.
     * @param variables The list of variables in the expression.
     */
    public void setExpressionOF(String expressionOF, List<String> variables) {
        this.variables = variables;
        this.expressionOF = this.loadFunction(expressionOF);
    }

    /**
     * Sets the objective function expression.
     *
     * @param expression The objective function expression as an Expression
     * object.
     */
    public void setExpressionOF(Expression expression) {
        this.expressionOF = expression;
    }

    /**
     * Sets the inequality and equality constraints expressions.
     *
     * @param constraintsInequality The inequality constraints expressions as a
     * 2D array of strings.
     * @param constraintsEquality The equality constraints expressions as a 2D
     * array of strings.
     */
    public void setConstraints(String[][] constraintsInequality,
            String[][] constraintsEquality) {

        if (constraintsInequality != null) {
            this.expressionConstInequality = new ArrayList();
            for (String[] constraintsIne : constraintsInequality) {
                this.expressionConstInequality.add(this.loadFunction(constraintsIne[0]));
            }

        }

        if (constraintsEquality != null) {
            this.expressionConstEquality = new ArrayList();
            for (String[] constraintsEqu : constraintsEquality) {
                this.expressionConstEquality.add(this.loadFunction(constraintsEqu[0]));
            }
        }
    }

    /**
     * Evaluates the objective function with the given variable values.
     *
     * @param values The values of the variables in the objective function.
     * @return The result of evaluating the objective function.
     */
    public double evaluateOF(double[] values) {
        this.setExpressionOF(this.setArguments(this.expressionOF, values));
        return this.expressionOF.calculate();
    }

    /**
     * Evaluates the inequality constraint at the specified index with the given
     * variable values.
     *
     * @param index The index of the inequality constraint.
     * @param values The values of the variables in the inequality constraint.
     * @return The result of evaluating the inequality constraint.
     */
    public double evaluateConstraintsInequality(int index, double[] values) {

        this.expressionConstInequality.set(index,
                this.setArguments(this.expressionConstInequality.get(index),
                        values)
        );

        return this.expressionConstInequality.get(index).calculate();

    }

    /**
     * Evaluates the equality constraint at the specified index with the given
     * variable values.
     *
     * @param index The index of the equality constraint.
     * @param values The values of the variables in the equality constraint.
     * @return The result of evaluating the equality constraint.
     */
    public double evaluateConstraintsEquality(int index, double[] values) {

        if (this.expressionConstEquality != null) {
            this.expressionConstEquality.set(index,
                    this.setArguments(this.expressionConstEquality.get(index),
                            values)
            );

            return this.expressionConstEquality.get(index).calculate();

        }

        return 0;

    }

    /**
     * Sets the argument values in the expression.
     *
     * @param expression The expression to set the argument values for.
     * @param values The values of the arguments.
     * @return The modified expression.
     */
    private Expression setArguments(Expression expression, double[] values) {
        for (int i = 0; i < values.length; i++) {
            expression.setArgumentValue(this.variables.get(i), values[i]);
        }
        return expression;

    }

    /**
     * Parses the function and adds the variables to the expression.
     *
     * @param function The function expression as a string.
     * @return The parsed Expression object.
     */
    private Expression loadFunction(String function) {

        Expression expressionFOAux = new Expression(function);

        if (expressionFOAux.checkLexSyntax()) {

            for (String variable : this.variables) {
                expressionFOAux.addArguments(new Argument(variable, 0.0));
            }
        }
        return expressionFOAux;
    }

}
