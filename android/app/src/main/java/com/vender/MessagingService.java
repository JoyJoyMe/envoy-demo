package com.vender;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    public static String LOG_TAG = "MyLog";

    @Override
    public void onNewToken(@NonNull String token) {
        // Upload token
//        JSONObject body = new JSONObject();
//        try {
//            body.put("new_message_token", token);
//        } catch (JSONException e) {
//            Log.i(LOG_TAG, "Invalid json");
//        }
//        Log.i(LOG_TAG, "Fetching new token");
//        String url = "https://restaurant-portal.foodtruck-uat.com/cloud-message/replace";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, body,
//                response -> Log.i(LOG_TAG, response.toString()),
//                error -> Log.i(LOG_TAG, error.toString())
//        );
//        Volley.newRequestQueue(this).add(request);
//        Log.i(LOG_TAG, "Request queued");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        logNotification(
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody()
        );
    }

    private void logNotification(String title, String body) {
        Log.i(LOG_TAG, "Message Notification: " + title + " " + body);
        // toast.show();
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2 , intent, 0);
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, "fcm_new_order_channel")
//                        .setContentTitle("FAKE title")
//                        .setContentText("YAY!!")
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(2 /* ID of notification */, notificationBuilder.build());
//        Log.i(LOG_TAG, "Notification succeeded");
    }


}
