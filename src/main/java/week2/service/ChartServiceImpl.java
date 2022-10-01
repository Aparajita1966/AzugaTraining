package week2.service;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.charts.BarChart;
import week2.charts.LineChart;
import week2.charts.PieChart;

import javax.swing.*;

public class ChartServiceImpl implements ChartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChartServiceImpl.class);

    @Override
    public void createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset) {
        LOGGER.info("Going to generate bar chart");
        BarChart example = new BarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
        example.setSize(1500, 1000);
        example.setLocationRelativeTo(null);
        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        example.setVisible(true);
    }

    @Override
    public void createPieChart(String title, String labelFormat, PieDataset dataset) {
        LOGGER.info("Going to generate pie chart");
        PieChart example = new PieChart(title, labelFormat, dataset);
        example.setSize(1500, 1200);
        example.setLocationRelativeTo(null);
        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        example.setVisible(true);
    }

    @Override
    public void createLineChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset) {
        LOGGER.info("Going to generate line chart");
        LineChart example = new LineChart(title, categoryAxisLabel, valueAxisLabel, dataset);
        example.setAlwaysOnTop(true);
        example.pack();
        example.setSize(1000, 800);
        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        example.setVisible(true);
    }
}
