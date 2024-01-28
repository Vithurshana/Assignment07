package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;
import com.example.assignment07.R;

import java.util.ArrayList;

public class BookListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class BookViewHolder {
        TextView bookId;
        TextView bookTile;
        TextView bookPublisher;
    }

    public BookListViewAdapter(Context context, ArrayList<ArrayList> list ){
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookViewHolder bookViewHolder = new BookViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_view_row, null);
            bookViewHolder.bookId = (TextView) convertView.findViewById(R.id.bookId);
            bookViewHolder.bookTile = (TextView) convertView.findViewById(R.id.bookTitle);
            bookViewHolder.bookPublisher = (TextView) convertView.findViewById(R.id.bookPublisher);
            convertView.setTag(bookViewHolder);
        } else {
            bookViewHolder = (BookViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        bookViewHolder.bookId.setText(arrayList.get(0));
        bookViewHolder.bookTile.setText(arrayList.get(1));
        bookViewHolder.bookPublisher.setText(arrayList.get(2));
        return convertView;
    }
}
