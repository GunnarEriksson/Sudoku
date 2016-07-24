package sudoku.generator;

/**
 * Generates a sudoku 4 x 4 grid with six numbers from
 * one to four to start the sudoku game and a solution
 * for the game.
 * 
 * @author Gunnar Eriksson
 * @version 2016-07-24
 */
public class SudokuGenerator
{
    private static final int NUM_OF_ROWS = 4;
    private static final int NUM_OF_COLUMNS = 4;
    
    private SudokuSolutionGenerator solutionGenerator;
    private SudokuMaskGenerator maskGenerator;
    private int[][] solutionGrid;
    
    /**
     * Constructor
     * Creates the logic to generate a grid to start the game from
     * the solution grid and the grid mask.
     * 
     * @param solutionGenerator the generator that generates the grid with the solution.
     * @param maskGenerator the generator that generates the mask to hide numbers from the solution.
     */
    public SudokuGenerator(SudokuSolutionGenerator solutionGenerator, SudokuMaskGenerator maskGenerator)
    {
        this.solutionGenerator = solutionGenerator;
        this.maskGenerator = maskGenerator;
    }
    
    /**
     * Generates a grid to start the game.
     * 
     * @return the starting game grid.
     */
    public int[][] generateGrid()
    {
        solutionGrid = solutionGenerator.generateGrid();
        int[][] maskGrid = maskGenerator.generateGrid();
        
        return generateStartGameGridFromMask(maskGrid);
    }
    
    /**
     * Helper method to generate the start game grid by masking the solution grid.
     * 
     * @param maskGrid the grid to mask the solution grid with.
     * 
     * @return the grid to start the game with.
     */
    private int[][] generateStartGameGridFromMask(int[][] maskGrid)
    {
        int[][] startGameGrid = new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
        
        for (int row = 0; row < NUM_OF_ROWS; row++)
        {
            for (int col = 0; col < NUM_OF_COLUMNS; col++)
            {
                startGameGrid[row][col] = solutionGrid[row][col] * maskGrid[row][col];
            }
        }
        
        return startGameGrid;
    }
    
    /**
     * Returns the grid which contains the solution of the sudoku. If no
     * grid is generated, an empty grid is returned.
     * 
     * @return  the grid with the solution of the sudoku. If game is not started, an empty
     *          grid is returned.
     */
    public int[][] getSolutionGrid()
    {
        if (solutionGrid == null)
        {
            return new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
        }
        else
        {
            return solutionGrid;
        }
    }
}
