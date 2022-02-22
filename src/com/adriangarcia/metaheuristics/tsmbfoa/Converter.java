/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adriangarcia.metaheuristics.tsmbfoa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author José Adrian
 */
public class Converter {
    private static final String SUMMATION = "sum";
    private static final String PRODUCER = "prod";    
    private int indexStart = 0, indexFinal = 0, i = 0;
    
    public Converter() {
        
    }

        
    
    // String funObjetivo = "5 * sum{x,1,4,xi} - 5 * sum{x,1,4,xi^2} - sum{x,5,13,xi}";      
    
    public String extendFunction(String function){        
                
        function = function.replaceAll("\\{", "#").replaceAll("\\}", "%");
        this.i = 0;
        
        String aux1 = "", type = "";
        String auxFunction = "";
        boolean flagSUMMATION = function.contains(Converter.SUMMATION);
        boolean flagPRODUCER = function.contains(Converter.PRODUCER);
        boolean flag = true;
        
        
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
        //System.out.println(function);       
        return function;
    }
    
    public String[] extendVariables(String varOrder){
        
        ArrayList<String> variable = new ArrayList();
        int to, from, j; 
        String[] array = null;
        
        if (varOrder.contains("iter")) {                            
            try {
                String[] iter =  varOrder.split(";");                

                for (int k = 0; k < iter.length; k++) {

                    iter[k] = iter[k].replaceAll("[\\{\\}]", "").replaceAll("iter", "");               
                    array = iter[k].split(",");
                    from = Integer.parseInt(array[1]);
                    to = Integer.parseInt(array[2]);

                    j = 0;
                    for (int i = from ; i <= to; i++) {                           
                        variable.add(array[0].concat(String.valueOf(i)));
                        j++;
                    }        
                }                        
                array = Arrays.stream(variable.toArray()).toArray(String[]::new);                                

            } catch (NumberFormatException e) {
                System.out.println("Error al insertar variables iteradas.");
            }
        
        }else{
            array = varOrder.split(","); 
        }     
        //System.out.println(Arrays.toString(array));
        return array;
    }
    
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
    
    public String[] extendConstraintsValuate(String constraintsValuate){
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
