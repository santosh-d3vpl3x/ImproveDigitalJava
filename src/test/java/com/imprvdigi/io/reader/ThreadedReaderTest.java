package com.imprvdigi.io.reader;

import com.imprvdigi.proc.WordCounter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

/**
 * Test class for {@link ThreadedReader}
 */

public class ThreadedReaderTest {

    @Test
    public void testCall(){
        final File resourcesDirectory = new File("src/test/resources");
        final String filePath = resourcesDirectory.getAbsolutePath()+File.separator+"File1.dat";
        final WordCounter wc = new WordCounter(1);
        final ThreadedReader tr = new ThreadedReader(filePath, wc::countWords, 0);
        tr.call();
        final Integer testCount = wc.getMap().get("test").getCounts()[0].get();
        assertTrue("Count no of test",testCount== 3);
        final Integer test1Count = wc.getMap().get("test1").getCounts()[0].get();
        assertTrue("Count no of test1",test1Count== 2);
    }
}
