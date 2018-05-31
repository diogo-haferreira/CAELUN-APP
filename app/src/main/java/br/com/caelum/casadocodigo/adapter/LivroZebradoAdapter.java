package br.com.caelum.casadocodigo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.caelum.casadocodigo.R;
import br.com.caelum.casadocodigo.modelo.Livro;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LivroZebradoAdapter extends RecyclerView.Adapter {


    private List<Livro> livros;
    private Context contexto;

    public LivroZebradoAdapter(List<Livro> livros, Context contexto) {
        this.livros = livros;
        this.contexto = contexto;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.item_livro_impar;
        if (viewType % 2 == 0) {
            layout = R.layout.item_livro_par;
        }
        View view = LayoutInflater.from(contexto).inflate(layout, parent, false);
        return new MeuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MeuViewHolder meuHolder = (MeuViewHolder) holder;

        Livro livro = livros.get(position);

        meuHolder.nome.setText(livro.getNome());

        Picasso.get().load(livro.getUrlFoto())
                .fit()
                .into(meuHolder.foto);


    }

    @Override
    public int getItemCount() {
        return livros.size();
    }


    class MeuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_livro_nome)
        TextView nome;
        @BindView(R.id.item_livro_foto)
        ImageView foto;

        public MeuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.item_livro)
        public void clickNoItem() {

            int position = getAdapterPosition();
            Livro livro = livros.get(position);


            EventBus.getDefault().post(livro);
        }
    }
}
