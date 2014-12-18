package com.evbrain.raspberrypi.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.evbrain.common.serializable.FileEvent;
import com.evbrain.raspberrypi.gpio.GpioHelper;

/**
 *
 * @author harunurhan
 */
public class GeneralPurposeServer implements IServer{

	private Socket connectionSocket;

	public void serveForever(int port) {

		try {
			ServerSocket server = new ServerSocket(port);
			System.out.printf("Local server started on %d \n", port);
			while (true) {
				connectionSocket = server.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String clientCommand = inFromClient.readLine();
				if (clientCommand != null) {
					System.out.println("Received command -> " + clientCommand);
					handleClientCommand(clientCommand);
				}
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}





	private void handleClientCommand(String cmd) {
		if( cmd == "file-request")
		{
			sendFileToClient("/Users/harunurhan/Desktop/toSend.rtf");
		}
		else if(cmd.startsWith("open"))
		{
			int pinNo = Integer.parseInt(cmd.split(" ")[1]);
			GpioHelper.setPin(true, pinNo);
		}
		else if(cmd.startsWith("close"))
		{
			int pinNo = Integer.parseInt(cmd.split(" ")[1]);
			GpioHelper.setPin(false, pinNo);
		}

	}

	private void sendFileToClient(String sourceFilePath) {
		try {
			FileEvent fileEvent = new FileEvent();
			String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
			String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
			fileEvent.setFilename(fileName);
			fileEvent.setSourceDirectory(sourceFilePath);
			File file = new File(sourceFilePath);
			if (file.isFile()) {

				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
						fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");

			} else {
				System.out.println("path specified is not pointing to a file");
				fileEvent.setStatus("Error");
			}
			//Now writing the FileEvent object to socket

			ObjectOutputStream outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
			outputStream.writeObject(fileEvent);
			System.out.println("File sent");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
