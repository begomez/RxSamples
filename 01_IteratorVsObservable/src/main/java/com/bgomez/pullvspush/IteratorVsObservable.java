/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.pullvspush;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;


/**
 * Sample program demonstrating behavior differences between pulling and pushing
 * 
 * @author bernatgomez
 */
public class IteratorVsObservable {
    
    public static void main(String[] args) {
        pullingWithIterator();
        pushingWithObservable();
        pushingWithObservableAddingNotifications();
    }
    
    /**
     * Pulling data using iterators. Data has to be "extracted" explicitly from source
     */
    private static void pullingWithIterator() {
        System.out.println("-------------------------------------------------");
        System.out.println("pullingWithIterator()");
        System.out.println("-------------------------------------------------");
        
        List<String> colors = Arrays.asList("Red", "Green", "Blue");
        
        Iterator<String> it = colors.iterator();
        
        while (it.hasNext()) {
            
            //XXX: pull the data from the source
            System.out.println("Element is: " + it.next());
        } 
    }
    
    /**
     * Pushing data using observables. Data is sent to "interested" objects
     */
    private static void pushingWithObservable() {
        System.out.println("-------------------------------------------------");
        System.out.println("pushingWithObservable()");
        System.out.println("-------------------------------------------------");

        List<String> colors = Arrays.asList("Cyan", "Magenta");
    
        //XXX: create observable that sends values SYNChronously from the data (list) to the observers
        Observable<String> obs = Observable.from(colors);
        
        //XXX: state that we want to receive notifications from this observable
        obs.subscribe(new Action1<String>() {
            
            //XXX: onNext()
            @Override
            public void call(String element) {
                System.out.println("call() received element: " + element);
            }
        });
    }

    /**
     * Pushing data using observables. Data is sent to "interested" objects
     */
    private static void pushingWithObservableAddingNotifications() {
        System.out.println("-------------------------------------------------");
        System.out.println("pushingWithObservableAddingNotifications()");
        System.out.println("-------------------------------------------------");

        List<String> strNums = Arrays.asList("One", "Two", "Three", "Four", "Five");
        
        Observable obs = Observable.from(strNums);
        
        obs.subscribe(
                
            //XXX: onNext()
            new Action1<String>() {
                @Override
                public void call(String element) {
                    System.out.println("On next(): " + element);
                }
            }, 
                
            //XXX: onError()
            new Action1<Throwable>() {
                @Override
                public void call(Throwable t1) {
                    System.out.println("On error(): " + t1.toString());
                }
            }, 
            
            //XXX: onCompleted()
            new Action0() {
                @Override
                public void call() {
                    System.out.println("On completed!");
                }
            }
        );
    }
}
