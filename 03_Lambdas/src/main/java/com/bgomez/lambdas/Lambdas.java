/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.lambdas;


import static com.bgomez.lambdas.Utils.doMapping;
import static com.bgomez.lambdas.Utils.dump;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Sample program that uses lambdas and high order functions
 * 
 * @author bernatgomez
 */
public class Lambdas {
    
    public static void main(String[] args) {
        oop();
        functional();
        functionalSavingLambdaInVar();
        functionalUsingLambdaDeclaringParamTypes();
        functionalUsingLambdaWithReturn();
    }    
    
    private static void oop() {
        System.out.println("-------------------------------------------------");
        System.out.println("oop()");
        System.out.println("-------------------------------------------------");
        
        List<Double> nums = Arrays.asList(1d, 2d, 3d);
        
        //XXX: we have to pass objects but in fact we only need functions....
        Mapper<Double, String> mapper = new Mapper<Double, String>() {
            @Override
            public String map(Double value) {
                return Double.toString(value);
            }
        }; 
        
        List<String> mapped = doMapping(nums, mapper);
        
        dump(mapped);
    }

    private static void functional() {
        System.out.println("-------------------------------------------------");
        System.out.println("functional()");
        System.out.println("-------------------------------------------------");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        
        List<Integer> squareNums = 
            doMapping(
                nums, 
                //XXX: pass a lambda instead of an instance of the marking interface
                value -> value * value);
        
        dump(squareNums);
    }

    private static void functionalSavingLambdaInVar() {
        System.out.println("-------------------------------------------------");
        System.out.println("functionalSavingLambdaInVar()");
        System.out.println("-------------------------------------------------");

        List numbers = Arrays.asList(100d, 200d, 300d, 400d, 500d);
        
        Mapper<Double, String> mapper = 
            //XXX: lambda enclosing params inside parenthesis
            (value) -> Double.toString(value);
             
        List<String> mapped = doMapping(numbers, mapper);
        
        dump(mapped);
    }

    private static void functionalUsingLambdaDeclaringParamTypes() {
        System.out.println("-------------------------------------------------");
        System.out.println("functionalUsingLambdaDeclaringParamTypes()");
        System.out.println("-------------------------------------------------");

        List<Integer> numbers = Arrays.asList(10, 20, 30);
        
        Mapper<Integer, Integer> mapper = 
            //XXX: lambda declaring data types for its params
            (Integer value) -> value / 10;
        
        List<Integer> mapped = doMapping(numbers, mapper);
        
        dump(mapped);
    }

    private static void functionalUsingLambdaWithReturn() {
        System.out.println("-------------------------------------------------");
        System.out.println("functionalUsingLambdaWithReturn()");
        System.out.println("-------------------------------------------------");

        List<Float> numbers = Arrays.asList(11.5f, 12.5f, 13.5f);
            
        Mapper<Float, Integer> mapper = value -> {
            System.out.println("Side effect when calculating...");//XXX: printing here is a side effect...
            return Math.round(value);
        };
        
        List<Integer> mapped = doMapping(numbers, mapper);
        
        dump(mapped);        
    }
}

/**
 * Marking interface to perform data type transformations. Can be substituted by a lambda
 * 
 * @author bernatgomez
 * @param <V> Source type
 * @param <M> Target type
 */
interface Mapper<V, M> {
    M map (V value);
}
