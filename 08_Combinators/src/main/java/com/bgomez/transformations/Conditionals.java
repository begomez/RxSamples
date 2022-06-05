/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.transformations;

import static com.bgomez.transformations.Utils.sleep;
import rx.Observable;
import java.util.Arrays;
import java.util.List;
import static com.bgomez.transformations.Utils.subscriptionWithLogs;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Usage samples for RXJava conditional operators
 * 
 * @author bernatgomez
 */
public class Conditionals {
    
    public static void main(String[] args) {    
        //amb();
        //randomAmb();
        //takeUntil();
        //takeWhile();
        //skipUntil();
        //skipWhile();
        //defaultIfEmpty();
        
        skipWhile();
        sleep(20000L);
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Emit default value when no values are emitted
     */
    private static void defaultIfEmpty() {
        Observable<Object> nums = 
            Observable.empty().defaultIfEmpty(5);
        
        subscriptionWithLogs(nums, "defaultIfEmpty()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Emit items from observable source until another source starts emitting,
     * then complete
     */
    private static void takeUntil() {
        Observable<String> words = 
            Observable.just("one", "way", "or", "another", "i", "will", "do", "it")
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a,b) -> a);
        
        Observable<Long> interval = 
            Observable.interval(500L, TimeUnit.MILLISECONDS);
        
        subscriptionWithLogs(words.takeUntil(interval), "takeUntil()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Emit items from observable source while condition is true,
     * then complete
     */
    private static void takeWhile() {
        Observable<String> words = 
            Observable.just("one", "way", "or", "another", "i", "will", "do", "it");
          
        subscriptionWithLogs(words.takeWhile(str -> str.length() >= 2), "takeWhile()");    
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Skip items from observable source until another source starts emitting,
     * then emit these items and complete
     */
    private static void skipUntil() {
        Observable<String> chars = 
            Observable.just("A", "B", "C", "D", "E", "F", "G", "H", "I").zipWith(
                Observable.interval(2000L, TimeUnit.MILLISECONDS), (str, num) -> str);   
        
        Observable<Long> nums = 
            Observable.interval(100L, TimeUnit.MILLISECONDS).delay(5000L, TimeUnit.MILLISECONDS);
        
        subscriptionWithLogs(chars.skipUntil(nums), "skipUntil()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Skip items from observable source while condition is true,
     * then emit remaining and complete
     */
    private static void skipWhile() {
        Observable<Integer> nums = 
            Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
         
        subscriptionWithLogs(nums.skipWhile(num -> num < 5), "skipWhile()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Emit items from the 1st observable passed as parameter that emits items.
     */    
    private static void randomAmb() {
        
        //XXX: emits with random delay
        Observable<String> source1 = 
            Observable.just("data from source 1")
                .delay(new Random().nextInt(1000), TimeUnit.MILLISECONDS);

        //XXX: emits with random delay too
        Observable<String> source2 = 
            Observable.just("data from source 2")
                .delay(new Random().nextInt(1000), TimeUnit.MILLISECONDS);

        subscriptionWithLogs(Observable.amb(source1, source2), "randomAmb()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Emit items from the 1st observable passed as parameter that emits items
     */
    private static void amb() {
        
        //XXX: emits immmediately
        Observable<String> words = 
            Observable.just("A", "B", "C");
        
        //XXX: emits with delay
        Observable<Long> interval = 
            Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(3);
        
        subscriptionWithLogs(Observable.amb(words, interval), "amb()");
    }
}