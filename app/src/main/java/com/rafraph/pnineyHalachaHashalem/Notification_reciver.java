package com.rafraph.pnineyHalachaHashalem;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class Notification_reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent pninaYomitRepit= new Intent(context,pninaYomit.class);
        pninaYomitRepit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,pninaYomitRepit,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"notifyLemubit")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.pniney_icon)
                .setContentTitle("הפנינה היומית")
                .setContentText("הגיע הזמן ללימוד הפנינה היומית")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100,builder.build());
    }
}
