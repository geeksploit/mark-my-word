package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.geeksploit.markmyword.model.entity.ParseProgress;
import me.geeksploit.markmyword.model.parser.ParserFactory;
import me.geeksploit.markmyword.view.ParseView;

@InjectViewState
public class ParsePresenter extends MvpPresenter<ParseView> {
    private Scheduler uiScheduler;
    private DisposableObserver<ParseProgress> disposableObserver;

    public ParsePresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        disposableObserver = new DisposableObserver<ParseProgress>() {
            @Override
            public void onNext(ParseProgress parseProgress) {
                getViewState().setBookTitle(parseProgress);
                getViewState().parseProgress(parseProgress);
                getViewState().setUniqueWords(parseProgress);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onComplete() {
                getViewState().parseFinish();
            }
        };
    }

    public void parseBook(String path){
        getViewState().parseStart();
        ParserFactory.getParser(path)
                .startParse()
                .subscribeOn(Schedulers.io())
                .delay(10, TimeUnit.MILLISECONDS)
                .observeOn(uiScheduler)
                .subscribe(disposableObserver);
    }

    public void parseCancel(){
        if (disposableObserver != null) {
            disposableObserver.dispose();
            getViewState().parseFinish();
        }
    }
}
