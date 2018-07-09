package me.geeksploit.markmyword.model.repository;

import android.util.Log;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.geeksploit.markmyword.model.api.YandexApiService;
import me.geeksploit.markmyword.model.dao.BookDao;
import me.geeksploit.markmyword.model.dao.LibDao;
import me.geeksploit.markmyword.model.dao.WordDao;
import me.geeksploit.markmyword.model.db.WordsDb;
import me.geeksploit.markmyword.model.entity.Book;
import me.geeksploit.markmyword.model.entity.Library;
import me.geeksploit.markmyword.model.entity.Translation;
import me.geeksploit.markmyword.model.entity.WordModel;

public class WordsRepository {

    private WordDao wordDao;
    private BookDao bookDao;
    private LibDao libDao;
    private YandexApiService apiService;
    private StringBuilder stringBuilder;
    private long bookId;

    public WordsRepository(WordsDb dataBase, YandexApiService apiService) {
        this.apiService = apiService;
        this.wordDao = dataBase.wordDao();
        this.bookDao = dataBase.bookDao();
        this.libDao = dataBase.libDao();
        stringBuilder = new StringBuilder();
    }

    public Flowable<List<Book>> getBooks(){
        return bookDao.getAllBooks();
    }

    public void insertNewBook(String title, String author){
        bookId = bookDao.insert(new Book(title,author));
        if (bookId < 0) {
            bookId = bookDao.getBookByPairKey(title, author).getId();
        }
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

    public Flowable<WordModel> getWord(WordModel wordModel){
        return wordDao.getWord(wordModel.getId());
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

    public void translateAndUpdate(WordModel wordModel) {
        apiService.getTraslation(wordModel.getWord())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<Translation>() {
                    @Override
                    public void onNext(Translation translation) {
                        for (String s : translation.getText()) {
                            stringBuilder.append(s).append("\n");
                        }
                        wordModel.setTranslate(stringBuilder.toString());
                        stringBuilder.setLength(0);
                        updateWord(wordModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        throw new RuntimeException(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addWord(WordModel wordModel) {
        long wordId = wordDao.insert(wordModel);
        if (wordId > -1) {
            libDao.insert(new Library(wordId, bookId));
        } else {
            wordId = wordDao.getByWord(wordModel.getWord()).getId();
            libDao.insert(new Library(wordId, bookId));
        }
    }
}
