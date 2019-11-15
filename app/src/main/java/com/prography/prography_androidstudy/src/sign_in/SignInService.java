package com.prography.prography_androidstudy.src.sign_in;

import com.prography.prography_androidstudy.src.common.models.UserResponse;
import com.prography.prography_androidstudy.src.sign_in.interfaces.SignInActivityView;
import com.prography.prography_androidstudy.src.sign_in.interfaces.SignInRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prography.prography_androidstudy.src.ApplicationClass.getRetrofit;

public class SignInService {
    final SignInActivityView mSignInActivityView;

    public SignInService(SignInActivityView mSignInActivityView) {
        this.mSignInActivityView = mSignInActivityView;
    }

    public void getSignIn() {
        final SignInRetrofitInterface signInRetrofitInterface = getRetrofit().create(SignInRetrofitInterface.class);
        signInRetrofitInterface.getUser().enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                final UserResponse userResponse = response.body();
                if (userResponse == null) {
                    mSignInActivityView.validateFalire(null);
                    return;
                }
                mSignInActivityView.validateSuccess(userResponse.getUniqueId());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mSignInActivityView.validateFalire(null);
            }
        });
    }
}
