import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class SnakeGame implements KeyListener {
    private JFrame frame;
    private JPanel panel;
    private int width = 20; // Oyun alanının genişliği
    private int height = 20; // Oyun alanının yüksekliği
    private int[][] grid; // Oyun alanını temsil eden 2D matris
    private int snakeX, snakeY; // Yılanın başının koordinatları
    private int appleX, appleY; // Elmanın koordinatları
    private int direction; // Yılanın hareket yönü
    private int score; // Oyuncunun puanı
    private Timer timer;

    public SnakeGame() {
        frame = new JFrame("Yılan Oyunu");
        panel = new JPanel();
        frame.add(panel);
        frame.setSize(width * 20, height * 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);

        grid = new int[width][height];
        snakeX = width / 2;
        snakeY = height / 2;
        grid[snakeX][snakeY] = 1; // Yılanın başının olduğu hücreyi işaretle
        generateApple();

        timer = new Timer(100, e -> gameLoop());
        timer.start();
    }

    private void generateApple() {
        appleX = (int) (Math.random() * width);
        appleY = (int) (Math.random() * height);

        // Elmanın düşmesi gereken yerde yılanın olduğu durumu kontrol et
        if (grid[appleX][appleY] != 0) {
            generateApple(); // Yılanın olduğu yerde yeniden elma üret
        }
    }

    private void gameLoop() {
        // Yılanın hareket ettiği kodlar
        if (direction == KeyEvent.VK_UP) {
            snakeY--;
        } else if (direction == KeyEvent.VK_DOWN) {
            snakeY++;
        } else if (direction == KeyEvent.VK_LEFT) {
            snakeX--;
        } else if (direction == KeyEvent.VK_RIGHT) {
            snakeX++;
        }

        // Yılanın oyun alanından çıkmasını kontrol et
        if (snakeX < 0 || snakeX >= width || snakeY < 0 || snakeY >= height) {
            gameOver();
            return;
        }

        // Yılanın kendine çarpmasını kontrol et
        if (grid[snakeX][snakeY] != 0) {
            gameOver();
            return;
        }

        // Yılanın hareket ettiği hücreyi işaretle
        grid[snakeX][snakeY] = 1;

        // Yılanın başının elmayı yemesini kontrol et
        if (snakeX == appleX && snakeY == appleY) {
            score++;
            generateApple();
        } else {
            // Yılanın kuyruğunu güncelle
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (grid[i][j] > 0) {
                        grid[i][j]--;
                    }
                }
            }
        }

        // Oyun alanını güncelle
        panel.repaint();
    }

    private void gameOver() {
        timer.stop();
        System.out.println("Oyun Bitti! Puanınız: " + score);
    }

    private void draw(Graphics g) {
        // Oyun alanını çiz
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width * 20, height * 20);

        // Yılanı çiz
        g.setColor(Color.GREEN);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] > 0) {
                    g.fillRect(i * 20, j * 20, 20, 20);
                }
            }
        }

        // Elmayı çiz
        g.setColor(Color.RED);
        g.fillRect(appleX * 20, appleY * 20, 20, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        direction = e.getKeyCode();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}
