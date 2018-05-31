package br.com.caelum.casadocodigo.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.casadocodigo.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                trocaTela();
            }
        }, 1000);


    }

    private void trocaTela() {

        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.logo), "logo").toBundle();
        startActivity(intent, bundle);
        finish();
    }
}
