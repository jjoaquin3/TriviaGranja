package com.example.joaquin.triviagranja.jose;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.joaquin.triviagranja.MainActivity;
import com.example.joaquin.triviagranja.R;
import com.example.joaquin.triviagranja.victor.Rext;

public class video2 extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Rext j = new Rext("Trivia");
        this.startVideo(j.ruta()+"/video1.mp4");
    }

    private void startVideo(String ruta)
    {
        video = (VideoView) findViewById(R.id.videoView_video);
        video.setVideoPath(ruta);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                    }
                }
        );
        video.start();
    }

    public void video2_btnTouch(View v)
    {
        video.stopPlayback();
        MainActivity.fondo.start();
        this.finish();
    }
}
