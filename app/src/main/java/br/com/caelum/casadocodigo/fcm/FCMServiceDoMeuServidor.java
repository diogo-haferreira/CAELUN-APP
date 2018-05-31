package br.com.caelum.casadocodigo.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.activity.MainActivity;

public class FCMServiceDoMeuServidor extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();


        String message = data.get("message");

        PendingIntent intencao = PendingIntent.getActivity(getApplication(), 123,
                new Intent(getApplication(), MainActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT);


        Notification notificacao = new NotificationCompat.Builder(getApplication())
                .setContentTitle("Olá, chegou uma nova notificação")
                .setContentText(message)
                .setContentIntent(intencao)
                .setSmallIcon(R.drawable.casadocodigo)
                .setAutoCancel(true)
                .build();


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(456, notificacao);

    }
}
