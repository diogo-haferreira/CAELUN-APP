package br.com.caelum.casadocodigo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.ws.WebClient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ErroFragment extends Fragment {

    @BindView(R.id.fragment_erro_mensagem)
    TextView msgErro;

    @BindView(R.id.fragment_erro_swipe)
    SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_erro, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();

        Throwable erro = (Throwable) arguments.getSerializable("erro");

        msgErro.setText(erro.getMessage());

        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_dark, android.R.color.holo_purple);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new WebClient().buscaPelosLivros(0,10);
                swipe.setRefreshing(false);
            }
        });


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
