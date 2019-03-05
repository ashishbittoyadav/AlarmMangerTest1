package com.headspire.alarmmangertest;

import android.Manifest;
import android.Manifest.permission;
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
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @version 1.0  02-03-2019
 * @Objective demonstrate the use of AlarmManager TelephonyManager and DownloadManager.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,Serializable {
    private static final int REQUEST_CODE =1 ;
    private TimePicker setTime;
    private Button setAlarm;
    private Button cancel;
    private Operation operation;
    
    private Button phoneState;

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

        phoneState=findViewById(R.id.phone_state);
        phoneState.setOnClickListener(this);
        operation=new Operation(this);

        cancel.setOnClickListener(this);
        setAlarm.setOnClickListener(this);
        calendar=Calendar.getInstance();

        operation.getPermission();
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
            case R.id.phone_state:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new PhoneInformation(operation))
                        .addToBackStack(null)
                        .commit();
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
