package com.example.muscinfinder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muscinfinder.R;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.modal.UserModal;
import com.example.muscinfinder.parser.BaseUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class RegisterActivity extends AppCompatActivity {

    Button btn_create_account;
    EditText etName_, etEmail_, etPhone_, editText_Password_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        // Button create account
        btn_create_account = findViewById(R.id.btnSignup);

        etName_ = findViewById(R.id.etName);
        etEmail_ = findViewById(R.id.etEmail);
        etPhone_ = findViewById(R.id.etPhone);
        editText_Password_ = findViewById(R.id.etPassword);

        //Adding Click Listener on button.
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        String name = etName_.getText().toString();
        String email = etEmail_.getText().toString();
        String phone = etPhone_.getText().toString();
        String password = editText_Password_.getText().toString();

        UserModal userModal = new UserModal(name, email, phone, password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Void> voidCall = apiInterface.registerUser(userModal);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Your account has been registered", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}