package me.geeksploit.markmyword.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.entity.WordModel;

@Database(entities = {WordModel.class}, version = 1)
public abstract class WordsDb extends RoomDatabase {
    public abstract WordDao wordDao();
}
