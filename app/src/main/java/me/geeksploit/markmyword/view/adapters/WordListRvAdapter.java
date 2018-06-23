package me.geeksploit.markmyword.view.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.geeksploit.markmyword.R;
import me.geeksploit.markmyword.model.WordModel;
import me.geeksploit.markmyword.model.image.IImageLoader;
import me.geeksploit.markmyword.presenter.MainPresenter;

public class WordListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WordModel> words;
    private MainPresenter presenter;

    @Inject
    IImageLoader<ImageView> imageLoader;

    public WordListRvAdapter(MainPresenter presenter) {
        this.presenter = presenter;
        words = presenter.getWords();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ListItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItemView listItem = (ListItemView) holder;
        WordModel word = words.get(position);
        listItem.tvWord.setText(word.getWord());
        listItem.tvWordTranslate.setText(word.getTranslate());
        imageLoader.loadInto(word.getImgUri(), listItem.ivItemImage);
        if (presenter.isImageOn()) {
            listItem.ivItemImage.setVisibility(View.VISIBLE);
        } else {
            listItem.ivItemImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class ListItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.iv_word_image_list_item)
        ImageView ivItemImage;
        @BindView(R.id.tv_word_list_item)
        TextView tvWord;
        @BindView(R.id.tv_word_desc_list_item)
        TextView tvWordTranslate;

        public ListItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Pressed: " + tvWord.getText(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
