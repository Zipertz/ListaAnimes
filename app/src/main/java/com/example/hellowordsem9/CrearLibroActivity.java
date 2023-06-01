package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.servicios.servicesWeb;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearLibroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libro);
        Button btn = findViewById(R.id.btnCrearLibro);
        EditText etTitulo = findViewById(R.id.etTitulo);
        EditText etResumen = findViewById(R.id.etResumen);
        EditText etAutor = findViewById(R.id.etAutor);
        EditText etUrl = findViewById(R.id.etImage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://63746cd448dfab73a4df8801.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicesWeb services = retrofit.create(servicesWeb.class);

                Libro libro = new Libro();
                libro.titulo = String.valueOf(etTitulo.getText());
                libro.resumen = String.valueOf(etResumen.getText());
                libro.autor = String.valueOf(etAutor.getText());
                libro.img = String.valueOf(etUrl.getText()); // Obtén el enlace de la imagen desde el EditText

                Call<Libro> call = services.create(libro);

                call.enqueue(new Callback<Libro>() {
                    @Override
                    public void onResponse(Call<Libro> call, Response<Libro> response) {
                        if (response.isSuccessful()) {
                            // La imagen se agregó correctamente a MockAPI
                        } else {
                            // Hubo un error al agregar la imagen a MockAPI
                        }
                    }

                    @Override
                    public void onFailure(Call<Libro> call, Throwable t) {
                        // Error de red o de la API
                    }
                });
            }
        });
    }
}
