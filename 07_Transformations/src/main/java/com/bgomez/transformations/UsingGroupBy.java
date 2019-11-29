/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.transformations;

import static com.bgomez.transformations.Utils.sleep;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import rx.subscriptions.Subscriptions;
import static com.bgomez.transformations.Utils.subscriptionWithLogs;


/**
 *
 * @author bernatgomez
 */
public class UsingGroupBy {
    
    public static void main(String[] args) {   
    
        System.out.println("groupby()-----------------------------------------");
        groupBy();
        groupBy2();
        
        System.out.println("cast()--------------------------------------------");
        cast();

        System.out.println("timestamp()---------------------------------------");
        timeStamp();
        timeStamp2();

        System.out.println("timeinterval()------------------------------------");
        timeInterval();
    }
    
    private static void groupBy() {
        List<String> albums = Arrays.asList("Use your illusion I", "Use your illusion II", "Use your illusion V");
        
        Observable
            .from(albums)
            .groupBy(album -> album.length())//XXX: returns observables
                .subscribe(obs -> subscriptionWithLogs(obs, "Obs with key: " + obs.getKey()));
    }
    
    private static void groupBy2() {
        List<String> albums = Arrays.asList("Use your illusion I", "Use your illusion II", "Use your illusion V");
        
        Observable
            .from(albums)
                .groupBy(
                    album -> album.replaceAll("[^iI]", "").length(), 
                    album -> album.replaceAll("[iI]", "*")
                )
                    .subscribe(obs -> subscriptionWithLogs(obs, "Obs with key: " + obs.getKey())
                    );
    }
    
    private static void cast() {
        List<Number> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        Observable<Integer> o = Observable.from(numbers).cast(Integer.class);
        
        subscriptionWithLogs(o, "cast()");
    }
    
    private static void timeStamp() {
        List<String> months = Arrays.asList("January", "February", "March");
        
        Observable o = 
            Observable.from(months).timestamp();
        
        subscriptionWithLogs(o, "timestamp()");
    }
    
    private static void timeStamp2() {
        List<String> months = Arrays.asList("January", "February", "March");
        
        Observable o = 
            Observable
                .from(months)
                    .timestamp()
                        .map((Timestamped<String> t) -> t.getTimestampMillis())
                        .map(l -> new Date(l));
        
        subscriptionWithLogs(o, "timestamp2()");
    }
    
    private static void timeInterval() {
        List<String> names = Arrays.asList("Maria", "Mary");
        
        Observable<TimeInterval<Integer>> o = Observable.from(names).range(0, 99).timeInterval();
                
        subscriptionWithLogs(o, "timeinterval()");
    }
}