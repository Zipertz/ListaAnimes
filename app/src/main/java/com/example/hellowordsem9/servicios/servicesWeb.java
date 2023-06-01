package com.example.hellowordsem9.servicios;

import com.example.hellowordsem9.models.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface servicesWeb {
    @GET("libros1/")
    Call<List<Libro>> getContacts();

    @GET("libros1/{id}")
    Call<Libro> findContact(@Path("id") int id);

    @POST("libros1")
    Call<Libro> create(@Body Libro libro);

    @PUT("libros1/{id}")
    Call<Void> actualizar(@Path("id") int id, @Body Libro libro);

    @DELETE("libros1/{id}")
    Call<Void> delete(@Path("id") int id);

}
