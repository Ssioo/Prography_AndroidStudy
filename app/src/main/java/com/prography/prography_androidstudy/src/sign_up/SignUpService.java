package com.prography.prography_androidstudy.src.sign_up;

import com.prography.prography_androidstudy.src.sign_up.interfaces.SignUpActivityView;
import com.prography.prography_androidstudy.src.sign_up.interfaces.SignUpRetrofitInterface;
import com.prography.prography_androidstudy.src.common.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prography.prography_androidstudy.src.ApplicationClass.getRetrofit;

public class SignUpService {
    final SignUpActivityView mSignUpActivityView;

    public SignUpService(SignUpActivityView mSignUpActivityView) {
        this.mSignUpActivityView = mSignUpActivityView;
    }

    public void postSignUp(String userName, String password, String passwordCheck) {
        final SignUpRetrofitInterface signUpRetrofitInterface = getRetrofit().create(SignUpRetrofitInterface.class);
        signUpRetrofitInterface.postUser(userName, password, passwordCheck).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                final UserResponse userResponse = response.body();
                if (userResponse == null) {
                    mSignUpActivityView.validateFalire(null);
                    return;
                }

                mSignUpActivityView.validateSuccess(userResponse.getUniqueId());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mSignUpActivityView.validateFalire(null);
            }
        });
    }
}
