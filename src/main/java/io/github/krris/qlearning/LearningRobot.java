package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
import io.github.krris.qlearning.reward.RewardType;
import io.github.krris.qlearning.reward.Rewards;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
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

    private QLearning ql = context.getBean("qlearning", QLearning.class);
    private Rewards rewards = context.getBean("rewards", Rewards.class);

    private GameStatus game;
    private final boolean deserialize = true;

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

    private void turnRight() {
        this.setTurnRight(Constants.TURN_ANGLE);
        this.execute();
        this.waitFor(new TurnCompleteCondition(this));
    }

    private void turnLeft() {
        this.setTurnLeft(Constants.TURN_ANGLE);
        this.execute();
        this.waitFor(new TurnCompleteCondition(this));
    }

    private void goBack() {
        this.setBack(Constants.MOVE_DISTANCE);
        this.execute();
        this.waitFor(new MoveCompleteCondition(this));
    }

    private void goAhead() {
        this.setAhead(Constants.MOVE_DISTANCE);
        this.execute();
        this.waitFor(new MoveCompleteCondition(this));
    }

    private void initTurnRightAction() {
        Executable turnRightAction = () -> {
            LOG.info("Right action.");
            turnRight();
        };
        ql.setActionFunction(Action.TURN_RIGHT, turnRightAction);
    }

    private void initTurnLeftAction() {
        Executable turnLeftAction = () -> {
            LOG.info("Left action.");
            turnLeft();
        };
        ql.setActionFunction(Action.TURN_LEFT, turnLeftAction);
    }

    private void initBackAction() {
        Executable backAction = () -> {
            LOG.info("Back action.");
            goBack();
        };
        ql.setActionFunction(Action.BACK, backAction);
    }


    private void initAheadAction() {
        Executable aheadAction = () -> {
            LOG.info("Ahead action.");
            goAhead();
        };
        ql.setActionFunction(Action.AHEAD, aheadAction);
    }

    public void run() {
        // Gun, radar and tank movements are independent
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

        addCustomEvent(new UpdateCoordsEvent("update_my_tank_coords"));

        if (deserialize) {
            ql.deserializeQ(this.getDataFile(Constants.serializedQFilePath));
        }

        LOG.info("StartBattle");
//        Util.printQTable(ql.getQTable());

        while (true) {
            this.setDebugProperties();
            State state = State.updateState(game);
            Action action = ql.nextAction(state, game.getRoundNum());
            action.execute();
            this.livingReward();
            this.setDebugProperties();
            // Prevents updating q-table after the end of the round.
            if (game.isAmIAlive() == false || game.getMyEnergy() == 0)
                break;
            State nextState = State.updateState(game);
            ql.updateQ(state, action, nextState);
            rewards.endOfCycle();
        }
    }

    private void setDebugProperties() {
        this.setDebugProperty("EnemyX", String.valueOf(this.game.getEnemyX()));
        this.setDebugProperty("EnemyY", String.valueOf(this.game.getEnemyY()));
        this.setDebugProperty("AngleToEnemy", String.valueOf(this.game.getAngleToEnemy()));
        this.setDebugProperty("DistToEnemy", String.valueOf(this.game.getDistanceToEnemy()));
        this.setDebugProperty("DistToNearestWall", String.valueOf(this.game.getDistanceToNearestWall()));
    }

    public void onStatus(StatusEvent e) {
        game.setRobotStatus(e.getStatus());
        game.setBattlefieldHeight(this.getBattleFieldHeight());
        game.setBattlefieldWidth(this.getBattleFieldWidth());
    }

    // Called when we have scanned a robot
    public void onScannedRobot(ScannedRobotEvent e) {
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());

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

    public void livingReward() {
        rewards.addReward(RewardType.LIVING_REWARD);
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
//        Util.printQTable(ql.getQTable());
        rewards.endOfRound();
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        super.onBattleEnded(event);
        LOG.info("Battle ended");
        ql.serializeQ(this.getDataFile(Constants.serializedQFilePath));
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
