import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static final int PORT = 7777;
    public static final String HOST = "localhost";
    private ServerSocket srvSocket;
    private Socket clientSocket;

    public void connecta() throws IOException {
        srvSocket = new ServerSocket(PORT);
        System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
        System.out.println("Esperant connexions a " + HOST + ":" + PORT);
        clientSocket = srvSocket.accept();
        System.out.println("Client connectat: " + clientSocket.getRemoteSocketAddress());
    }

    public void repDades() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println("Rebut: " + line);
            if ("Adeu!".equals(line)) break;
        }
    }

    public void tanca() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
        }
        try {
            if (srvSocket != null && !srvSocket.isClosed()) {
                srvSocket.close();
            }
        } catch (IOException e) {
        }
        System.out.println("Servidor tancat.");
    }

    public static void main(String[] args) {
        Servidor serv = new Servidor();
        try {
            serv.connecta();
            serv.repDades();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            serv.tanca();
        }
    }
}
