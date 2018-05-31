package br.com.caelum.casadocodigo.delegates;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.modelo.Livro;

public interface LivroDelegate {
    void lidaComLivroSelecionado(Livro livro);

    void lidaComLista(ArrayList<Livro> livros);

    void lidaComErro(Throwable t);
}
