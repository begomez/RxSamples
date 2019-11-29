/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.functions;


import java.text.SimpleDateFormat;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Sample program with pure and high order functions
 * @author bernatgomez
 */
public class Functions {
    
    public static void main(String[] args) {
        pureFunctions();
        impureFunctions();
        highOrderFunctions();
        moreHighOrderFunctions();
    }
    
    private static void pureFunctions() {
        System.out.println("-------------------------------------------------");
        System.out.println("pureFunctions()");
        System.out.println("-------------------------------------------------");

        // interface with string param, no return
        Consumer<String> printer = (msg) -> System.out.println(msg);
        
        // interface with integer param, returns BOOLEAN
        Predicate<Integer> isEven = (number) -> number % 2 == 0;
        
        int i = 5;
        
        while (i > 0) {
            i--;
            printer.accept("Is 4 even? " + isEven.test(4));//XXX: is pure because its output is idempotent and has no side effects
        }
    }
    
    private static void impureFunctions() {        
        System.out.println("-------------------------------------------------");
        System.out.println("impureFunctions()");
        System.out.println("-------------------------------------------------");

        Consumer<String> printer = (msg) -> {
            //XXX: printing is a side effect, so function is not pure
            System.out.println("(side effect) The time is " + new SimpleDateFormat().format(System.currentTimeMillis()));
            
            System.out.println(msg);
        };
        
        Predicate<Integer> isEven = (num) -> num % 2 == 0; 
        
        // interface with string param, returns integer
        Function<String, Integer> mapper = (str) -> Integer.parseInt(str); 
        
        String num = "4";
        
        printer.accept("Is " + num + " even? " + isEven.test(mapper.apply(num)));
    }

    private static void highOrderFunctions() {
        System.out.println("-------------------------------------------------");
        System.out.println("highOrderFunctions()");
        System.out.println("-------------------------------------------------");

        Consumer<Double> printer = (num) -> System.out.println("Total: " + num);
        Function<Double, Double> f1 = (num) -> Math.pow(num, 2);
        Function<Double, Double> f2 = (num) -> Math.sqrt(num);
        double data1 = 3.0;
        double data2 = 2.0;
        printer.accept(highSum(f1, f2, data1, data2));
    }
    
    //XXX: takes functions as params so it is a high order func
    private static<T, R> double highSum(Function<T, Double> f1, Function<R, Double> f2, T data1, R data2) {
        return f1.apply(data1) + f2.apply(data2);
    }
    
    private static void moreHighOrderFunctions() {
        System.out.println("-------------------------------------------------");
        System.out.println("moreHighOrderFunctions()");
        System.out.println("-------------------------------------------------");
        
        Consumer<String> printer = msg -> System.out.println(msg);
        
        printer.accept(greet("Bon dia ").apply("Maria"));
    }
    
    //XXX: returns a FUNCTION so it is a high order func too
    public static Function<String, String> greet(String greeting) {
        return (String name) -> greeting + " " + name + "!";
    }
    
    //XXX: Function.apply()
    //XXX: Predicate.test()
    //XXX: Consumer.accept()
}

