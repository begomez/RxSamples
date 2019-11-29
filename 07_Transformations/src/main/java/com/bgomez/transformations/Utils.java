/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.transformations;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.subscriptions.Subscriptions;


/**
 *
 * @author bernatgomez
 */
public abstract class Utils {
    
    public static void sleep(long time) {
    
        try {
            Thread.sleep(time);
            
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    public static <T> void subscriptionWithLogs(Observable<T> obs, String name) {
        obs.subscribe(
            s -> System.out.println(name + " emitted " + s + " of type: " + s.getClass().getSimpleName()), 
            e -> System.err.println(name + " throw an error " + e), 
            () -> System.out.println(name + " on Completed!")
        );    
    }
}