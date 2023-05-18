import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // Belirli bir portu dinlemek için sunucu soketi oluşturma
            ServerSocket serverSocket = new ServerSocket(8080);

            System.out.println("Sunucu başlatıldı. İstemci bağlantıları dinleniyor...");

            // İstemci bağlantısı kabul etme
            Socket clientSocket = serverSocket.accept();
            System.out.println("İstemci bağlantısı kabul edildi.");

            // Soket üzerinden veri göndermek için PrintWriter kullanma
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Soket üzerinden veri almak için BufferedReader kullanma
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // İstemciden gelen mesajı alma
            String message = in.readLine();
            System.out.println("İstemciden gelen mesaj: " + message);

            // İstemciye cevap gönderme
            out.println("Mesajınızı aldım.");

            // Kaynakları temizleme
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
