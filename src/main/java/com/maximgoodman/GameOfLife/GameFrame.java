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
    JFrame frame;

    //JFrame frame = new JFrame("Game of Life; Iteration -" + iteration);

    //JInternalFrame grid;
    JPanel settingsPanel;
    GameBoard game;
    Dimension dimension = new Dimension(600,720);
    Timer timer = new Timer();

    public GameFrame(int squareSize)
    {
        this.squareSize =squareSize;
        
        /*
        JInternalFrame grid = new JInternalFrame();

        this.grid = grid;
*/
        JPanel settingsPanel = new JPanel();

        this.settingsPanel=settingsPanel;

        JFrame frame = new JFrame();
        this.frame=frame;

       // formatGrid();
        formatSettings();

        populateGrid();


        frame.setLayout(new BorderLayout());
       // frame.add(grid, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.PAGE_END);
        frame.setPreferredSize(dimension);
        frame.pack();
        frame.setVisible(true);

       // update();


    }

    private void update()
    {
        TimerTask task;

        task = new TimerTask() {
            @Override
            public void run() { 
                game.nextIteration();
                byte[][] board = game.getBoard();
                frame.removeAll();
                frame.repaint();
                /*
                grid.removeAll();
                grid.repaint();
*/
                for(int i = 0;i<squareSize;i++){
                    frame.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","").replace("0", " ")));
                    //grid.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","").replace("0", " ")));
                }



               iteration++;

               //frame.setTitle("Game of Life; Iteration -" + iteration);
              // formatGrid();
               //grid.pack();
               //grid.setVisible(true);
              // frame.setLayout(new BorderLayout());
               //frame.add(grid, BorderLayout.CENTER);

               frame.add(settingsPanel, BorderLayout.PAGE_END);
               frame.pack();
            }
        };
         timer.schedule(task, 0, 200);
    }
    private void formatGrid()
    {
        //grid.setLayout(new BoxLayout(grid, BoxLayout.PAGE_AXIS));
        //grid.add(Box.createRigidArea(new Dimension(0,5)));


    }

    private void formatSettings(){
        //settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.LINE_AXIS));
       // settingsPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

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

                frame.add(new JLabel(""+i));
               // frame.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","")));
                //grid.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",","")));

            }
        frame.add(new JLabel("X"));
    }
}
