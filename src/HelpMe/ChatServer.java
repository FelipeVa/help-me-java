/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HelpMe;

/**
 *
 * @author pipe
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 8888;
    private List<PrintWriter> clientOutputStreams;

    public static void main(String[] args) {
        new ChatServer().start();
    }

    private void start() {
        clientOutputStreams = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Chat server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(clientSocket, writer));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket, PrintWriter writer) {
            try {
                this.clientSocket = socket;
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.writer = writer;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received: " + message);
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcastMessage(String message) {
            for (PrintWriter clientWriter : clientOutputStreams) {
                clientWriter.println(message);
                clientWriter.flush();
            }
        }
    }
}

