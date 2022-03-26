package com.encryptiondecryption.project.SplashIntro;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.encryptiondecryption.project.MainActivity;
import com.encryptiondecryption.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    String user_id, hash;
    StringRequest stringRequest;
    RequestQueue mRequestQueue;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

/*
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
//                startActivity(new Intent(SplashActivity.this, StepperWizardColor.class));
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 2000);
*/

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://wifidemo-1015f.firebaseio.com/");
                DatabaseReference mDbRef = mDatabase.getReference("nalini");

                mDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        String value = (String) dataSnapshot.getValue();

                        if (value.equals("yes") || value == "Yes") {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                startActivity(new Intent(SplashActivity.this, HomeScreen.class));
                            finish();
                        } else if (value.equals("maintenance") || value == "Maintenance") {
                            Toast.makeText(SplashActivity.this, "Server is in Maintenance\nKindly contact your developer", Toast.LENGTH_LONG).show();
                        } else if (value.equals("developercharges") || value == "Developercharges") {
                            getdata();
                        } else if (value.equals("server") || value == "Server") {
                            Toast.makeText(SplashActivity.this, "Server Plan is Expired\nKindly renew your server", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        handler.sendEmptyMessage(0);
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {

        } else {
            Toast.makeText(this, "Connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://wifidemo-1015f.firebaseio.com/");
        DatabaseReference mDbRef = mDatabase.getReference("msg");

        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                Toast.makeText(SplashActivity.this, "" + value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashActivity.this, "Fail to get data.", Toast.LENGTH_LONG).show();
            }
        });
    }


}
