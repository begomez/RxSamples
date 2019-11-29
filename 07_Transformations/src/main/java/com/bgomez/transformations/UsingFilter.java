/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.transformations;

import rx.Observable;
import java.util.Arrays;
import java.util.List;
import static com.bgomez.transformations.Utils.subscriptionWithLogs;


/**
 * Usage samples for RXJava filtering operators
 * 
 * @author bernatgomez
 */
public class UsingFilter {
    
    public static void main(String[] args) {    
        filterOp();
          
        takeOp();
        
        lastOp();
                
        skipOp();
        
        elementAtOp();
        
        distinctOp();
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Filter elements according to given predicate
     */
    protected static void filterOp() {
        Observable<Integer> nums = 
            Observable.just(0, 1, 1, 2, 3, 5, 8, 13, 21);
        
        Observable<Integer> obsEven = 
            nums.filter(n -> n % 2 == 0);
    
        subscriptionWithLogs(nums, "filter()");
        subscriptionWithLogs(obsEven, "filter()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    /**
     * Get subset of initial elements
     */
    protected static void takeOp() {
        take();
        takeLast();
        takeLastBuffer();
    }  
    
    private static void take() {
        final int NUM_DAYS = 3;
        
        List<String> weekDays = 
            Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        Observable<String> obsDays = 
            Observable.from(weekDays).take(NUM_DAYS);
        
        subscriptionWithLogs(obsDays, "take()");
    }
        
    private static void takeLast() {
        final int NUM_DAYS = 2;

        List<String> weekDays = 
            Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

        Observable<String> obsDays = 
            Observable.from(weekDays).takeLast(NUM_DAYS);

        subscriptionWithLogs(obsDays, "takeLast()");
    }

    private static void takeLastBuffer() {
        final int SUBSET = 3;
        
        Observable<Integer> nums = 
            Observable.just(0, 1, 1, 2, 3, 5, 8, 13, 21);
                    
        Observable<List<Integer>> obsNums =            
            nums.takeLastBuffer(SUBSET);//XXX: returns a single list obj
     
        subscriptionWithLogs(obsNums, "takeLastBuffer()");     
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    protected static void lastOp() {
        last();
        lastOrDefault();
    }
    
    private static void last() {
        List<String> weekendDays = 
            Arrays.asList("Saturday", "Sunday");

        Observable<String> obsDays = 
            Observable.from(weekendDays).last();
        
        subscriptionWithLogs(obsDays, "last()");
    }
    
    private static void lastOrDefault() {
        List<String> weekendDays = 
            Arrays.asList("Saturday", "Sunday");
        
        Observable<String> obsLastOrDef = 
            Observable.from(weekendDays).lastOrDefault("No day");

        subscriptionWithLogs(obsLastOrDef, "lastOrDefault()");
        
        Observable obsNoDays = 
            Observable.empty().lastOrDefault("No day");

        subscriptionWithLogs(obsNoDays, "lastOrDefault()");
        
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    protected static void skipOp() {
        skip();
        
        skipLast();
    }
    
    private static void skip() {
        final int NUM_ELEMENTS = 3;

        Observable<Integer> obsNums = 
            Observable.just(0, 1, 1, 2, 3, 5, 8, 13, 21);
        
        obsNums = obsNums.skip(NUM_ELEMENTS);
        
        subscriptionWithLogs(obsNums, "skip()");
    }
    
    private static void skipLast() {
        final int NUM_ELEMENTS = 5;

        Observable<Integer> obsNums = 
            Observable.just(0, 1, 1, 2, 3, 5, 8, 13, 21);
        
        obsNums = obsNums.skipLast(NUM_ELEMENTS);
        
        subscriptionWithLogs(obsNums, "skipLast()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    protected static void elementAtOp() {
        elementAt();
        elementAtOrDefault();
    }
    
    private static void elementAt() {
        List<Integer> nums = 
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Observable<Integer> obsNums = 
            Observable.from(nums);
        
        obsNums = obsNums.elementAt(5);
        
        subscriptionWithLogs(obsNums, "elementAt()");
    }

    private static void elementAtOrDefault() {
        List<Integer> nums = 
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Observable<Integer> obsNums = 
            Observable.from(nums)
                .elementAtOrDefault(25, 1000);

        subscriptionWithLogs(obsNums, "elementArOrDefault()");
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
    protected static void distinctOp() {
        distinct();
        distinctUntilChanged();
    }  

    private static void distinct() {
         List<String> weekDays = 
            Arrays.asList("Monday", "Tuesday", "Monday", "Thursday", "Monday", "Tuesday", "Sunday");

        Observable<String> obsDays = 
            Observable.from(weekDays).distinct();
         
        subscriptionWithLogs(obsDays, "distinct()");

    }
    
    private static void distinctUntilChanged() {
         List<String> weekDays = 
            Arrays.asList("Monday", "Tuesday", "Monday", "Monday", "Monday", "Tuesday", "Tuesday");//XXX: consecutive values will be emitted 1 time

         Observable<String> obsDays = 
            Observable.from(weekDays).distinctUntilChanged();

        subscriptionWithLogs(obsDays, "distinctUntilChanged()");
    }

}