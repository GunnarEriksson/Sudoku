package sudoku.generator;

import java.util.Random;

/**
 * Generates a 4 x 4 grid, divided in four 2 x 2 boxes, with six ones 
 * and the rest of the grid zeros. Every box has at least one element
 * set to one and the two other ones are set in randomly chosen boxes.
 * 
 * 
 * @author Gunnar Eriksson
 * @version 2016-07-24
 */
public class SudokuMaskGenerator
{
    private static final int NUM_OF_ROWS = 4;
    private static final int NUM_OF_COLUMNS = 4;
    private static final int NUM_OF_START_DIGITS = 6;
    private static final int NUM_OF_UNIQUE_NUMBERS = 4;
    private static final int BOX_SIDE_LENGTH = 2;
    private static final int MAX_NUM_DIGITS_IN_ROW = 2;
    
    private int[][] grid;
    
    /**
     * Constructor
     * 
     * Instantiates the grid.
     */
    public SudokuMaskGenerator()
    {
        grid = new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
    }
    
    /**
     * Resets the grid before creating a new grid.
     * 
     * @return the grid.
     */
    public int[][] generateGrid()
    {
        resetGrid();
        generateMask();
        
        return grid;
    }
    
    /**
     * Helper method to reset the grid by setting all
     * elements in the grid to zero.
     */
    private void resetGrid()
    {
        for (int row = 0; row < NUM_OF_ROWS; row++)
        {
            for (int col = 0; col < NUM_OF_COLUMNS; col++)
            {
                grid[row][col] = 0;
            }
        }
    }
    
    /**
     * Helper method to generate the grid. Sets a element to one in each
     * box. Other elements are set in random boxes.
     */
    private void generateMask()
    {
        setOneElementRandomlyInEachBox();
        setNumOfElementsInRandomBoxes(NUM_OF_START_DIGITS - NUM_OF_UNIQUE_NUMBERS);
    }
    
    /**
     * Helper method to set a element to one in each {@value #BOX_SIDE_LENGTH} x 
     * {@value #BOX_SIDE_LENGTH} box in the grid.
     */
    private void setOneElementRandomlyInEachBox()
    {
        for (int row = 0; row < NUM_OF_ROWS; row += BOX_SIDE_LENGTH)
        {
            for (int col = 0; col < NUM_OF_COLUMNS; col += BOX_SIDE_LENGTH)
            {
                setOneElementRandomlyInOneBox(row, col);
            }
        }
    }
    
    /**
     * Recursive helper method to set a element to one in a random position
     * in the {@value #BOX_SIDE_LENGTH} x {@value #BOX_SIDE_LENGTH} box if 
     * the position is available (set to zero) and it is only one element 
     * set to one in the same row.
     * If the position is already set or there are already two elements set
     * to one at the same row, the method calls itself to get a new random
     * position in the {@value #BOX_SIDE_LENGTH} x {@value #BOX_SIDE_LENGTH} 
     * box until a free position is found.
     * 
     * @param row the row position in the grid.
     * @param col the column position in the grid.
     */
    private void setOneElementRandomlyInOneBox(int row, int col)
    {
        int startRow = row / BOX_SIDE_LENGTH * BOX_SIDE_LENGTH;
        int startCol = col / BOX_SIDE_LENGTH * BOX_SIDE_LENGTH;
        
        Random random = new Random();
        
        int randomRow = startRow + random.nextInt(BOX_SIDE_LENGTH);
        int randomCol = startCol + random.nextInt(BOX_SIDE_LENGTH);
        
        if (grid[randomRow][randomCol] == 0 && isOnlyOneElementSetInRow(randomRow))
        {
            grid[randomRow][randomCol] = 1;
        }
        else
        {
            setOneElementRandomlyInOneBox(randomRow, randomCol);
        }
    }
    
    /**
     * Helper method to check if there is only one element set to one
     * in a row.
     * 
     * @param row the row position in the grid.
     * 
     * @return <code>true</code> if the row only has one element set to one.
     *         <code>false</code> Otherwise false.
     */
    private boolean isOnlyOneElementSetInRow(int row)
    {
        boolean isOnlyOneDigitInRow = false;
        int numOfDigits = 0;
        
        for (int col = 0; col < NUM_OF_COLUMNS; col++)
        {
            if (grid[row][col] == 1)
            {
                numOfDigits++;
            }
        }
        
        if (numOfDigits < MAX_NUM_DIGITS_IN_ROW)
        {
            isOnlyOneDigitInRow = true;
        }
        
        return isOnlyOneDigitInRow;
    }
    
    /**
     * Helper method to set elements in random {@value #BOX_SIDE_LENGTH} 
     * x {@value #BOX_SIDE_LENGTH} boxes in the grid.
     * 
     * @param numberOfBits  the number of elements to set to one in random
     *                      {@value #BOX_SIDE_LENGTH} x 
     *                      {@value #BOX_SIDE_LENGTH} boxes in the grid.
     */
    private void setNumOfElementsInRandomBoxes(int numberOfBits)
    {
        Random random = new Random();
        
        for (int i = 0; i < numberOfBits; i++)
        {
            setOneElementRandomlyInOneBox(random.nextInt(NUM_OF_ROWS), random.nextInt(NUM_OF_COLUMNS));
        }
    }
}
