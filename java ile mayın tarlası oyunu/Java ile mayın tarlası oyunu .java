import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {
    private static final int BOARD_SIZE = 10;
    private static final int NUM_MINES = 10;
    private static final char HIDDEN_CELL = '-';
    private static final char MINE_CELL = '*';

    private char[][] board;
    private boolean[][] revealed;
    private boolean gameOver;

    public MinesweeperGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
        gameOver = false;

        // Board'u gizli hücrelerle doldur
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = HIDDEN_CELL;
            }
        }

        // Mayınları yerleştir
        Random random = new Random();
        int count = 0;
        while (count < NUM_MINES) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (board[row][col] != MINE_CELL) {
                board[row][col] = MINE_CELL;
                count++;
            }
        }
    }

    public void play() {
        System.out.println("Ronesa Oyununa Hoş Geldiniz! Yapımcı Muter");
        System.out.println("Oyuna başlamak için koordinatları girmeniz gerekmektedir.");

        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            printBoard();

            System.out.print("Satır girin (0-" + (BOARD_SIZE - 1) + "): ");
            int row = scanner.nextInt();

            System.out.print("Sütun girin (0-" + (BOARD_SIZE - 1) + "): ");
            int col = scanner.nextInt();

            if (isValidMove(row, col)) {
                if (board[row][col] == MINE_CELL) {
                    gameOver = true;
                    revealBoard();
                    System.out.println("Mayına bastınız! Oyun bitti.");
                } else {
                    revealCell(row, col);
                    if (checkWin()) {
                        gameOver = true;
                        revealBoard();
                        System.out.println("Tebrikler! Oyunu kazandınız.");
                    }
                }
            } else {
                System.out.println("Geçersiz hamle. Lütfen tekrar deneyin.");
            }
        }

        scanner.close();
    }

    private void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(HIDDEN_CELL + " ");
                }
            }
            System.out.println();
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && !revealed[row][col];
    }

    private void revealCell(int row, int col) {
        if (board[row][col] == HIDDEN_CELL) {
            revealed[row][col] = true;

            int count = countAdjacentMines(row, col);
            if (count > 0) {
                board[row][col] = (char) (count + '0');
            } else {
                revealAdjacentCells(row, col);
            }
        }
    }

    private void revealAdjacentCells(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE) {
                    revealCell(i, j);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && board[i][j] == MINE_CELL) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean checkWin() {
        int totalCells = BOARD_SIZE * BOARD_SIZE;
        int revealedCells = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (revealed[i][j]) {
                    revealedCells++;
                }
            }
        }
        return revealedCells == totalCells - NUM_MINES;
    }

    private void revealBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                revealed[i][j] = true;
            }
        }
    }

    public static void main(String[] args) {
        MinesweeperGame game = new MinesweeperGame();
        game.play();
    }
}
