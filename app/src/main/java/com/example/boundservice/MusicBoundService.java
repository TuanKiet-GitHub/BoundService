package com.example.boundservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicBoundService  extends Service {
    private MyBinder mBinder = new MyBinder();
    public static final String log = "Log";
    private MediaPlayer mediaPlayer ;
    public class MyBinder extends Binder {
        MusicBoundService getMusicBoundService()
        {
            return MusicBoundService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(log , "On Create !!!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(log , "On Destroy !!!");
        if (mediaPlayer!=null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(log , "On Un Bind !!!");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(log , "On Bind !!!");
        return mBinder;
    }
    public void startMusic()
    {
        if(mediaPlayer == null)
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext() , R.raw.saigondaulongqua);
        }
        mediaPlayer.start();

    }
}
