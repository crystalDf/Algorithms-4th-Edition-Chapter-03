package com.star.searching.section05;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class LookupCSV {

    public static void main(String[] args) {

        In in = new In(args[0]);

        int keyField = Integer.parseInt(args[1]);
        int valueField = Integer.parseInt(args[2]);

        HashMap<String, String> hashMap = new HashMap<>();

        while (in.hasNextLine()) {

            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String value = tokens[valueField];
            hashMap.put(key, value);
        }

        while (!StdIn.isEmpty()) {

            String query = StdIn.readString();

            if (hashMap.containsKey(query)) {
                StdOut.println(hashMap.get(query));
            }
        }
    }
}
