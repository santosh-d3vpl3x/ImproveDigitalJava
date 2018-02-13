package com.imprvdigi.io.reader;

import com.imprvdigi.proc.domain.FilewiseWord;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Class to read file with separate thread.
 * It accepts function in constructor which accepts word in a file represented by {@link FilewiseWord}
 */
public class ThreadedReader implements Callable<Boolean> {
    private String filePath;
    private Function<FilewiseWord, Boolean> func;
    private Integer fileIndex;

    /**
     * Constructor for {@link ThreadedReader}
     *
     * @param filePath  absolute path of the file
     * @param func      function to execute on each word from the file
     * @param fileIndex index of the file needed for keeping sequence
     */
    public ThreadedReader(String filePath, Function<FilewiseWord, Boolean> func, final Integer fileIndex) {
        this.filePath = filePath;
        this.func = func;
        this.fileIndex = fileIndex;
    }

    @Override
    public Boolean call() {
        final File file = new File(filePath);
        try (Scanner input = new Scanner(file, Charset.forName("UTF-8").name())) {
            input.useDelimiter("\\W");
            while (input.hasNext()) {
                func.apply(new FilewiseWord(filePath, input.next(), fileIndex));
            }
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
        return true;
    }
}
