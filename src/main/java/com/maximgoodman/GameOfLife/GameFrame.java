package com.maximgoodman.GameOfLife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameFrame extends JFrame 
{
    int squareSize;
    int iteration = 0;
    JFrame frame;

    JPanel grid;
    JPanel settingsPanel;
    GameBoard game;
    Dimension dimension = new Dimension(600,720);
    Timer timer = new Timer();
    JLabel iterLabel = new JLabel("Iterations: "+iteration);
    boolean cancelPressed = false;

    TimerTask task;
    int interval =200;

    public GameFrame(int squareSize)
    {
        this.squareSize =squareSize;

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(squareSize,squareSize));
        this.grid = grid;

        JPanel settingsPanel = new JPanel();

        this.settingsPanel=settingsPanel;

        JFrame frame = new JFrame();
        this.frame=frame;

        formatSettings();

        populateGrid();

        frame.setLayout(new BorderLayout());
        frame.add(grid, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.PAGE_END);
        frame.setPreferredSize(dimension);
        frame.pack();
        frame.setVisible(true);

        //create the task in the constructor so we can toggle on off
        task = new createTask();

        update();


    }

    class createTask extends TimerTask{
        @Override
        public void run () {
                    /*
                    if(cancelPressed){
                        timer.cancel();
                        return;
                    }
*/
            iteration++;
            game.nextIteration();
            byte[][] board = game.getBoard();

            grid.removeAll();
            grid.repaint();

            for (int i = 0; i < squareSize; i++) {

                grid.add(new JLabel(Arrays.toString(board[i]).replace("[", "").replace("]", "").replace(",", "").replace("0", " ")));
            }


            iterLabel.setText("Iterations: " + iteration);

            grid.setVisible(true);

            frame.add(grid, BorderLayout.CENTER);

            frame.pack();
        }
    }

    private void update()
    {

         timer.schedule(task,0,interval);

    }

    private void formatSettings(){

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelButtonPressed();
            }
        });

        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(iterLabel);
        settingsPanel.add(cancel);

    }

    private void cancelButtonPressed(){
        cancelPressed = !cancelPressed;

        if(!cancelPressed){

            //have to create new task as old is cancelled
            task= new createTask();
            timer.schedule(task,0,interval);
        }
        else{
            //necessary to cancel task, not timer, to toggle the action
            task.cancel();
        }
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
