package serveurChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Serveur {
	    public static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

	    public static void main(String[] args) {
	        int port = 1234;
	        try (ServerSocket server = new ServerSocket(port)) {
	            System.out.println("Serveur lancé sur le port " + port);

	            while (true) {
	                Socket clientSocket = server.accept();
	                ClientHandler handler = new ClientHandler(clientSocket);
	                handler.start();
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
