import com.imprvdigi.io.reader.ThreadedReader;
import com.imprvdigi.proc.PrinterThread;
import com.imprvdigi.proc.WordCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Launcher for word counter.
 * To run, pass absolute filenames as arguments to the code.
 */
public class WordCounterLauncher {
    public static void main(String[] args) {
        final WordCounter wc = new WordCounter(args.length);

        final List<ThreadedReader> trs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            trs.add(new ThreadedReader(args[i], wc::countWords, i));
        }

        final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            final List<Future<Boolean>> futures = es.invokeAll(trs);
            for (Future<Boolean> future : futures) {
                future.get();
            }

        } catch (final InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            es.shutdown();
        }

        PrinterThread pt = new PrinterThread(wc);
        Thread printerThread = new Thread(pt);
        printerThread.start();
        try {
            printerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
