package com.headspire.alarmmangertest;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @version 1.0  02-03-2019
 * @Objective demonstrate the use of AlarmManager and DownloadManager.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,Serializable {
    private static final int REQUEST_CODE =1 ;
    private TimePicker setTime;
    private Button setAlarm;
    private Button cancel;
    private AlarmManager alarmManager;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTime=findViewById(R.id.set_up_alarm);
        setAlarm=findViewById(R.id.set_alarm);
        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        setAlarm.setOnClickListener(this);
        calendar=Calendar.getInstance();

        getPermission();
    }

    private void getPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET}
                        ,REQUEST_CODE);
            }
        }
        else
            return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.set_alarm:

                if(Build.VERSION.SDK_INT>=23) {
                    calendar.set(calendar.get(Calendar.YEAR)
                            , calendar.get(Calendar.MONTH)
                            , calendar.get(Calendar.DAY_OF_MONTH),
                            setTime.getHour(), setTime.getMinute(), 0);
                }
                else
                    calendar.set(calendar.get(Calendar.YEAR)
                            , calendar.get(Calendar.MONTH)
                            , calendar.get(Calendar.DAY_OF_MONTH),
                            setTime.getCurrentHour(), setTime.getCurrentMinute(), 0);
                setAlarmTo(calendar.getTimeInMillis());
                break;
            case R.id.cancel:
                if(alarmManager!=null)
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(this,"job cancelled",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setAlarmTo(long timeInMillis) {
        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmIntent=new Intent(getApplicationContext(),MyAlarm.class);
        pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),REQUEST_CODE,alarmIntent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(this,"job is scheduled..",Toast.LENGTH_SHORT).show();
    }
}
