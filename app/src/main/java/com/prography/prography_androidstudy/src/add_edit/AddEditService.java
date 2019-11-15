package com.prography.prography_androidstudy.src.add_edit;

import com.prography.prography_androidstudy.src.add_edit.interfaces.AddEditActivityView;
import com.prography.prography_androidstudy.src.add_edit.interfaces.AddEditRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prography.prography_androidstudy.src.ApplicationClass.getFCMRetrofit;

public class AddEditService {
    final AddEditActivityView mAddEditActivityView;

    public AddEditService(AddEditActivityView mAddEditActivityView) {
        this.mAddEditActivityView = mAddEditActivityView;
    }

    public void addPushToFCM() {
        AddEditRetrofitInterface addEditRetrofitInterface = getFCMRetrofit().create(AddEditRetrofitInterface.class);
        addEditRetrofitInterface.postToFCM().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
