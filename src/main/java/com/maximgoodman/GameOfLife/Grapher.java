package com.maximgoodman.GameOfLife;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.List;

public class Grapher extends ApplicationFrame {

    public Grapher(String frameTitle, String chartTitle, List data){
        super(frameTitle);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Generation",
                "Living cells",
                createDataSet(data),
                PlotOrientation.VERTICAL,
                true,true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(500,500));
        final XYPlot plot = lineChart.getXYPlot();

        setContentPane(chartPanel);
    }

    private XYSeriesCollection createDataSet(List data){

        final XYSeries living = new XYSeries("living");

        for(int i =0; i<data.size(); i++){
            living.add(i, (int)data.get(i));
        }

        final XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(living);

        return dataSet;
    }

}
