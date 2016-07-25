package sudoku.generator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates a 4 x 4 grid with a sudoku solution.
 * 
 * @author Gunnar Eriksson
 * @version 2016-07-24
 */
public class SudokuSolutionGenerator
{
    private static final int NUM_OF_ROWS = 4;
    private static final int NUM_OF_COLUMNS = 4;
    private static final int NUM_OF_UNIQUE_NUMBERS = 4;
    private static final int BOX_SIDE_LENGTH = 2;

    private int[][] grid;

    /**
     * Constructor
     * 
     * Instantiates an empty {@value NUM_OF_ROWS} x {@value NUM_OF_COLUMNS} grid.
     */
    public SudokuSolutionGenerator()
    {
        grid = new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
    }

    /**
     * Resets the grid before generates a {@value NUM_OF_ROWS} x {@value NUM_OF_COLUMNS} grid with a sudoku solution.
     * 
     * @return the grid with a sudoku solution.
     */
    public int[][] generateGrid()
    {
        resetGrid();
        generateSolution(0, 0);

        return grid;
    }

    /**
     * Helper method to reset the grid by setting all elements in the grid to zero.
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
     * Recursive helper method to generate a sudoku solution using the sudoku
     * backtracking algorithm. According to the sudoku rules, a number can only
     * exist once in a box, in a row and in a column.
     * 
     * <p>
     * Each position is tested with a unique random number. The number is set only
     * if the sudoku rules are fulfilled. If not, a new number is tested for the
     * position. If none of the number fulfills the rules for the position, the
     * position is moved to previous position and the old number is erased so a
     * new number can be tested (backtracking). The algorithm continues until all
     * numbers are set
     * </p>
     * 
     * @param row the row position in the grid.
     * @param col the column position in the grid.
     * 
     * @return <code>true</code> if one or all numbers are set.
     *         <code>false</code> Otherwise false.
     */
    private boolean generateSolution(int row, int col)
    {
        if (row == NUM_OF_ROWS)
        {
            return true;
        }

        Integer[] uniqueRandomNumbers = generateUniqueRandomNumbers();
        for (int i = 0; i < NUM_OF_UNIQUE_NUMBERS; i++)
        {
            if (isNumUniqueInRowColAndBox(row, col, uniqueRandomNumbers[i]))
            {
                if (setNumberInCellGrid(row, col, uniqueRandomNumbers[i]))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Helper method to create numbers from one to {@value #NUM_OF_UNIQUE_NUMBERS}.
     * The numbers is shuffled to be in a random order.
     * 
     * @return the array with unique numbers in random order.
     */
    private Integer[] generateUniqueRandomNumbers()
    {
        ArrayList<Integer> randomNumList = new ArrayList<Integer>();
        for (int i = 0; i < NUM_OF_UNIQUE_NUMBERS; i++)
        {
            randomNumList.add(i + 1);
        }

        Collections.shuffle(randomNumList);

        return randomNumList.toArray(new Integer[NUM_OF_UNIQUE_NUMBERS]);
    }

    /**
     * Helper method to check if a number in a cell follows the rules of sudoku.
     * According to the rules of sudoku, a number can only exist once in a
     * box, once in a row and once in a column at the same time.
     * 
     * @param row the row position in the grid.
     * @param col the column position in the grid.
     * @param number the number to be tested for a specific position in the grid.
     * 
     * @return <code>true</code> If the number is valid to be set for a specific
     *         position in the grid. <code>false</code> Otherwise false.
     */
    private boolean isNumUniqueInRowColAndBox(int row, int col, int number)
    {
        return isNumUniqueInRow(row, col, number)
                && isNumUniqueInColumn(row, col, number)
                && isNumUniqueInBox(row, col, number);
    }

    /**
     * Helper method to check if a number already exist or not in a row. The number
     * is checked against the values in all the columns in a row, except the column
     * for the number that should be tested.
     * 
     * @param row the row position in the grid.
     * @param numberColPos the column position of the number to be tested.
     * @param number the number to be tested for a specific position in the row.
     * 
     * @return <code>true</code> If the number does not already exist in the row.
     *         <code>false</code> Otherwise false.
     */
    private boolean isNumUniqueInRow(int row, int numberColPos, int number)
    {
        for (int col = 0; col < NUM_OF_COLUMNS; col++)
        {
            if (col != numberColPos)
            {
                if (grid[row][col] == number)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Helper method to check if a number already exist or not in a column. The number
     * is checked against the values in all the rows in a column, except the row for
     * the number that should be tested.
     * 
     * @param numberRowPos the row position of the number to be tested.
     * @param col the column position in the grid.
     * @param number the number to be tested for a specific position in the column.
     * 
     * @return <code>true</code> If the number does not already exist in the column.
     *         <code>false</code> Otherwise false.
     */
    private boolean isNumUniqueInColumn(int numberRowPos, int col, int number)
    {
        for (int row = 0; row < NUM_OF_ROWS; row++)
        {
            if (row != numberRowPos)
            {
                if (grid[row][col] == number)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Helper method to check if a number exist or not in a box. The number is checked
     * against the values of all the positions in the box, except for numbers own position.
     * 
     * @param numberRowPos the row position of the number to be tested.
     * @param numberColPos the column position of the number to be tested.
     * @param number the number to be tested for a specific position in the box.
     * 
     * @return <code>true</code> if the number does not already exists in box.
     *         <code>false</code> Otherwise false.
     */
    private boolean isNumUniqueInBox(int numberRowPos, int numberColPos, int number)
    {
        int startRow = numberRowPos / BOX_SIDE_LENGTH * BOX_SIDE_LENGTH;
        int startCol = numberColPos / BOX_SIDE_LENGTH * BOX_SIDE_LENGTH;

        for (int row = startRow; row < startRow + BOX_SIDE_LENGTH; row++)
        {
            for (int col = startCol; col < startCol + BOX_SIDE_LENGTH; col++)
            {
                if (!(row == numberRowPos && col == numberColPos))
                {
                    if (grid[row][col] == number)
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Helper method to set a number in a cell in the grid by moving the position from
     * upper left to down right. If number could not be set, it moves back to previous
     * cell in the grid and sets the cell to zero (backtracking). When the cell is set
     * to zero, the cell can be tested once again and to be set with another number.
     * 
     * @param row the row position in the grid.
     * @param col the column position in the grid.
     * @param number the number to be set as an element in the grid.
     * 
     * @return <code>true</code>If number is set.
     *         <code>false</code>Otherwise false.
     */
    private boolean setNumberInCellGrid(int row, int col, int number)
    {
        boolean isNumberSet = false;

        grid[row][col] = number;

        if (col == NUM_OF_COLUMNS - 1)
        {
            row += 1;
        }

        if (generateSolution(row, (col + 1) % NUM_OF_COLUMNS))
        {
            isNumberSet = true;
        }
        else
        {
            grid[row][col] = 0;
        }

        return isNumberSet;
    }
}
