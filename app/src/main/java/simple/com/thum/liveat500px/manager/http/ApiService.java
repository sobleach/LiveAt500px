package simple.com.thum.liveat500px.manager.http;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import simple.com.thum.liveat500px.dao.PhotoItemCollectionDao;

/**
 * Created by Thum on 8/1/2560.
 */

public interface ApiService {

    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemCollectionDao> loadPhotoListBeforeId(@Path("id") int id);
}
