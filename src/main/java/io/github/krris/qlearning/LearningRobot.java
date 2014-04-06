package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
import io.github.krris.qlearning.chart.Chart;
import io.github.krris.qlearning.reward.RewardType;
import io.github.krris.qlearning.reward.Rewards;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
import io.github.krris.qlearning.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robocode.*;

import java.awt.*;

/**
 * Created by krris on 16.03.14.
 */
public class LearningRobot extends AdvancedRobot {
    private final Logger LOG = LoggerFactory.getLogger(LearningRobot.class);
    private static ApplicationContext context = new ClassPathXmlApplicationContext("io/github/krris/qlearning/beans.xml");

    private static QLearning ql = context.getBean("qlearning", QLearning.class);
    private static Rewards rewards = context.getBean("rewards", Rewards.class);

    private GameStatus game;

    public LearningRobot() {
        init();
        game = new GameStatus.Builder()
                .amIAlive(true)
                .enemyEnergy(100)
                .build();
    }

    private void init() {
        initAheadAction();
        initBackAction();
        initTurnLeftAction();
        initTurnRightAction();
        ql.init();
    }

    private void initTurnRightAction() {
        Executable turnRightAction = () -> {
            LOG.info("Turn right action.");
            this.setTurnRight(Constants.TURN_ANGLE);
            this.execute();
            this.waitFor(new TurnCompleteCondition(this));
        };

        ql.setActionFunction(Action.TURN_RIGHT, turnRightAction);
    }

    private void initTurnLeftAction() {
        Executable turnLeftAction = () -> {
            LOG.info("Turn left action.");
            this.setTurnLeft(Constants.TURN_ANGLE);
            this.execute();
            this.waitFor(new TurnCompleteCondition(this));
        };

        ql.setActionFunction(Action.TURN_LEFT, turnLeftAction);
    }

    private void initBackAction() {
        Executable backAction = () -> {
            LOG.info("Back action.");
            this.setBack(Constants.MOVE_DISTANCE);
            this.execute();
            this.waitFor(new MoveCompleteCondition(this));
        };

        ql.setActionFunction(Action.BACK, backAction);
    }

    private void initAheadAction() {
        Executable aheadAction = () -> {
            LOG.info("Ahead action.");
            this.setAhead(Constants.MOVE_DISTANCE);
            this.execute();
            this.waitFor(new MoveCompleteCondition(this));
        };

        ql.setActionFunction(Action.AHEAD, aheadAction);
    }

    public void run() {
        // Gun, radar and tank movements are independent
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        addCustomEvent(new UpdateCoordsEvent("update_my_tank_coords"));

        State currentState = State.updateState(game);

        LOG.info("StartBattle");
        LOG.info("Qlearning address [start]: " + ql );
        Util.printQTable(ql.getQ());

        while (true) {
            Action action = ql.nextAction(currentState);
            action.execute();
            currentState = State.updateState(game);
            ql.updateQ(currentState, action);
            rewards.endOfCycle();
        }
    }

    public void onStatus(StatusEvent e) {
        game.setRobotStatus(e.getStatus());
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
    }

    // Called when we have scanned a robot
    public void onScannedRobot(ScannedRobotEvent e) {
        // Update enemy energy
        game.setEnemyEnergy(e.getEnergy());

        // Update angle to enemy
        game.setAngleToEnemy(e.getBearing());

        // Calculate the angle to the scanned robot
        double angle = Math.toRadians((game.getHeading() + game.getAngleToEnemy()) % 360);

        // Calculate the coordinates of the robot
        double tempEnemyX = (game.getX() + Math.sin(angle) * e.getDistance());
        double tempEnemyY = (game.getY() + Math.cos(angle) * e.getDistance());

        if (tempEnemyX != game.getEnemyX() || tempEnemyY != game.getEnemyY()) {
            game.setEnemyX(tempEnemyX);
            game.setEnemyY(tempEnemyY);
        }

        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
        setTurnGunRightRadians(
                robocode.util.Utils.normalRelativeAngle(absoluteBearing -
                        getGunHeadingRadians()));
    }

    public void onHitByBullet(HitByBulletEvent e) {
        LOG.info("Hit by a bullet! Power: " + e.getPower());
    }

    public void onHitRobot(HitRobotEvent e) {
        LOG.info("Hit another robot!");
        rewards.addReward(RewardType.COLLISION_WITH_ENEMY);
    }

    public void onBulletHit(BulletHitEvent e) {
        LOG.info("My bullet hit an opponent!");
    }

    public void onHitWall(HitWallEvent e) {
        LOG.info("Hit a wall!");
        rewards.addReward(RewardType.HIT_A_WALL);
    }

    // Paint a transparent square on top of the last scanned robot
    public void onPaint(Graphics2D g) {
        // Set the paint color to a red half transparent color
        g.setColor(new Color(0xff, 0x00, 0x00, 0x80));

        // Draw a line from our robot to the scanned robot
        g.drawLine((int)game.getEnemyX(), (int)game.getEnemyY(), (int)game.getX(), (int)game.getY());

        // Draw a filled square on top of the scanned robot that covers it
        g.fillRect((int) game.getEnemyX() - 20, (int) game.getEnemyY() - 20, 40, 40);
    }

    public void onDeath(DeathEvent e) {
        game.setAmIAlive(false);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        super.onRoundEnded(event);
        LOG.info("Round ended");
        LOG.info("Qlearning address [end]: " + ql );
        Util.printQTable(ql.getQ());
        rewards.endOfRound();
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        // Print a chart with rewards
        Chart.printToFile(rewards.getRewardsPerRound());

        super.onBattleEnded(event);
        LOG.info("Battle ended");
    }

    public void onWin(WinEvent e) {
        LOG.info("Your robot won!");
    }

    class UpdateCoordsEvent extends Condition {
        public UpdateCoordsEvent(String string) {
            super(string);
        }

        @Override
        public boolean test() {
            if (game.isAmIAlive()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
