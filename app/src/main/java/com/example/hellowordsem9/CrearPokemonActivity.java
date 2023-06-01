package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.models.Pokemon;
import com.example.hellowordsem9.servicios.ServicesWebPokemon;
import com.example.hellowordsem9.servicios.servicesWeb;

import java.security.Policy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pokemon);

        Button btn = findViewById(R.id.btnCrearPokemon);
        EditText etNumero = findViewById(R.id.etNumero);
        EditText etNombre= findViewById(R.id.etNombre);
        EditText etTipo = findViewById(R.id.etTipo);
        EditText etUrl = findViewById(R.id.etImage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://63746cd448dfab73a4df8801.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ServicesWebPokemon services = retrofit.create(ServicesWebPokemon.class);

                Pokemon pokemon = new Pokemon();
                pokemon.numero = String.valueOf(etNumero.getText());
                pokemon.nombre = String.valueOf(etNombre.getText());
                pokemon.tipo = String.valueOf(etTipo.getText());
                pokemon.img = String.valueOf(etUrl.getText()); // Obtén el enlace de la imagen desde el EditText

                Call<Pokemon> call = services.create(pokemon);


                call.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if (response.isSuccessful()) {
                            // La imagen se agregó correctamente a MockAPI
                        } else {
                            // Hubo un error al agregar la imagen a MockAPI
                        }
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        // Error de red o de la API
                    }
                });
            }
        });

    }
}