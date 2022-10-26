
package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * To optimize a CNOP it is necessary to make constant evaluations of 
 * its mathematical expressions.  However, they are never the same or 
 * of the same type. For them this class is in charge of preparing 
 * the CNOP input so that the expression evaluator is in charge 
 * of evaluating and substituting the exact values. 
 * 
 * @author <b>Adrian García-López</b><br>
 *         <a href="https://github.com/garcialopez" target="_blank">JMetaBFOP on GitHub</a><br>
 *         <a href="mailto:joseadrian_g97@hotmail.com">joseadrian_g97@hotmail.com</a><br>
 * @version 1.0
 */

public class Converter {
    
    private static final String SUMMATION = "sum";
    private static final String PRODUCER = "prod";    
    private int indexStart = 0, indexFinal = 0, i = 0;
    
    public Converter() {
        
    }

    /**
     * This method takes care of obtaining a summarized mathematical
     * expression and extends it, very common in the case of 
     * summations and products.Extends a function introduced by string, e.g:
     * <br><br>
     * 
     * if the entry expression is <br>
     * 5 * sum{x,1,4,xi} - prod{x,1,4,(xi+i)} <br><br>
     * 
     * conversion will return <br>
     * 5 * (x1+x2+x3+x4) - ((x1+1)*(x2+2)*(x3+3)*(x4+4))<br><br>
     * 
     * the purpose is that mXparse is in charge of evaluating and substituting 
     * directly the values for each variable. <br>allowed syntax:<br> <br>
     * 
     * summation: sum{var, from, to, f(x1,x2,...,xn)} <br>
     * Product: prod{var, from, to, f(x1,x2,...,xn)}
     * 
     * @param function a summarized mathematical expression
     * @return an extended mathematical expression
     */
    public String extendFunction(String function){        
                
        function = function.replaceAll("\\{", "#").replaceAll("\\}", "%");
        this.i = 0;
        
        String aux1 = "", type = "";
        String auxFunction = "";
        boolean flagSUMMATION = function.contains(Converter.SUMMATION);
        boolean flagPRODUCER = function.contains(Converter.PRODUCER);
        boolean flag = true;
        
//        Pattern pat = Pattern.compile(".*sum.*|.*prod.*");
//        Matcher mat = pat.matcher(function);                                                                           
       
        while (flagSUMMATION || flagPRODUCER){
       
            
            if (flagSUMMATION){ 
                type = "sum";
                flag = false;                
            }else if (flagPRODUCER && flag)
                type = "prod";                            
                
            this.indexStart = function.indexOf(type + "#");
            this.indexFinal = function.indexOf("%");
            
            if (this.indexStart >= this.indexFinal) {
                if (flag) {
                    type = "sum";
                }else type = "prod";
                this.indexStart = function.indexOf(type + "#");
            }                                   
            
            aux1 = function.substring(indexStart, indexFinal+1); 
            function = function.replace(aux1, "|"+ this.i +"|");
                                   
            aux1 = aux1.replaceAll(type + "#", "").replaceAll("%", "");
            
            String[] aux = aux1.split(",");
            auxFunction = "";                        
            
            for (int k = Integer.parseInt(aux[1]); k <= Integer.parseInt(aux[2]); k++) {
                auxFunction += aux[3].replaceAll(aux[0].concat("i"), aux[0] + k).replaceAll("i", String.valueOf(k));
                if (k < Integer.parseInt(aux[2])) {                    
                    auxFunction += (type.equals("sum"))?"+":"*";
                }                              
            }
            
            auxFunction = "("+ auxFunction +")";
            
            function = function.replace("|" +i+"|", auxFunction);
            
            this.i++;
            flag = true;
            flagSUMMATION = function.contains(Converter.SUMMATION);
            flagPRODUCER = function.contains(Converter.PRODUCER);
        }                        
        return function;
    }
    
