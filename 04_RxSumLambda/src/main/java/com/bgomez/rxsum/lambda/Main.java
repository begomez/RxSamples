/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.rxsum.lambda;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.observables.ConnectableObservable;


/**
 * Sample program that performs the addition of 2 values mocking spreadsheet programs behavior: 
 * it automatically updates every time an operand changes.
 * 
 * Uses lambdas instead of functional interfaces.
 * 
 * @author bernatgomez
 */
public class Main {
    
    public static void main(String[] args) {
        launch();
    }
    
    private static void launch() {
        
        //XXX: create observable to read values from standard input
        ConnectableObservable<String> input = 
            getObservable4Input(new BufferedReader(new InputStreamReader(System.in)));
        
        //XXX: create observables to represent both operands
        Observable<Double> a = getObservable4Oper("a", input);
        Observable<Double> b = getObservable4Operand("b", input);
        
        //XXX: perform sum
        reactiveSum(a, b);
        
        //XXX: tell observable to start emitting items to observers
        input.connect();
    }

    private static ConnectableObservable<String> getObservable4Input(final BufferedReader br) {
        Observable<String> input =
                
            //XXX: user Observable.create() to get custom observables
            Observable.create(
                    
                //XXX: action performed by subscribers
                new OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        
                        try {
                            String line;
                            
                            while (
                                (!subscriber.isUnsubscribed()) && ((line = br.readLine()) != null)
                                ) {
                                
                                if (line.equals("exit")) {
                                    break;
                                }
                                
                                subscriber.onNext(line);    
                            }
                            
                        } catch (IOException e) {
                            subscriber.onError(e);
                        }
                        
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                         
                    }    
                }
            );
        
        //XXX: transform Observable to ConnectableObservable so logic is only executed 1 time (vs 1 execution per subscriber)
        return input.publish();
    }

    private static Observable<Double> getObservable4Oper(final String name, ConnectableObservable<String> input) {
        final Pattern pattern = Pattern.compile(name + "[=](\\d+)$");
    
        return 
            input
                .map(pattern::matcher)//XXX: convert statement to lamba
                .filter(matcher -> matcher.matches() && matcher.group(1) != null)
                .map(matcher -> matcher.group(1))
                .map(Double::parseDouble);//XXX: convert again
    }
    
    private static Observable<Double> getObservable4Operand(final String name, ConnectableObservable<String> input) {
        final Pattern pattern = Pattern.compile(name + "[=](\\d+)$"); 

        return
            input
                .map(msg -> pattern.matcher(msg))
                .filter(matcher -> matcher.matches() && matcher.group(1) != null)
                .map(matcher -> matcher.group(1))
                .map(str -> Double.parseDouble(str));
            }

   public static void reactiveSum(Observable<Double> a, Observable<Double> b) {
       Observable
            .combineLatest(
                a, 
                b, 
                (x, y) -> x + y)
                    .subscribe(
                            
                        // onNext()
                        sum -> System.out.println("a + b: " + sum),
                            
                        // onError()
                        error -> {
                            System.err.println("Error!");
                            error.printStackTrace();
                        },
                        
                        // onCompleted()
                        () -> System.out.print("Done!")
                    );
   }
}
