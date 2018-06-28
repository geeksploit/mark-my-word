package me.geeksploit.markmyword.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;
import me.geeksploit.markmyword.model.WordModel;
import me.geeksploit.markmyword.model.image.IImageLoader;
import me.geeksploit.markmyword.presenter.MainPresenter;

public abstract class WordRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<WordModel> words;
    protected MainPresenter presenter;

    @Inject
    IImageLoader<ImageView> imageLoader;

    public WordRvAdapter(MainPresenter presenter) {
        this.presenter = presenter;
        words = presenter.getWords();
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
