package com.maximgoodman.GameOfLife;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Grapher{

    int maxX;
    int maxY;
    int xIndexes;
    int xIncrement;
    List livingList;




    public Grapher(List livingList){

        this.livingList =livingList;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        JPanel graph = new JPanel();
        Graphics2D graphics= (Graphics2D)g;
        graph.paintComponent(graphics);

        maxX = frame.getWidth();
        maxY = frame.getHeight();
        xIndexes = livingList.size()-1;
        xIncrement = maxX/xIndexes;

        //Graphics2D plotter = (Graphics2D)g;


        //graph.repaint();
        frame.add();
        frame.setPreferredSize(new Dimension (400,600));
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i <xIndexes;i++ ){
            g.setColor(Color.red);
            g.fillRect(i*xIncrement, (int)livingList.get(i)/100,5,5);
            // graph.paintImmediately(i*xIncrement,(int)livingList.get(i)/100,20,20);
        }
    }

}
