package com.evbrain.android.tcp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.evbrain.common.serializable.FileEvent;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class AsyncSender extends AsyncTask<String, Void, Boolean> {

	private Socket clientSocket;
	private int port;
	private String ip;
	
	public AsyncSender(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
        try
        {
        	clientSocket = new Socket(ip, port);
        	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        	outToServer.writeBytes(params[0] + '\n');
        	if(params[0].equals("file-request"))
        		getFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/");
        	clientSocket.close();
        	return Boolean.TRUE;
        }
        catch(IOException ex)
        {
        	ex.printStackTrace();
        	return Boolean.FALSE;
        }
	}
	
	private void getFile(String destinationPath) {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            FileEvent fileEvent = (FileEvent) inputStream.readObject();
            fileEvent.setDestinationDirectory(destinationPath);
            if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                Log.d("DEBUG_","Error occurred ..So exiting");
                return;
            }
            String outputFile = fileEvent.getDestinationDirectory() + fileEvent.getFilename();
            if (!new File(fileEvent.getDestinationDirectory()).exists()) {
                new File(fileEvent.getDestinationDirectory()).mkdirs();
            }
            File dstFile = new File(outputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            clientSocket.close();
            Log.d("DEBUG_","Output file : " + outputFile + " is successfully saved ");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	
}
