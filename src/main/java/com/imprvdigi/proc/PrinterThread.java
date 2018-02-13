package com.imprvdigi.proc;

import java.util.Arrays;
import java.util.Map;

/**
 * Thread to print final output.
 */
public class PrinterThread implements Runnable {
    private WordCounter wc;

    public PrinterThread(WordCounter wc) {
        this.wc = wc;
    }

    @Override
    public void run() {
        wc.getMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    Integer totalCount = Arrays.stream(entry.getValue().getCounts()).map(i -> i != null ? i.get() : 0).reduce((x, y) -> {
                        if (x == null) {
                            return y;
                        } else {
                            return x + y;
                        }
                    }).get();
                    String[] simplifiedCounts = Arrays.stream(entry.getValue().getCounts()).map(x -> x != null ? x.get() + "" : "0").toArray(String[]::new);
                    System.out.println(entry.getKey() + " " + totalCount + " = " + String.join(" + ", simplifiedCounts));
                });
    }
}
