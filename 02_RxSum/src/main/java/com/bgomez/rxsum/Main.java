/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.rxsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

/**
 * Sample program that performs the addition of 2 values mocking spreadsheet programs behavior: 
 * it automatically updates every time an operand changes.
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
        Observable<Double> a = getObservable4Operand("a", input);
        Observable<Double> b = getObservable4Operand("b", input);
        
        //XXX: create object that receives updates
        ReactiveSum result = new ReactiveSum(a, b);
        
        //XXX: tell observable to start emitting items (inputs from keyboard) to observers
        input.connect();
    }

    private static ConnectableObservable<String> getObservable4Input(final BufferedReader br) {
        Observable<String> input =
                
            //XXX: use Observable.create() to get custom observables
            Observable.create(
                    
                //XXX: action performed by subscribers of this observable
                new OnSubscribe<String>() {
                    
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        
                        try {
                            String line = "";
                            
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

    private static Observable<Double> getObservable4Operand(final String operandName, ConnectableObservable<String> source) {
        //XXX: accepted text lines
        final Pattern pattern = Pattern.compile(operandName + "[=](\\d+)$"); 

        Observable<Double> ret = 
            source
                    
                //XXX: get matcher from input
                .map(new Func1<String, Matcher>() {
                    @Override
                    public Matcher call(String msg) {
                        return pattern.matcher(msg);
                    }    
                })
                    
                //XXX: discard not accepted items
                .filter(new Func1<Matcher, Boolean>() {
                    @Override
                    public Boolean call(Matcher matcher) {
                        return matcher.matches() && matcher.group(1) != null;
                    }

                })
                    
                //XXX: get number from entered value
                .map(new Func1<Matcher, Double>() {
                    @Override
                    public Double call(Matcher matcher) {
                        return Double.parseDouble(matcher.group(1));
                    }

                });
        
        return ret;
    }
}
