package br.com.caelum.casadocodigo.ws;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeviceService {

    @POST("/device/register/{email}/{id}")
    Call<String> registraAparelho(@Path("email") String email, @Path("id") String id);
}
