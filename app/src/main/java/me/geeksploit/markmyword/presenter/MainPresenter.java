package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

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
                .observeOn(uiScheduler)
                .subscribe(new DisposableSubscriber<List<WordModel>>() {
                    @Override
                    public void onNext(List<WordModel> wordModels) {
                        wordsList.clear();
                        wordsList.addAll(wordModels);
                        getViewState().updateAdapters();
                        request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        throw new RuntimeException(t);
                    }

                    @Override
                    public void onComplete() {
                        getViewState().updateAdapters();
                    }
                });
    }

    public void refreshWords(String title){
        this.bookTitle = title;
        getWordsList(title);
    }

    public void refreshWords(){
        getWordsList(bookTitle);
    }



    public void switchImageVisibility(boolean isImageOn) {
        this.isImageOn = isImageOn;
        getViewState().updateAdapters();
    }

    public void openBooks(){
        getViewState().openBooks();
    }

    public void parseBook(){
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
