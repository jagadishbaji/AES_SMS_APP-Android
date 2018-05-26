package com.example.jagadish.smsapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReadSMS extends AppCompatActivity {
ListView listView;
    private static final int PERMISSION_REQUEST_READ_CONTACTS=100;
    ArrayList<String> smsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);

        listView=(ListView) findViewById(R.id.listView);
        //method to start service
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED)
        {
            showContacts();
        }
        else
        {
            //TODO
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},PERMISSION_REQUEST_READ_CONTACTS);
        }

    }

    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults)
    {
        if(requestCode==PERMISSION_REQUEST_READ_CONTACTS)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                showContacts();

            }
            else
            {
                Toast.makeText(this,"Permission should grant",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showContacts()
    {
        Uri inboxURI=Uri.parse("content://sms/inbox");
        smsList =new ArrayList<String>();

        ContentResolver cr=getContentResolver();
        Cursor c=cr.query(inboxURI,null,null,null,null);
        while(c.moveToNext())
        {
            String Number=c.getString(c.getColumnIndexOrThrow("address")).toString();
            String Body=c.getString(c.getColumnIndexOrThrow("body")).toString();
            smsList.add("Number: "+Number+"\n"+"Body: "+Body);

        }
        c.close();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,smsList);
        listView.setAdapter(adapter);

    }
}
