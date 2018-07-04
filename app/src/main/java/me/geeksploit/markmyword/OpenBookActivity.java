package me.geeksploit.markmyword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.geeksploit.markmyword.presenter.OpenBooksPresenter;
import me.geeksploit.markmyword.utils.IntentExtrasFields;
import me.geeksploit.markmyword.view.OpenBooksView;
import me.geeksploit.markmyword.view.adapters.OpenBooksRvAdapter;

public class OpenBookActivity extends MvpAppCompatActivity implements OpenBooksView {

    @BindView(R.id.rv_open_books) RecyclerView rvBooks;
    private OpenBooksRvAdapter adapter;
    private String openedBookTitle;

    @InjectPresenter
    OpenBooksPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_book);
        ButterKnife.bind(this);
        getBookTitle();
        initRV();
    }

    private void getBookTitle() {
        Intent openedTitle = getIntent();
        if (openedTitle.hasExtra(IntentExtrasFields.BOOK_TITLE))
            openedBookTitle = openedTitle.getStringExtra(IntentExtrasFields.BOOK_TITLE);
    }

    private void initRV() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new OpenBooksRvAdapter(presenter, openedBookTitle);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBooks.setLayoutManager(layoutManager);
        rvBooks.setAdapter(adapter);
    }

    @ProvidePresenter
    public OpenBooksPresenter providePresenter(){
        OpenBooksPresenter presenter = new OpenBooksPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void chooseBook(String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentExtrasFields.BOOK_TITLE, title);
        startActivity(intent);
        finish();
    }
}
