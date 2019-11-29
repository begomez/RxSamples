/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgomez.lambdas;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Helper methods
 * 
 * @author bernatgomez
 */
public abstract class Utils {
        
    public static <V, M> List<M> doMapping(List<V> data, Mapper<V, M> mapper) {
        List<M> mapped = new ArrayList<M>(data.size());
        
        for (V item : data) {
            mapped.add(mapper.map(item));
        }
        
        return mapped;
    }

    public static <M> void dump(List<M> data) {
        for (M item : data) {
            System.out.println("value: " + item + " class: " + item.getClass().toString());
        }
    }
}
