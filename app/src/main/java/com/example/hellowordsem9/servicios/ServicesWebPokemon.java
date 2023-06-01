package com.example.hellowordsem9.servicios;

import com.example.hellowordsem9.models.Libro;
import com.example.hellowordsem9.models.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServicesWebPokemon {
    @GET("pokemons/")
    Call<List<Pokemon>> getPokemons();

    @GET("pokemons/{id}")
    Call<Pokemon> findContact(@Path("id") int id);

    @POST("pokemons")
    Call<Pokemon> create(@Body Pokemon pokemon);

    @PUT("pokemons/{id}")
    Call<Void> actualizar(@Path("id") int id, @Body Pokemon pokemon);

    @DELETE("pokemons/{id}")
    Call<Void> delete(@Path("id") int id);
}
