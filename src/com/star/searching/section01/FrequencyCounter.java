package com.star.searching.section01;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FrequencyCounter {

    public static void main(String[] args) {

        int minLen = Integer.parseInt(args[0]);

        String[] array = new In(args[1]).readAllStrings();

        SequentialSearchST<String, Integer> sequentialSearchST =
                new SequentialSearchST<>();

        for (int i = 0; i < array.length; i++) {

            String word = array[i];

            if (word.length() >= minLen) {

                if (!sequentialSearchST.contains(word)) {

                    sequentialSearchST.put(word, 1);
                } else {

                    sequentialSearchST.put(word, sequentialSearchST.get(word) + 1);
                }
            }
        }

        String max = "";

        sequentialSearchST.put(max, 0);

        for (String word : sequentialSearchST.keys()) {

            if (sequentialSearchST.get(word) > sequentialSearchST.get(max)) {

                max = word;
            }
        }

        StdOut.println(max + " " + sequentialSearchST.get(max));
    }
}
