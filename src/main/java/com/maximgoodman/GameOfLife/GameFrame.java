package com.maximgoodman.GameOfLife;

import java.awt.*;
import java.io.Console;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;

public class GameFrame extends JFrame 
{
    int squareSize;
    int iteration = 0;
    Container frame = getContentPane();
    //JFrame frame = new JFrame("Game of Life; Iteration -" + iteration);

    JPanel grid;
    JPanel settingsPanel;
    GameBoard game;
    Dimension dimension = new Dimension(600,720);
    Timer timer = new Timer();

    public GameFrame(int squareSize)
    {
        this.squareSize =squareSize;
        
        
        JPanel grid = new JPanel();

        this.grid = grid;

        settingsPanel =new JPanel();

        

        formatGrid();
        formatSettings();
        populateGrid();
        update();

        //frame.pack();
        frame.add(grid, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        System.out.println("Here");
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
               frame.add(settingsPanel);
               iteration++;

               //frame.setTitle("Game of Life; Iteration -" + iteration);
               formatGrid();
               formatSettings();
               //frame.pack();
            }
        };
         timer.schedule(task, 0, 20);
    }
    private void formatGrid()
    {
        grid.setLayout(new BoxLayout(grid, BoxLayout.PAGE_AXIS));
        grid.add(Box.createRigidArea(new Dimension(0,5)));

        grid.setPreferredSize(dimension);
        


        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);




    }

    private void formatSettings(){
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.LINE_AXIS));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

        JButton cancel = new JButton("Cancel");
        JTextArea iterations = new JTextArea(1,20);

        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(cancel);
        settingsPanel.add(iterations);


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
