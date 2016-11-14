package com.stolser.javatraining.block07.collection.task;

import java.util.*;

public class ArrayUtils {

    /**
     * Compare elements from the first array with elements from the second one and
     * returns only those that are contained in the first but not in the second array.
     * @param groupA elements to be included in the result
     * @param groupB elements to be excluded from the result
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Collection<T> getDifferenceUsingForLoop(T[] groupA, T[] groupB) {
        List<T> result = new ArrayList<>();

        groupAFor:
        for (T elementA: groupA) {
            for (T elementB: groupB) {
                if (0 == elementA.compareTo(elementB)) {
                    continue groupAFor;
                }
            }

            result.add(elementA);
        }

        return result;
    }

    public static <T extends Comparable<T>> void printlnAsSorted(T[] array, String label) {
        List<T> list = Arrays.asList(array);
        Collections.sort(list);
        System.out.printf("%s: %s\n", label, list);
    }

}
