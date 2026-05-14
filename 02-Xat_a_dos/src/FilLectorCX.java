import java.io.BufferedReader;
import java.io.IOException;

public class FilLectorCX extends Thread {
    private BufferedReader in;

    public FilLectorCX(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Servidor: " + line);
                if (line.equalsIgnoreCase(ClientXat.MSG_SORTIR)) {
                    System.out.println("Servidor ha finalitzat la connexió");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connexió tancada o error: " + e.getMessage());
        }
    }
}
