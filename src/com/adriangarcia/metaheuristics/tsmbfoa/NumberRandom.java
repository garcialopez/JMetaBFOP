package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.Random;

/**
 * This class is used to generate pseudo-random numbers from the Java Random 
 * class. Two instances of the Random class are realized in the class, 
 * the first uses a 48-bit seed by default and the second one uses 
 * a customized seed with up to 64 bits as the initial value.
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 * @see Random
 */

public class NumberRandom {
    // Private variable of the Random class for generating random numbers
    private Random random;
    
    
    /**     
     * Creates a new random number generator, sets the seed with the current 
     * time in milliseconds, is returned by method:
     * {@code currentTimeMillis()}<br>
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code NumberRandom nbr = new NumberRandom();} </pre>
     */
    public NumberRandom() {
        // The Random class instance is created
        this.random = new Random(System.currentTimeMillis());
    }
    
    /**
     * Creates a new pseudo-random number generator, 
     * using a single seed: {@code long}.
     * 
     * @param seed initial seed 
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code NumberRandom nbr = new NumberRandom(seed);}</pre>
     * 
     * @see #setRandom(long)
     *      
     */
    public NumberRandom(long seed) {               
        this.random = new Random(seed);
    }    
    
    /**
     * Adjusts the seed of the pseudo-random number generator, 
     * using a single seed: {@code long}.
     * 
     * @param seed la semilla inicial 
     * 
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code nbr.setRandom(seed);}</pre>
     */
    public void setRandom(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Returns a {@code double} uniformly distributed pseudo-random,
     * between 0.0 and 1.0
     * 
     * @return value {@code double}
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code double random =  nbr.getRandomUnif();}</pre>
     */
    public double getRandomUnif() {
        return this.random.nextDouble();
    }
    
    /**
     * Returns a {@code double} uniformly distributed 
     * pseudo-random with specified range.
     * 
     * @param min value {@code double} minimum specific range
     * @param max value {@code double} maximum specific range
     * @return value {@code double} between specified range
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code double random =  nbr.getRandomRankUnif(min, max);}</pre>
     */
    public double getRandomRankUnif(double min, double max) {
        return min + (this.random.nextDouble() * (max - min));
    }
    
    /**
     * Returns a {@code int} uniformly distributed 
     * pseudo-random with specified range.
     * 
     * @param min value {@code int} minimum specific range
     * @param max value {@code int} maximum specific range
     * @return número {@code int} between specified range
     * 
     * <p>The invocation is as follows:</p><br>
     * <pre>{@code int random =  nbr.getRandomRankUnif(min, max);}</pre>
     */
    public int getRandomRankUnif(int min, int max) {
        return min+this.random.nextInt((max+1) - min);
    }
    
}
