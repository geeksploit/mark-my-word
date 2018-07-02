package me.geeksploit.markmyword.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.geeksploit.markmyword.model.entity.WordModel;
@Dao
public interface WordDao {
    @Query("SELECT * FROM wordmodel")
    List<WordModel> getAll();

    @Query("SELECT * FROM wordmodel WHERE word = :word")
    WordModel getByWord(String word);

    @Insert
    void insert(WordModel word);

    @Update
    void update(WordModel word);

    @Delete
    void delete(WordModel word);
}
