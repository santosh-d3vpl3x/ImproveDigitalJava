package com.imprvdigi.proc;

import com.imprvdigi.proc.domain.FilewiseWord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by santosh on 13/02/18.
 */
public class PrinterThreadTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    public void testPrintedOutput() {
        final WordCounter wc = new WordCounter(2);
        wc.countWords(new FilewiseWord("dummyFile1", "test", 0));
        wc.countWords(new FilewiseWord("dummyFile2", "test", 1));
        wc.countWords(new FilewiseWord("dummyFile1", "test", 0));
        wc.countWords(new FilewiseWord("dummyFile1", "random", 0));
        final PrinterThread printerThread = new PrinterThread(wc);
        printerThread.run();
        assertEquals(outContent.toString(), "test 3 = 2 + 1\nrandom 1 = 1 + 0\n");
    }
}
