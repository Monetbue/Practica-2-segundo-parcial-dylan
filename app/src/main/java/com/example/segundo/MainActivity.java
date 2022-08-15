package com.example.segundo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button boton1;
    Button boton2;
    Button boton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton1 = (Button)findViewById(R.id.boton1);
        boton2=(Button)findViewById(R.id.boton2);
        boton3=(Button)findViewById(R.id.boton3);

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reproducirVideo();
                Intent intent = new Intent(getApplicationContext(), video.class);
                startActivity(intent);

            }
        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproducirAudio();
            }
        });
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarNotificacion();
            }
        });
    }
    private void reproducirVideo() {
        Uri myUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            mediaPlayer.setDataSource(getApplicationContext(),myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }catch(IllegalStateException e1){
            e1.printStackTrace();
        }catch(SecurityException e2){
            e2.printStackTrace();
        }catch(IllegalArgumentException e3){
            e3.printStackTrace();
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });

    }

    private void reproducirAudio() {
        Uri myUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sound);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(this,myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    private void mostrarNotificacion() {
        int NOTIFICATION_ID= 1;
        Context context=getApplicationContext();
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "mi_canal_1";
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence nombre= "canal_1";
            String description = "Este sera mi nuevo canal";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mCanal= new NotificationChannel(CHANNEL_ID,nombre,importance);
            mCanal.setDescription(description);
            mCanal.enableLights(true);
            mCanal.setLightColor(Color.RED);
            mCanal.enableVibration(true);
            mCanal.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            mCanal.setShowBadge(false);
            notificationManager.createNotificationChannel(mCanal);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.descargar)
                .setContentTitle("Recibiste un nuevo mensaje")
                .setContentText("Xenoblade 3 ya esta a la venta");
        Intent intent = new Intent(context,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID,builder.build());


    }

}