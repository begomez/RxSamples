/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.factories;


import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import rx.Observable;


/**
 * Sample program that creates observables using different API functions
 * 
 * @author bernatgomez
 */
public class FromAndJustFactories {
    
    public static void main(String[] args) {    
        from();
        just();
    }   
    
    private static void from() {   
        fromUsingList();
        fromUsingIterator();
        fromUsingArray();
    }
    
    private static void fromUsingList() {
        System.out.println("-------------------------------------------------");
        System.out.println("fromUsingList()");
        System.out.println("-------------------------------------------------");

        List<Integer> nums = Arrays.asList(1, 2, 3);
    
        Observable<Integer> obs = Observable.from(nums);
        
        //XXX: emit all list items one by one
        obs.subscribe(System.out::println);
        obs.subscribe(num -> System.out.println(num));
        obs.subscribe((Integer num) -> System.out.println(num));
    }
    
    private static void fromUsingIterator() {
        System.out.println("-------------------------------------------------");
        System.out.println("fromUsingIterator()");
        System.out.println("-------------------------------------------------");

        Path sources = Paths.get("src", "main");
        
        try (DirectoryStream<Path> dir = Files.newDirectoryStream(sources)) {
            Observable<Path> pObs = Observable.from(dir);
        
            pObs.subscribe(System.out::println);
            
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    
    private static void fromUsingArray() {
        System.out.println("-------------------------------------------------");
        System.out.println("fromUsingArray()");
        System.out.println("-------------------------------------------------");

        Observable<String> obs = Observable.from(new String[] {"A", "B", "C"});
        
        obs.subscribe(System.out::println);
    }
    
    private static void just() {    
        justUsingPrimitiveValue();
        justUsingArray();
        justUsingObj();
    }
 
    private static void justUsingPrimitiveValue() {
        System.out.println("-------------------------------------------------");
        System.out.println("justUsingPrimitiveValue()");
        System.out.println("-------------------------------------------------");

        char letter = 'S';
        
        Observable<Character> obs = Observable.just(letter);
        //Observable<char> obs = Observable.just(letter);//XXX: error
        
        obs.subscribe(System.out::println);
    }
    
    private static void justUsingArray() {
        System.out.println("-------------------------------------------------");
        System.out.println("justUsingArray()");
        System.out.println("-------------------------------------------------");

        char[] letters = new char[] {'J', 'a', 'v', 'a'};
        
        //Observable<Character[]> obs = Observable.just(letters);//XXX: error
        Observable<char[]> obs = Observable.just(letters);
        
        obs.subscribe(System.out::println);//XXX: emitted all array at once
    }
    
    private static void justUsingObj() {
        System.out.println("-------------------------------------------------");
        System.out.println("justUsingObj()");
        System.out.println("-------------------------------------------------");

        Observable<User> obs = Observable.just(new User("John", "Dough"));
    
        obs.map(u -> u.getName()).subscribe(System.out::println);
    }
    
    /**
     * User POJO
     */
    private static class User {
        private final String name;
        private final String surname;
        
        public User(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        @Override
        public String toString() {
            return "User{" + "name=" + name + ", surname=" + surname + '}';
        } 
    }
}
