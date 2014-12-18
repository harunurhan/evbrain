package com.evbrain.android.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.evbrain.android.tcp.AsyncSender;
import com.evbrain.common.serializable.FileEvent;
import com.example.raspberrypiconnect.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    public static String IP; //TODO: these will be passed with intent
    public static int PORT; 
	
    private EditText ipBox;
    private EditText portBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipBox = (EditText) findViewById(R.id.textview_ip);
        portBox = (EditText) findViewById(R.id.textview_port);
        findViewById(R.id.button_connect).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				IP = ipBox.getText().toString();
				PORT = Integer.parseInt(portBox.getText().toString());
				Intent controlActivity = new Intent(MainActivity.this,ControlActivity.class);
				startActivity(controlActivity);
			}
		});
    }
	



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    
}
