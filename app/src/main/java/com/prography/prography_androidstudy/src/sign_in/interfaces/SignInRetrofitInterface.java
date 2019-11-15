package com.prography.prography_androidstudy.src.sign_in.interfaces;

import com.prography.prography_androidstudy.src.common.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SignInRetrofitInterface {
    @GET("users")
    Call<UserResponse> getUser();
}
