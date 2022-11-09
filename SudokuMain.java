
public class SudokuMain {
    public static void main(String[] args) throws InterruptedException {
        int[][] grid = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
                { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
                { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
                { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
                { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
                { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
        // creates visual
        SudokuBoard newBoard = new SudokuBoard(grid);
        newBoard.showboard();

        // adds back end solving function
        SudokuSolver1 solver = new SudokuSolver1(grid);
        // VisualSolver solver = new VisualSolver(grid);
        solver.addBoard(newBoard);
        long start = System.currentTimeMillis();
        solver.solver();
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        solver.addFieldAction();
        solver.solveButtonAction();
        System.out.println(elapsedTime);
    }
}
