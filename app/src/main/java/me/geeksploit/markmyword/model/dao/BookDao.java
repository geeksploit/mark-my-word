package me.geeksploit.markmyword.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import me.geeksploit.markmyword.model.entity.Book;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book")
    Flowable<List<Book>> getAllBooks();

    @Query("SELECT * FROM book WHERE id = :id")
    Book getBookById(long id);

    @Query("SELECT * FROM book WHERE title = :title")
    Book getBookByTitle(String title);

    @Query("SELECT * FROM book WHERE author = :author")
    Book getBookByAuthor(String author);

    @Insert
    long insert(Book book);

    @Update
    void update(Book book);

    @Delete
    int delete(Book book);
}
