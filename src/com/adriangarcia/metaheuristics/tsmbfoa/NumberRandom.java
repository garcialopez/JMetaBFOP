package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.Random;

/**
 * Una instancia de esta clase se usa para generar números pseudoaleatorios 
 * a partir de la clase Random de Java. Dentro de la clase se realizan dos 
 * instancias de la clase Random, una utilizando una semilla de 48 bits 
 * por default y otra estableciendo una semilla personalizada llegando 
 * a utilizar hasta 64 bits como valor inicial.
 * 
 * @author <b>José Adrian García López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">TS-MBFOA on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.2
 */
public class NumberRandom {
    // Variable privada de la clase Random para generar números aleatorios
    private Random random;
    
    
    /**     
     * Crea un nuevo generador de números aleatorios estableciendo la semilla 
     * con la hora actual en milisegundos devuelto por el método 
     * {@code currentTimeMillis()}
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code NumberRandom nbr = new NumberRandom();} </pre>
     */
    public NumberRandom() {
        // Se crea la instancia de la clase Random
        this.random = new Random(System.currentTimeMillis());
    }
    
    /**
     * Crea un nuevo generador de números pseudoaleatorio
     * usando una sola semilla {@code long}.
     * 
     * @param seed la semilla inicial 
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code NumberRandom nbr = new NumberRandom(seed);}</pre>
     * 
     * @see #setRandom(long)
     *      
     */
    public NumberRandom(long seed) {               
        this.random = new Random(seed);
    }    
    
    /**
     * Ajusta la semilla de este generador de números pseudoaleatorio 
     * usando una sola semilla {@code long}.
     * 
     * @param seed la semilla inicial 
     * 
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code nbr.setRandom(seed);}</pre>
     */
    public void setRandom(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Devuelve un valor {@code double} pseudoaleatorio distribuido uniformemente 
     * entre 0.0 y 1.0
     * 
     * @return valor {@code double}
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code double random =  nbr.getRandomUnif();}</pre>
     */
    public double getRandomUnif() {
        return this.random.nextDouble();
    }
    
    /**
     * Genera un número {@code double} pseudoaleatorio con rango específico
     * distribuido uniformemente.
     * 
     * @param min valor {@code double} minimo del rango específico
     * @param max valor {@code double} máximo del rango específico
     * @return un valor {@code double} entre el rango especificado
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code double random =  nbr.getRandomRankUnif(min, max);}</pre>
     */
    public double getRandomRankUnif(double min, double max) {
        return min + (this.random.nextDouble() * (max - min));
    }
    
    /**
     * Genera un número {@code double} pseudoaleatorio entero con rango 
     * específico distribuido uniformemente.
     * 
     * @param min valor {@code int} minimo del rango específico
     * @param max valor {@code int} máximo del rango específico
     * @return número {@code int} entre el rango especificado
     * 
     * <p>La invocación es la siguiente:</p>
     * <pre>{@code int random =  nbr.getRandomRankUnif(min, max);}</pre>
     */
    public int getRandomRankUnif(int min, int max) {
        return min+this.random.nextInt((max+1) - min);
    }
    
}
