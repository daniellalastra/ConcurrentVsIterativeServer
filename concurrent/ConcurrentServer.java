
package concurrent;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import common.CountdownProtocol;

public class ConcurrentServer {
    private static final int PORT = 8889;
    private static final int THREAD_POOL_SIZE = 10;
    
    public static void main(String[] args) {
        System.out.println("=== CONCURRENT COUNTDOWN SERVER STARTING ===");
        System.out.println("Server can handle multiple clients simultaneously");
        System.out.println("Listening on port: " + PORT);
        
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                
                // Handle each client in a separate thread
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            CountdownProtocol protocol = new CountdownProtocol();
            String clientMessage = in.readLine();
            System.out.println("[" + Thread.currentThread().getName() + "] Received: " + clientMessage);
            
            if (clientMessage != null) {
                String response = protocol.processRequest(clientMessage);
                out.println(response);
                
                while (protocol.isSessionActive()) {
                    response = protocol.processRequest("continue");
                    out.println(response);
                }
            }
            
            System.out.println("[" + Thread.currentThread().getName() + "] Client session completed");
            
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

