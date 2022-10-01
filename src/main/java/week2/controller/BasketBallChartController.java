package week2.controller;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week2.service.ChartService;
import week2.service.ChartServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class BasketBallChartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketBallChartController.class);

    public static void basketBallChart() {
        LOGGER.info("Calling BasketBall Chart");
        DefaultCategoryDataset barChartDataSet = getGamesForBarChart();
        DefaultCategoryDataset lineChartDataset = getGamesForLineChart();
        PieDataset pieChartDataset = getGamesForPieChart();
        createChart(barChartDataSet, lineChartDataset, pieChartDataset);
    }

    public static void createChart(DefaultCategoryDataset barChartDataSet, DefaultCategoryDataset lineChartDataset, PieDataset pieChartDataset) {
        ChartService chartService = new ChartServiceImpl();
        chartService.createPieChart("Number of Matches", "Year {0} : Percentage of Matches({2})", pieChartDataset);
        chartService.createLineChart("Games Line Chart", "Year", "No of Games", lineChartDataset);
        chartService.createBarChart("Games Bar Chart", "Years", "No of Games", barChartDataSet);
    }


    private static DefaultCategoryDataset getGamesForBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Integer> teamList = new ArrayList<>();
        teamList.add(1);
        teamList.add(2);
        teamList.add(3);
        teamList.add(4);
        teamList.add(5);
        List<String> list = new ArrayList<>();
        list.add("1971-1980");
        list.add("1981-1990");
        list.add("1991-2000");
        list.add("2001-2010");
        list.add("2011-2020");
        list.add("2021-2030");
        for (String range : list) {
            StringBuilder urlApp = new StringBuilder();
            String[] arr = range.split("-");
            Integer val = Integer.parseInt(arr[0]);
            while (val <= Integer.parseInt(arr[1])) {
                urlApp.append("&seasons[]=").append(val);
                val++;
            }
            for (Integer integer : teamList) {
                String response = BasketBallApiController.getGamesData(false, urlApp + "&team_ids[]=" + integer, range, false);
                JSONObject json = new JSONObject(response);
                JSONObject jsonArr = json.getJSONObject("meta");
                LOGGER.info(range + " " + jsonArr.get("total_count"));
                dataset.addValue(jsonArr.getInt("total_count"), integer.toString() + " Team", range);
            }
        }
        LOGGER.info("Dataset created : " + dataset);
        return dataset;
    }

    private static DefaultCategoryDataset getGamesForLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Integer> teamList = new ArrayList<>();
        teamList.add(4);
        List<String> list = new ArrayList<>();
        list.add("1991-2000");
        dataForYears(dataset, teamList, list);
        LOGGER.info("Dataset created : " + dataset);
        return dataset;
    }

    private static void dataForYears(DefaultCategoryDataset dataset, List<Integer> teamList, List<String> list) {
        for (String range : list) {
            String[] arr = range.split("-");
            int val = Integer.parseInt(arr[0]);
            while (val <= Integer.parseInt(arr[1])) {
                String response = BasketBallApiController.getGamesData(false,"&seasons[]=" + val + "&team_ids[]=" + teamList.get(0), Integer.toString(val), false);
                JSONObject json = new JSONObject(response);
                JSONObject jsonArr = json.getJSONObject("meta");
                LOGGER.info(val + " " + jsonArr.get("total_count"));
                dataset.addValue(jsonArr.getInt("total_count"), teamList.get(0).toString() + " Team", Integer.toString(val));
                val++;
            }
        }
    }

    private static void dataForMultipleYear(DefaultCategoryDataset dataset, List<Integer> teamList, List<String> list) {
        for (String range : list) {
            StringBuilder urlApp = new StringBuilder();
            String[] arr = range.split("-");
            Integer val = Integer.parseInt(arr[0]);
            while (val <= Integer.parseInt(arr[1])) {
                urlApp.append("&seasons[]=").append(val);
                val++;
            }
            for (Integer integer : teamList) {
                String response = BasketBallApiController.getGamesData(false, urlApp + "&team_ids[]=" + integer, range, false);
                JSONObject json = new JSONObject(response);
                JSONObject jsonArr = json.getJSONObject("meta");
                LOGGER.info(range + " " + jsonArr.get("total_count"));
                dataset.addValue(jsonArr.getInt("total_count"), integer.toString() + " Team", range);
            }
        }
    }

    private static DefaultPieDataset getGamesForPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<String> list = new ArrayList<>();
        list.add("1971-1980");
        list.add("1981-1990");
        list.add("1991-2000");
        list.add("2001-2010");
        list.add("2011-2020");
        list.add("2021-2030");

        for (String range : list) {
            StringBuilder urlApp = new StringBuilder();
            String[] arr = range.split("-");
            Integer year = Integer.valueOf(arr[0]);
            while (year <= Integer.parseInt(arr[1])) {
                urlApp.append("&seasons[]=").append(year);
                year++;
            }
            String response = BasketBallApiController.getGamesData(false, urlApp.toString(), range, false);
            if (null != response) {
                JSONObject json = new JSONObject(response);
                JSONObject metaJson = json.getJSONObject("meta");
                LOGGER.info(range + " " + metaJson.get("total_count"));
                dataset.setValue(range, metaJson.getInt("total_count"));
            }
        }
        LOGGER.info("Dataset created pie chart: " + dataset);
        return dataset;
    }
}
