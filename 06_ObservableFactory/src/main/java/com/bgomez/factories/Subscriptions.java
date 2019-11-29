/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.factories;


import static com.bgomez.factories.Utils.createObservable;
import java.util.Arrays;
import java.util.Iterator;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;


/**
 * Sample program using subscriptions
 * 
 * @author bernatgomez
 */
public class Subscriptions {
    
    public static void main(String[] args) {            
        Observable obs = createObservable(Arrays.asList("R", "X", "J", "a", "v", "a"));
    
        subscribeWithActions(obs);
        subscribeWithObserver(obs);
        subscribeWithSubscriber(obs);
    }
           
    private static void subscribeWithActions(Observable obs) {
        System.out.println("-------------------------------------------------");
        System.out.println("subscribeWithActions()");
        System.out.println("-------------------------------------------------");

        //XXX: only invoke call()
        Subscription s = obs.subscribe();
        
        //XXX: get emitted elements, ignore errors
        obs.subscribe(System.out::println);
        
        //XXX: get emitted elements, ignore errors
        obs.subscribe(
            new Action1() {
                @Override
                public void call(Object t1) {
                    System.out.println("(A1) Received " + t1.toString());
                }
            }
        );
        
        //XXX: deal with errors
        obs.subscribe(
            new Action1() {
                @Override
                public void call(Object t1) {
                    System.out.println("(A1, A1) Received " + t1.toString());
                }
            }, new Action1() {
                @Override
                public void call(Object t1) {
                    System.err.println("(A1, A1) Error " + t1);
                }
            }
        );
        
        //XXX: deal with errors and completions
        obs.subscribe(
            new Action1() {
                @Override
                public void call(Object t1) {
                    System.out.println("(A1, A1, A0) Received " + t1.toString());
                }
            },
            new Action1() {
                @Override
                public void call(Object t1) {
                    System.err.println("(A1, A1, A0) Error " + t1); //To change body of generated methods, choose Tools | Templates.
                }
            }, 
            new Action0() {
                @Override
                public void call() {
                    System.out.println("(A1, A1, A0) Done"); //To change body of generated methods, choose Tools | Templates.
                }
            }
        );
        
        //XXX: deal with errors and completions
        obs.subscribe(
            (v) -> System.out.println("(Lambda) received " + v),
            (e) -> System.err.println("(Lambda) " + e),
            () -> System.out.println("(Lambda) Done")
        );
    }
    
    private static void subscribeWithObserver(Observable obs) {
        System.out.println("-------------------------------------------------");
        System.out.println("subscribeWithObserver()");
        System.out.println("-------------------------------------------------");

        Subscription s = 
            obs.subscribe(new Observer() {
                @Override
                public void onCompleted() {
                    System.out.println("(Observer) Completed");
                }

                @Override
                public void onError(Throwable thrwbl) {
                    System.err.println("(Observer) Error " + thrwbl.toString());            
                }

                @Override
                public void onNext(Object t) {
                    System.out.println("(Observer) Received " + t.toString());
                }
            });
    }
     
    private static void subscribeWithSubscriber(Observable obs) {
        System.out.println("-------------------------------------------------");
        System.out.println("subscribeWithSubscriber()");
        System.out.println("-------------------------------------------------");
        
        Subscription s = 
            //XXX: add flow control and unsubscription
            obs.subscribe(new Subscriber() {
                @Override
                public void onCompleted() {
                    System.out.println("(SUBSCRIBER) Completed");

                    this.unsubscribe();
                }

                @Override
                public void onError(Throwable thrwbl) {
                    System.err.println("(SUBSCRIBER) Error " + thrwbl.toString());            
                }

                @Override
                public void onNext(Object t) {
                    System.out.println("(SUBSCRIBER) Received " + t.toString());
                }
            });
    }
}
