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
import java.util.concurrent.TimeUnit;


/**
 * Usage samples for RXJava combination operators
 * 
 * @author bernatgomez
 */
public class Combinators {
    
    public static void main(String[] args) {    
        //zip();
        //timedZip();
        //zipWith();
        //combineLatest();
        //combineLatestGreetingsExample();
        //merge();
        //concat();
        //startWith();
        
        startWith();
        sleep(15000L);
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Combine elements emitted by each one of the observable inputs.
     * Combination is done through function passed as parameter.
     */
    protected static void zip() {
        Observable<Integer> nums = 
            Observable.just(1, 3, 4);
        
        Observable<Integer> moreNums = 
            Observable.just(5, 2, 6);

        Observable multiplied = 
            Observable.zip(
                nums, 
                moreNums, 
                (a, b) -> a * b);

        Observable added = 
            Observable.zip(
                nums, 
                moreNums, 
                (a, b) -> a + b);

        subscriptionWithLogs(added, "zip(add)");

        subscriptionWithLogs(multiplied, "zip(mult)");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Combine elements emitted by each one of the observable inputs.
     * Combination is done through function passed as parameter.
     * Output speed of resulting observable is variable.
     */
    private static void timedZip() {
        
        //XXX: output emission speed is determined by input emission speed
        
        Observable<String> timedZip = 
            Observable.zip(
                Observable.from(Arrays.asList("Z", "I", "P", "P")),
                Observable.interval(2L, TimeUnit.SECONDS),
                (str, num) -> str
            );
        
        subscriptionWithLogs(timedZip, "timedZip()");
    }
    
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Combine elements emitted by each one of the observable inputs.
     * Combination is done through function passed as parameter.
     * Use instance method Observable.zipWith(obser, fun) instead of static method zip(obser, obser, fun)
     */
    private static void zipWith() {
        Observable<Long> o = 
            Observable
                .from(Arrays.asList("Z", "I", "P", "P"))
                .zipWith(
                    Observable.interval(1L, TimeUnit.SECONDS), 
                    (str, num) -> num);
        
        subscriptionWithLogs(o, "zipWith()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Combine latest elements emitted by each one of observable inputs.
     * Number of resulting emissions may vary
     */
    private static void combineLatest() {
        Observable<Integer> combined = 
            Observable.combineLatest(
                Observable.just(1, 3),
                Observable.just(5, 2, 4, 6),
                (a, b) -> a - b);
        
        subscriptionWithLogs(combined, "combineLatest()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Combine latest elements emitted by each one of observable inputs.
     * Number of resulting emissions may vary
     */
    private static void combineLatestGreetingsExample() {
        Observable<String> greetings = 
            Observable.just("Hello", "Hi", "Howdy")
                .zipWith(
                    Observable.interval(1L, TimeUnit.SECONDS), 
                    Combinators::onlyFirstArg
            );
          
        Observable<String> names = 
            Observable.just("Meddle", "Tanya", "Dali")
                .zipWith(
                    Observable.interval(1500L, TimeUnit.MILLISECONDS),
                    Combinators::onlyFirstArg
            );
        
        Observable<String> puncts = 
            Observable.just(".", "?", "!")
                .zipWith(
                    Observable.interval(2000L, TimeUnit.MILLISECONDS), 
                    Combinators::onlyFirstArg
            );

        Observable<String> combined = 
            Observable.combineLatest(
                greetings, 
                names, 
                puncts, 
                (g, n, p) -> g + " " + n + " " + p);
    
        subscriptionWithLogs(combined, "combineLatestGreetings()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Merge several input sources into a single stream interleaving source items.
     * Handles several subscriptions.
     */
    private static void merge() {
        Observable<String> greetings = 
            Observable.just("Hello", "Hi", "Howdy")
                .zipWith(
                    Observable.interval(1L, TimeUnit.SECONDS), 
                    Combinators::onlyFirstArg
            );
          
        Observable<String> names = 
            Observable.just("Meddle", "Tanya", "Dali")
                .zipWith(
                    Observable.interval(1500L, TimeUnit.MILLISECONDS),
                    Combinators::onlyFirstArg
            );
        
        Observable<String> puncts = 
            Observable.just(".", "?", "!")
                .zipWith(
                    Observable.interval(2000L, TimeUnit.MILLISECONDS), 
                    Combinators::onlyFirstArg
            );

        Observable<String> merged = 
            Observable.merge(
                greetings, 
                names, 
                puncts);
    
        subscriptionWithLogs(merged, "mergeLatestGreetings()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Transform several input sources into a single stream by concatenating emitted items.
     * Handles only one subscription at any time.
     */
    private static void concat() {
        Observable<String> greetings = 
            Observable.just("Hello", "Hi", "Howdy")
                .zipWith(
                    Observable.interval(1L, TimeUnit.SECONDS), 
                    Combinators::onlyFirstArg
            );
          
        Observable<String> names = 
            Observable.just("Meddle", "Tanya", "Dali")
                .zipWith(
                    Observable.interval(1500L, TimeUnit.MILLISECONDS),
                    Combinators::onlyFirstArg
            );
        
        Observable<String> puncts = 
            Observable.just(".", "?", "!")
                .zipWith(
                    Observable.interval(2000L, TimeUnit.MILLISECONDS), 
                    Combinators::onlyFirstArg
            );
        
        Observable<String> concatted = 
            Observable.concat(greetings, names, puncts);
        
        subscriptionWithLogs(concatted, "concat()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Operator that prepends items to an observable instance.
     */
    private static void startWith() {
        Observable<String> greetings = 
            Observable.just("Hello", "Hi", "Howdy")
                .zipWith(
                    Observable.interval(1L, TimeUnit.SECONDS), 
                    Combinators::onlyFirstArg
            );
          
        Observable<String> names = 
            Observable.just("Meddle", "Tanya", "Dali")
                .zipWith(
                    Observable.interval(1500L, TimeUnit.MILLISECONDS),
                    Combinators::onlyFirstArg
            );
        
        Observable<String> puncts = 
            Observable.just(".", "?", "!")
                .zipWith(
                    Observable.interval(2000L, TimeUnit.MILLISECONDS), 
                    Combinators::onlyFirstArg
            );
        
        Observable<String> result = 
            puncts
                .startWith(names)
                    .startWith(greetings);
        
        subscriptionWithLogs(result, "startWith()");
    }
    
////////////////////////////////////////////////////////////////////////////////
// Helpers
////////////////////////////////////////////////////////////////////////////////

    private static<F, S> F onlyFirstArg(F first, S second) {
        return first;
    }
}