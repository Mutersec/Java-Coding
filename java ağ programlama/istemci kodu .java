import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // Sunucuya bağlanmak için socket oluşturma
            Socket socket = new Socket("localhost", 8080);

            // Soket üzerinden veri göndermek için PrintWriter kullanma
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Soket üzerinden veri almak için BufferedReader kullanma
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Kullanıcıdan giriş alma
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Mesajınızı girin: ");
            String message = userInput.readLine();

            // Mesajı sunucuya gönderme
            out.println(message);

            // Sunucudan gelen cevabı alma
            String response = in.readLine();
            System.out.println("Sunucudan gelen cevap: " + response);

            // Kaynakları temizleme
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}
	
}

