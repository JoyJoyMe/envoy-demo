package com.vender;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import android.support.v4.app.NotificationCompat;



public class ForegroundService extends Service {

    private static final long TIMEOUT_MILLS = 1000L;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, "fcm_default_channel_persistent")
                .setContentTitle("Foreground Service Channel")
                .setContentText(input)
                .setContentIntent(pendingIntent)
                .build();


        startForeground(1, notification);

//        scheduleTimeout();
        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    public void scheduleTimeout(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //timeout logic
                Log.d("scheduleTimeout", "111111111111111111");
                get();
                handler.postDelayed(this, 1000 * 60);

            }
        };
        handler.post(runnable);
    }

    public void get() {
        //开启线程，发送请求
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                Log.i("TAG_GET","1111");
                URL url = null;

                try {
                    url = new URL("https://www.baidu.com");

                    // 打开一个HttpURLConnection连接
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时时间
                    urlConn.setConnectTimeout(6 * 1000);
                    // 开始连接
                    urlConn.connect();
                    Log.i("TAG_GET","1111===" + urlConn.getResponseCode());

//                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
//                    toneG.startTone(24);

//                    player.start();
                    // 判断请求是否成功
                    if (urlConn.getResponseCode() == 200) {
                        AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.voyager);
                        MediaPlayer player = new MediaPlayer();
                        player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                            }
                        });
                        player.prepareAsync();



                    }
                    // 关闭连接
                    urlConn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//            urlConn.disconnect();
                }
            }
        }).start();
    }



}