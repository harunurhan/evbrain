package com.evbrain.raspberrypi.server;

import java.io.IOException;
import java.util.Scanner;

public class MainServerTest {

	public static void main(String[] args) throws IOException {
        final int port;
        Scanner scn = new Scanner(System.in);
        if (args.length <= 1) {
            System.out.println("Please write port number to start serving");
            port = scn.nextInt();
        } else {
            port = Integer.valueOf(args[1]);
        }
        System.out.print("What do you want to test ?\n"
                + "1) General purpose server\n"
                + "2) Audio streaming\n");
        String selected = scn.nextLine();
        switch(scn.nextLine())
        {
            case "1":
                testGeneralPurposeServer(port);
                break;
            case "2":
                testAudioStreamingServer(port);
                break;

        }

	}
	
    private static void testGeneralPurposeServer(int port) throws IOException {
        GeneralPurposeServer server = new GeneralPurposeServer();
        server.serveForever(port);
    }
    
    private static void testAudioStreamingServer(int port)
    {
        AudioStreamingServer server = new AudioStreamingServer();
        server.serveForever(port);
    }

}
