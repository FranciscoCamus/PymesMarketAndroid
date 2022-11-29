package com.francisco.cliente.e_commerceapp.api;

import com.francisco.cliente.e_commerceapp.entity.GenericResponse;
import com.francisco.cliente.e_commerceapp.entity.service.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UsuarioApi {
    //ruta del controlador usuario
    String base = "api/usuario";

    //Ruta del Controlador usuario + la ruta del método
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("email") String email, @Field("pass") String contrasenia);

    @POST(base)
    Call<GenericResponse<Usuario>> save(@Body Usuario u);
}
