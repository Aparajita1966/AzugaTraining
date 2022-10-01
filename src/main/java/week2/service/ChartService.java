package week2.service;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

public interface ChartService {

    void createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset);

    void createPieChart(String title, String labelFormat, PieDataset dataset);

    void createLineChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset);

}
