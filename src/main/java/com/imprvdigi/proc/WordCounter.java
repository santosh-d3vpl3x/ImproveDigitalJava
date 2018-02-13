package com.imprvdigi.proc;

import com.imprvdigi.proc.domain.Counts;
import com.imprvdigi.proc.domain.FilewiseWord;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cache of counts for words read from various files. Provides function necessary for {@link com.imprvdigi.io.reader.ThreadedReader}
 */
public class WordCounter {
    private ConcurrentHashMap<String, Counts> map = new ConcurrentHashMap<>();
    private Integer numFiles;

    public WordCounter(Integer numFiles) {
        this.numFiles = numFiles;
    }

    /**
     * Maintains a cache of word occurances in array of {@link AtomicInteger}
     *
     * @param filewiseWord object presenting a word and file for that word
     * @return true indicating state of process
     */
    public Boolean countWords(final FilewiseWord filewiseWord) {
        if("".equals(filewiseWord.getWord().trim())){
            return true;
        }
        map.putIfAbsent(filewiseWord.getWord().toLowerCase(), new Counts(numFiles));
        map.computeIfPresent(filewiseWord.getWord().toLowerCase(), (word, counts) -> {
            if (counts.getCounts()[filewiseWord.getFileIndex()] != null) {
                counts.getCounts()[filewiseWord.getFileIndex()].getAndIncrement();
            } else {
                counts.getCounts()[filewiseWord.getFileIndex()] = new AtomicInteger(1);
            }
            return counts;
        });
        return true;
    }

    /**
     * Get cache map in accumulator
     * @return cache map
     */
    public Map<String, Counts> getMap() {
        return map;
    }
}
