package com.github.internetlibraryapp.service;

import com.github.internetlibraryapp.entity.Book;
import com.github.internetlibraryapp.entity.BookContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookService {

    @GET("search.json")
    Call<BookContainer> findBooks(@Query("q") String query);

    @GET("isbn/{isbn}.json")
    Call<Book> findBook(@Path("isbn") String isbn);
}
