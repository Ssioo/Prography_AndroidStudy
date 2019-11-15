package com.prography.prography_androidstudy.src.main.interfaces;

import com.prography.prography_androidstudy.src.main.models.TodoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainRetrofitInterface {
    @GET("users/{userid}/todo/{todoid}")
    Call<TodoResponse> getTodos(@Path("userid") int userid,
                                @Path("todoid") int todoid);
}
