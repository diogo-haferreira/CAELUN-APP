package br.com.caelum.casadocodigo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.application.CasaDoCodigoApplication;
import br.com.caelum.casadocodigo.modelo.Carrinho;
import br.com.caelum.casadocodigo.modelo.Item;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.modelo.TipoDeCompra;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalheLivroFragment extends Fragment {


    @BindView(R.id.detalhes_livro_foto)
    ImageView fotoLivro;
    @BindView(R.id.detalhes_livro_nome)
    TextView nomeLivro;
    @BindView(R.id.detalhes_livro_autores)
    TextView autoresLivro;
    @BindView(R.id.detalhes_livro_descricao)
    TextView descricao;
    @BindView(R.id.detalhes_livro_num_paginas)
    TextView numeroPaginas;
    @BindView(R.id.detalhes_livro_isbn)
    TextView isbn;
    @BindView(R.id.detalhes_livro_data_publicacao)
    TextView dataPublicacao;
    private Livro livro;

    @Inject
    Carrinho carrinho;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle arguments = getArguments();

        livro = (Livro) arguments.getSerializable("livro");

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity.setTitle(livro.getNome());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detalhes_livro, container, false);
        ButterKnife.bind(this, view);

        CasaDoCodigoApplication.getInstance().getComponent().inject(this);


        Picasso.get().load(livro.getUrlFoto()).fit().into(fotoLivro);
        nomeLivro.setText(livro.getNome());
        descricao.setText(livro.getDescricao());

        //todo implementar o restante dos campos


        return view;
    }

    @OnClick({R.id.detalhes_livro_comprar_ambos, R.id.detalhes_livro_comprar_fisico, R.id.detalhes_livro_comprar_ebook})
    public void colocaNoCarrinhoLivroAmbosFormatos(View view) {

        final Item item = new Item(livro, tipoDeCompraDa(view));

        carrinho.adiciona(item);

        Snackbar.make(dataPublicacao, "Adicionado no carrinho", Snackbar.LENGTH_LONG)
                .setAction("Remover", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        carrinho.remove(item);
                    }
                }).show();

    }

    @NonNull
    private TipoDeCompra tipoDeCompraDa(View view) {

        switch (view.getId()) {
            case R.id.detalhes_livro_comprar_ambos:
                return TipoDeCompra.JUNTOS;

            case R.id.detalhes_livro_comprar_fisico:
                return TipoDeCompra.FISICO;

            default:
                return TipoDeCompra.VIRTUAL;

        }

    }
}
