package simple.com.thum.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import simple.com.thum.liveat500px.R;
import simple.com.thum.liveat500px.adapter.PhotoListAdapter;
import simple.com.thum.liveat500px.dao.PhotoItemCollectionDao;
import simple.com.thum.liveat500px.manager.HttpManager;
import simple.com.thum.liveat500px.manager.PhotoListManager;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView;
    PhotoListAdapter listAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    PhotoListManager photoListManager;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        photoListManager = new PhotoListManager();
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
            }
        });

        refreshData();
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadDataNewer();
        }

    }

    class PhotoListLoadfCallBack implements Callback<PhotoItemCollectionDao> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;

        int mode;

        public PhotoListLoadfCallBack(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemCollectionDao dao = response.body();
                if (mode == MODE_RELOAD_NEWER)
                    photoListManager.insertDaoAtTopPosition(dao);
                else if (mode == MODE_RELOAD)
                    photoListManager.setDao(dao);
                listAdapter.setDao(photoListManager.getDao());
                listAdapter.notifyDataSetChanged();
                Toast.makeText(Contextor.getInstance().getContext(),
                        "Laod Completed",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                // Handle
                try {
                    Toast.makeText(Contextor.getInstance().getContext(),
                            response.errorBody().string(),
                            Toast.LENGTH_SHORT)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Contextor.getInstance().getContext(),
                    t.toString(),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadfCallBack(PhotoListLoadfCallBack.MODE_RELOAD_NEWER));

    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadfCallBack(PhotoListLoadfCallBack.MODE_RELOAD));
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
