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
    int whiteFrac =50;
    int iteration = 0;
    JFrame frame;

    JPanel grid;
    JPanel settingsPanel;
    JButton cancel;
    JTextArea randomInput;
    JTextArea intervalInput;

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

        populateGrid(whiteFrac);

        frame.setLayout(new BorderLayout());
        frame.add(grid, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.PAGE_END);
        frame.setPreferredSize(dimension);
        frame.pack();
        frame.setVisible(true);

        //create the task in the constructor so we can toggle on off
        task = new createTask();

        timer.schedule(task,0,interval);


    }

    class createTask extends TimerTask{
        @Override
        public void run () {

          //  byte[][] board = game.getBoard();

            if(iteration!=0){

                game.nextIteration();
               // board = game.getBoard();
                grid.removeAll();
                grid.repaint();

                updateGrid();

                iterLabel.setText("Iterations: " + iteration);



                //grid.setVisible(true);
                frame.pack();
            }

            iteration++;
        }
    }

    private void updateGrid(){

        byte[][] board = game.getBoard();

        for (int i = 0; i < squareSize; i++) {

            grid.add(new JLabel(Arrays.toString(board[i]).replace("[",
                    "").replace("]", "").replace(",",
                    "").replace("0", " ")));
        }
    }

    private void formatSettings(){

        JLabel intervalLabel = new JLabel("Interval (ms)");
        intervalInput = new JTextArea(1,4);
        JLabel random = new JLabel("White Fraction");
        randomInput = new JTextArea(1,2);
        JLabel percentLabel = new JLabel("%");

        JButton setButt = new JButton("Set!");
        setButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setButtPressed();
            }
        });
        cancel = new JButton("Pause");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelButtonPressed();
            }
        });

        intervalInput.setText(""+interval);
        randomInput.setText(""+whiteFrac);
        settingsPanel.add(Box.createHorizontalGlue());
        settingsPanel.add(intervalLabel);
        settingsPanel.add(intervalInput);
        settingsPanel.add(random);
        settingsPanel.add(randomInput);
        settingsPanel.add(percentLabel);
        settingsPanel.add(setButt);
        settingsPanel.add(iterLabel);
        settingsPanel.add(cancel);

    }

    private void cancelButtonPressed(){
        cancelPressed = !cancelPressed;

        if(!cancelPressed){

            //have to create new task as old is cancelled
            task= new createTask();
            timer.schedule(task,0,interval);
            cancel.setText("Pause");
        }
        else{
            //necessary to cancel task, not timer, to toggle the action
            task.cancel();
            cancel.setText("Resume");
        }
    }

    private void setButtPressed(){

        String interInput = intervalInput.getText();
        String randInput = randomInput.getText();

        //if user has entered a valid input for interval, cancel task, start with new interval
        try{
            if(Integer.parseInt(interInput)>0){
                //task.cancel();
                interval = Integer.parseInt(interInput);
                //timer.schedule(task, interval);
            }
            else{
                intervalInput.setText("ERR!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(interInput);
            intervalInput.setText("NaN!");
        }

        try {
            //changing the white fractions and redrawing board
            if (Integer.parseInt(randInput) >= 0 && Integer.parseInt(randInput) <= 100) {
                whiteFrac = Integer.parseInt(randInput);

            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(randInput);
            System.out.println(whiteFrac);
            randomInput.setText("??");
        }
        finally {
            iteration=0;

            frame.remove(grid);
            grid.removeAll();
            grid.repaint();

            task.cancel();
            populateGrid(whiteFrac);

            grid.setVisible(true);
            frame.add(grid, BorderLayout.CENTER);

            frame.pack();
            task=new createTask();

            timer.schedule(task,interval,interval);


            System.out.println(game.getInitialAlive() + "/" + squareSize*squareSize);


        }
    }

    private void populateGrid(int whiteFrac)
    {
        GameBoard game = new GameBoard(squareSize, whiteFrac);
        this.game = game;
        byte[][] board = game.getBoard();
        game.printBoard(board);

        updateGrid();

    }
}
