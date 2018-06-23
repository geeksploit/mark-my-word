package ru.supernacho.markmywordtemp.view.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.supernacho.markmywordtemp.R;
import ru.supernacho.markmywordtemp.model.WordModel;
import ru.supernacho.markmywordtemp.presenter.MainPresenter;

public class WordCardRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WordModel> words;
    private MainPresenter presenter;

    public WordCardRvAdapter(MainPresenter presenter) {
        this.presenter = presenter;

        //test collection
        words = new ArrayList<>();
        words.add(new WordModel("User", "Пользователь"));
        words.add(new WordModel("Computer", "Компьютер"));
        words.add(new WordModel("Synchronization", "Синхронизация"));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view, parent, false);
        return new CardItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CardItemView cardItem = (CardItemView) holder;
        WordModel word = words.get(position);
        cardItem.tvWord.setText(word.getWord());
        cardItem.tvWordTranslate.setText(word.getTranslate());
        cardItem.tvWordDescription.setText(word.getDescription());
        if (presenter.isImageOn()) {
            cardItem.ivItemImage.setVisibility(View.VISIBLE);
        } else {
            cardItem.ivItemImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class CardItemView extends RecyclerView.ViewHolder{

        @BindView(R.id.cl_card_item) ConstraintLayout itemLayout;
        @BindView(R.id.iv_image_word_card_item) ImageView ivItemImage;
        @BindView(R.id.tv_word_card_view) TextView tvWord;
        @BindView(R.id.tv_main_translate_card_view) TextView tvWordTranslate;
        @BindView(R.id.tv_word_description_card_view) TextView tvWordDescription;

        public CardItemView(View itemView) {
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
