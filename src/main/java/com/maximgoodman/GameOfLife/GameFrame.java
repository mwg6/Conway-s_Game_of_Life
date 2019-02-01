package com.maximgoodman.GameOfLife;

import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
    JPanel infoPanel;
    JLabel livingLabel;
    JButton cancel;
    JTextArea randomInput;
    JTextArea intervalInput;

    List<Integer> livingList = new ArrayList<>();

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

        JPanel infoPanel = new JPanel();

        this.infoPanel=infoPanel;

        JFrame frame = new JFrame();
        this.frame=frame;

        formatSettings();
        formatInfo();

        populateGrid(whiteFrac);

        frame.setLayout(new BorderLayout());

        frame.add(infoPanel, BorderLayout.PAGE_START);
        frame.add(grid, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.PAGE_END);

        frame.setPreferredSize(dimension);
        frame.pack();
        frame.setVisible(true);

        //create the task in the constructor so we can toggle on off
        task = new createTask();

        timer.schedule(task,0,interval);

        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


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
                livingLabel.setText(game.getAlive()+"/"+squareSize*squareSize);
                livingList.add(game.getAlive());

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

        settingsPanel.add(cancel);

    }

    private void formatInfo(){
        JLabel livingText = new JLabel("Living Cells");
        livingLabel = new JLabel();

        infoPanel.add(livingText);
        infoPanel.add(livingLabel);
        infoPanel.add(iterLabel);
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

            graph(livingList);

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
            livingList.clear();

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


            System.out.println(game.getAlive() + "/" + squareSize*squareSize);


        }
    }

    private void populateGrid(int whiteFrac)
    {
        GameBoard game = new GameBoard(squareSize, whiteFrac);
        this.game = game;
        byte[][] board = game.getBoard();
        game.printBoard(board);
        livingLabel.setText(game.getAlive()+"/"+squareSize*squareSize);
        livingList.add(game.getAlive());
        updateGrid();

    }

    private void graph(List livingList){
        Grapher graphFrame = new Grapher("Living Cells Chart", "Simulation Survival", livingList);
        graphFrame.pack();
        RefineryUtilities.centerFrameOnScreen(graphFrame);
        graphFrame.setVisible(true);
        graphFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
