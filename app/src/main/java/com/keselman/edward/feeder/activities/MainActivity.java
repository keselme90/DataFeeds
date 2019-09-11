package com.keselman.edward.feeder.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.keselman.edward.feeder.network.Api;
import com.keselman.edward.feeder.R;
import com.keselman.edward.feeder.adapters.PostsAdapter;
import com.keselman.edward.feeder.listeners.ClickListener;
import com.keselman.edward.feeder.listeners.JsonPlaceHolderApi;
import com.keselman.edward.feeder.listeners.RecyclerTouchListener;
import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Root;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private List<Entry> mPostList;
    private RecyclerView mRecyclerView;
    private PostsAdapter mAdapter;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;
    private FloatingActionButton mFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("עדכוני חדשות");
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.progressBar);
        mFAB = findViewById(R.id.fab);
        mFAB.hide();
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFAB.hide();
                fetchPosts();
            }
        });

        mPostList = new ArrayList<>();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new PostsAdapter(MainActivity.this, mPostList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener( new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d(TAG, "onClick: ");
                Entry entry = mPostList.get(position);

                if(entry.getType().getValue().toLowerCase().equals("link"))
                    openNextActivity(entry);
                else
                    openVideoDialog(entry);
            }
        }));

        fetchPosts();

    }

    private void fetchPosts() {

        mProgressBar.setVisibility(View.VISIBLE);
        JsonPlaceHolderApi jsonPlaceHolderApi = Api.getInstance().getUserService();
        List<Observable<?>> requests = new ArrayList<>();
        requests.add(jsonPlaceHolderApi.getLinkPosts());
        requests.add(jsonPlaceHolderApi.getVideoPosts());

        Observable.zip(requests, new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] objects) {
                if(objects.length > 0){
                    for(Object o : objects){
                        mPostList.addAll(((Root) o).getEntry());
                    }
                    return true;
                }
                return false;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Object>() {

                     @Override
                     public void accept(final Object o) {

                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {

                                 mProgressBar.setVisibility(View.GONE);
                                 mAdapter.notifyDataSetChanged();
                                 mAdapter.setFullList();
                                 if(!(boolean)o){
                                     showErrorDialog();
                                 }
                             }
                         });
                         Log.d(TAG, "accept: ");
                     }
                },
                // Will be triggered if any error during requests will happen
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mProgressBar.setVisibility(View.GONE);
                                showErrorDialog();
                            }
                        });
                        //Do something on error completion of requests
                    }
                });
    }

    private void showErrorDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("שגיאה");
        alertDialog.setMessage("משהו השתבש בזמן נסיון משיכת פוסטים. אנא נסה שנית מאוחר יותר.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "סגור",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mFAB.show();
                            }
                        });

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "נסה שנית", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mFAB.hide();
                fetchPosts();
            }
        });
        alertDialog.show();
    }

    private void openVideoDialog(Entry entry) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_player_dialog);
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
        MediaController mediaController = new MediaController(MainActivity.this);
        final VideoView videoview = (VideoView) dialog.findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse(entry.getContent().getSrc()));
        videoview.setMediaController(mediaController);
        mediaController.setAnchorView(videoview);
        videoview.start();
    }

    private void openNextActivity(Entry entry) {

        Intent intent;
        if(entry.getType().getValue().toLowerCase().equals("link")) {
            intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("URL_TO_OPEN", entry.getLink().getHref());
        }
        else {
            intent = new Intent(MainActivity.this, VideoViewActivity.class);
            intent.putExtra("URL_TO_OPEN", entry.getContent().getSrc());
        }
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
//                mAdapter.getFilter().filter(query);
                return true;
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
}
