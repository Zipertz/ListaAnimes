package com.example.hellowordsem9.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellowordsem9.DetalleLibroActivity;
import com.example.hellowordsem9.R;
import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.servicios.servicesWeb;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class libroAdapter extends RecyclerView.Adapter<libroAdapter.libroViewHolder> {
    List<Libro> libros;

    public libroAdapter(List<Libro> libros) {
        this.libros = libros;
    }

    @NonNull
    @Override
    public libroAdapter.libroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        libroAdapter.libroViewHolder vh = new libroAdapter.libroViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull libroAdapter.libroViewHolder holder, int position) {
        View vw = holder.itemView;

        Libro libro = libros.get(position);
        TextView itemTitulo = holder.itemView.findViewById(R.id.tvTitulo);
        TextView itemAutor = holder.itemView.findViewById(R.id.tvAutor);
        ImageView itemImg = holder.itemView.findViewById(R.id.ivAvatar);

        itemTitulo.setText(libro.titulo);
        itemAutor.setText(libro.autor);

        Picasso.get()
                .load(libro.img) // Carga la imagen desde el enlace proporcionado en el objeto Libro
                .into(itemImg);

        vw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new  Retrofit.Builder()
                        .baseUrl("https://63746cd448dfab73a4df8801.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                servicesWeb services = retrofit.create(servicesWeb.class);
                Call<Libro> call=services.findContact(libro.id);

                call.enqueue(new Callback<Libro>() {
                    @Override
                    public void onResponse(Call<Libro> call, Response<Libro> response) {
                        if (!response.isSuccessful()){
                            Log.e("asd1234", "error");
                        }else {

                            Log.i("asdasd12312", new Gson().toJson(response.body()));
                            Log.i("asd32", "Respuesta correcta por id");

                            Intent intent= new Intent(vw.getContext(), DetalleLibroActivity.class);


                            Log.i("asd32", "Respuesta correcta por id------------ ");
//
                            String libroJson = new Gson().toJson(libro);
                            intent.putExtra("Libros",libroJson);

                            vw.getContext().startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Libro> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public class libroViewHolder extends RecyclerView.ViewHolder {
        public libroViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
