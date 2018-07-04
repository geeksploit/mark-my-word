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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.geeksploit.markmyword.R;
import me.geeksploit.markmyword.model.entity.WordModel;
import me.geeksploit.markmyword.presenter.MainPresenter;

public class ListRvAdapter extends WordRvAdapter {

    public ListRvAdapter(MainPresenter presenter) {
        super(presenter);
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
        if (presenter.isImageOn()) {
            imageLoader.loadInto(word.getImgUri(), listItem.ivItemImage);
            listItem.ivItemImage.setVisibility(View.VISIBLE);
        } else {
            listItem.ivItemImage.setVisibility(View.GONE);
        }
    }

    class ListItemView extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.iv_word_image_list_item)
        ImageView ivItemImage;
        @BindView(R.id.tv_word_list_item)
        TextView tvWord;
        @BindView(R.id.tv_word_desc_list_item) TextView tvWordTranslate;

        public ListItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.switchToCard(getLayoutPosition());
                    Snackbar.make(v, "Pressed: " + tvWord.getText(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
