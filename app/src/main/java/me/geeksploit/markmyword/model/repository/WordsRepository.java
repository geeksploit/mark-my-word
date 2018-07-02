package me.geeksploit.markmyword.model.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.db.WordsDb;
import me.geeksploit.markmyword.model.entity.WordModel;

public class WordsRepository {

    private WordDao wordDao;

    public WordsRepository(WordsDb dataBase) {
        this.wordDao = dataBase.wordDao();
    }

    public void insertWordsMap(Map<WordModel, Integer> wordMap){
        for (Map.Entry<WordModel, Integer> entry : wordMap.entrySet()){
            if (wordDao.getByWord(entry.getKey().getWord()) == null)
            wordDao.insert(entry.getKey());
        }
    }

    public void insertWordsCollection(Collection<WordModel> wordCollection){
        for (WordModel wordModel : wordCollection) {
            if (wordDao.getByWord(wordModel.getWord()) == null)
                wordDao.insert(wordModel);
        }
    }

    public Flowable<List<WordModel>> getAllWords(){
        return wordDao.getAll();
    }

    public void insertWord(WordModel word){
//        if (wordDao.getByWord(word.getWord()) == null)
            wordDao.insert(word);
    }

    public void updateWord(WordModel word){
        wordDao.update(word);
    }

    public void deleteWord(WordModel word){
        wordDao.delete(word);
    }
}
