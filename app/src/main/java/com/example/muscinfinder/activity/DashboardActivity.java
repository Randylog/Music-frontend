package com.example.muscinfinder.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.muscinfinder.R;
import com.example.muscinfinder.fragment.ProfileFragment;
import com.example.muscinfinder.fragment.FavouriteFragment;
import com.example.muscinfinder.fragment.HomeFragment;
import com.example.muscinfinder.fragment.MapLoadNewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private FavouriteFragment cartFragment;
    private ProfileFragment profileFragment;
    private MapLoadNewFragment mapLoadNewFragment;

    private SensorManager sensorManager;
    private TextView tvSensors;
    TextView textLIGHT_available, textLIGHT_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        SensorEventListener grrplistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] <= 4) {

                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = 0F;
                    getWindow().setAttributes(layout);


                } else  {

                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = 1F;
                    getWindow().setAttributes(layout);
                }
            };

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(grrplistener,sensor, SensorManager.SENSOR_DELAY_FASTEST);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));// set status bac

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        cartFragment = new FavouriteFragment();
        profileFragment = new ProfileFragment();
        mapLoadNewFragment = new MapLoadNewFragment();

        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_cart:
                        setFragment(cartFragment);
                        return true;
                    case R.id.nav_account:
                        setFragment(profileFragment);
                        return true;
                    case R.id.nav_map:
                        setFragment(mapLoadNewFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        tvSensors = findViewById(R.id.tvSensor);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 0; i < sensorList.size(); i++) {
            String sensors = "";
            sensors += sensorList.get(i).getName() + "\n";
            tvSensors.append(sensors);
        }

        textLIGHT_available
                = (TextView) findViewById(R.id.LIGHT_available);
        textLIGHT_reading
                = (TextView) findViewById(R.id.LIGHT_reading);

        SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            textLIGHT_available.setText("Sensor.TYPE_LIGHT Available");
            mySensorManager.registerListener(
                    lightSensorListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            textLIGHT_available.setText("Sensor.TYPE_LIGHT NOT Available");
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private final SensorEventListener lightSensorListener
            = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        //
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                textLIGHT_reading.setText("LIGHT: " + event.values[0]);
                if (event.values[0] < 100) {
                    // Toast.makeText(getApplicationContext(), "Adjust your screen light", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

}