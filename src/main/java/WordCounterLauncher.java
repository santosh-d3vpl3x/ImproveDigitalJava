import com.imprvdigi.io.reader.ThreadedReader;
import com.imprvdigi.proc.PrinterThread;
import com.imprvdigi.proc.WordCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordCounterLauncher {
    public static void main(String[] args) {
        final List<String> fileList = new ArrayList<>();
        fileList.add("/Users/santosh/IdeaProjects/ImproveDigital/src/main/resources/file1.dat");
        fileList.add("/Users/santosh/IdeaProjects/ImproveDigital/src/main/resources/file2.dat");

        final WordCounter wc = new WordCounter(fileList.size());

        final List<ThreadedReader> trs = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            trs.add(new ThreadedReader(fileList.get(i), wc::countWords, i));
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
