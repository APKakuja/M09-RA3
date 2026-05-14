import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class FilServidorXat extends Thread {
	private Socket socket;
	private BufferedReader in;

	public FilServidorXat(Socket socket, BufferedReader in) {
		this.socket = socket;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("Client: " + line);
				if (line.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
					System.out.println("Client ha demanat sortir");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Connexió tancada o error: " + e.getMessage());
		} finally {
			try { if (socket != null && !socket.isClosed()) socket.close(); } catch (IOException ex) { /* ignore */ }
		}
	}
}
