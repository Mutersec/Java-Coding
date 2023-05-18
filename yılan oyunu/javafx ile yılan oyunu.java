import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    private static final int WIDTH = 800; // Oyun alanının genişliği
    private static final int HEIGHT = 600; // Oyun alanının yüksekliği
    private static final int CELL_SIZE = 20; // Hücre boyutu
    private static final int GRID_WIDTH = WIDTH / CELL_SIZE; // Oyun alanının genişliği hücre sayısı
    private static final int GRID_HEIGHT = HEIGHT / CELL_SIZE; // Oyun alanının yüksekliği hücre sayısı

    private int snakeX, snakeY; // Yılanın başının koordinatları
    private int appleX, appleY; // Elmanın koordinatları
    private int directionX, directionY; // Yılanın hareket yönü
    private boolean gameRunning = true; // Oyun durumu
    private int score = 0; // Oyuncunun puanı

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.UP && directionY != 1) {
                directionX = 0;
                directionY = -1;
            } else if (keyCode == KeyCode.DOWN && directionY != -1) {
                directionX = 0;
                directionY = 1;
            } else if (keyCode == KeyCode.LEFT && directionX != 1) {
                directionX = -1;
                directionY = 0;
            } else if (keyCode == KeyCode.RIGHT && directionX != -1) {
                directionX = 1;
                directionY = 0;
            }
        });

        primaryStage.setTitle("Yılan Oyunu");
        primaryStage.setScene(scene);
        primaryStage.show();

        snakeX = GRID_WIDTH / 2;
        snakeY = GRID_HEIGHT / 2;
        directionX = 0;
        directionY = -1;
        generateApple();

        AnimationTimer gameLoop = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) {
                    lastUpdate = now;

                    if (gameRunning) {
                        update();
                        draw(gc);
                    }
                }
            }
        };

        gameLoop.start();
    }

    private void update() {
        snakeX += directionX;
        snakeY += directionY;

        // Yılanın oyun alanından çıkmasını kontrol et
        if (snakeX < 0 || snakeX >= GRID_WIDTH || snakeY < 0 || snakeY >= GRID_HEIGHT) {
            gameRunning = false;
        }

        // Yılanın kendine çarpmasını kontrol et
        // TODO: Yılanın kendine çarpm
