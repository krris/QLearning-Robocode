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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        File file = new File(Constants.CHART_PATH);

        try {
            LOG.info("Printing chart...");
            ChartUtilities.saveChartAsPNG(file, qlearning, Constants.CHART_WIDTH, Constants.CHART_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            System.out.println("Reading file with rewards ...");
            List<String> lines = Files.readAllLines(Paths.get(Constants.REWARDS_PATH));
            List<Integer> rewards = new ArrayList<>();
            for (String number : lines) {
                rewards.add(Integer.parseInt(number));
            }
            printToFile(rewards);
            System.out.println("Printing a chart with rewards is finished!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}