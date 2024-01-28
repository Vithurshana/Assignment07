package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class BookCopyListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class BookCopyViewHolder {
        TextView bookId;
        TextView branchId;
        TextView accessNo;
    }

    public BookCopyListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        BookCopyViewHolder bookCopyViewHolder = new BookCopyViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_copy_view_row, null);
            bookCopyViewHolder.bookId = (TextView) convertView.findViewById(R.id.txtCopyBookId);
            bookCopyViewHolder.branchId = (TextView) convertView.findViewById(R.id.txtCopyBranchId);
            bookCopyViewHolder.accessNo = (TextView) convertView.findViewById(R.id.txtCopyAccessNo);
            convertView.setTag(bookCopyViewHolder);
        } else {
            bookCopyViewHolder = (BookCopyViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        bookCopyViewHolder.bookId.setText(arrayList.get(0).toString());
        bookCopyViewHolder.branchId.setText(arrayList.get(1).toString());
        bookCopyViewHolder.accessNo.setText(arrayList.get(2).toString());
        return convertView;
    }
}
