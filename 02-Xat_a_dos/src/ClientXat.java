import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {
    public static final String HOST = "localhost";
    public static final int PORT = 9999;
    public static final String MSG_SORTIR = "sortir";

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public void connecta(String nom) throws IOException {
        socket = new Socket(HOST, PORT);
        System.out.println("Connectat al servidor: " + socket.getRemoteSocketAddress());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        out.println(nom);
    }

    public void tancarClient() {
        try { if (socket != null && !socket.isClosed()) socket.close(); } catch (IOException ex) { /* ignore */ }
    }

    public static void main(String[] args) {
        ClientXat client = new ClientXat();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Introdueix el teu nom: ");
            String nom = scanner.nextLine();
            client.connecta(nom);
            
            FilLectorCX fil = new FilLectorCX(client.in);
            
            fil.start();
            
            System.out.println("Escriu missatges (escriu '" + MSG_SORTIR + "' per tancar):");
            String line;
            while (true) {
                line = scanner.nextLine();
                client.out.println(line);
                if (line.equalsIgnoreCase(MSG_SORTIR)) break;
            }
            
            fil.join();
            
            client.tancarClient();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
