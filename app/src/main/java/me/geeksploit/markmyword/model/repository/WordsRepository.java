package me.geeksploit.markmyword.model.repository;

import javax.inject.Inject;

import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.db.WordsDb;

public class WordsRepository {
//    @Inject
//    WordsDb dataBase;

    private WordDao wordDao;

    public WordsRepository(WordsDb dataBase) {
        this.wordDao = dataBase.wordDao();
    }
}
