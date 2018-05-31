package br.com.caelum.casadocodigo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.delegates.LivroDelegate;
import br.com.caelum.casadocodigo.eventos.MensagemRecebida;
import br.com.caelum.casadocodigo.fragments.CarregandoFragment;
import br.com.caelum.casadocodigo.fragments.DetalheLivroFragment;
import br.com.caelum.casadocodigo.fragments.ErroFragment;
import br.com.caelum.casadocodigo.fragments.ListaLivrosFragment;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.ws.WebClient;

public class MainActivity extends AppCompatActivity implements LivroDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            exibe(new CarregandoFragment(), false);


            new WebClient().buscaPelosLivros(0, 10);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @NonNull
    public ListaLivrosFragment listaCom(ArrayList<Livro> livros) {
        ListaLivrosFragment lista = new ListaLivrosFragment();
        Bundle arguments = new Bundle();

        arguments.putSerializable("lista", livros);

        lista.setArguments(arguments);
        return lista;
    }

    public void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.frame_principal, fragment);

        if (empilhado) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }


    @NonNull
    private DetalheLivroFragment detalhesDo(Livro livro) {
        DetalheLivroFragment detalhes = new DetalheLivroFragment();

        Bundle arguments = new Bundle();

        arguments.putSerializable("livro", livro);

        detalhes.setArguments(arguments);
        return detalhes;
    }

    @Override
    @Subscribe
    public void lidaComLivroSelecionado(Livro livro) {
        exibe(detalhesDo(livro), true);

    }

    @Override
    @Subscribe
    public void lidaComLista(ArrayList<Livro> livros) {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.frame_principal);

        if (fragment instanceof ListaLivrosFragment) {
            ListaLivrosFragment listaLivrosFragment = (ListaLivrosFragment) fragment;

            listaLivrosFragment.atualiza(livros);
        } else {
            exibe(listaCom(livros), false);

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recebe(MensagemRecebida mensagem) {

        Toast.makeText(this, mensagem.getCupom(), Toast.LENGTH_SHORT).show();
    }

    @Override
    @Subscribe
    public void lidaComErro(Throwable erro) {


        exibe(fragmentCom(erro), false);

    }

    private ErroFragment fragmentCom(Throwable erro) {
        ErroFragment erroFragment = new ErroFragment();

        Bundle arguments = new Bundle();

        arguments.putSerializable("erro", erro);

        erroFragment.setArguments(arguments);

        return erroFragment;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;

            case R.id.main_carrinho:

                Intent intent = new Intent(this, CarrinhoActivity.class);
                startActivity(intent);

                return true;

            case R.id.main_logout:
                FirebaseAuth.getInstance().signOut();

                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu, menu);


        return true;

    }
}
