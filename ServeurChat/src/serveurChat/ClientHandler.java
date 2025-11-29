package serveurChat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    
            out.write("Username: ");
            out.flush();
            String user = in.readLine();

            out.write("Password: ");
            out.flush();
            String pass = in.readLine();

            if (!authentifier(user, pass)) {
                out.write("Identifiants invalides\n");
                out.flush();
                socket.close();
                return;
            }


            this.username = user;
            Serveur.clients.add(this);
            broadcast("*" + username + "* a rejoint le chat");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) break;
                broadcast("*" + username + "* " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Serveur.clients.remove(this);
                broadcast("*" + username + "* a quitté le chat");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean authentifier(String user, String pass) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(pass)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void broadcast(String message) {
        synchronized (Serveur.clients) {
            Iterator<ClientHandler> it = Serveur.clients.iterator();
            while(it.hasNext()) {
                ClientHandler client = it.next();
                try {
                    client.out.write(message + "\n");
                    client.out.flush();
                } catch (IOException e) {
                    it.remove(); // enlève les clients morts
                }
            }
        }
    }

}
