package simple.com.thum.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import simple.com.thum.liveat500px.R;
import simple.com.thum.liveat500px.dao.PhotoItemCollectionDao;
import simple.com.thum.liveat500px.dao.PhotoItemDao;
import simple.com.thum.liveat500px.datatype.MutableInteger;
import simple.com.thum.liveat500px.view.PhotoListItem;

/**
 * Created by Thum on 8/1/2560.
 */

public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDao dao;
    MutableInteger lastPosition;

    public PhotoListAdapter(MutableInteger lastPosition) {
        this.lastPosition = lastPosition;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;
        if (convertView != null) {
            item = (PhotoListItem) convertView;
        } else {
            item = new PhotoListItem(parent.getContext());
        }

        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        if (position > lastPosition.getValue()) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition.setValue(position);
        }

        return item;
    }

    public void increaseLastPosition(int additionalSize) {
        lastPosition.setValue(lastPosition.getValue() + additionalSize);
    }
}
