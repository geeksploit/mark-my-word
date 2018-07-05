package me.geeksploit.markmyword.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;
import me.geeksploit.markmyword.model.entity.WordModel;
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

    protected void getTranslation(WordModel word, int position){
        if (word.getTranslate().equals("") || word.getTranslate() == null){
            presenter.translateWord(word, position);
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
