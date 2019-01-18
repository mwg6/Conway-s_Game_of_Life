package com.maximgoodman.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroFrame {

    JFrame frame;
    JTextArea area;
    JLabel areaLabel;
    JButton submitButton;


    public IntroFrame(){
        frame = new JFrame("Intro");

        frame.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));

        area = new JTextArea(1, 20);
        areaLabel = new JLabel("Desired Square Dimensions");
        submitButton = new JButton("submit");
        frame.add(areaLabel);
        frame.add(area);
        frame.add(submitButton);
        frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submitButtonPressed();
            }
        });

        frame.pack();

        frame.setVisible(true);
    }

    private void submitButtonPressed(){
        int sizeOfBoard =-1;


        try
        {

            int hold = Integer.parseInt(area.getText());

            if(hold>0)
            {
                sizeOfBoard=hold;
            }
        }
        catch(Exception e)
        {
            area.setText("Not an int>0!");
        }
        if(sizeOfBoard>0)
        {
            System.out.println(sizeOfBoard);
            GameFrame game = new GameFrame(sizeOfBoard);
        }


    }
}
