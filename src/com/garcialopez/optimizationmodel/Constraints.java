package com.garcialopez.optimizationmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the constraints for a problem.
 * <br>
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Constraints {

    private final String regularExpression = "(?<!<|>|=)(?=[<>=])\\s*|\\s*(?<=<|>|=)(?![<>=])\\s*";

    private final List<String> constraints;
    private int numberConsEQuality;
    private int numberConsIneQuality;

    private String[][] constraintsInequality;
    private String[][] constraintsEquality;

    public Constraints() {
        this.constraints = new ArrayList();
    }

    /**
     * Adds a constraint to the set of constraints.
     * <br>
     * This method is used to add a constraint to the existing set of
     * constraints. The "constraints" parameter specifies the constraint to be
     * added.
     * <br>
     * If the constraint contains the operators "<=", ">=", "<", or ">", it is
     * considered an inequality constraint, and the inequality constraint
     * counter is incremented. If it contains the "=" operator, it is considered
     * an equality constraint, and the equality constraint counter is
     * incremented.
     * <br>
     * Examples of constraints:
     * <br>
     * - Inequality constraint: 1-(D^3*N)/(71785*d^4) <= 0 This constraint
     * involves the inequality operators "<=" and variables like D, N, and d.
     * <br> - Equality constraint: 1-(140.45*d/((D^2)*N)) = 0 This constraint
     * involves the equality operator "=" and variables like D, N, and d.
     * <br>
     *
     * @param constraints the constraint to be added.
     */
    public void add(String constraints) {

        if (constraints.contains("<=") || constraints.contains(">=")
                || constraints.contains("<") || constraints.contains(">")) {
            this.numberConsIneQuality++;
        } else if (constraints.contains("=")) {
            this.numberConsEQuality++;
        }

        this.constraints.add(constraints);
    }

    /**
     * Obtains the inequality matrix.
     * <br>
     * This method retrieves the inequality matrix based on the stored
     * constraints. The inequality matrix represents the constraints that
     * contain the operators "<=", ">=", "<", or ">". The method checks if there
     * are any inequality constraints stored. If so, it initializes the
     * inequality matrix, splits the constraints into parts, extends the
     * function in the first part, and populates the inequality matrix. Finally,
     * it removes the processed constraints from the stored constraints list and
     * returns the inequality matrix. If there are no inequality constraints, it
     * returns an empty matrix.
     * <br>
     *
     * @return the inequality matrix as a 2D array of strings.
     */
    public String[][] getInequalityMatrix() {

        if (this.numberConsIneQuality > 0) {

            this.constraintsInequality = new String[this.numberConsIneQuality][];
            String[] parts;
            int countAux = 0;

            for (int i = 0; i < this.constraints.size(); i++) {

                if (this.constraints.get(i).contains("<=")) {

                    parts = this.constraints.get(i).split(regularExpression);
                    constraintsInequality[countAux] = parts;
                    constraintsInequality[countAux][0] = Transform
                            .extendFunction(constraintsInequality[countAux][0]);
                    countAux++;
                    this.constraints.remove(i);
                    i--;

                } else if (this.constraints.get(i).contains(">=")) {

                    parts = this.constraints.get(i).split(regularExpression);
                    constraintsInequality[countAux] = parts;
                    constraintsInequality[countAux][0] = Transform
                            .extendFunction(constraintsInequality[countAux][0]);
                    countAux++;
                    this.constraints.remove(i);
                    i--;

                } else if (this.constraints.get(i).contains("<")) {

                    parts = this.constraints.get(i).split(regularExpression);
                    constraintsInequality[countAux] = parts;
                    constraintsInequality[countAux][0] = Transform
                            .extendFunction(constraintsInequality[countAux][0]);
                    countAux++;
                    this.constraints.remove(i);
                    i--;

                } else if (this.constraints.get(i).contains(">")) {

                    parts = this.constraints.get(i).split(regularExpression);
                    constraintsInequality[countAux] = parts;
                    constraintsInequality[countAux][0] = Transform
                            .extendFunction(constraintsInequality[countAux][0]);
                    countAux++;
                    this.constraints.remove(i);
                    i--;

                }

            } // close for
            return constraintsInequality;
        }
        return new String[0][];
    }

    /**
     * Obtains the equality matrix.
     * <br>
     * This method retrieves the equality matrix based on the stored
     * constraints. The equality matrix represents the constraints that contain
     * the "=" operator but do not contain the operators "<=" or ">=". The
     * method checks if there are any equality constraints stored. If so, it
     * initializes the equality matrix, splits the constraints into parts,
     * extends the function in the first part, and populates the equality
     * matrix. Finally, it removes the processed constraints from the stored
     * constraints list and returns the equality matrix. If there are no
     * equality constraints, it returns an empty matrix.
     * <br>
     *
     * @return the equality matrix as a 2D array of strings.
     */
    public String[][] getEqualityMatrix() {
        if (this.numberConsEQuality > 0) {

            this.constraintsEquality = new String[this.numberConsEQuality][];
            String[] parts;
            int countAux = 0;
            for (int i = 0; i < this.constraints.size(); i++) {

                if (this.constraints.get(i).contains("=") && !this.constraints.get(i).contains("<=") && !this.constraints.get(i).contains(">=")) {

                    parts = this.constraints.get(i).split(regularExpression);
                    System.out.println("asdASD: " + Arrays.toString(parts));
                    constraintsEquality[countAux] = parts;
                    constraintsEquality[countAux][0] = Transform
                            .extendFunction(constraintsEquality[countAux][0]);

                    countAux++;
                    this.constraints.remove(i);
                    i--;

                }

            } // close for
            return constraintsEquality;
        }
        return new String[0][];
    }

    /**
     * Returns the number of constraints.
     *
     * This method retrieves the current number of constraints stored in the
     * object. It returns the size of the constraints list.
     *
     * @return the number of constraints.
     */
    public int getNumbreConstraints() {
        return this.constraints.size();
    }

}
