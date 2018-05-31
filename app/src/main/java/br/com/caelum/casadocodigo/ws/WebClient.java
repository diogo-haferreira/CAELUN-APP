package br.com.caelum.casadocodigo.ws;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.converter.LivroServiceConverterFactory;
import br.com.caelum.casadocodigo.modelo.Livro;
import br.com.caelum.casadocodigo.ws.services.LivroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebClient {


    public static final String BASE_URL = "http://cdcmob.herokuapp.com/";
    public static final LivroServiceConverterFactory FACTORY = new LivroServiceConverterFactory();
    private Retrofit retrofit;

    public WebClient() {

        retrofit = new Retrofit.Builder()
                .addConverterFactory(FACTORY)
                .baseUrl(BASE_URL)
                .build();
    }

    public void buscaPelosLivros(int indice, int quantidade) {


        LivroService livroService = retrofit.create(LivroService.class);

        Call<ArrayList<Livro>> chamada = livroService.buscaDosLivros(indice, quantidade);


        chamada.enqueue(new Callback<ArrayList<Livro>>() {
            @Override
            public void onResponse(Call<ArrayList<Livro>> call, Response<ArrayList<Livro>> response) {

                ArrayList<Livro> livros = response.body();


                EventBus.getDefault().post(livros);
            }

            @Override
            public void onFailure(Call<ArrayList<Livro>> call, Throwable t) {

                EventBus.getDefault().post(t);

            }
        });
    }

    public void registraAparelho(String email, String idDoCelular) {


        Retrofit retrofitDoServidor = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeviceService service = retrofitDoServidor.create(DeviceService.class);


        service.registraAparelho(email, idDoCelular).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
