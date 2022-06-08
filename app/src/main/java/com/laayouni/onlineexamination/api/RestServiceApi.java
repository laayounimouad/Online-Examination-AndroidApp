package com.laayouni.onlineexamination.api;


import com.laayouni.onlineexamination.entities.Test;
import com.laayouni.onlineexamination.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestServiceApi {
    @GET("users/")
    Call<List<User>> listUsers();
    @POST("users/")
    Call<User> createUser(@Body User user);
    @POST("userauth/")
    Call<User> isPassCorrect(@Body User user);

}

