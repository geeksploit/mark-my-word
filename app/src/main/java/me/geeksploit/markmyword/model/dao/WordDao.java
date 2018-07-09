package me.geeksploit.markmyword.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import me.geeksploit.markmyword.model.entity.WordModel;
@Dao
public interface WordDao {
    @Query("SELECT * FROM wordmodel")
    Flowable<List<WordModel>> getAll();

    @Query("SELECT * FROM wordmodel WHERE word = :word")
    WordModel getByWord(String word);

    @Query("SELECT * FROM wordmodel WHERE id = :id")
    Flowable<WordModel> getWord(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(WordModel word);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(WordModel word);

    @Delete
    void delete(WordModel word);
}
