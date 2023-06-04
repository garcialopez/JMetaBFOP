package com.garcialopez.optimizationmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.mariuszgromada.math.mxparser.Expression;

/**
 *
 * Utility class for transforming mathematical expressions and ranges.
 * <br>
 *
 * @author <b>Adrian García-López</b><br>
 * <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on
 * GitHub</a><br>
 * <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */
public class Transform {

    public static boolean[] CONTINUOUS;

    /**
     * Extends a summarized mathematical expression by replacing function calls
     * with their expanded forms.
     *
     * @param function A summarized mathematical expression.
     * @return An extended mathematical expression.
     *
     * Example: Input: "5 * sum{x,1,4,xi} - prod{x,1,4,(xi+i)}" Output: "5 *
     * (x1+x2+x3+x4) - ((x1+1)*(x2+2)*(x3+3)*(x4+4))"
     */
    public static String extendFunction(String function) {
        function = function.replaceAll("\\s", "");
        if (function.contains("sum")
                || function.contains("prod")) {
            Pattern functionPattern = Pattern.compile("\\b(\\w+)\\{(\\w+),(\\d+),(\\d+),([^{}]+)}");
            Matcher functionMatcher = functionPattern.matcher(function);

            int i = 0;
            List<String> functionExpressions = new ArrayList<>();

            while (functionMatcher.find()) {
                String functionName = functionMatcher.group(1);
                String variableName = functionMatcher.group(2);
                int start = Integer.parseInt(functionMatcher.group(3));
                int end = Integer.parseInt(functionMatcher.group(4));
                String body = functionMatcher.group(5);

                String functionExpression = IntStream.rangeClosed(start, end)
                        .mapToObj(j -> body.replaceAll(variableName + "i", variableName + j).replaceAll("i", String.valueOf(j)))
                        .collect(Collectors.joining(functionName.equals("sum") ? "+" : "*"));

                functionExpressions.add(functionExpression);
                function = functionMatcher.replaceFirst("|" + i++ + "|");
                functionMatcher.reset(function);
            }

            for (int j = 0; j < functionExpressions.size(); j++) {
                function = function.replace("|" + j + "|", "(" + functionExpressions.get(j) + ")");
            }

        }
        return function;
    }//close 

    /**
     * Extends variable names and ranges defined in a string into a list of
     * variable names.
     *
     * @param input the input string containing variable definitions
     * @return a list of extended variable names
     *
     * Example: Input: "var{x,1-3}; var{y,1-2}; z" Output: ["x1", "x2", "x3",
     * "y1", "y2", "z"]
     */
    public static List<String> extendVariables(String input) {
        input = input.replaceAll("\\s+", "");
        return (input.contains("var"))
                ? Arrays.stream(input.split(";"))
                        .map(String::trim)
                        .flatMap(token -> {
                            if (token.startsWith("var")) {
                                String[] iterTokens = token.split("[{},-]");
                                String variable = iterTokens[1];
                                int start = Integer.parseInt(iterTokens[2]);
                                int end = Integer.parseInt(iterTokens[3]);

                                return IntStream.rangeClosed(start, end)
                                        .mapToObj(i -> variable + i);
                            } else {
                                return Stream.of(token);
                            }
                        })
                        .collect(Collectors.toList())
                : Arrays
                        .stream(input.split(";"))
                        .collect(Collectors.toList());
    } //close

    /**
     * Converts a string of sets into a matrix of numeric values, where each row
     * of the matrix represents a set of values. The sets can be continuous or
     * discrete and can be defined using numeric values or mathematical
     * expressions. The input string should be formatted as follows: each set
     * should be separated by a semicolon (;), and the values of each set should
     * be surrounded by parentheses for continuous sets or braces for discrete
     * sets. Continuous sets can be defined using a mathematical expression for
     * the maximum value. Discrete sets can contain any number of numeric values
     * separated by commas.
     *
     * @param ranges The string containing the sets of values to process.
     * @return A matrix of numeric values representing the processed sets.
     *
     * Example: Input: "(1, 10); {2, 4, 6, 8}; (0, 2+3)" Output: [[1.0, 10.0],
     * [2.0, 4.0, 6.0, 8.0], [0.0, 5.0]]
     */
    public static double[][] createRanges(String ranges) {
        // Eliminar espacios en blanco de la cadena de entrada
        ranges = ranges.replaceAll("\\s", "");
        if (ranges.contains("ran")) {
            ranges = extendRanges(ranges);
        }

        String[] sets = ranges.split(";");
        List<Boolean> continuo = new ArrayList<>();
        double[][] arreglo = Arrays.stream(sets)
                .map(conjunto -> {

                    if (conjunto.startsWith("(")) {
                        // Procesar el conjunto continuo
                        String[] rango = conjunto.substring(1, conjunto.length() - 1).split(",");
                        double min = Double.parseDouble(rango[0]);
                        double max;
                        if (rango[1].matches(".*[-+*/^().]+.*")) {
                            Expression exp = new Expression(rango[1]);
                            max = exp.calculate();
                        } else {
                            max = Double.parseDouble(rango[1]);
                        }
                        continuo.add(true);
                        return new double[]{min, max};
                    } else if (conjunto.startsWith("{")) {
                        // Procesar el conjunto discreto
                        String[] valores = conjunto.substring(1, conjunto.length() - 1).split(",");
                        double[] valoresD = Arrays.stream(valores)
                                .mapToDouble(Double::parseDouble)
                                .toArray();
                        continuo.add(false);
                        return valoresD;
                    } else {
                        // Procesar el conjunto continuo con una expresión
                        String[] rango = conjunto.substring(1, conjunto.length() - 1).split(",");
                        double min = Double.parseDouble(rango[0]);
                        Expression exp = new Expression(rango[1]);
                        double max = exp.calculate();
                        continuo.add(true);
                        return new double[]{min, max};
                    }
                })
                .toArray(double[][]::new);

        CONTINUOUS = new boolean[continuo.size()];
        for (int i = 0; i < continuo.size(); i++) {
            CONTINUOUS[i] = continuo.get(i);
        }
        return arreglo;

    }

    /**
     * Generates a string with the values of each variable defined by the input
     * string.
     *
     * @param ranges The input string that contains variables defined in the
     * format "ran[start-end:(range)]". Only works for continuous ranges.
     * @return A string containing the values of each variable defined in the
     * input string.
     *
     * Example: Input: "ran[1-5:(x${1})]; ran[10-15:(y${1}+1)]" Output:
     * "x1;x2;x3;x4;x5;y10+1;y11+1;y12+1;y13+1;y14+1;y15+1"
     */
    public static String extendRanges(String ranges) {

        // Definir expresión regular para analizar la cadena de entrada
        String patron = "ran\\[(\\d+)-(\\d+):(.*?)]";
        // Generar una cadena con los valores de cada variable
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(ranges);

        StringBuilder result = new StringBuilder();
        AtomicReference<Integer> endFin = new AtomicReference(0);
        matcher.results()
                .forEach(matchResult -> {
                    int start = Integer.parseInt(matchResult.group(1));
                    int end = Integer.parseInt(matchResult.group(2));
                    String rango = matchResult.group(3);
                    result.append(ranges, endFin.get(), matchResult.start())
                            .append(IntStream.rangeClosed(start, end)
                                    .mapToObj(i -> rango.replaceAll("\\$\\{" + i + "}", Integer.toString(i))
                                    .replaceAll("\\{(.*?)\\}", "$1"))
                                    .collect(Collectors.joining(";")));
                    endFin.set(matchResult.end());
                });
        result.append(ranges.substring(endFin.get()));
        return result.toString();
    }

}
