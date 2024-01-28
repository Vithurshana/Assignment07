package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class BookAuthorListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class BookAuthorViewHolder {
        TextView bookId;
        TextView bookAuthorName;
    }

    public BookAuthorListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        BookAuthorViewHolder bookAuthorViewHolder = new BookAuthorViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_author_view_row, null);
            bookAuthorViewHolder.bookId = (TextView) convertView.findViewById(R.id.txtAutherBookId);
            bookAuthorViewHolder.bookAuthorName = (TextView) convertView.findViewById(R.id.txtAuthorName);
            convertView.setTag(bookAuthorViewHolder);
        } else {
            bookAuthorViewHolder = (BookAuthorViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        bookAuthorViewHolder.bookId.setText(arrayList.get(0));
        bookAuthorViewHolder.bookAuthorName.setText(arrayList.get(1));
        return convertView;
    }
}
