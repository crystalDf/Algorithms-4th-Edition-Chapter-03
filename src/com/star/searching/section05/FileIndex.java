package com.star.searching.section05;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class FileIndex {

    public static void main(String[] args) {

        HashMap<String, HashSet<File>> hashMap = new HashMap<>();

        for (String filename : args) {

            File file = new File(filename);

            In in = new In(file);

            while (!in.isEmpty()) {

                String word = in.readString();

                if (!hashMap.containsKey(word)) {
                    hashMap.put(word, new HashSet<>());
                }

                HashSet<File> hashSet = hashMap.get(word);

                hashSet.add(file);
            }
        }

        while (!StdIn.isEmpty()) {

            String query = StdIn.readString();

            if (hashMap.containsKey(query)) {

                for (File file : hashMap.get(query)) {
                    StdOut.println("    " + file.getName());
                }
            }
        }
    }
}
