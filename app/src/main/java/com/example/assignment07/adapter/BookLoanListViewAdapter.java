package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class BookLoanListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class BookLoanViewHolder {
        TextView accessNo;
        TextView branchId;
        TextView cardNo;
        TextView dateOut;
        TextView dateDue;
        TextView dateReturned;
    }

    public BookLoanListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        BookLoanViewHolder bookLoanViewHolder = new BookLoanViewHolder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_loan_row, null);
            bookLoanViewHolder.accessNo = (TextView) convertView.findViewById(R.id.txtBooKLoanAccessNo);
            bookLoanViewHolder.branchId = (TextView) convertView.findViewById(R.id.txtBooKLoanBranchId);
            bookLoanViewHolder.cardNo = (TextView) convertView.findViewById(R.id.txtBooKLoanCardNo);
            bookLoanViewHolder.dateOut = (TextView) convertView.findViewById(R.id.txtBooKLoanDateOut);
            bookLoanViewHolder.dateDue = (TextView) convertView.findViewById(R.id.txtBooKLoanDateDue);
            bookLoanViewHolder.dateReturned = (TextView) convertView.findViewById(R.id.txtBooKLoanDateReturn);
            convertView.setTag(bookLoanViewHolder);
        } else {
            bookLoanViewHolder = (BookLoanViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        bookLoanViewHolder.accessNo.setText(arrayList.get(0));
        bookLoanViewHolder.branchId.setText(arrayList.get(1));
        bookLoanViewHolder.cardNo.setText(arrayList.get(2));
        bookLoanViewHolder.dateOut.setText(arrayList.get(3));
        bookLoanViewHolder.dateDue.setText(arrayList.get(4));
        bookLoanViewHolder.dateReturned.setText(arrayList.get(5));
        return convertView;
    }
}
