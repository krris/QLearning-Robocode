package chart;

import io.github.krris.qlearning.chart.Chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by krris on 23.03.14.
 */
public class ChartTest {
    public static void main(String[] args) {
        List<Integer> rewards = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 40; i++) {
            rewards.add(random.nextInt(300));
        }

        Chart.print(rewards);
    }
}
