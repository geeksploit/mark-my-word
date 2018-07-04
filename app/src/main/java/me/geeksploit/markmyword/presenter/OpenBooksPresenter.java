package me.geeksploit.markmyword.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.geeksploit.markmyword.model.entity.Book;
import me.geeksploit.markmyword.model.repository.WordsRepository;
import me.geeksploit.markmyword.view.OpenBooksView;

@InjectViewState
public class OpenBooksPresenter extends MvpPresenter<OpenBooksView>{

    private List<Book> books;
    private Scheduler uiScheduler;
    private Disposable subscribe;

    @Inject
    WordsRepository repository;

    public OpenBooksPresenter(Scheduler uiScheduler) {
        this.uiScheduler = uiScheduler;
        books = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        subscribe = repository.getBooks().subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(books -> {
                    this.books.addAll(books);
                    getViewState().update();
                });
    }

    public void openBook(String title){
        getViewState().chooseBook(title);
    }

    public List<Book> getBooks(){
        return books;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null && !subscribe.isDisposed()) subscribe.dispose();
    }
}
