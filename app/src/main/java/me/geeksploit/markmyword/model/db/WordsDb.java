package me.geeksploit.markmyword.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.geeksploit.markmyword.model.dao.BookDao;
import me.geeksploit.markmyword.model.dao.LibDao;
import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.entity.Book;
import me.geeksploit.markmyword.model.entity.Library;
import me.geeksploit.markmyword.model.entity.WordModel;

@Database(entities = {WordModel.class, Book.class, Library.class}, version = 1, exportSchema = false)
public abstract class WordsDb extends RoomDatabase {
    public abstract WordDao wordDao();
    public abstract BookDao bookDao();
    public abstract LibDao libDao();
}
