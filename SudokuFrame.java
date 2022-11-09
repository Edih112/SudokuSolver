import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuFrame {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Sudoku Solver");
        JButton solveButton = new JButton("solve");
        JPanel Board = new JPanel(new GridLayout(3, 3));

        JPanel board[][] = new JPanel[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = newSubBoard();
                board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                Board.add(board[i][j]);
            }
        }
        Board.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        JPanel buttons = new JPanel();
        buttons.add(solveButton);

        frame.setSize(500, 500);
        frame.add(buttons);

        frame.add(Board);
        frame.setVisible(true);
    }

    public static JPanel newSubBoard() {
        JPanel subPanel = new JPanel(new GridLayout(3, 3));
        JLabel subBoard[][] = new JLabel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JLabel subBoardComponent = subBoard[i][j];
                subBoardComponent = new JLabel();
                subBoardComponent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                Font font = new Font("Arial", Font.PLAIN, 20);

                subBoard[i][j].setFont(font);
                subBoard[i][j].setForeground(Color.WHITE);
                subBoard[i][j].setBackground(Color.WHITE);
                subBoard[i][j].setOpaque(true);
                subBoard[i][j].setHorizontalAlignment(JTextField.CENTER);

                subPanel.add(subBoard[i][j]);
            }
        }
        return subPanel;
    }
}
