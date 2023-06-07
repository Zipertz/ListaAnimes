package com.example.hellowordsem9;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.models.Pokemon;
import com.example.hellowordsem9.servicios.ServicesWebPokemon;
import com.example.hellowordsem9.servicios.servicesWeb;

import java.io.ByteArrayOutputStream;
import java.security.Policy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPokemonActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pokemon);

        Button btn = findViewById(R.id.btnCrearPokemon);
        EditText etNumero = findViewById(R.id.etNumero);
        EditText etNombre= findViewById(R.id.etNombre);
        EditText etTipo = findViewById(R.id.etTipo);
        Button btnCamara = findViewById(R.id.btnCamera);
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
                pokemon.img = String.valueOf("https://assets.pokemon.com/assets/cms2/img/pokedex/full/"+String.valueOf(etNumero.getText())+".png"); // Obtén el enlace de la imagen desde el EditText

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

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Log.i("My app","El permiso de la cámara ya se ha otorgado, puedes realizar la acción deseada aquí");
                    OpenCamera();
                } else {
                    // El permiso de la cámara no se ha otorgado, solicítalo al usuario
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);
                    Log.i("My app","No tienes permiso");
                }
            }
        });
    }

    private void OpenCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            // La foto se tomó correctamente, puedes manejar el resultado aquí
            // Por ejemplo, puedes obtener la imagen capturada utilizando data.getExtras().get("data")
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            // Convierte el bitmap a una cadena Base64
            String base64Image = convertBitmapToBase64(bitmap);
            imprimirImagenEnLog(base64Image);
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private void imprimirImagenEnLog(String base64Image) {
        Log.d("ImagenBase64", base64Image);
    }
}