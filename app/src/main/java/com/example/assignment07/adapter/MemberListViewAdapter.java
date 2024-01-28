package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class MemberListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class MemberViewHolder {
        TextView cardNo;
        TextView memberName;
        TextView memberAddress;
        TextView phoneNo;
        TextView unpaidDues;
    }

    public MemberListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        MemberViewHolder memberViewHolder = new MemberViewHolder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.member_view_row, null);
            memberViewHolder.cardNo = (TextView) convertView.findViewById(R.id.txtMemCardNo);
            memberViewHolder.memberName = (TextView) convertView.findViewById(R.id.txtMemName);
            memberViewHolder.memberAddress = (TextView) convertView.findViewById(R.id.txtMemAddress);
            memberViewHolder.phoneNo = (TextView) convertView.findViewById(R.id.txtMemPhone);
            memberViewHolder.unpaidDues = (TextView) convertView.findViewById(R.id.txtMemUnpaidDue);
            convertView.setTag(memberViewHolder);
        } else {
            memberViewHolder = (MemberViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        memberViewHolder.cardNo.setText(arrayList.get(0));
        memberViewHolder.memberName.setText(arrayList.get(1));
        memberViewHolder.memberAddress.setText(arrayList.get(2));
        memberViewHolder.phoneNo.setText(arrayList.get(3));
        memberViewHolder.unpaidDues.setText(arrayList.get(4));
        return convertView;
    }
}
