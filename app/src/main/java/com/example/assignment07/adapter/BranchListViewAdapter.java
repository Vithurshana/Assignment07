package com.example.assignment07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.assignment07.R;

import java.util.ArrayList;

public class BranchListViewAdapter extends BaseAdapter {

    public ArrayList<ArrayList> list;
    Context context;

    private class BranchViewHolder {
        TextView branchId;
        TextView branchName;
        TextView address;
    }

    public BranchListViewAdapter(Context context, ArrayList<ArrayList> list ){
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
        BranchViewHolder branchViewHolder = new BranchViewHolder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.branch_view_row, null);
            branchViewHolder.branchId = (TextView) convertView.findViewById(R.id.txtBranchId);
            branchViewHolder.branchName = (TextView) convertView.findViewById(R.id.txtBranchName);
            branchViewHolder.address = (TextView) convertView.findViewById(R.id.txtBranchAddress);
            convertView.setTag(branchViewHolder);
        } else {
            branchViewHolder = (BranchViewHolder) convertView.getTag();
        }

        ArrayList<String> arrayList = list.get(position);
        branchViewHolder.branchId.setText(arrayList.get(0));
        branchViewHolder.branchName.setText(arrayList.get(1));
        branchViewHolder.address.setText(arrayList.get(2));
        return convertView;
    }
}
