package com.example.muscinfinder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.muscinfinder.R;
import com.example.muscinfinder.api_classes.ApiInterface;
import com.example.muscinfinder.modal.BookModal;
import com.example.muscinfinder.modal.FavouriteModal;
import com.example.muscinfinder.parser.BaseUrl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstituteDetailActivity extends AppCompatActivity {

    public static String userID = null;
    public static String productID = null;
    TextView txt_name, txt_desc, txt_price , txt_cost;
    ImageView img_picture;
    Button btnBook;
    EditText etDate, etTime, etPhone;
    String strDate, strTime, strPhone;

    LinearLayout ly_book, ly_intro;
    Button btn_book, btn_pinned;

    private SensorManager sensorManager;

    // accelerometer
    TextView tvaccdis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_book = findViewById(R.id.show);
        ly_book = findViewById(R.id.booknowLY);
        ly_intro = findViewById(R.id.ind);
        ly_book.setVisibility(View.GONE);

        btn_pinned = findViewById(R.id.pinned);
        btn_pinned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFab();
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_book.setVisibility(View.VISIBLE);
                ly_intro.setVisibility(View.GONE);
            }
        });

        getWindow().setStatusBarColor(Color.parseColor("#ffb902"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        txt_name = findViewById(R.id.title);
        txt_price = findViewById(R.id.txtPrice);
        txt_desc = findViewById(R.id.desc);
        img_picture = findViewById(R.id.image);
        txt_cost = findViewById(R.id.cost);

        btnBook = findViewById(R.id.booknow);

        etDate = findViewById(R.id.date);
        etTime = findViewById(R.id.times);
        etPhone = findViewById(R.id.phone);

        Bundle val = getIntent().getExtras();
        String _name = val.getString("_name");
        String _desc = val.getString("_desc");
        String _location = "Location: " + val.getString("_location");
        String _img = val.getString("_img");

        txt_name.setText(_name);
        txt_desc.setText(_desc);
        txt_price.setText(_location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstituteDetailActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        if (val.getString("_cost") == null) {
            txt_cost.setVisibility(View.GONE);
        } else {
            String _cost = "Course fee: " + val.getString("_cost");
        }

        if (val.getString("_st").equals("0")) {
            btn_pinned.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else if (val.getString("_st").equals("1")) {
            btn_pinned.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        Picasso.get()
                .load(_img)
                .placeholder(R.drawable.sample_product)
                .resize(350, 350)
                .centerCrop()
                .into(img_picture);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDate = etDate.getText().toString();
                strTime = etTime.getText().toString();
                strPhone = etPhone.getText().toString();
                bookRestaurant();
            }
        });

        // accelerometer code
        tvaccdis = findViewById(R.id.tvaccdis);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sel = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                String xAxis = "x:" + values[0];
                String yAxis = "y:" + values[1];
                String zAxis = "z:" + values[2];

                if (values[0] > 5) {
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }

                tvaccdis.setText(xAxis + " " + yAxis + " " + zAxis);
            }
        };
        if (sensor != null) {
            sensorManager.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getApplicationContext(), "No sensor Found", Toast.LENGTH_SHORT).show();
        }

    }

    private void addFab() {
        FavouriteModal reviewModal = new FavouriteModal(productID, userID);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface userInt = retrofit.create(ApiInterface.class);
        Call<Void> voidCall = userInt.addCart(reviewModal);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Added to Favourite", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bookRestaurant() {
        BookModal bookModal = new BookModal(productID, userID, strDate, strTime, strPhone);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface userInt = retrofit.create(ApiInterface.class);
        Call<Void> voidCall = userInt.addBook(bookModal);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Your reservation sent successfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}