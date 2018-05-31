package br.com.caelum.casadocodigo.fcm;

import com.google.firebase.iid.FirebaseInstanceId;

public class FCMRegister {

    public String getId(){
        FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();

        String token = firebaseInstanceId.getToken();

        return token;
    }
}
