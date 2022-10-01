package week2.charts;


import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class LineChart extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(LineChart.class);

    public LineChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset) {
        super(title);
        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(title, // Chart title
                categoryAxisLabel, // X-Axis Label
                valueAxisLabel, // Y-Axis Label
                dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        LOGGER.info("LineChart Created");
    }
}
