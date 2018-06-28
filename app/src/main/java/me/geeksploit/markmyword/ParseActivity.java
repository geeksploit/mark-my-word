package me.geeksploit.markmyword;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.geeksploit.markmyword.view.ParseView;

public class ParseActivity extends MvpAppCompatActivity implements ParseView{

    @BindView(R.id.tv_book_title_parse) TextView tvBookTitle;
    @BindView(R.id.tv_file_name_parse) TextView tvFileName;
    @BindView(R.id.pb_progress_parse) ProgressBar progressBar;
    @BindView(R.id.tv_words_count_parse) TextView tvWordsCount;
    @BindView(R.id.btn_done_parse) Button btnDone;
    @BindView(R.id.btn_cancel_parse) Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())){
            intent.getDataString();
            Uri uri = intent.getData();
            tvFileName.setText(uri.getLastPathSegment());
        }
    }
}
