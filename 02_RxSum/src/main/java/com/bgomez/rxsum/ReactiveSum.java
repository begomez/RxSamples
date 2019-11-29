/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.rxsum;


import rx.Observable;
import rx.Observer;
import rx.functions.Func2;


/**
 * Class that behaves like an observer object. Keeps track of changes on
 * operand values
 * 
 * @author bernatgomez
 */
public class ReactiveSum implements Observer<Double> {

    private Double sum;
    
    
    public ReactiveSum(Observable<Double> a, Observable<Double> b) {
        this.sum = 0d;
        
        //XXX: create a new observable that emits the values of adding
        //current values of each operand
        Observable.combineLatest(a, b, new Func2<Double, Double, Double>() {
            
            @Override
            public Double call(Double t1, Double t2) {
                return t1 + t2;
            }
            
        }).subscribe(this);//XXX: subscribe this class to resulting observable
    }

////////////////////////////////////////////////////////////////////////////////
// Observer implementation
////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void onCompleted() {
        System.out.println("onCompleted() with value: " + this.sum);
    }

    @Override
    public void onError(Throwable thrwbl) {
        System.err.println("onError()");
        
        thrwbl.printStackTrace();
    }

    @Override
    public void onNext(Double t) {
        this.sum = t;
        
        System.out.println("Now " + " a + b = " + this.sum);
    }
}
