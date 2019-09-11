package com.keselman.edward.feeder.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.MediaController;
import android.widget.VideoView;

import com.keselman.edward.feeder.R;

import java.net.URI;

public class VideoViewActivity extends AppCompatActivity {

    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("TITLE"));
        setSupportActionBar(toolbar);

        mVideoView = findViewById(R.id.videoview);

        Uri uri = Uri.parse(intent.getStringExtra("URL_TO_OPEN"));
        mVideoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(VideoViewActivity.this);
        mVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(mVideoView);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
