package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class PublisherListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class PublisherViewHolder {
        TextView publisherName;
        TextView publisherAddress;
        TextView phoneNo;
    }

    public PublisherListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        PublisherViewHolder publisherViewHolder = new PublisherViewHolder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.publisher_view_row, null);
            publisherViewHolder.publisherName = (TextView) convertView.findViewById(R.id.txtPubName);
            publisherViewHolder.publisherAddress = (TextView) convertView.findViewById(R.id.txtPubAddress);
            publisherViewHolder.phoneNo = (TextView) convertView.findViewById(R.id.txtPubPhone);
            convertView.setTag(publisherViewHolder);
        } else {
            publisherViewHolder = (PublisherViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        publisherViewHolder.publisherName.setText(arrayList.get(0));
        publisherViewHolder.publisherAddress.setText(arrayList.get(1));
        publisherViewHolder.phoneNo.setText(arrayList.get(2));
        return convertView;
    }
}
