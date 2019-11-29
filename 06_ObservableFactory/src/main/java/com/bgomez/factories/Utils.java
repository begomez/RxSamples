/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.factories;


import java.util.Iterator;
import rx.Observable;
import rx.Subscriber;


/**
 * Helper methods

 * @author bernatgomez
 */
public abstract class Utils {
    
    
    public static <T> Observable<T> createObservable(final Iterable<T> iterable) {
        Observable<T> returned = 
            Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subs) {
                        try {

                            if (subs.isUnsubscribed()) {
                                return;
                            }

                            System.out.println("call()");

                            Iterator<T> iterator = iterable.iterator();

                            while (iterator.hasNext()) {
                                subs.onNext(iterator.next());
                            }

                            if (!subs.isUnsubscribed()) {
                                subs.onCompleted();
                            }

                        } catch (Exception e) {
                            if (!subs.isUnsubscribed()) {
                                subs.onError(e);
                            }
                        }
                    }            
                }
            );
        
        return returned;
    }
    
    public static <T> void subscriptionWithLogs(Observable<T> obs, String name) {
        obs.subscribe(
                v -> System.out.println(name + " emitted " + v),
                e -> {
                    System.err.println(name +  " got an error");
                    System.err.println(e.getMessage());
                },
                () -> System.out.println(name + " is Done!")
        );
    }
}