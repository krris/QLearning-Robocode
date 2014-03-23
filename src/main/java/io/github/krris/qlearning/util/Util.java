package io.github.krris.qlearning.util;

import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.State;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by krris on 22.03.14.
 */
public class Util {
    public static Set<State> allAvailableStates() {
        return generateStates(Constants.ALL_RANGES);
    }

    public static Set<State> generateStates(Range[][] ranges) {
        int depth = 0;
        Set<State> generatedStates = new HashSet<>();
        return generate(ranges, depth, new State.Builder(), generatedStates);
    }

    private static Set<State> generate(Range[][] ranges, int depth, State.Builder stateBuilder,
                                       Set<State> generatedStates) {
        if (ranges.length < depth + 1) {
            generatedStates.add(stateBuilder.build());
        } else {
            for (Range range : ranges[depth]) {
                stateBuilder.setProperty(range);
                depth++;
                generate(ranges, depth, stateBuilder, generatedStates);
                depth--;
            }
        }
        return generatedStates;
    }

    static public double distanceBetween2Points(double x1, double y1, double x2, double y2)
    {
        double distance = Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
        return distance;
    }
}
