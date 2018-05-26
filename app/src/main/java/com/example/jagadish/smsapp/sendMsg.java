package com.example.jagadish.smsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class sendMsg extends AppCompatActivity  {
    Button Send,clearbtn;
    EditText varMsg,varMobile;
    TextView txt;
    String sms;
    String AES="AES";
    String enc;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        Send=(Button) findViewById(R.id.send);
        varMobile=(EditText) findViewById(R.id.txt_mobile);
        varMsg=(EditText) findViewById(R.id.txt_msg);
        txt=(TextView) findViewById(R.id.txt_enc);
        clearbtn=(Button) findViewById(R.id.clear_btn);

        clearbtn.setOnClickListener(new View.OnClickListener(){

            @Override
                    public void onClick(View view){
                sendClear();
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }
    //Method to start service



    public void sendSms()
    {
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED)
        {
            MyMessage();
        }
        else
        {
            //todo
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }


public void sendClear(){
    String number=varMobile.getText().toString().trim();
    String msg=varMsg.getText().toString().trim();
    //check mobile number
    if(number==null || number.equals("")||msg==null||msg.equals("")){
        Toast.makeText(this,"Field cannot be empty",Toast.LENGTH_SHORT).show();

    }
    else
    {
        if(TextUtils.isDigitsOnly(number))
        {

                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,msg,null,null);
                Toast.makeText(this,"Message Sent without Data  Encryption",Toast.LENGTH_SHORT).show();
            txt.setText("Data sent without Encryption");
            txt.setTextColor(0xFFFF0000);


        }
        else
        {
            Toast.makeText(this,"Please Enter Integer Only",Toast.LENGTH_SHORT).show();
        }

    }
}

    public void MyMessage(){
        String number=varMobile.getText().toString().trim();
        String msg=varMsg.getText().toString().trim();
        //check mobile number
        if(number==null || number.equals("")||msg==null||msg.equals("")){
            Toast.makeText(this,"Field cannot be empty",Toast.LENGTH_SHORT).show();

        }
        else
        {
            if(TextUtils.isDigitsOnly(number))
            {
                try {
                    sms = encryptData(msg);
                    txt.setText(sms);
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,msg,null,null);
                    Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(this,"Please Enter Integer Only",Toast.LENGTH_SHORT).show();
            }

        }
        //end checking number

    }
    public String encryptData(String data) throws Exception
    {
        SecretKeySpec key=generateKey(data);
        Cipher cipher=Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal=cipher.doFinal(data.getBytes());
        String encryptVal= Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptVal;



    }
    private SecretKeySpec generateKey(String data) throws Exception{
        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=data.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec spec=new SecretKeySpec(key,"AES");
        return spec;
    }






    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResult)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if(grantResult.length>=0&& grantResult[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //name of method calling
                    MyMessage();
                }
                else
                {
                    Toast.makeText(this,"You dont have permissins",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
