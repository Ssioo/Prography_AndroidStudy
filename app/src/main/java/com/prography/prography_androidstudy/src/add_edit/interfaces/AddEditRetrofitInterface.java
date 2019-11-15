package com.prography.prography_androidstudy.src.add_edit.interfaces;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddEditRetrofitInterface {
    @Headers({"Authorization: key=Legacy Service Key", "Content-Type:application/json"})
    @POST("fcm/send")
    Call<Response> postToFCM();
}
