package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.geeksploit.markmyword.model.parser.StubParser;
import me.geeksploit.markmyword.view.ParseView;

@InjectViewState
public class ParsePresenter extends MvpPresenter<ParseView> {
    private Scheduler uiScheduler;
    private DisposableObserver<Integer> disposableObserver;

    public ParsePresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                getViewState().parseProgress(integer);
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
        StubParser.getParser(path)
                .startParse()
                .subscribeOn(Schedulers.io())
                .delay(100, TimeUnit.MILLISECONDS)
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
