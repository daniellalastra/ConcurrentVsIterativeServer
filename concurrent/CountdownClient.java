package concurrent;

import java.io.*;
import java.net.*;

public class CountdownClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java concurrent.CountdownClient <server_ip> <number>");
            System.out.println("Example: java concurrent.CountdownClient localhost 5");
            return;
        }
        
        String serverIP = args[0];
        int number = Integer.parseInt(args[1]);
        
        System.out.println("=== CONCURRENT CLIENT STARTING ===");
        System.out.println("Connecting to: " + serverIP + ", Countdown from: " + number);
        
        try {
            Socket socket = new Socket(serverIP, 8889);  // Different port!
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            out.println(number);
            System.out.println("Sent to server: " + number);
            
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Received from server: " + response);
                if (response.equals("Countdown complete!")) {
                    break;
                }
            }
            
            System.out.println("=== COUNTDOWN COMPLETE ===");
            socket.close();
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
