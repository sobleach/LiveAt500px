package simple.com.thum.liveat500px.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import simple.com.thum.liveat500px.R;
import simple.com.thum.liveat500px.dao.PhotoItemDao;
import simple.com.thum.liveat500px.fragment.MoreInfoFlagment;

public class MoreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        innitInstances();
        PhotoItemDao dao = getIntent().getParcelableExtra("dao");

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.moreInfoContent, MoreInfoFlagment.newInstance(dao))
                    .commit();
        }
    }

    private  void innitInstances(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
