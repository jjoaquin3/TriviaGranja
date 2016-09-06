package com.example.joaquin.triviagranja.jose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

import com.example.joaquin.triviagranja.R;

public class video extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.startVideo("/sdcard/video.mp4");
    }

    private void startVideo(String ruta)
    {
        video = (VideoView) findViewById(R.id.videoView_video);
        video.setVideoPath(ruta);
        video.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Log.i("ActionBar","Atras!");
                finish();
                return  true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    public void video_VideoView(View v)
    {
        this.finish();
    }
}
