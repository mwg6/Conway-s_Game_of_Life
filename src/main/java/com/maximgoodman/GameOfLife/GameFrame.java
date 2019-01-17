package com.maximgoodman.GameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameFrame extends JFrame 
{
    int squareSize;
    int iteration = 0;
    JFrame frame = new JFrame("Game of Life; Iteration -" + iteration);
    Border border = BorderFactory.createLineBorder(Color.black, 5);
    JPanel grid;
    GameBoard game;
    Dimension dimension = new Dimension(600,720);
    Timer timer = new Timer();

    public GameFrame(int squareSize)
    {
        this.squareSize =squareSize;
        
        
        JPanel grid = new JPanel();
        
        this.grid = grid;
        

        formatGrid();
        populateGrid();
        update();
    }

    private void update()
    {
        TimerTask task;

        task = new TimerTask() {
            @Override
            public void run() { 
                game.nextIteration();
                byte[][] board = game.getBoard();
                grid.removeAll();
                grid.repaint();

                for(int i = 0;i<squareSize;i++){
                    grid.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","").replace("0", " ")));
                }

               grid.setPreferredSize(dimension);
                frame.add(grid);
               iteration++;
               frame.setTitle("Game of Life; Iteration -" + iteration);
               frame.pack();          
            }
        };
         timer.schedule(task, 0, 20);
    }
    private void formatGrid()
    {
        grid.setLayout(new GridLayout(squareSize, squareSize));
        grid.setBorder(border);
        grid.setPreferredSize(dimension);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        frame.add(grid);
        frame.pack();
        frame.setVisible(true);
    }

    private void populateGrid()
    {
        GameBoard game = new GameBoard(squareSize);
        this.game = game;
        byte[][] board = game.getBoard();
 
            for (int i = 0; i < squareSize; i++) {

                grid.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","")));

            }

    }
}
