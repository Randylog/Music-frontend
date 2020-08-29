package com.example.muscinfinder.required_classes;

import com.example.muscinfinder.activity.InstituteDetailActivity;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.fragment.ProfileFragment;
import com.example.muscinfinder.fragment.FavouriteFragment;
import com.example.muscinfinder.parser.BaseUrl;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBll {
    boolean isSuccess = false;

    public boolean checkuser(String email, String password) {

        ApiInterface ApiInterface = BaseUrl.getInstance().create(ApiInterface.class);
        Call<SignUpResponse> usersCall = ApiInterface.checkUser(email, password);

        try {
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
                    loginResponse.body().getStatus().equals("Login success!")) {
                InstituteDetailActivity.userID = loginResponse.body().get_id();
                FavouriteFragment.userID = loginResponse.body().get_id();
                ProfileFragment.userID = loginResponse.body().get_id();
                ProfileFragment.token = loginResponse.body().getToken();
                ProfileFragment.full_name = loginResponse.body().getFullname();
                System.out.println("UserID " + loginResponse.body().get_id());
                BaseUrl.token += loginResponse.body().getToken();
                isSuccess = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
