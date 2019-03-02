package com.headspire.alarmmangertest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class Operation {

    private static final int REQUEST_CODE =1 ;
    private String IMEINumber;
    private String subscriberID;
    private String SIMSerialNumber;
    private String networkCountryISO;
    private String SIMCountryISO;
    private String softwareVersion;
    private String voiceMailNumber;
    private StringBuffer phoneType;
    private StringBuffer phoneInfo;
    private Context context;
    public Operation(Context context)
    {
        this.context=context;
    }
    @SuppressLint("HardwareIds")
    public StringBuffer phoneStateLoad() {
        phoneInfo=new StringBuffer();
        phoneType=new StringBuffer();
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                IMEINumber = telephonyManager.getImei();
                subscriberID=telephonyManager.getSubscriberId();
            }
            else
            {
                getPermission();
            }
        }
        else
        {
            IMEINumber=telephonyManager.getDeviceId();
            subscriberID=telephonyManager.getDeviceId();
        }
        SIMCountryISO=telephonyManager.getSimCountryIso();
        SIMSerialNumber=telephonyManager.getSimSerialNumber();
        networkCountryISO=telephonyManager.getNetworkCountryIso();
        softwareVersion=telephonyManager.getDeviceSoftwareVersion();
        voiceMailNumber=telephonyManager.getVoiceMailNumber();

        switch (telephonyManager.getPhoneType())
        {
            case TelephonyManager.PHONE_TYPE_CDMA:
                phoneType.append(" CDMA");
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                phoneType.append(" GSM");
                break;
            case TelephonyManager.PHONE_TYPE_NONE:
                phoneType.append(" NONE");
                break;
        }
        phoneInfo.append(context.getResources().getString(R.string.phone_detail));
        phoneInfo.append("\nIMEI Number:").append(IMEINumber)
                .append("\nSubscriberID:"+subscriberID)
                .append("\nSim Serial Number:"+SIMSerialNumber)
                .append("\nNetwork Country ISO:"+networkCountryISO)
                .append("\nSim Country ISO:"+SIMCountryISO)
                .append("\nSoftware Version:"+softwareVersion)
                .append("\nVoice Mail Number:"+voiceMailNumber)
                .append("\nPhone type:"+phoneType)
                .append("\nIn Roaming:"+telephonyManager.isNetworkRoaming());
        return phoneInfo;
    }

    public void getPermission() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(context
        ,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.INTERNET,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_PHONE_STATE},REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.INTERNET
                                , Manifest.permission.READ_PHONE_STATE}
                        ,REQUEST_CODE);
            }
        }
    }
}
