import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorXat {
    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";

    ServerSocket serverSocket;

    public void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat al port " + PORT);
    }

    public void pararServidor() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
            System.out.println("Servidor aturat");
        }
    }

    public String getNom(BufferedReader in) throws IOException {
        System.out.print("Esperant nom del client... ");
        String nom = in.readLine();
        System.out.println("Nom rebut: " + nom);
        return nom;
    }

    public static void main(String[] args) {
        ServidorXat servidor = new ServidorXat();
        try {
            servidor.iniciarServidor();
            
            Socket clientSocket = servidor.serverSocket.accept();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            FilServidorXat fil = new FilServidorXat(clientSocket, in);
            
            fil.start();
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escriu missatges (escriu '" + MSG_SORTIR + "' per tancar):");
            String line;
            while (true) {
                line = scanner.nextLine();
                out.println(line);
                if (line.equalsIgnoreCase(MSG_SORTIR)) break;
            }
            
            fil.join();
            
            try { clientSocket.close(); } catch (IOException ex) { /* ignore */ }
            servidor.pararServidor();
            scanner.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

