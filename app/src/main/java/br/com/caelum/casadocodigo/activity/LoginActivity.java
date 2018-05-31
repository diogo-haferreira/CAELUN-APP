package br.com.caelum.casadocodigo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.fcm.FCMRegister;
import br.com.caelum.casadocodigo.ws.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @BindView(R.id.login_email)
    EditText campoEmail;
    @BindView(R.id.login_senha)
    EditText campoSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (taLogado(currentUser)) {
            vaiParaMain();
        }

    }

    private void vaiParaMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean taLogado(FirebaseUser currentUser) {
        return currentUser != null;
    }


    @OnClick(R.id.login_novo)
    public void criaUsuario() {

        final String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            String idDoCelular = new FCMRegister().getId();

                            WebClient webClient = new WebClient();

                            webClient.registraAparelho(email, idDoCelular);


                            vaiParaMain();
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setIcon(R.drawable.casadocodigo)
                                    .setTitle("Desculpe")
                                    .setMessage(task.getException().getMessage())
                                    .show();
                        }
                    }
                });
    }

    @OnClick(R.id.login_logar)
    public void logaUsuario() {

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            vaiParaMain();
                        } else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setIcon(R.drawable.casadocodigo)
                                    .setTitle("Desculpe")
                                    .setMessage(task.getException().getMessage())
                                    .show();
                        }
                    }
                });
    }

}
