package com.star.searching.section05;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

public class DeDup {

    public static void main(String[] args) {

        HashSet<String> hashSet = new HashSet<>();

        String[] array = new In(args[0]).readAllStrings();

        for (int i = 0; i < array.length; i++) {

            if (!hashSet.contains(array[i])) {
                hashSet.add(array[i]);
                StdOut.println(array[i]);
            }
        }
    }
}
