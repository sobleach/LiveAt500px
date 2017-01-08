package simple.com.thum.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import simple.com.thum.liveat500px.view.PhotoListItem;

/**
 * Created by Thum on 8/1/2560.
 */

public class PhotoListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 10000;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position % 2 == 0) {
            PhotoListItem item;
            if (convertView != null) {
                item = (PhotoListItem) convertView;
            } else {
                item = new PhotoListItem(parent.getContext());
            }
            return item;
        } else {
            TextView item;
            item = new TextView(parent.getContext());
            item.setText("Position: " + position);
            return item;
        }
    }
}
