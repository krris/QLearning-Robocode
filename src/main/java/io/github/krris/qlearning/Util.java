package io.github.krris.qlearning;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by krris on 22.03.14.
 */
public class Util {
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
}
