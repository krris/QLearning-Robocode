package io.github.krris.qlearning.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by krris on 08.06.14.
 */
public class BattleConfigurator {
    private static Config config = ConfigFactory.load();

    private static String generateBattleConfig() {
        String battle =
                "robocode.battle.gunCoolingRate=0.1\n" +
                "robocode.battle.rules.inactivityTime=450\n" +
                "robocode.battle.hideEnemyNames=true\n";

        battle += "robocode.battleField.width=" + config.getString("battlefieldWidth") + "\n";
        battle += "robocode.battleField.height=" + config.getString("battlefieldHeight") + "\n";

        int rounds = config.getInt("learningRounds") + config.getInt("optimalPolicyRounds");
        battle += "robocode.battle.numRounds=" + rounds + "\n";

        battle += "robocode.battle.selectedRobots=" + config.getString("selectedRobots") + "\n";

        battle += "robocode.battle.initialPositions= " + config.getString("initialPositions") + "\n";
        return battle;
    }

    public static void main(String[] args) {
        System.out.println("Configuration of generated.battle file...");
        try {
            File file = new File(Constants.BATTLE_CONFIG_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            String battleConfig = generateBattleConfig();
            Files.write(file.toPath(), battleConfig.getBytes());
            System.out.println("Generated new .battle file: " + file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
