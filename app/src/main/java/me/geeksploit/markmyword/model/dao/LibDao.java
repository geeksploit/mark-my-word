package me.geeksploit.markmyword.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import me.geeksploit.markmyword.model.entity.Library;
import me.geeksploit.markmyword.model.entity.WordModel;

@Dao
public interface LibDao {

    @Query("SELECT wordmodel.id, wordmodel.word, wordmodel.translate, wordmodel.description, " +
            "wordmodel.imgUri FROM library JOIN wordmodel ON wordmodel.id = library.wordId " +
            "JOIN book ON book.id = library.bookId WHERE book.title = :title")
    Flowable<List<WordModel>> getWordsForBookById(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Library library);

    @Update
    void update(Library library);

    @Delete
    int delete(Library library);
}
