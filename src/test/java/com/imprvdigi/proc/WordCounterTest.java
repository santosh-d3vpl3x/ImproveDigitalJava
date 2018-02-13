package com.imprvdigi.proc;

import com.imprvdigi.io.reader.ThreadedReader;
import com.imprvdigi.proc.domain.FilewiseWord;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link WordCounter}
 */
public class WordCounterTest {
    @Test
    public void testMapCounter(){
        final WordCounter wc = new WordCounter(2);
        wc.countWords(new FilewiseWord("dummyFile1", "test", 0));
        final Integer test11Count = wc.getMap().get("test").getCounts()[0].get();
        assertTrue("Count no of test in first file",test11Count== 1);
        wc.countWords(new FilewiseWord("dummyFile2", "test", 1));
        final Integer test21Count = wc.getMap().get("test").getCounts()[1].get();
        assertTrue("Count no of test in second file",test21Count== 1);
        wc.countWords(new FilewiseWord("dummyFile1", "test", 0));
        final Integer test12Count = wc.getMap().get("test").getCounts()[0].get();
        assertTrue("Count no of test in first file",test12Count== 2);
        wc.countWords(new FilewiseWord("dummyFile1", "random", 0));
        final Integer ramdom11Count = wc.getMap().get("random").getCounts()[0].get();
        assertTrue("Count no of random in first file",ramdom11Count== 1);
    }
}
