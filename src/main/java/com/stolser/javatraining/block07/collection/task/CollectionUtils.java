package com.stolser.javatraining.block07.collection.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    /**
     * Compare elements from the first group with elements from the second one and
     * returns only those that are contained in the first but not in the second group.
     *
     * @param groupA elements to be included in the result
     * @param groupB elements to be excluded from the result
     */
    public static <T extends Iterable<E>, E extends Comparable<? super E>>
    Collection<E> getDifferenceUsingForLoop(T groupA, T groupB) {

        List<E> result = new ArrayList<>();



        return result;
    }
}
