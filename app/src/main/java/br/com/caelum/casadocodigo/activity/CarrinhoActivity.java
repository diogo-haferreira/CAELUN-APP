package br.com.caelum.casadocodigo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.adapter.ItensAdapter;
import br.com.caelum.casadocodigo.application.CasaDoCodigoApplication;
import br.com.caelum.casadocodigo.modelo.Carrinho;
import br.com.caelum.casadocodigo.modelo.Item;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoActivity extends AppCompatActivity {


    private Carrinho carrinho;

    @BindView(R.id.lista_itens_carrinho)
    RecyclerView lista;
    @BindView(R.id.valor_carrinho)
    TextView preco;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CasaDoCodigoApplication.getInstance().getComponent().inject(this);

        lista.setLayoutManager(new LinearLayoutManager(this));

        lista.setAdapter(new ItensAdapter(carrinho.getItens(), this));


        preco.setText("R$ " + carregaPreco());


    }

    private BigDecimal carregaPreco() {

        double valor = 0.00;
        for (Item item : carrinho.getItens()) {
            valor += item.getValor();
        }

        BigDecimal preco = new BigDecimal(valor);


        return preco.setScale(2, RoundingMode.HALF_UP);

    }

    @Inject
    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return true;
    }
}
