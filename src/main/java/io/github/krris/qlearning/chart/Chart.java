package io.github.krris.qlearning.chart;

import io.github.krris.qlearning.util.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
* Created by krris on 23.03.14.
*/
public class Chart {
    private static Logger LOG = LoggerFactory.getLogger(Chart.class);

    public static XYSeriesCollection getSeriesCollection(List<Integer> rewards) {
        XYSeries series = new XYSeries("Reward per round");
        int round = 1;
        for (int reward : rewards) {
            series.add(round, reward);
            round++;
        }
        return new XYSeriesCollection(series);
    }

    private static JFreeChart getJFreeChart(XYSeriesCollection data) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "QLearning",
                "round",
                "reward",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

    public static void printToFile(List<Integer> rewards) {
        XYSeriesCollection data = getSeriesCollection(rewards);
        JFreeChart qlearning = getJFreeChart(data);

        int width = 600;
        int height = 400;
        File file = new File(Constants.CHART_PATH);

        try {
            ChartUtilities.saveChartAsPNG(file, qlearning, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}