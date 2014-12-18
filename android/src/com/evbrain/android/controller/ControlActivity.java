package com.evbrain.android.controller;

import com.evbrain.android.tcp.AsyncSender;
import com.example.raspberrypiconnect.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ControlActivity extends Activity {

	private EditText commandBox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		commandBox = (EditText) findViewById(R.id.textview_command);
		
		findViewById(R.id.button_send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncSender(MainActivity.IP, MainActivity.PORT).execute(commandBox.getText().toString());
				commandBox.setText("");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
