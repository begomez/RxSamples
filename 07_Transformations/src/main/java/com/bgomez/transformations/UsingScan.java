/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.transformations;

import static com.bgomez.transformations.Utils.subscriptionWithLogs;
import rx.Observable;


/**
 *
 * @author bernatgomez
 */
public class UsingScan {
    
    public static void main(String[] args) {   
        scan();
    }
    
    private static void scan() {
        Observable obs = 
            Observable
                .range(1, 10)
                .scan((o, p) -> {
                    System.out.println(o + " + " + p + " will result in... ");
                    return o + p;
                });
        
        subscriptionWithLogs(obs, "scan()");
        
        subscriptionWithLogs(obs.first(), "first()");

        subscriptionWithLogs(obs.last(), "last()");
     
        subscriptionWithLogs(obs.elementAt(3), "elementAt()");
    }
}