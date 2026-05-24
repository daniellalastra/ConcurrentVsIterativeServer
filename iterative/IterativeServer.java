// iterative/IterativeServer.java
package iterative;

import java.io.*;
import java.net.*;
import common.CountdownProtocol;

public class IterativeServer {
    private static final int PORT = 8888;
    
    public static void main(String[] args) {
        System.out.println("=== ITERATIVE COUNTDOWN SERVER STARTING ===");
        System.out.println("Server will handle one client at a time");
        System.out.println("Listening on port: " + PORT);
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            
            while (true) {
                System.out.println("\n--- Waiting for client connection ---");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
                clientSocket.close();
                System.out.println("Client disconnected. Ready for next client.");
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            CountdownProtocol protocol = new CountdownProtocol();
            
            String clientMessage = in.readLine();
            System.out.println("Received from client: " + clientMessage);
            
            if (clientMessage != null) {
                String response = protocol.processRequest(clientMessage);
                out.println(response);
                System.out.println("Sent to client: " + response);
                
                while (protocol.isSessionActive()) {
                    response = protocol.processRequest("continue");
                    out.println(response);
                    System.out.println("Sent to client: " + response);
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}
