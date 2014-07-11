package io.github.krris.qlearning.chart;

import io.github.krris.qlearning.util.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
* Created by krris on 23.03.14.
*/
public class TicksChart {
    public static XYSeriesCollection getSeriesCollection(List<Double> rewards) {
        Double[] arr = rewards.toArray(new Double[rewards.size()]);

        XYSeries series = new XYSeries("Num of ticks per round");

        for (int i = 1; i < (arr.length - 1); i +=3) {
            double x = arr[i-1] + arr[i] + arr[i+1];
            x = x / 3;
            series.add(i, x);
        }
        return new XYSeriesCollection(series);
    }

    private static JFreeChart getJFreeChart(XYSeriesCollection data) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Ticks",
                "round",
                "Num",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        return chart;
    }

    public static void printToFile(List<Double> rewards) {
        XYSeriesCollection data = getSeriesCollection(rewards);
        JFreeChart qlearning = getJFreeChart(data);

        File file = new File(Constants.TICKS_CHART_PATH);

        try {
            System.out.println("Printing a chart with ticks...");
            ChartUtilities.saveChartAsPNG(file, qlearning, Constants.CHART_WIDTH, Constants.CHART_HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Reading file with rewards ...");
            List<String> lines = Files.readAllLines(Paths.get(Constants.TICKS_PATH));
            List<Double> rewards = new ArrayList<>();
            for (String number : lines) {
                rewards.add(Double.parseDouble(number));
            }
            printToFile(rewards);
            System.out.println("Printing a chart with ticks is finished!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}