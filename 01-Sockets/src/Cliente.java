import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static final int PORT = 7777;
    public static final String HOST = "localhost";
    private Socket socket;
    private PrintWriter out;

    public void connecta() throws IOException {
        socket = new Socket(HOST, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
    }

    public void envia(String msg) {
        if (out != null) {
            out.println(msg);
            System.out.println("Enviat al servidor: " + msg);
        }
    }

    public void tanca() {
        try {
            if (out != null) out.close();
        } catch (Exception e) {
        }
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
        }
        System.out.println("Client tancat");
    }

    public static void main(String[] args) {
        Cliente c = new Cliente();
        try {
            c.connecta();
            c.envia("Prova d'enviament 1");
            c.envia("Prova d'enviament 2");
            c.envia("Adeu!");
            System.out.println("Prem Enter per tancar el client...");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            c.tanca();
        }
    }
}
