package me.geeksploit.markmyword.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import me.geeksploit.markmyword.model.entity.WordModel;
import me.geeksploit.markmyword.model.repository.WordsRepository;
import me.geeksploit.markmyword.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private Scheduler uiScheduler;
    private boolean isImageOn;
    private List<WordModel> wordsList;
    private String bookTitle;

    @Inject
    WordsRepository repository;

    public MainPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        this.wordsList = new ArrayList<>();
    }

    private void getWordsList(String title) {
        repository.getWordsFromBook(title)
                .subscribeOn(Schedulers.io())
                .debounce(1000, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(uiScheduler)
                .subscribe(new DisposableSubscriber<List<WordModel>>() {
                    @Override
                    public void onNext(List<WordModel> wordModels) {
                        wordsList.clear();
                        wordsList.addAll(wordModels);
                        getViewState().updateAdapters();
                    }

                    @Override
                    public void onError(Throwable t) {
                        throw new RuntimeException(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void translateWord(WordModel wordModel, int pos) {
        repository.translateAndUpdate(wordModel);
        repository.getWord(wordModel)
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(uiScheduler)
                .subscribe(new DisposableSubscriber<WordModel>() {
                    @Override
                    public void onNext(WordModel translatedWord) {
                        wordsList.remove(pos);
                        wordsList.add(pos, translatedWord);
                    }

                    @Override
                    public void onError(Throwable t) {
                        throw new RuntimeException(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void refreshWords(String title) {
        this.bookTitle = title;
        getWordsList(title);
    }

    public void refreshWords() {
        getWordsList(bookTitle);
    }


    public void switchImageVisibility(boolean isImageOn) {
        this.isImageOn = isImageOn;
        getViewState().updateAdapters();
    }

    public void openBooks() {
        getViewState().openBooks();
    }

    public void parseBook() {
        getViewState().chooseBookToParse();
    }

    public void switchToCard(int wordPos) {
        getViewState().switchToCard(wordPos);
    }

    public boolean isImageOn() {
        return isImageOn;
    }

    public List<WordModel> getWords() {
        return wordsList;
    }
}
