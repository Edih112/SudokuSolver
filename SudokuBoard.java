import javax.swing.*;
import java.awt.*;

public class SudokuBoard {
    private JFrame window;
    private JPanel board;
    JTextField nums[][];
    private JButton solve;
    private JButton newGame;
    private JPanel controls;
    private JLabel mistakes;
    private JLabel gameOver;

    public SudokuBoard(int[][] grid) {
        window = new JFrame();
        window.setSize(new Dimension(450, 400));
        GridBagLayout myLayout = new GridBagLayout();
        window.setLayout(myLayout);
        // window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareSudokuGrid(grid);
        setControlPanel();
    }

    // override actionPerformed method
    public JButton solveButton() {
        return solve;
    }

    public JPanel gridPanel() {
        return board;
    }

    public JFrame gameWindow() {
        return window;
    }

    public JButton newGame() {
        return newGame;
    }

    public void prepareSudokuGrid(int[][] grid) {
        board = new JPanel(new GridLayout(9, 9));
        board.setSize(new Dimension(700, 700));
        nums = new JTextField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nums[i][j] = new JTextField();
                nums[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // everyother 3x3 has different backgound color
                if (((i < 3 || i > 5) && (j < 3 || j > 5)) || (i > 2 && i < 6) && (j > 2 && j < 6)) {
                    nums[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    nums[i][j].setBackground(Color.WHITE);
                }
                nums[i][j].setEditable(true);
                nums[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                Font font = new Font("Arial", Font.BOLD, 20);
                nums[i][j].setFont(font);
                if (grid[i][j] != 0) {
                    nums[i][j].setText("" + grid[i][j]);
                    nums[i][j].setEditable(false);
                }
                board.add(nums[i][j]);
            }
        }
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 6;
        c.ipadx = 130;
        c.ipady = 10;
        c.fill = GridBagConstraints.VERTICAL;
        window.add(board, c);
    }

    public void setControlPanel() {
        mistakes = new JLabel();
        gameOver = new JLabel();
        solve = new JButton("solve");
        newGame = new JButton("new Game");
        controls = new JPanel(new GridLayout(4, 1));
        controls.add(solve);
        controls.add(mistakes);
        controls.add(gameOver);
        controls.add(newGame);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 10);
        window.add(controls, c);
    }

    public void updateMistakes(int mistakeCount, int maxMistakes) {
        mistakes.setText("Mistakes: " + mistakeCount + "/" + maxMistakes);
    }

    public void showGameOver() {
        gameOver.setText("GAME OVER");
        gameOver.setForeground(Color.RED);
    }

    public void showboard() {
        window.setVisible(true);
    }

    // updates the board with new solved value
    public void updateBoard(int[][] newGrid) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nums[i][j].setText("" + newGrid[i][j]);
            }
        }
    }
}