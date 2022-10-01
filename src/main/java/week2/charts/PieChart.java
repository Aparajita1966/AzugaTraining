package week2.charts;

import java.text.DecimalFormat;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class PieChart extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(PieChart.class);


    public PieChart(String title, String labelFormat, PieDataset dataset) {
        super(title);
        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        // Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(labelFormat,
                new DecimalFormat("0"), new DecimalFormat("0%"));

        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        LOGGER.info("PieChart Created");
    }
}
