package me.geeksploit.markmyword.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.geeksploit.markmyword.R;
import me.geeksploit.markmyword.model.entity.Book;
import me.geeksploit.markmyword.presenter.OpenBooksPresenter;

public class OpenBooksRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OpenBooksPresenter presenter;
    private String openedBookTitle;
    private List<Book> books;

    public OpenBooksRvAdapter(OpenBooksPresenter presenter, String title) {
        this.presenter = presenter;
        this.openedBookTitle = title;
        this.books = presenter.getBooks();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_books_item, parent, false);
        return new OpenBookItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OpenBookItem bookItem = (OpenBookItem) holder;
        Book book = books.get(position);
        if (books.get(position).getTitle().equals(openedBookTitle)) bookItem.ivIcon.setImageResource(R.drawable.ic_book_selected);
        bookItem.tvTitle.setText(book.getTitle());
        bookItem.tvAuthor.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class OpenBookItem extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_open_books) LinearLayout llItem;
        @BindView(R.id.iv_icon_open_books) ImageView ivIcon;
        @BindView(R.id.tv_author_open_books) TextView tvAuthor;
        @BindView(R.id.tv_title_open_books) TextView tvTitle;

        public OpenBookItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            llItem.setOnClickListener(v -> {
                presenter.openBook(books.get(getLayoutPosition()).getTitle());
            });
        }
    }
}
