package me.carda.awesome_notifications.notifications.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.carda.awesome_notifications.BroadcastSender;
import me.carda.awesome_notifications.notifications.NotificationBuilder;
import me.carda.awesome_notifications.notifications.managers.DefaultsManager;
import me.carda.awesome_notifications.notifications.models.returnedData.ActionReceived;

import io.flutter.Log;
import me.carda.awesome_notifications.utils.BitmapUtils;
import me.carda.awesome_notifications.utils.StringUtils;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
/**
 * Created by michaelbui on 24/3/18.
 */

public class KeepOnTopActionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        ActionReceived actionReceived = NotificationBuilder.buildNotificationActionFromIntent(context, intent);


        if (actionReceived != null) {
            String isHandleNative = actionReceived.payload.get("handle-natively");
            if(isHandleNative!=null) {
                if(isHandleNative.equals("true") ){
                     handleHere(actionReceived,context);
                    return;
                }
            }
            try {

                BroadcastSender.SendBroadcastKeepOnTopAction(
                        context,
                        actionReceived
                );


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private  void handleHere(ActionReceived action, Context context){
        String body = action.payload.get("answertext") ;
        boolean isTrue =  action.actionKey.equals("true") ;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, action.channelKey )
                .setSmallIcon(getDefaultIcon(context))
                .setContentTitle( isTrue ? "اجابة صحيحة" : "اجابة خطا" )
                .setContentText(body)
                .setColor( isTrue ? 0xFF4CAF50 : 0xFFF44336 )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int foor each notification that you must define
        notificationManager.notify(action.id , builder.build());

    }

    private int getDefaultIcon(Context context){
        String defaultIcon = DefaultsManager.getDefaultIconByKey(context);

        if (StringUtils.isNullOrEmpty(defaultIcon)) {


                int defaultResource = context.getResources().getIdentifier(
                        "ic_launcher",
                        "mipmap",
                        context.getPackageName()
                );

                if(defaultResource > 0){
                 return  defaultResource;
                }

        } else {
            int resourceIndex = BitmapUtils.getDrawableResourceId(context, defaultIcon);
            if(resourceIndex > 0){
                return resourceIndex;
            }
        }
        return 0;

    }
}
