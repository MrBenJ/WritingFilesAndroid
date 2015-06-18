package com.prismmobile.cyberdustcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Custom Adapter
 * Created by benjunya on 6/18/15.
 */
public class textAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> mList;

    public textAdapter(Context context, List<String> stringList) {
        mInflater = LayoutInflater.from(context);
        mList = stringList;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.text_listview, null);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.listText);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }


}
