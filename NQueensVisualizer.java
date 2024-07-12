import javax.swing.*;
import java.awt.*;

public class NQueensVisualizer extends JPanel {

    private int size;
    private int[][] board;
    private static final int CELL_SIZE = 50;
    private static final int PADDING = 100; // Padding around the board
    private static final int SLEEP_TIME = 1000; // Sleep time in milliseconds

    public NQueensVisualizer(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = PADDING / 2;
        int yOffset = PADDING / 2;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.CYAN);
                }
                g.fillRect(xOffset + col * CELL_SIZE, yOffset + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (board[row][col] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(xOffset + col * CELL_SIZE, yOffset + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void placeQueens(int n) {
        placeQueensUtil(0, n);
    }

    private boolean placeQueensUtil(int row, int n) {
        if (n == 0) {
            repaint();
            return true;
        }
        if (row >= size) {
            return false;
        }
        for (int col = 0; col < size; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1;
                repaint();
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (placeQueensUtil(row + 1, n - 1)) {
                    return true;
                }
                board[row][col] = 0;
                repaint();
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        for (int i = row, j = col; i >= 0 && j < size; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(JOptionPane.showInputDialog("Enter board size:"));
        int queens = Integer.parseInt(JOptionPane.showInputDialog("Enter number of queens:"));

        JFrame frame = new JFrame("N-Queens Visualizer");
        NQueensVisualizer visualizer = new NQueensVisualizer(size);
        frame.add(visualizer);
        frame.setSize(size * CELL_SIZE + PADDING, size * CELL_SIZE + PADDING);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        visualizer.placeQueens(queens);
    }
}
