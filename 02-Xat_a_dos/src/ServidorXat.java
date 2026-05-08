import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;

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
}

