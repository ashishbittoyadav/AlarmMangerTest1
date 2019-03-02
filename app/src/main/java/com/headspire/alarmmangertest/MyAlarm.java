package com.headspire.alarmmangertest;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class MyAlarm extends BroadcastReceiver {
    private DownloadManager downloadManager;
    //path of the resource to be download....
    private String path="https://mp3.gisher.org/download/1613";
    int dID;

    /**
     * onReceive will contain code that will executed to be on specified time.
     * @param context Main Activity context
     * @param intent intent from main activity.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(path));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.setTitle("Broken_Dreams.mp3");
        request.setDescription("downloading.....");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/mysong"+"broken_dreams.mp3");
        dID= (int) downloadManager.enqueue(request);
        Toast.makeText(context,"downloading started...",Toast.LENGTH_SHORT).show();
    }
}
