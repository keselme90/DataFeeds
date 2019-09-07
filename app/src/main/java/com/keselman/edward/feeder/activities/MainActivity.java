package com.keselman.edward.feeder.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.keselman.edward.feeder.Api;
import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.adapters.PostsAdapter;
import com.keselman.edward.feeder.listeners.ClickListener;
import com.keselman.edward.feeder.listeners.JsonPlaceHolderApi;
import com.keselman.edward.feeder.listeners.RecyclerTouchListener;
import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Root;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<Root> {

    private static final String TAG = "MainActivity";
    private List<Entry> mPostList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private PostsAdapter mAdapter;
    private SearchView mSearchView;
    private FrameLayout mFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mAdapter = new PostsAdapter(MainActivity.this, mPostList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener( new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d(TAG, "onClick: ");
                Entry entry = mPostList.get(position);

                openNextActivity(entry);
            }
        }));
//        preparePostData();

        JsonPlaceHolderApi jsonPlaceHolderApi = Api.getInstance().getUserService();
        Call<Root> call = jsonPlaceHolderApi.getLinkPosts();
        call.enqueue(MainActivity.this);

        call = jsonPlaceHolderApi.getVideoPosts();
        call.enqueue(MainActivity.this);

    }

    private void openNextActivity(Entry entry) {

        Intent intent;
        if(entry.getType().getValue().toLowerCase().equals("link"))
            intent =  new Intent(MainActivity.this, WebViewActivity.class);
        else
            intent =  new Intent(MainActivity.this, VideoViewActivity.class);

        intent.putExtra("URL_TO_OPEN", entry.getLink().getHref());
        intent.putExtra("TITLE", entry.getTitle());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Call<Root> call, Response<Root> response) {

        if(response.isSuccessful()) {
            if(response.body() != null) {
                mPostList.addAll(response.body().getEntry());
                mAdapter.notifyDataSetChanged();
            }
        } else {
            Log.d(TAG, "onResponse: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<Root> call, Throwable t) {

        t.printStackTrace();
    }
}
