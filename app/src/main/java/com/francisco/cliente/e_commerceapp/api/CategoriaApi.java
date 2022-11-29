package com.francisco.cliente.e_commerceapp.api;

import com.francisco.cliente.e_commerceapp.entity.GenericResponse;
import com.francisco.cliente.e_commerceapp.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
