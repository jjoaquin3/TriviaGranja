package com.example.joaquin.triviagranja.jose;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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
        this.startVideo(j.ruta()+"/video.mp4");
    }

    private void startVideo(String ruta)
    {
        video = (VideoView) findViewById(R.id.videoView_video);
        video.setVideoPath(ruta);
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer)
            {
                mediaPlayer.setLooping(true);
            }
        });
        video.start();
    }

    public void video2_btnTouch(View v)
    {
        video.stopPlayback();
        MainActivity.mp_fondo.start();
        this.finish();
    }

    public void onBackPressed()
    {
        try
        {
            if(video.isPlaying())
                video.stopPlayback();
            MainActivity.mp_fondo.start();
        } catch (Exception e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
        this.finish();
    }

    public void onPause() {
        System.exit(0);
        super.onPause();
    }

//    @Override
//    protected void onUserLeaveHint() {
//        try
//        {
//            if(MainActivity.mp_fondo.isPlaying())
//                MainActivity.mp_fondo.stop();
//        } catch (Exception e)
//        {
//            Log.v(getString(R.string.app_name), e.getMessage());
//        }
//        this.finish();
//        //Toast.makeText(MainActivity.this, "Home button pressed", Toast.LENGTH_LONG).show();
//        super.onUserLeaveHint();
//    }

}
