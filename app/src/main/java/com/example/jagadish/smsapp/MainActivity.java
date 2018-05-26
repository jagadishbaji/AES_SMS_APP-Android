package com.example.jagadish.smsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {
    Button Send,readSms;
    EditText varMsg,varMobile;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Send=(Button) findViewById(R.id.send);
        readSms=(Button) findViewById(R.id.Read);


        readSms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnactivityRead();
            }
        });


        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnactivity();
            }
        });

    }
    //Method to start service

    public void btnactivity()
    {

        Intent i=new Intent(this,sendMsg.class);
        startActivity(i);
    }

    public void btnactivityRead()
    {

        Intent i=new Intent(this,ReadSMS.class);
        startActivity(i);
    }





}
