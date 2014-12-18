package com.evbrain.android.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.evbrain.android.tcp.AsyncAudioSender;
import com.example.raspberrypiconnect.R;

public class MusicActivity extends ListActivity {

	// TODO: DOWNLOADS will be changed to MUSIC
	private final String MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
	private final int PORT = 4020;
	private final String IP = "192.168.2.105";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		loadMusicsToListView();
		getCurrentSongData();
		findViewById(R.id.button_play).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				clickedPlayButton();
			}
		});
		
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HashMap<String, String> song = (HashMap<String, String>) getListView().getItemAtPosition(position);
				sendAudioToServer(song.get("songPath"));
			}
		});
	}
	
	private void getCurrentSongData()
	{
		
	}
	
	private void loadMusicsToListView()
	{
		ListAdapter adapter = new SimpleAdapter(this, getPlayList(),
                R.layout.play_list_item, new String[] { "songTitle" }, new int[] {
                        R.id.songTitle });
		setListAdapter(adapter);
	}
	
    private ArrayList<HashMap<String, String>> getPlayList(){
        File home = new File(MEDIA_PATH);
        ArrayList<HashMap<String, String>> songList = new ArrayList<HashMap<String, String>>();
        Mp3ExtensionFilter filter = new Mp3ExtensionFilter();
        if (home.listFiles(filter).length > 0) {
        	for (File file : home.listFiles(filter)) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());
                songList.add(song);
            }
        }
        // return songs list array
        return songList;
    }
    
    private void sendAudioToServer(String audioPath)
    {
        new AsyncAudioSender(IP,PORT).execute(audioPath);
    }
    
    private void clickedPlayButton()
    {
    	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music, menu);
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

	private class Mp3ExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}
