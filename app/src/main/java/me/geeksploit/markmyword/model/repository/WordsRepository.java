package me.geeksploit.markmyword.model.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import me.geeksploit.markmyword.model.dao.BookDao;
import me.geeksploit.markmyword.model.dao.LibDao;
import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.db.WordsDb;
import me.geeksploit.markmyword.model.entity.Book;
import me.geeksploit.markmyword.model.entity.Library;
import me.geeksploit.markmyword.model.entity.WordModel;

public class WordsRepository {

    private WordDao wordDao;
    private BookDao bookDao;
    private LibDao libDao;
    private long bookId;
    private long wordId;

    public WordsRepository(WordsDb dataBase) {
        this.wordDao = dataBase.wordDao();
        this.bookDao = dataBase.bookDao();
        this.libDao = dataBase.libDao();
    }

    public void insertNewBook(String title, String author){
        bookId = bookDao.insert(new Book(title,author));
    }

    public void insertWordsMap(Map<WordModel, Integer> wordMap){
        for (Map.Entry<WordModel, Integer> entry : wordMap.entrySet()){
            addWord(entry.getKey());
        }
    }

    public void insertWordsCollection(Collection<WordModel> wordCollection){
        for (WordModel wordModel : wordCollection) {
            addWord(wordModel);
        }
    }

    public Flowable<List<WordModel>> getAllWords(){
        return wordDao.getAll();
    }

    public Flowable<List<WordModel>> getWordsFromBook(String title){
        return libDao.getWordsForBookById(title);
    }

    public void insertWord(WordModel word){
        addWord(word);
    }

    public void updateWord(WordModel word){
        wordDao.update(word);
    }

    public void deleteWord(WordModel word){
        wordDao.delete(word);
    }

    private void addWord(WordModel wordModel) {
        wordId = wordDao.insert(wordModel);
        if (wordId > -1) {
            libDao.insert(new Library(wordId, bookId));
        } else {
            wordId = wordDao.getByWord(wordModel.getWord()).getId();
            libDao.insert(new Library(wordId, bookId));
        }
    }
}
