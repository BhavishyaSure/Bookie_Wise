package com.example.hyndavirenikunta.bookie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SURE BHAVISHYA on 09-04-2018.
 */

class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private List<BookPojo> bookNames;
    private BookClickListener bookClickListener;

    BooksAdapter(List<BookPojo> bookNames, BookClickListener bookClickListener) {
        this.bookNames = bookNames;
        this.bookClickListener = bookClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_book, parent, false),bookClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView bookName = (TextView) holder.itemView.findViewById(R.id.book_name);
        bookName.setText(bookNames.get(position).getBookName());
    }

    public void replaceData(List<BookPojo> bookNames) {
        if(this.bookNames==null) {
            this.bookNames = new ArrayList<>();
        }
        this.bookNames.clear();
        this.bookNames.addAll(bookNames);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (bookNames == null) ? 0 : bookNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView = (ImageView) itemView.findViewById(R.id.book_image);
        BookClickListener bookClickListener;

        ViewHolder(View itemView,BookClickListener bookClickListener) {
            super(itemView);
            imageView.setOnClickListener(this);
            this.bookClickListener = bookClickListener;
        }

        @Override
        public void onClick(View v) {
            bookClickListener.onBookClick(bookNames.get(getAdapterPosition()));
        }
    }
}

