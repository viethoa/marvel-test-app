package com.viethoa.potdemo.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VietHoa on 23/06/15.
 */
public class BaseArrayAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> dataArray;

    public BaseArrayAdapter(Context context, List<T> data) {
        this.context = context;
        this.dataArray = data;
    }

    public void refreshData(List<T> data) {
        dataArray = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (dataArray == null) {
            return 0;
        }
        return dataArray.size();
    }

    @Override
    public T getItem(int i) {
        if (dataArray == null) {
            return null;
        }
        if (i < 0 || i >= dataArray.size()) {
            return null;
        }

        return dataArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (dataArray == null) {
            return 0;
        }
        if (i >= dataArray.size()) {
            return 0;
        }

        return dataArray.get(i).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        return null;
    }
}
