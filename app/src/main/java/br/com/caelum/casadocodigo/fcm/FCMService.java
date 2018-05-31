package br.com.caelum.casadocodigo.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import br.com.caelum.casadocodigo.eventos.MensagemRecebida;

public class FCMService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Map<String, String> informacoes = remoteMessage.getData();

        String cupom = informacoes.get("cupom");

        EventBus.getDefault().post(new MensagemRecebida(cupom));


    }
}
