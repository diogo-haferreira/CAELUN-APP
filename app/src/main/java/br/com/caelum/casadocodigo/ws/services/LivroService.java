package br.com.caelum.casadocodigo.ws.services;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.modelo.Livro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LivroService {


    @GET("listarLivros")
    Call<ArrayList<Livro>> buscaDosLivros(@Query("indicePrimeiroLivro") int indice,
                                          @Query("qtdLivros") int quantidade);

}
