package me.geeksploit.markmyword.model.entity;

public class ParseProgress {
    private String bookTitle;
    private int wordCount;
    private int uniqueWord;

    public ParseProgress(String bookTitle, int wordCount, int uniqueWord) {
        this.bookTitle = bookTitle;
        this.wordCount = wordCount;
        this.uniqueWord = uniqueWord;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getUniqueWord() {
        return uniqueWord;
    }

    public void setUniqueWord(int uniqueWord) {
        this.uniqueWord = uniqueWord;
    }
}
