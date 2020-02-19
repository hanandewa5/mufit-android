package com.nostratech.mufit.consumer.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.NotificationHandler;

import java.util.Map;

public class MyFirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseService";
    private NotificationHandler notificationHandler;

    //Callback ketika firebase push notif masuk
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(notificationHandler == null) notificationHandler = new NotificationHandler(this);

        if( remoteMessage.getData().size() > 0 ){
            String action = remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.ACTION);

            switch(action){
                case Constants.NotificationAction.CHAT_ACTIVATED:
                    //TODO: CometChat Login
                    break;

                case Constants.NotificationAction.OPEN_HOME:
                    notificationHandler.showEventVerifiedNotification(remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.TITLE),
                            remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.BODY));
                    break;

                case Constants.NotificationAction.OPEN_NEWS:
                    notificationHandler.showNewsVerifiedNotification(remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.TITLE),
                            remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.BODY));
                    break;

                case Constants.NotificationAction.OPEN_BOOKING:
                    notificationHandler.showBookingVerifiedNotification(remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.TITLE),
                            remoteMessage.getData().get(Constants.FirebaseNotificationDataPayload.BODY),
                            remoteMessage.getData().get("booking_id"));
                    break;
                default:
                    break;
            }

            ///Logging
            Map<String,String> map = remoteMessage.getData();
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
            }

        }
    }


    /**
     *
     * In some situations, FCM may not deliver a message. This occurs when there are too many messages (>100) pending for your app
     * on a particular device at the time it connects or if the device hasn't connected to FCM in more than one month.
     *  In these cases, you may receive a callback to FirebaseMessagingService.onDeletedMessages() When the app instance receives this callback,
     *  it should perform a full sync with your app server.
     *  If you haven't sent a message to the app on that device within the last 4 weeks, FCM won't call onDeletedMessages().
     */
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }
}
