package com.francisco.cliente.e_commerceapp.api;

import com.francisco.cliente.e_commerceapp.entity.GenericResponse;
import com.francisco.cliente.e_commerceapp.entity.service.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoApi {
    String base = "api/producto";

    @GET(base)
    Call<GenericResponse<List<Producto>>> listarProductosRecomendados();

    @GET(base + "/{idC}")
    Call<GenericResponse<List<Producto>>> listarProductosPorCategoria(@Path("idC") int idC);
}
