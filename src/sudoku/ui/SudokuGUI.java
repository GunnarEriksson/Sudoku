package sudoku.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sudoku.generator.SudokuGenerator;

/**
 * The sudoku GUI for the sudoku generator.
 * 
 * @author Gunnar Eriksson
 * @version 2016-07-24
 */
public class SudokuGUI extends JFrame
{
    private static final long serialVersionUID = -6998622348653738821L;
    private static final String SUDOKU_GUI_TITLE = "Sudoku";
    private static final String GAME_MENU_TITLE = "Game Menu";
    private static final String MENU_ITEM_NEW_GAME = "New Game";
    private static final String MENU_ITEM_SOLUTION = "Get Solution";
    private static final int NUM_OF_BOXES = 4;
    private static final int NUM_OF_ROWS = 4;
    private static final int NUM_OF_COLUMNS = 4;
    private static final int NUM_OF_BOXES_IN_ROW = 2;
    private static final int BOX_DISTANCE = 4;
    private static final int NUM_OF_BOX_ROWS = 2;
    private static final int NUM_OF_BOX_COLUMNS = 2;
    
    private SudokuGenerator sudokuGenerator;
    
    JTextField[][] cells;
    
    private JMenuItem newGameMenuItem, gameSolutionMenuItem;
    
    /**
     * Constructor
     * Sets the object reference to the sudoku generator,
     * the title, initializes the GUI and add the listeners
     * to the menu items.
     * 
     * @param sudokuGenerator
     */
    public SudokuGUI(SudokuGenerator sudokuGenerator)
    {
        this.sudokuGenerator = sudokuGenerator;
        setTitle(SUDOKU_GUI_TITLE);
        initializeGUI();
        addMenuItemListeners();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Helper method to initialize the GUI. Creates the
     * menu bar and the sudoku board with the boxes.
     */
    private void initializeGUI()
    {
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        JPanel boardPanel = createSudokuBoard();
        JPanel[] boxesInBoard = setupBoxes(boardPanel);
        setupCells(boxesInBoard);
        
        add(boardPanel);
        this.pack();
        
        setSize(400, 400);
        setResizable(false);
    }


    /**
     * Helper method to create the menu bar with the menu bar items.
     * 
     * @return the menu bar with the menu items.
     */
    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu(GAME_MENU_TITLE);
        createGameMenuItems(gameMenu);
        menuBar.add(gameMenu);
        
        return menuBar;
    }

    /**
     * Helper method to create the menu items in the menu bar.
     * 
     * @param gameMenu the menu in the menu bar.
     */
    private void createGameMenuItems(JMenu gameMenu)
    {
        newGameMenuItem = new JMenuItem(MENU_ITEM_NEW_GAME);
        gameMenu.add(newGameMenuItem);
        
        gameSolutionMenuItem = new JMenuItem(MENU_ITEM_SOLUTION);
        gameMenu.add(gameSolutionMenuItem);
    }
    
    /**
     * Helper method to create the sudoku board. The panel consist of
     * {@value #NUM_OF_BOXES} boxes divided in {@value #NUM_OF_BOXES_IN_ROW}
     * in one row.
     * 
     * @return the board panel.
     */
    private JPanel createSudokuBoard()
    {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(NUM_OF_BOXES_IN_ROW, NUM_OF_BOXES_IN_ROW, BOX_DISTANCE, BOX_DISTANCE));
        
        return boardPanel;
    }
    
    /**
     * Helper method to set up the {@value #NUM_OF_BOXES} boxes.
     * Each box contains of {@value #NUM_OF_BOX_ROWS} rows and
     * {@value #NUM_OF_BOX_COLUMNS} columns.
     * 
     * @param boardPanel the panel, which is a wrapper for the boxes.
     * 
     * @return the array of boxes.
     */
    private JPanel[] setupBoxes(JPanel boardPanel)
    {
        JPanel[] boxes = new JPanel[NUM_OF_BOXES];
        
        for (int i = 0; i < NUM_OF_BOXES; i++) {
            boxes[i] = new JPanel();
            boxes[i].setLayout(new GridLayout(NUM_OF_BOX_ROWS, NUM_OF_BOX_COLUMNS));
            boardPanel.add(boxes[i]);
        }
        
        return boxes;
    }
    
    /**
     * Helper method to set up the cells which are divided in
     * {@value #NUM_OF_BOXES} boxes in the board.
     * 
     * @param boxesInBoard the array with boxes.
     */
    private void setupCells(JPanel[] boxesInBoard)
    {
        cells = new JTextField[NUM_OF_ROWS][NUM_OF_COLUMNS];
        
        
        int boxRowStartPos;
        for (int row = 0; row < NUM_OF_ROWS; row++) 
        {
            if (row <= 1)
            {
                boxRowStartPos = 0;
            }
            else 
            {
                boxRowStartPos = 2;
            }
            
            for (int col = 0; col < NUM_OF_COLUMNS; col++) 
            {
                cells[row][col] = new JTextField();
                setTextFieldsSettings(cells[row][col]);
                
                boxesInBoard[boxRowStartPos + (col / 2)].add(cells[row][col]);
            }
        }
    }
    
    /**
     * Helper method to setup the cells text alignment and font size.
     * 
     * @param cell the cell in the board.
     */
    private void setTextFieldsSettings(JTextField cell)
    {
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Arial", Font.BOLD, 22));
    }
    
    /**
     * Helper method to add action listeners to the menu items in the
     * menu bar.
     */
    private void addMenuItemListeners()
    {
        // The new game menu item to generate a sudoku game board
        // with severity easy.
        newGameMenuItem.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[][] startGameGrid = sudokuGenerator.generateGrid();
                fillCellsWithGridValues(startGameGrid);
                
            }
        });
        
        gameSolutionMenuItem.addActionListener(new ActionListener()
        {
            // Game solution menu item to show the solution of the
            // game.
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[][] solutionGrid = sudokuGenerator.getSolutionGrid();
                
                fillCellsWithGridValues(solutionGrid);
            }
        });
    }
    
    /**
     * Helper method to fill the sudoku board with numbers from a grid.
     * 
     * @param grid the grid with numbers, which size matches
     * the sudoku board.
     */
    private void fillCellsWithGridValues(int[][] grid)
    {
        for (int row = 0; row < NUM_OF_ROWS; row++)
        {
            for (int col = 0; col < NUM_OF_COLUMNS; col++)
            {
                setValueInCellFromGrid(row, col, grid);
            }
        }
    }
    
    /**
     * Helper method to set the value in the board cell from a grid.
     * If the value is zero, the cell on the board will be blank.
     * 
     * @param row the row number.
     * @param col the column number.
     * @param grid the grid with values which should be set in the
     *             corresponding cell on the board.
     */
    private void setValueInCellFromGrid(int row, int col, int[][] grid)
    {
        String value = "";
        if (grid[row][col] != 0)
        {
            value = Integer.toString(grid[row][col]);
        }
        
        cells[row][col].setText(value);
    }

}
