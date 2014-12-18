package com.evbrain.raspberrypi.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author harunurhan
 */
public class AudioStreamingServer implements IServer {

    private Socket connectionSocket;
    private Player player;

    public void serveForever(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.printf("Local server started on %d \n", port);
            while (true) {
                connectionSocket = server.accept();
                BufferedInputStream bis = new BufferedInputStream(connectionSocket.getInputStream());
                if(player != null)
                    player.close();
                player = new Player(bis);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            player.play();
                        } catch (JavaLayerException ex) {
                            System.err.println(ex);
                        }
                    }
                }).start();

            }
        } catch (IOException | JavaLayerException ex) {
            System.err.println(ex);
        }
    }

}
