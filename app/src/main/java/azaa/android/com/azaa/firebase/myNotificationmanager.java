package azaa.android.com.azaa.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import azaa.android.com.azaa.R;

public class myNotificationmanager {

    public static final int NOTIFICATION_ID = 234;
    private Context ctx;

    public myNotificationmanager(Context ctx){
        this.ctx =  ctx;
    }

    public void showNotification(String from, String notification, Intent intent)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                ctx,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);

        Notification mnotification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),R.mipmap.ic_launcher))
                .build();
        mnotification.flags |=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mnotification);
    }

}
