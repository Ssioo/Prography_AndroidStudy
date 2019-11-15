package com.prography.prography_androidstudy.src.sign_up.interfaces;

import com.prography.prography_androidstudy.src.common.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignUpRetrofitInterface {
    @POST("users")
    Call<UserResponse> postUser(@Query("username") String userName,
                                @Query("password1") String password1,
                                @Query("password2") String password2);
}
