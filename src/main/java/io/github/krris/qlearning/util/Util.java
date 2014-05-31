package io.github.krris.qlearning.util;

import com.google.common.collect.Table;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.feature.Feature;
import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robocode.RobotStatus;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by krris on 22.03.14.
 */
public class Util {
    private static Logger LOG = LoggerFactory.getLogger(Util.class);

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

    static public double distanceBetween2Points(double x1, double y1, double x2, double y2) {
        double distance = Math.sqrt( Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) );
        return distance;
    }

    static public double angleToEnemy(double myX, double myY, double enemyX, double enemyY) {
        double dx;
        double dy;
        if (myX > enemyX ) {
            dx = Math.abs(myX - enemyX);
        } else {
            dx = Math.abs(enemyX - myX);
        }

        if (myY > enemyY ) {
            dy = Math.abs(myY - enemyY);
        } else {
            dy = Math.abs(enemyY - myY);
        }

        return Math.toDegrees(Math.atan2(dy, dx));
    }

    static public double[] getCoordinatesAfterAction(State state, Action action) {
        RobotStatus status = state.getGameStatus().getRobotStatus();
        double[] coordinates = new double[2];
        double angle = status.getHeading();
        // normalize angle
        angle = (- angle);
        angle += 90 % 360;

        switch (action) {
            case AHEAD:
                coordinates[0] = status.getX() + getVx(angle, Constants.MOVE_DISTANCE);
                coordinates[1] = status.getY() + getVy(angle, Constants.MOVE_DISTANCE);
                break;
            case BACK:
                coordinates[0] = status.getX() + getVx(angle, Constants.MOVE_DISTANCE);
                coordinates[1] = status.getY() + getVy(angle, Constants.MOVE_DISTANCE);
                break;
            case TURN_LEFT:
            case TURN_RIGHT:
                coordinates[0] = status.getX();
                coordinates[1] = status.getY();
                break;
            default:
                throw new IllegalStateException();
        }

        return coordinates;
    }

    static private double getVx(double angle, double r) {
        double vx = Math.cos(Math.toRadians(angle)) * r;
        return Math.round(vx);
    }

    static private double getVy(double angle, double r) {
        double vy = Math.sin(Math.toRadians(angle)) * r;
        return Math.round(vy);
    }

    static public void printQTable(Table<State, Action, Double> Q) {
        LOG.info("Printing Q-table");
        String message = "";
        for (State state : Q.rowKeySet()) {
            for (Action action : Q.columnKeySet()) {
                 message += "\n[" + state + "], [" + action + "]" + Q.get(state, action);
            }
        }
        LOG.info(message);
    }

    static public void printWeights(Map<Feature, Double> weights) {
        LOG.info("Printing weights");
        String message = "";
        for (Map.Entry<Feature, Double> entry : weights.entrySet()) {
            message += "\n[" + entry.getKey() + "] = " + entry.getValue();
        }
        LOG.info(message);
    }

    static public double normalize(double x, double minX, double maxX) {
        return (x - minX) / (maxX-minX);
    }
}
