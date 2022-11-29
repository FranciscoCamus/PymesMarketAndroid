package com.francisco.cliente.e_commerceapp.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francisco.cliente.e_commerceapp.R;
import com.francisco.cliente.e_commerceapp.adapter.CategoriaAdapter;
import com.francisco.cliente.e_commerceapp.adapter.ProductosRecomendadosAdapter;
import com.francisco.cliente.e_commerceapp.adapter.SliderAdapter;
import com.francisco.cliente.e_commerceapp.communication.Communication;
import com.francisco.cliente.e_commerceapp.communication.MostrarBadgeCommunication;
import com.francisco.cliente.e_commerceapp.entity.SliderItem;
import com.francisco.cliente.e_commerceapp.entity.service.DetallePedido;
import com.francisco.cliente.e_commerceapp.entity.service.Producto;
import com.francisco.cliente.e_commerceapp.utils.Carrito;
import com.francisco.cliente.e_commerceapp.viewmodel.CategoriaViewModel;
import com.francisco.cliente.e_commerceapp.viewmodel.ProductoViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class InicioFragment extends Fragment implements Communication, MostrarBadgeCommunication {
    private CategoriaViewModel categoriaViewModel;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductosRecomendados;
    private ProductosRecomendadosAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private GridView gvCAtegorias;
    private CategoriaAdapter categoriaAdapter;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }
    private void init(View v) {
        svCarrusel = v.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);
        //Categorias
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCAtegorias = v.findViewById(R.id.gvCAtegorias);
        //Productos
        rcvProductosRecomendados = v.findViewById(R.id.rcvProductosRecomendados);
        rcvProductosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        productoViewModel = vmp.get(ProductoViewModel.class);

    }

    private void initAdapter() {
        //Carrusel de Imágenes
        sliderAdapter = new SliderAdapter(getContext());
        svCarrusel.setSliderAdapter(sliderAdapter);
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarrusel.startAutoCycle();
        //Categorias
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCAtegorias.setAdapter(categoriaAdapter);
        //Productos
        adapter = new ProductosRecomendadosAdapter(productos, this, this);
        rcvProductosRecomendados.setAdapter(adapter);
    }

    private void loadData() {
        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.artesania, "Hechas a mano"));
        lista.add(new SliderItem(R.drawable.herramientas, "Herramientas de calidad"));
        lista.add(new SliderItem(R.drawable.zapatillas, "Al mejor precio"));
        lista.add(new SliderItem(R.drawable.deportes, "Variedad y calidad"));
        sliderAdapter.updateItem(lista);
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() == 1) {
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else {
                System.out.println("Error al obtener las categorias activas");
            }
        });
        productoViewModel.listarProductosRecomendados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItems(response.getBody());
        });
    }


    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void exportInvoice(int idCli, int idOrden, String fileName) {

    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarProductos(dp));
        BadgeDrawable badgeDrawable = BadgeDrawable.create(this.getContext());
        badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        BadgeUtils.attachBadgeDrawable(badgeDrawable, getActivity().findViewById(R.id.toolbar), R.id.bolsaCompras);
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }
}