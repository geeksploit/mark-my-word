package me.geeksploit.markmyword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.geeksploit.markmyword.model.entity.ParseProgress;
import me.geeksploit.markmyword.presenter.ParsePresenter;
import me.geeksploit.markmyword.utils.IntentExtrasFields;
import me.geeksploit.markmyword.view.IntentActions;
import me.geeksploit.markmyword.view.ParseView;

public class ParseActivity extends MvpAppCompatActivity implements ParseView{

    @BindView(R.id.tv_book_title_parse) TextView tvBookTitle;
    @BindView(R.id.tv_file_name_parse) TextView tvFileName;
    @BindView(R.id.pb_progress_parse) ProgressBar progressBar;
    @BindView(R.id.tv_words_count_parse) TextView tvWordsCount;
    @BindView(R.id.tv_unwords_count_parse) TextView tvUnWordsCount;
    @BindView(R.id.btn_done_parse) Button btnDone;
    @BindView(R.id.btn_cancel_parse) Button btnCancel;
    @BindView(R.id.iv_done_parse) ImageView ivDone;
    @BindView(R.id.iv_cancel_parse) ImageView ivCancel;

    private boolean isParsing;
    private boolean isDone;
    private boolean isCanceled;
    private String bookTitle;

    @InjectPresenter
    ParsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        ButterKnife.bind(this);
        setUIVisibility();
        startBookParse();
    }

    private void setUIVisibility() {
        progressBar.setVisibility(getParseVisibility());
        btnDone.setVisibility(isDone ? View.VISIBLE : View.GONE);
        btnCancel.setVisibility(getParseVisibility());
        ivCancel.setVisibility(isCanceled ? View.VISIBLE : View.GONE);
        ivDone.setVisibility(!isCanceled && isDone ? View.VISIBLE : View.GONE);
    }

    private int getParseVisibility() {
        return isParsing ? View.VISIBLE : View.GONE;
    }

    private void startBookParse() {
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction()) || IntentActions.PARSE_BOOK.equals(intent.getAction())){
            intent.getDataString();
            Uri uri = intent.getData();
            presenter.parseBook(uri != null ? uri.getPath() : null);
            tvFileName.setText(uri != null ?
                    uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1) : "File not found");
        }
    }

    @ProvidePresenter
    public ParsePresenter parsePresenter(){
        ParsePresenter presenter = new ParsePresenter(AndroidSchedulers.mainThread());
        //place holder for future dagger injection
        return presenter;
    }

    @OnClick(R.id.btn_done_parse)
    public void onClickDone(){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentExtrasFields.BOOK_TITLE, bookTitle);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cancel_parse)
    public void onClickCancel(){
        isParsing = false;
        isCanceled = true;
        presenter.parseCancel();
    }

    @Override
    public void parseStart() {
        isParsing = true;
        setUIVisibility();
    }

    @Override
    public void parseProgress(ParseProgress progress) {
        tvWordsCount.setText(String.valueOf(progress.getWordCount()));
    }

    @Override
    public void setUniqueWords(ParseProgress progress) {
        tvUnWordsCount.setText(String.valueOf(progress.getUniqueWord()));
    }

    @Override
    public void parseFinish() {
        isDone = true;
        isParsing = false;
        setUIVisibility();
    }

    @Override
    public void setBookTitle(ParseProgress progress) {
        this.bookTitle = progress.getBookTitle();
    }
}
