package br.com.caelum.casadocodigo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.BaseAttacher;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.adapter.LivroAdapter;
import br.com.caelum.casadocodigo.adapter.LivroZebradoAdapter;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.ws.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaLivrosFragment extends Fragment {

    @BindView(R.id.lista_livros)
    RecyclerView lista;
    private ArrayList<Livro> livros;
    private boolean carregando = false;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config);


        mFirebaseRemoteConfig.fetch(10).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        activity.setTitle("Catalogo");


        RecyclerView.Adapter adapter;
        if (mFirebaseRemoteConfig.getBoolean("idioma")) {
            adapter = new LivroAdapter(livros, getActivity());
        } else {
            adapter = new LivroZebradoAdapter(livros, getActivity());
        }


        lista.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_livros,
                container, false);

        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();

        livros = (ArrayList<Livro>) arguments.getSerializable("lista");


        lista.setLayoutManager(new LinearLayoutManager(getContext()));

        // compile 'com.vinaysshenoy:mugen:1.0.2 -- no build.gradle do modulo

        BaseAttacher attacher = Mugen.with(lista, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                carregando = true;
                new WebClient().buscaPelosLivros(livros.size(), 10);
                Snackbar.make(lista, "carregando novos livros", Snackbar.LENGTH_LONG)
                        .show();
            }

            @Override
            public boolean isLoading() {
                return carregando;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return livros.size() == 20;
            }
        }).start();

        attacher.setLoadMoreEnabled(true);


        return view;
    }

    public void atualiza(ArrayList<Livro> livros) {
        this.livros.addAll(livros);
        this.lista.getAdapter().notifyDataSetChanged();

        carregando = false;
    }
}
