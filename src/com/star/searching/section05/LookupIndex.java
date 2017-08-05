package com.star.searching.section05;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class LookupIndex {

    public static void main(String[] args) {

        In in = new In(args[0]);

        String separator = args[1];

        HashMap<String, Queue<String>> hashMap = new HashMap<>();
        HashMap<String, Queue<String>> reverseHashMap = new HashMap<>();

        while (in.hasNextLine()) {

            String[] tokens = in.readLine().split(separator);
            String key = tokens[0];

            for (int i = 1; i < tokens.length; i++) {

                String value = tokens[i];

                if (!hashMap.containsKey(key)) {

                    hashMap.put(key, new Queue<>());
                }

                if (!reverseHashMap.containsKey(value)) {

                    reverseHashMap.put(value, new Queue<>());
                }

                hashMap.get(key).enqueue(value);
                reverseHashMap.get(value).enqueue(key);
            }
        }

        while (!StdIn.isEmpty()) {

            String query = StdIn.readString();

            if (hashMap.containsKey(query)) {

                for (String string : hashMap.get(query)) {
                    StdOut.println("    " + string);
                }
            }

            if (reverseHashMap.containsKey(query)) {

                for (String string : reverseHashMap.get(query)) {
                    StdOut.println("    " + string);
                }
            }
        }
    }
}
