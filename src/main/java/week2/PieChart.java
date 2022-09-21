package week2;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.JSONException;
import org.json.JSONObject;

public class PieChart extends JFrame {



    public PieChart(String title) {
        super(title);
        // Create dataset
        PieDataset dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createPieChart("Number of Matches", dataset, true, true, false);
        // Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("Year {0} : Percentage of Matches({2})",
                new DecimalFormat("0"), new DecimalFormat("0%"));

        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }


    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            getGames(dataset);
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataset;
    }

    private static void getGames(DefaultPieDataset dataset) throws IOException, JSONException {
        List<String> list = new ArrayList<String>();
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
            while (year <= Integer.valueOf(arr[1])) {
                urlApp.append("&seasons[]=").append(year);
                year++;
            }
            String response = BasketBallApi.getGamesData(urlApp.toString(), range, false);
            if (null != response) {
                JSONObject json = new JSONObject(response);
                JSONObject metaJson = json.getJSONObject("meta");
                System.out.println(range + " " + metaJson.get("total_count"));
                dataset.setValue(range,  metaJson.getInt("total_count"));
            }
        }
    }

    public static void main(String[] args) throws JSONException {
        SwingUtilities.invokeLater(() -> {
            PieChart example = new PieChart("Number of Matches");
            example.setSize(1500, 1200);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });

    }
}
