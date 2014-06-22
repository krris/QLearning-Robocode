package io.github.krris.qlearning;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
import io.github.krris.qlearning.reward.RewardType;
import io.github.krris.qlearning.reward.Rewards;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
import io.github.krris.qlearning.util.TickCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robocode.*;

import java.awt.*;
import java.io.File;

/**
 * Created by krris on 16.03.14.
 */
public class LearningRobot extends AdvancedRobot {
    private final Logger LOG = LoggerFactory.getLogger(LearningRobot.class);
    private static ApplicationContext context = new ClassPathXmlApplicationContext("io/github/krris/qlearning/beans.xml");

    private QLearning ql = context.getBean("qlearning", QLearning.class);
    private Rewards rewards = context.getBean("rewards", Rewards.class);

    private GameStatus game;
    private static boolean deserialize;
    private static boolean serialize;
    private boolean isOptimalPolicy;

    private TickCounter tickCounter;

    private static Config config = ConfigFactory.parseFile(new File("/home/krris/programowanie/idea-robot/QLearning-Robocode/application.conf"));

    static {
        serialize = config.getBoolean("serialize");
        deserialize = config.getBoolean("deserialize");
    }

    public LearningRobot() {
        init();
        game = new GameStatus.Builder()
                .amIAlive(true)
                .enemyEnergy(100)
                .build();
        tickCounter = new TickCounter();
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

        deserialize();
        this.updateIsOptimalPolicy();

        LOG.info("StartBattle");
//        Util.printQTable(ql.getQTable());

        while (true) {
            this.setDebugProperties();
            State state = State.updateState(game);
            Action action = chooseAction(state);
            action.execute();
            this.livingReward();
            this.distanceToEnemyReward();
            this.setDebugProperties();
            // Prevents updating q-table after the end of the round.
            if (game.isAmIAlive() == false || game.getMyEnergy() == 0)
                break;
            if (!isOptimalPolicy) {
                State nextState = State.updateState(game);
                ql.updateQ(state, action, nextState);
            }
            rewards.endOfCycle();
            game.resetDataAtTheEndOfCycle();
            tickCounter.tick();
        }
    }

    private Action chooseAction(State state) {
        if (isOptimalPolicy) {
            LOG.debug("Optimal policy (round: {}, learningRounds: {})", game.getRoundNum(), Constants.LEARNING_ROUNDS);
            return ql.bestAction(state);
        }
        LOG.debug("Learning policy (round: {}, learningRounds: {})", game.getRoundNum(), Constants.LEARNING_ROUNDS);
        return ql.nextAction(state, game.getRoundNum());
    }

    private void deserialize() {
        if (deserialize) {
            ql.deserializeQ(this.getDataFile(Constants.serializedQFilePath));
            if (ql instanceof ApproximateQLearning) {
                ((ApproximateQLearning)ql).deserializeWeights(this.getDataFile(Constants.serializedWeightsFilePath));
            }
        }
    }

    private void setDebugProperties() {
        this.setDebugProperty("EnemyX", String.valueOf(this.game.getEnemyX()));
        this.setDebugProperty("EnemyY", String.valueOf(this.game.getEnemyY()));
        this.setDebugProperty("AngleToEnemy", String.valueOf(this.game.getAngleToEnemy()));
        this.setDebugProperty("DistToEnemy", String.valueOf(this.game.getDistanceToEnemy()));
        this.setDebugProperty("DistToNearestWall", String.valueOf(this.game.getDistanceToNearestWall()));
        this.setDebugProperty("EnemeShotABullet", String.valueOf(this.game.getEnemyShotABullet()));
        this.setDebugProperty("EnemeMovementDirection", String.valueOf(this.game.getEnemyMovementDirection()));
    }

    public void onStatus(StatusEvent e) {
        game.setRobotStatus(e.getStatus());
        game.setBattlefieldHeight(this.getBattleFieldHeight());
        game.setBattlefieldWidth(this.getBattleFieldWidth());
    }

    private void updateIsOptimalPolicy(){
        if (game.getRoundNum() >= Constants.LEARNING_ROUNDS){
            this.isOptimalPolicy = true;
        } else {
            this.isOptimalPolicy = false;
        }
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
		// If the other robot is close by, and we have plenty of life,
		// fire hard!
//        if (getGunHeat() == 0) {
//            if (e.getDistance() < 50 && game.getMyEnergy() > 50 )
//                fire(3);
//                // otherwise, fire 1.
//            else
//                fire(1);
//        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        LOG.info("Hit by a bullet! Power: " + e.getPower());
        int baseValue = 0;
        rewards.addReward(RewardType.HIT_BY_BULLET);
    }

    public void onHitRobot(HitRobotEvent e) {
        LOG.info("Hit another robot!");
        // check if we kill a robot
        double damage = game.getEnemyEnergy() - e.getEnergy();
        LOG.info("Hit damage: " + damage);
        if (e.getEnergy() <= 0) {
            LOG.info("Killed an enemy!");
            rewards.addReward(RewardType.COLLISION_AND_KILL_ENEMY);
        } else {
            rewards.addReward(RewardType.COLLISION_WITH_ENEMY);
        }
    }

    public void livingReward() {
        rewards.addReward(RewardType.LIVING_REWARD);
    }

    private void distanceToEnemyReward() {
        double tooClose = 50;
        double close = 200;
        if (game.getDistanceToEnemy() < tooClose) {
            LOG.debug("Too close to the enemy! Distance < 50!!!");
            rewards.addReward(RewardType.DISTANCE_TO_ENEMY_LESS_THAN_50);
        } else if (game.getDistanceToEnemy() < close) {
            LOG.debug("close to the enemy! Distance < 200!");
            rewards.addReward(RewardType.DISTANCE_TO_ENEMY_LESS_THAN_200);
        }
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
//        LOG.info("Round ended");
//        rewards.endOfRound();
        game.setAmIAlive(false);
    }


    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        super.onRoundEnded(event);
        LOG.info("Round ended");
        rewards.endOfRound();
        tickCounter.endOfRound();
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        super.onBattleEnded(event);
        LOG.info("Battle ended");
        if (serialize) {
            ql.serializeQ(this.getDataFile(Constants.serializedQFilePath));
            if (ql instanceof  ApproximateQLearning) {
                ((ApproximateQLearning)ql).serializeWeights(this.getDataFile(Constants.serializedWeightsFilePath));
            }
        }
    }

    public void onWin(WinEvent e) {
//        LOG.info("Round ended");
//        rewards.endOfRound();
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
