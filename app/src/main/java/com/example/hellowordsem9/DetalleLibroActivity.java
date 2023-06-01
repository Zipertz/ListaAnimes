package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hellowordsem9.Adapters.libroAdapter;
import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.servicios.servicesWeb;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleLibroActivity extends AppCompatActivity {
    ImageView imgA;
    Button btnEliminar;
    private libroAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        String libroJson = getIntent().getStringExtra("Libros");
        Libro libro = new Gson().fromJson(libroJson, Libro.class);


        imgA = findViewById(R.id.imgAvatar);
        EditText tvTitulo = findViewById(R.id.tvTitulomin);
        EditText tvmResumen = findViewById(R.id.tvResumenmin);
        EditText tvmAutor = findViewById(R.id.tvAutormin);
        EditText tvmUrl = findViewById(R.id.tvUrl);
        btnEliminar = findViewById(R.id.btnEliminar);
        Button btnEditar = findViewById(R.id.btnActualizar);


        tvTitulo.setText(libro.titulo);
        tvmResumen.setText(libro.resumen);
        tvmAutor.setText(libro.autor);
        tvmUrl.setText(libro.img);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://63746cd448dfab73a4df8801.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicesWeb services = retrofit.create(servicesWeb.class);

                Libro libroEditado = new Libro();
                libroEditado.titulo = String.valueOf(tvTitulo.getText());
                libroEditado.resumen = String.valueOf(tvmResumen.getText());
                libroEditado.autor = String.valueOf(tvmAutor.getText());
                libroEditado.img = String.valueOf(tvmUrl.getText());

                Call<Void> call = services.actualizar(libro.id,libroEditado);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // La imagen se agregó correctamente a MockAPI
                            onBackPressed();
                        } else {
                            // Hubo un error al agregar la imagen a MockAPI
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Error de red o de la API
                    }
                });
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://63746cd448dfab73a4df8801.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicesWeb services = retrofit.create(servicesWeb.class);


                Call<Void> call = services.delete(libro.id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // La imagen se agregó correctamente a MockAPI
                            onBackPressed();
                        } else {
                            // Hubo un error al agregar la imagen a MockAPI
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Error de red o de la API
                    }
                });
            }
        });


    }

    public void setAdapter(libroAdapter adapter) {
        this.adapter = adapter;
    }
}