    /**
     * This method is in charge of punctuating the number of variable 
     * that the CNOP contains, to give it a value at the moment of evaluation.
     * If the input of variables is extensive, iteration can be used, e.g:<br><br>
     * 
     * if the variables input is:<br>
     * p1; p2; iter{x,1,3}; iter{y,1,4}; p1; p2<br><br>
     * 
     * conversion will return <br>
     * [p1,  p2,  x1,  x2,  x3,  y1,  y2,  y3,  y4,  p1,  p2]<br><br>
     * 
     * all elements inside the iteration are separated by commas (,) 
     * and outside the iteration are separated by semicolons (;).
     * 
     * @param varOrder variable expression
     * @return vector of FO variables
     */
    public String[] extendVariables(String varOrder){
        
        ArrayList<String> variable = new ArrayList();
        int to, from;//, j; 
        String[] array = null;
        
        if (varOrder.contains("iter")) {                            
            try {
                String[] iter =  varOrder.split(";");                
                //System.out.println(Arrays.toString(iter));
                for (int k = 0; k < iter.length; k++) {

                    if (iter[k].contains("iter")) {
                        iter[k] = iter[k].replaceAll("[\\{\\}]", "").replaceAll("iter", "");  
                        
                        array = iter[k].split(",");
                        from = Integer.parseInt(array[1]);
                        to = Integer.parseInt(array[2]);

                        //j = 0;
                        for (int i = from ; i <= to; i++) {                           
                            variable.add(array[0].concat(String.valueOf(i)));
                           // j++;
                        }
                    }else{
                        variable.add(iter[k]);
                    }
                }
                
                array = Arrays.stream(variable.toArray()).toArray(String[]::new);                                

            } catch (NumberFormatException e) {
                System.err.println("Error inserting iterated variables.");
                array = new String[]{};
            }
        
        }else{
            array = varOrder.split(","); 
        }     
        //System.out.println(Arrays.toString(array));
        return array;
    }
    
    /**
     * Sets the variable ranges. When there are many variables and a number 
     * of them have the same rank, the rank iteration can be used: 
     * for example if we have 10 variables and the first two are different 
     * ranks, the five consequent ones have the same value and the last 
     * three have a different rank, the following abbreviation can be used:<br><br>
     * 
     * (0,1.2);(0,1.8);ran{3:7,0:1.2};ran{8:10,0.25:2}<br><br>
     * 
     * conversion will return <br>
     * (0,1.2),(0,1.8),(0,1.2),(0,1.2),(0,1.2),(0,1.2),(0,1.2),(0.25,2),(0.25,2),(0.25,2)<br><br>
     * 
     * all elements within the iteration are separated by commas (,) 
     * and ranges by colons (:) and outside the iteration are 
     * separated by semicolons (;).
     * 
     * @param rankVar expression of the range of variables to iterate
     * @return Ranges for each variable
     */
    public String extendRankVariables(String rankVar){
        int flag = 0;
        
        if (rankVar.contains("ran")) {
            String[] rank = rankVar.split(";");
            String rankVarAux = "";
            
            for (int j = 0; j < rank.length; j++) {
                rankVarAux += (flag>0)?",":"";
                if (rank[j].contains("ran")) {                                        
                    
                    rank[j] = rank[j].replaceAll("[\\{\\}]", "").replaceAll("ran", "");
                    String[] array = rank[j].split(",");
                    
                    String[] aux = array[0].split(":");
                    int x = Integer.parseInt(aux[0])
                            , y = Integer.parseInt(aux[1]);
                    
                    aux = array[1].split(":");
                    for (int k = x; k <= y; k++) {
                        rankVarAux += ("(" + aux[0] + "," + aux[1] + ")");
                        
                        rankVarAux += (k<y)?",":"";
                        
                    }                                        
                    
                    flag++;
                }else{
                    rankVarAux += (rank[j]);
                   
                    flag++;
                }
                
            }
                        
            rankVar = rankVarAux;
        }
        //System.out.println(rankVar);
        return rankVar;
    }
    
    /**
     * Method that punctuates the constraints.
     * @param constraintsValuate constraints
     * @return constraints ordered
     */
    protected String[] extendConstraintsValuate(String constraintsValuate){
        String[] val = null;
        String args = constraintsValuate;
        if (!args.isEmpty()) {
            
            //val{g1,p,q,r,1:9,<}
            
            if (args.contains("val{")) {
                args = args.replaceAll("val", "").replaceAll("[\\{\\}]", "");
                args = args.substring(1);
                val = args.split(",");                
            }                                   
        } else {
            
        }        
        return val;
    }    
    
}
