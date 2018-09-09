package com.sergey.root.orderkkt;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.sergey.root.orderkkt.Activity.MainActivity;
import com.sergey.root.orderkkt.DataBase.OrderLab;
import com.sergey.root.orderkkt.Model.ListItem;
import com.yandex.disk.rest.ProgressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class YandexService extends IntentService implements ProgressListener{
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS


    public YandexService() {
        super("YandexService");
    }

    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onHandleIntent(Intent intent) {
        File file = new File(Environment.getExternalStorageDirectory(), "Order");
        if (!file.exists()) {
            file.mkdirs();
        }
        if(!isNetworkAvailableAndConnected()){
            return;
        }
        Yandex yandex  = new Yandex(this);
        ArrayList<File> mFiles = new ArrayList<>();
        File[]files = file.listFiles();
        for(File f : files){
            if(f.isFile()){
                mFiles.add(f);
            }
        }
        if(mFiles.size() == 0){
            return;
        }
        for(File f:mFiles){
            yandex.upLoadFile(f,YandexService.this);
            f.delete();
        }
        File file1 = new File(file.getAbsoluteFile()+"/goods.xml");
        ArrayList<ListItem> items = yandex.getItem();
        if(items.size() == 0){
            return;
        }
        ListItem item = items.get(0);
        yandex.dowloadsFile(item.getName(),file1,YandexService.this);
        OrderLab.getInstance(this).setXML(file1);
        yandex.delete(item.getPath());

        Intent i = MainActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        Notification notification = new NotificationCompat.Builder(this).setTicker("Данные обновлены")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Добавлены новые заказы")
                .setContentText("Добавлены заказы")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);

    }

    @Override
    public void updateProgress(long loaded, long total) {

    }

    @Override
    public boolean hasCancelled() {
        return false;
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, YandexService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = YandexService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
    public static boolean isServiceAlarmOn(Context context) {
        Intent i = YandexService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

}
