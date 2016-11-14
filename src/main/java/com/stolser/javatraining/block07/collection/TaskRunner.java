package com.stolser.javatraining.block07.collection;

import java.util.Collection;

import static com.stolser.javatraining.block07.collection.task.ArrayUtils.getDifferenceUsingForLoop;
import static com.stolser.javatraining.block07.collection.task.ArrayUtils.printlnAsSorted;

public class TaskRunner {
    public static void main(String[] args) {
        generateIntegerArraysAndPrintDifferentElements();
    }

    private static void generateIntegerArraysAndPrintDifferentElements() {
        Integer[] array1 = {1, 3, 5, 5, 20, 4, 2};
        Integer[] array2 = {3, 1, 55, 21, 8, 2, 0, 5, 5};

        printlnAsSorted(array1, "First array");
        printlnAsSorted(array2, "Second array");

        Collection<Integer> difference = getDifferenceUsingForLoop(array1, array2);
        System.out.printf("Difference using a for loop: %s\n", difference);



    }
}
