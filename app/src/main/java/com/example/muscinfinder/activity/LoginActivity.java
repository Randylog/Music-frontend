package com.example.muscinfinder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muscinfinder.R;
import com.example.muscinfinder.required_classes.LoginBll;
import com.example.muscinfinder.required_classes.StrictModeClass;

public class LoginActivity extends AppCompatActivity {

    TextView txtCreate,tvLogin;
    EditText editText_email, editText_password;
    Button btn_login;
    public static String status = "loggedin";

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        txtCreate = findViewById(R.id.create);
        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login = findViewById(R.id.btnLogin);
        editText_email = findViewById(R.id.etEmail);
        editText_password = findViewById(R.id.etPassword);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();
        LoginBll loginBLL = new LoginBll();
        StrictModeClass.StrictMode();
        if (loginBLL.checkuser(email, password)) {

            // notification here
            Intent notificationIntent = new Intent(LoginActivity.this, MainActivity. class );
            notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
            notificationIntent.setAction(Intent. ACTION_MAIN) ;
            notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
            PendingIntent resultIntent = PendingIntent. getActivity (LoginActivity.this, 0 , notificationIntent , 0 ) ;
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LoginActivity. this, default_notification_channel_id )
                    .setSmallIcon(R.drawable.launcher)
                    .setContentTitle( "Login Successful" )
                    .setContentIntent(resultIntent)
                    .setStyle( new NotificationCompat.InboxStyle())
                    .setContentText( "You are successfully been logged in!!" ) ;
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                int importance = NotificationManager. IMPORTANCE_HIGH ;
                NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                assert mNotificationManager != null;
                mNotificationManager.createNotificationChannel(notificationChannel) ;
            }
            assert mNotificationManager != null;
            mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;

            // store status of successful login
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("status", status); // Storing string
            editor.commit(); // commit changes
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();

            editText_email.requestFocus();
        }


    }

}
