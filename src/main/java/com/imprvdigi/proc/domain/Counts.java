package com.imprvdigi.proc.domain;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Wrapper for counts of individual words. Counts are maintained in array of {@link AtomicInteger} with each index presenting
 * individual file. Implements Comparable for easy final result sorting.
 */
public class Counts implements Comparable<Counts> {
    private AtomicInteger[] counts;

    public Counts(Integer count) {
        this.counts = new AtomicInteger[count];
    }

    public AtomicInteger[] getCounts() {
        return counts;
    }

    @Override
    public int compareTo(Counts o) {
        Integer sumThis = Arrays.stream(this.counts).map(i -> i != null ? i.get() : 0).reduce((x, y) -> {
            if (x == null) {
                x = y;
            } else {
                x = x + y;
            }
            return x;
        }).get();
        Integer sumOther = Arrays.stream(o.counts).map(i -> i != null ? i.get() : 0).reduce((x, y) -> {
            if (x == null) {
                x = y;
            } else {
                x = x + y;
            }
            return x;
        }).get();
        return sumOther.compareTo(sumThis);
    }
}
