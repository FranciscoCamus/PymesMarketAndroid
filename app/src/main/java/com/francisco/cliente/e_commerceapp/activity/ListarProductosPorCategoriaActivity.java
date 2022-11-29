package com.francisco.cliente.e_commerceapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.load.model.ModelLoader;
import com.francisco.cliente.e_commerceapp.R;
import com.francisco.cliente.e_commerceapp.adapter.ProductosPorCategoriaAdapter;
import com.francisco.cliente.e_commerceapp.entity.service.Producto;
import com.francisco.cliente.e_commerceapp.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListarProductosPorCategoriaActivity extends AppCompatActivity {
    private ProductoViewModel productoViewModel;
    private ProductosPorCategoriaAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private RecyclerView rcvProductoPorCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_por_categoria);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }
    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.productoViewModel = vmp.get(ProductoViewModel.class);
    }

    private void initAdapter() {
        adapter = new ProductosPorCategoriaAdapter(productos);
        rcvProductoPorCategoria = findViewById(R.id.rcvProductosPorCategoria);
        rcvProductoPorCategoria.setAdapter(adapter);
        rcvProductoPorCategoria.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        int idC = getIntent().getIntExtra("idC", 0); //Recibimos el id de la categoria
        productoViewModel.listarProductosPorCategoria(idC).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });
    }


}