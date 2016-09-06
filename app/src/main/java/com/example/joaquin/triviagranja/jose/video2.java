package com.example.joaquin.triviagranja.jose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.joaquin.triviagranja.R;

public class video2 extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        this.startVideo("/sdcard/video.mp4");
    }

    private void startVideo(String ruta)
    {
        video = (VideoView) findViewById(R.id.videoView_video);
        video.setVideoPath(ruta);
        video.start();
    }

    public void video2_btnTouch(View v)
    {
        this.finish();
    }
}
