package io.github.krris.qlearning.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.List;

/**
* Created by krris on 23.03.14.
*/
public class Chart extends ApplicationFrame {

    public Chart(final String title) {
        super(title);
    }

    public void setRewards(List<Integer> rewards) {
        final XYSeries series = new XYSeries("Reward per round");
        int round = 1;
        for (int reward : rewards) {
            series.add(round, reward);
            round++;
        }

        final XYSeriesCollection data = new XYSeriesCollection(series);
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

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public static void print(List<Integer> rewards) {
        Chart qlearning = new Chart("QLearning - reward per round");
        qlearning.setRewards(rewards);
        qlearning.pack();
        RefineryUtilities.centerFrameOnScreen(qlearning);
        qlearning.setVisible(true);

    }

}