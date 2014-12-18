package com.evbrain.android.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.AsyncTask;

public class AsyncAudioSender extends AsyncTask<String, Void, Boolean> {

	private Socket clientSocket;
	private String ip;
	private int port;
	
	public AsyncAudioSender(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
        try
        {
			clientSocket = new Socket(ip, port);
	    	File file = new File(params[0]);
	        int length = (int)file.length();
	        FileInputStream fis = new FileInputStream(params[0]);
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        BufferedOutputStream outToServer = new BufferedOutputStream(clientSocket.getOutputStream());
	        System.out.println(length);
	        byte [] byte_array = new byte[length];
	        bis.read(byte_array, 0, length);
	        outToServer.write(byte_array, 0, length);
	        clientSocket.close();
        	return Boolean.TRUE;
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        	return Boolean.FALSE;
        }
	}
	
}
