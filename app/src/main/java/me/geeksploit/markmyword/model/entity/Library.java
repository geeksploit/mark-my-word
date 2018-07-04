package me.geeksploit.markmyword.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(indices = {@Index(value = {"wordId", "bookId"}, unique = true)})
public class Library {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ForeignKey(entity = WordModel.class, parentColumns = {"id"}, childColumns = {"wordId"})
    private long wordId;
    @ForeignKey(entity = Book.class, parentColumns = {"id"}, childColumns = {"bookId"})
    private long bookId;

    public Library(long wordId, long bookId) {
        this.wordId = wordId;
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
