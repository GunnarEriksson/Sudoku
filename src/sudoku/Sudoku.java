package sudoku;

import sudoku.generator.SudokuGenerator;
import sudoku.generator.SudokuMaskGenerator;
import sudoku.generator.SudokuSolutionGenerator;
import sudoku.ui.SudokuGUI;

/**
 * Program entry point. Generates a 4 x 4 sudoku with six numbers and
 * 10 empty cells. The solution could be shown via the menu bar.
 * 
 * This version does not contains functions to play the game.
 * 
 * @author Gunnar Eriksson
 * @version 2016-07-24
 *
 */
public class Sudoku
{

    public static void main(String[] args)
    {
        SudokuSolutionGenerator solutionGenerator = new SudokuSolutionGenerator();
        SudokuMaskGenerator maskGenerator = new SudokuMaskGenerator();
        SudokuGenerator sudokuGenerator = new SudokuGenerator(solutionGenerator, maskGenerator);
        
        new SudokuGUI(sudokuGenerator).setVisible(true);
    }

}
