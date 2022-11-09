import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SudokuSolver1 implements ActionListener {
    private int[][] grid;
    private int[][] oldGrid;
    private SudokuBoard board;
    private int mistakeCounter;
    private int maxMistakes;
    private Random rand;

    // takes unsolved sudoku grid as input
    // intializes new SudokuSolver for grid
    // Parameters:
    // int[][] grid - 2d array of sudoku board, empty cells = 0
    public SudokuSolver1(int[][] inputGrid) {
        rand = new Random();
        mistakeCounter = 0;
        maxMistakes = 3;
        this.grid = inputGrid;
        this.oldGrid = new int[9][9];
        for (int i = 0; i < grid.length; i++) {
            int[] subArray = new int[grid[0].length];
            System.arraycopy(grid[i], 0, subArray, 0, 9);
            oldGrid[i] = subArray;
        }
    }

    public void addBoard(SudokuBoard board) {
        this.board = board;
        board.updateMistakes(mistakeCounter, maxMistakes);
    }

    // Adds action checker for every empty sudoku square, user can
    // enter guess by typing number and pressing "ENTER", if number
    // is correct, number turns blue, if false, number turns red.
    //
    // should also implement mistake counter.
    public void addFieldAction() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (oldGrid[i][j] == 0) {
                    int correctVal = grid[i][j];
                    JTextField curr = board.nums[i][j];
                    Action action = new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String val = curr.getText();
                            try {
                                int intVal = Integer.parseUnsignedInt(val);
                                if (correctVal != intVal) {
                                    curr.setForeground(Color.RED);
                                    mistakeCounter += 1;
                                    board.updateMistakes(mistakeCounter, maxMistakes);
                                    if (mistakeCounter == 3) {
                                        board.showGameOver();
                                    }
                                } else {
                                    curr.setForeground(Color.BLUE);
                                    curr.setEditable(false);
                                }
                            } catch (NumberFormatException ex) {
                                curr.setForeground(Color.BLACK);
                            }
                        }
                    };
                    curr.addActionListener(action);
                }
            }
        }
    }

    // checks if the Sudoku grid is valid when cell at position
    // "row, col,"" is assigned the "val."
    // returns true if valid, otherwise false;
    // Parameters:
    // int row - row number of cell
    // int col - col number of cell
    // int val - value assigned to cell
    private boolean isValid(int row, int col, int val, int[][] grid) {
        // checks row and column
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == val || grid[i][col] == val) {
                return false;
            }
        }
        // check box
        int[][] bounds = boxBound(row, col);
        for (int i = bounds[0][0]; i < bounds[0][1] + 1; i++) {
            for (int j = bounds[1][0]; j < bounds[1][1] + 1; j++) {
                if (grid[i][j] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    // checks if the sudoku is solvable, checks for invalid
    // placements in sudoku game
    private Boolean isSolvable() {
        Set<Integer> numCounts = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (numCounts.contains(grid[i][j])) {
                    return false;
                } else if (grid[i][j] != 0) {
                    numCounts.add(grid[i][j]);
                }
            }
            numCounts.clear();
        }
        return true;
    }

    // finds the 3x3 box within the 9x9 sudoku board in which the
    // given coordinates "row, col" lie in,
    // returns row constraints as bounds[0] and column constraints as bounds[1]
    // Parameters:
    // int row - row number of cell
    // int col - col number of cell
    private int[][] boxBound(int row, int col) {
        int[] boxCols = new int[2];
        int[] boxRows = new int[2];
        if (row >= 0 && row <= 2) {
            boxRows[0] = 0;
            boxRows[1] = 2;
        } else if (row >= 3 && row <= 5) {
            boxRows[0] = 3;
            boxRows[1] = 5;
        } else if (row >= 6 && row <= 8) {
            boxRows[0] = 6;
            boxRows[1] = 8;
        }
        if (col >= 0 && col <= 2) {
            boxCols[0] = 0;
            boxCols[1] = 2;
        } else if (col >= 3 && col <= 5) {
            boxCols[0] = 3;
            boxCols[1] = 5;
        } else if (col >= 6 && col <= 8) {
            boxCols[0] = 6;
            boxCols[1] = 8;
        }
        int[][] bounds = new int[2][2];
        bounds[0] = boxRows;
        bounds[1] = boxCols;
        return bounds;
    }

    public void solveButtonAction() {
        board.solveButton().addActionListener(this);
    }

    public void newGameAction() {
        board.newGame().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            board.updateBoard(grid);
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    // solves sudoku, if not possible informs
    // that sudoku board is unsolvable.
    public boolean solver() throws InterruptedException {
        if (!isSolvable()) {
            System.out.println("Sudoku unsolvable");
            return false;
        } else {
            boolean res = solverHelper(0, 0);
            if (!res) {
                System.out.println("Sudoku unsolvable");
                return false;
            }
        }
        return true;
    }

    // recursive backtracking function to solve sudoku
    // assigns value to empty cell, if valid continues values until
    // board is solved, otherwise backtracks to find new value.
    // returns true if board is solved
    // returns false if unsolvable
    // Parameters:
    // int row - row number of cell
    // int col - col number of cell
    private boolean solverHelper(int row, int col) throws InterruptedException {
        if (row > 8) {
            return true;
        }
        if (grid[row][col] == 0) {
            for (int i = 1; i <= 9; i++) {
                if (isValid(row, col, i, grid)) {
                    grid[row][col] = i;
                    if (checkNext(row, col)) {
                        return true;
                    } else {
                        grid[row][col] = 0;
                    }
                    // Thread.sleep(10);
                }
            }

        } else {
            return checkNext(row, col);
        }
        return false;
    }

    // determines row and col value for next call
    // goes down col first, once last column is reached
    // row++ and col = 0;
    private boolean checkNext(int row, int col) throws InterruptedException {
        if (col < 8) {
            return solverHelper(row, col + 1);
        } else {
            return solverHelper(row + 1, 0);
        }
    }

    public void printSolvedSudoku(int[][] someGrid) {
        for (int i = 0; i < someGrid.length; i++) {
            System.out.println(Arrays.toString(someGrid[i]));
        }
    }

    public int[][] getSolvedGrid() {
        return this.grid;
    }

    public int[][] getOldGrid() {
        return this.oldGrid;
    }

    // generate new sudoku board to solve

    // create filled board
    public int[][] createNewGrid() {
        int[][] createGrid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = rand.nextInt(9) + 1;
                while (!isValid(i, j, num, createGrid)) {
                    num = rand.nextInt(9) + 1;
                }
                createGrid[i][j] = num;
            }
        }
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(createGrid[i]));
        }
        return createGrid;
    }
}
