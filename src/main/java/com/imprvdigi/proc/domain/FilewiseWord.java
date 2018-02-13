package com.imprvdigi.proc.domain;

/**
 * Class to represent words read from individual files.
 */
public class FilewiseWord {
    private String fileName;

    private String word;

    private Integer fileIndex;

    /**
     * Constructor for {@link FilewiseWord}
     *
     * @param fileName absolute path of file from which word is read
     * @param word     word read from file
     */
    public FilewiseWord(String fileName, String word, Integer fileIndex) {
        this.fileName = fileName;
        this.word = word;
        this.fileIndex = fileIndex;
    }


    /**
     * getter for filename
     *
     * @return absolute path of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * getter for word
     *
     * @return word read from file
     */
    public String getWord() {
        return word;
    }

    /**
     * getter for index of the file
     *
     * @return file index
     */
    public Integer getFileIndex() {
        return fileIndex;
    }

    @Override
    public String toString() {
        return "FilewiseWord{" +
                "fileName='" + fileName + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
