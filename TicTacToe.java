import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    private final int boardWidth = 600;
    private final int boardHeight = 700; // Extra space for restart button

    private final JFrame frame = new JFrame("Tic-Tac-Toe");
    private final JLabel textLabel = new JLabel();
    private final JPanel textPanel = new JPanel();
    private final JPanel boardPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JButton restartButton = new JButton("Restart");

    private final JButton[][] board = new JButton[3][3];
    private final String playerX = "X";
    private final String playerO = "O";
    private String currentPlayer = playerX;

    private boolean gameOver = false;
    private int turns = 0;

    TicTacToe() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Label
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Game Board
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(e -> {
                    if (gameOver) return;
                    JButton clickedTile = (JButton) e.getSource();
                    if (clickedTile.getText().isEmpty()) {
                        clickedTile.setText(currentPlayer);
                        turns++;
                        checkWinner();
                        if (!gameOver) {
                            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                            textLabel.setText(currentPlayer + "'s turn");
                        }
                    }
                });
            }
        }

        // Restart Button
        restartButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> resetGame());

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(restartButton, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void checkWinner() {
        // Horizontal & Vertical Checks
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].getText().isEmpty() &&
                    board[i][0].getText().equals(board[i][1].getText()) &&
                    board[i][1].getText().equals(board[i][2].getText())) {
                highlightWinner(board[i][0], board[i][1], board[i][2]);
                return;
            }

            if (!board[0][i].getText().isEmpty() &&
                    board[0][i].getText().equals(board[1][i].getText()) &&
                    board[1][i].getText().equals(board[2][i].getText())) {
                highlightWinner(board[0][i], board[1][i], board[2][i]);
                return;
            }
        }

        // Diagonal Checks
        if (!board[0][0].getText().isEmpty() &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            highlightWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        if (!board[0][2].getText().isEmpty() &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            highlightWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        // Tie Condition
        if (turns == 9) {
            textLabel.setText("It's a Tie!");
            for (JButton[] row : board) {
                for (JButton tile : row) {
                    tile.setForeground(Color.ORANGE);
                    tile.setBackground(Color.GRAY);
                }
            }
            gameOver = true;
        }
    }

    private void highlightWinner(JButton... tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.GREEN);
            tile.setBackground(Color.GRAY);
        }
        textLabel.setText(currentPlayer + " Wins!");
        gameOver = true;
    }

    private void resetGame() {
        for (JButton[] row : board) {
            for (JButton tile : row) {
                tile.setText("");
                tile.setForeground(Color.WHITE);
                tile.setBackground(Color.DARK_GRAY);
            }
        }
        currentPlayer = playerX;
        gameOver = false;
        turns = 0;
        textLabel.setText("Tic-Tac-Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
