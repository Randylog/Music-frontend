package com.example.muscinfinder.fragment;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.muscinfinder.R;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.api_classes.BaseURL;
import com.example.muscinfinder.modal.GpsTracker;
import com.example.muscinfinder.modal.UpdateModal;
import com.example.muscinfinder.parser.BaseUrl;
import com.example.muscinfinder.required_classes.UserModalAno;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    public static String full_name = null;
    public static String token = null;
    public static String userID = "";

    Button btnlogins;

    TextView txtname1, txtname2, txtEmail, txtAddress , txtphone;
    UserModalAno UserModalAno;

    EditText et_name, et_email, et_phone ,et_password;

    public GpsTracker gpsTracker;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtname1 = view.findViewById(R.id.name);
        txtname2 = view.findViewById(R.id.name2);
        txtEmail = view.findViewById(R.id.email);
        txtAddress = view.findViewById(R.id.address);
        txtphone = view.findViewById(R.id.phone);

        et_name = view.findViewById(R.id.etrname);
        et_email = view.findViewById(R.id.etremail);
        et_phone = view.findViewById(R.id.etrphone);
        et_password = view.findViewById(R.id.etrpass);

        btnlogins = view.findViewById(R.id.btnlogins);

        btnlogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(et_name.getText())) {
                    et_name.setError("Enter full name ");
                    return;
                }
                
//                if (TextUtils.isEmpty(et_email.getText())) {
//                    et_email.setError("Enter new email ");
//                    return;
//                }

                if (TextUtils.isEmpty(et_phone.getText())) {
                    et_email.setError("Enter new phone ");
                    return;
                }
//
//                if (TextUtils.isEmpty(et_password.getText())) {
//                    et_email.setError("Enter new password ");
//                    return;
//                }

                update();
            }
        });

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffb902"));
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        getLocation(view);
        reqUser();

        return view;
    }


    private void reqUser(){
        ApiInterface interfaces = BaseURL.getRetrofit().create(ApiInterface.class);
        Call<UserModalAno> userCall = interfaces.getUserDetails(BaseUrl.token);
        userCall.enqueue(new Callback<UserModalAno>() {
            @Override
            public void onResponse(Call<UserModalAno> call, Response<UserModalAno> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                    return;
                }
                UserModalAno = response.body();
                txtname1.setText("Name: " + response.body().getFullname());
                txtname2.setText(response.body().getFullname());
                txtEmail.setText("Email: " + response.body().getEmail());
                txtphone.setText("Phone:" + response.body().getPhone());

            }

            @Override
            public void onFailure(Call<UserModalAno> call, Throwable t) {
                Toast.makeText(getActivity(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLocation(View view){
        gpsTracker = new GpsTracker(getActivity());
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
//            Toast.makeText(getActivity(), "Latitute: " + String.valueOf(latitude) + "Longitute: " +
//                    String.valueOf(longitude), Toast.LENGTH_SHORT).show();

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                add = add + "\n" + obj.getLocality();

                txtAddress.setText("Current Location: " + obj.getLocality());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    // update profile code
    public void update() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateModal usersCUD = new UpdateModal(userID,et_name.getText().toString(), et_phone.getText().toString());
        ApiInterface employeeApi = retrofit.create(ApiInterface.class);
        Call<UpdateModal> updateModelCall = employeeApi.edituser(BaseUrl.token, usersCUD);

        updateModelCall.enqueue(new Callback<UpdateModal>() {
            @Override
            public void onResponse(Call<UpdateModal> call, Response<UpdateModal> response) {
                androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                builder1.setMessage("Updated Successfully");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(ProfileFragment.this).attach(ProfileFragment.this).commit();
                                et_name.setText("");
                                et_email.setText("");
                                et_phone.setText("");
                                et_password.setText("");
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

            @Override
            public void onFailure(Call<UpdateModal> call, Throwable t) {

            }
        });

    }
}
