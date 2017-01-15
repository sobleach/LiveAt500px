package simple.com.thum.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import simple.com.thum.liveat500px.R;
import simple.com.thum.liveat500px.dao.PhotoItemDao;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class PhotoSummaryFlagment extends Fragment {
    PhotoItemDao dao;
    ImageView ivImg;
    TextView tvName;
    TextView tvDescription;
    public PhotoSummaryFlagment() {
        super();
    }

    @SuppressWarnings("unused")
    public static PhotoSummaryFlagment newInstance(PhotoItemDao dao) {
        PhotoSummaryFlagment fragment = new PhotoSummaryFlagment();
        Bundle args = new Bundle();
        args.putParcelable("dao",dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        dao = getArguments().getParcelable("dao");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flagment_photo_summary, container, false);
        initInstances(rootView, savedInstanceState);

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        ivImg = (ImageView) rootView.findViewById(R.id.ivImg);
        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);

        tvName.setText(dao.getCaption());
        tvDescription.setText(dao.getUsername() + "\n" + dao.getCamera());
        Glide.with(PhotoSummaryFlagment.this)
                .load(dao.getImageUrl())
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImg);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
