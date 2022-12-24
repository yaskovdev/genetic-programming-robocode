package org.example;


import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import java.io.File;

public class BattleRunner {
    public static void main(final String[] args) {
        allowRobotsReadExternalFiles();
        RobocodeEngine.setLogMessagesEnabled(false);

        final RobocodeEngine engine = new RobocodeEngine(new File("/Users/yaskovdev/robocode"));
        engine.addBattleListener(new BattleObserver());
        engine.setVisible(true);
        final int numberOfRounds = 1;

        for (int i = 0; i < 70; i++) {
            final BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
            final RobotSpecification[] selectedRobots = engine.getLocalRepository("org.example.PushRobot,sample.Tracker");
            final BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
            engine.runBattle(battleSpec, true);
            System.out.println("Battle " + i + " is over");
        }

        engine.close();
        System.exit(0);
    }

    private static void allowRobotsReadExternalFiles() {
        System.setProperty("NOSECURITY", "true");
    }
}
