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
import static com.bgomez.transformations.Utils.subscriptionWithLogs;


/**
 *
 * @author bernatgomez
 */
public class UsingMap {
    
    public static void main(String[] args) {    
        System.out.println("map()--------------------------------------------------");
        map();
                  
        System.out.println("flatmap()--------------------------------------------------");
        flatmap();

        System.out.println("concatmap()--------------------------------------------------");
        concatmap();

        System.out.println("switchmap()--------------------------------------------------");
        switchmap();
    }
    
    public static void map() {
        Observable o = 
            Observable
                .just(1, 2, 3, 4, 5, 6)
                    .map((Integer n) -> n * 3)
                    .map((Integer n) -> (n % 2 == 0)? "even" : "odd");
        
        subscriptionWithLogs(o, "map()");
    }
    
    public static void flatmap() {
        Observable<String> fObs = 
            getObservablePathFromDir(Paths.get("src", "main","java", "com", "bs", "transformations", "samples"), "{*.java}")
                .flatMap(path -> getObservableStringFromPath(path));
  
        subscriptionWithLogs(fObs, "flatmap()");
    }
    
    private static void concatmap() {
    
    }
    
    private static void switchmap() {
        Observable<Object> obs = 
            Observable.interval(40L, TimeUnit.MILLISECONDS)
                .switchMap(v -> Observable.timer(0L, 10L, TimeUnit.MILLISECONDS)
                    .map(u -> "Observable " + (v + 1) + " : " + (v + u)))
                ;
    
        subscriptionWithLogs(obs, "switchmap()");
    }
    
    /**
     * Get observable representing a directory. Emit files that match pattern as Path
     * @param dir
     * @param pattern
     * @return 
     */
    private static Observable<Path> getObservablePathFromDir(Path dir, String pattern) {
        Observable o = null;
        
        o = 
            Observable.<Path>create(
                subscriber -> {
                    try {
                        DirectoryStream<Path> stream = Files.newDirectoryStream(dir, pattern);

                        subscriber.add(
                            //XXX: executed when unsubscribed
                            Subscriptions.create(
                                () -> { 
                                    try { 
                                        stream.close(); 
                                        
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            )
                        );

                        Observable.<Path>from(stream).subscribe(subscriber);

                    } catch (DirectoryIteratorException die) {
                        subscriber.onError(die);

                    } catch (IOException ioe) {
                        subscriber.onError(ioe);
                    }
                }
            );
        
        return o;
    }
    
    /**
     * Emit content of a file line by line
     * @param p
     * @return 
     */
    private static Observable<String> getObservableStringFromPath(final Path p) {
        Observable<String> o = null;
        
        o = 
            Observable.<String>create(
                subscriber -> {
                    try {
                        BufferedReader br = Files.newBufferedReader(p);

                        //XXX: executed when unsubscribed
                        subscriber.add(
                            Subscriptions.create(
                                () -> {
                                    try {
                                        br.close();

                                    } catch (IOException ioe) {
                                        ioe.printStackTrace();
                                    }
                                }
                            )
                        );

                        String line = null;

                        while (((line = br.readLine()) != null) && (!subscriber.isUnsubscribed())) {
                            subscriber.onNext(line);
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }

                    } catch (IOException ioe) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(ioe);
                        }
                    }
                }
            );
        
        return o;
    }
}