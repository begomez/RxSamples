/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.factories;


import java.util.concurrent.TimeUnit;
import rx.Observable;
import static com.bgomez.factories.Utils.subscriptionWithLogs;

/**
 * Sample program that creates observables using different API functions

 * @author bernatgomez
 */
public class MoreFactories {
    
    public static void main(String[] args) {    
        interval();
        timer();
        error();
        empty();
        never();
        range();

        try {
            Thread.sleep(10000);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //XXX: emits consecutive numbers, runs on comput thread
    private static void interval() {
        System.out.println("-------------------------------------------------");
        System.out.println("interval()");
        System.out.println("-------------------------------------------------");
        
        //XXX: emit value according to given freq
        Observable obs = Observable.interval(500L, TimeUnit.MILLISECONDS);
        
        subscriptionWithLogs(obs, "Interval obs");
    }
    
    //XXX: runs on comput thread
    private static void timer() {
        System.out.println("-------------------------------------------------");
        System.out.println("timer()");
        System.out.println("-------------------------------------------------");
        
        //XXX: wait, then emit values according to freq 
        long wait = 1l;
        long freq = 100L;
        Observable ob = Observable.timer(wait, freq, TimeUnit.MILLISECONDS);
    
        subscriptionWithLogs(ob, "Timer obs");
        
        //XXX: emit a 0 value after given time
        Observable obs = Observable.timer(wait, TimeUnit.SECONDS);
    
        subscriptionWithLogs(obs, "TIMER obs");
    }
    
    //XXX: launches error
    private static void error() {
        System.out.println("-------------------------------------------------");
        System.out.println("error()");
        System.out.println("-------------------------------------------------");
        
        //XXX: emits onError()
        Observable obs = Observable.error(new Exception("Test error"));
        
        subscriptionWithLogs(obs, "Error obs");
    }
    
    //XXX: completes immmediately
    private static void empty() {
        System.out.println("-------------------------------------------------");
        System.out.println("empty()");
        System.out.println("-------------------------------------------------");

        //XXX: emit nothing but execute onCompleted()
        Observable obs = Observable.empty();
        
        subscriptionWithLogs(obs, "Empty obs");
    }
    
    //XXX: does nothing
    private static void never() {
        System.out.println("-------------------------------------------------");
        System.out.println("never()");
        System.out.println("-------------------------------------------------");

        //XXX: do nothing
        Observable obs = Observable.never();
        
        subscriptionWithLogs(obs, "Never obs");
    }
    
    private static void range() {
        System.out.println("-------------------------------------------------");
        System.out.println("range()");
        System.out.println("-------------------------------------------------");

        Observable obs = Observable.range(1, 10);
        
        subscriptionWithLogs(obs, "Range obs");
    }
}