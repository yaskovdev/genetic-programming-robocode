package org.example;


import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import java.io.Closeable;
import java.io.File;

public class BattleRunner implements Closeable {
    private final RobocodeEngine engine;
    private BattleResults x;
    private BattleResults y;

    public BattleRunner() {
        allowRobotsReadExternalFiles();
        RobocodeEngine.setLogMessagesEnabled(false);
        final RobocodeEngine engine = new RobocodeEngine(new File("/Users/yaskovdev/robocode"));
        engine.addBattleListener(new BattleObserver(this::setScores));
        engine.setVisible(false);
        this.engine = engine;
    }

    private void setScores(final BattleResults x, final BattleResults y) {
        this.x = x;
        this.y = y;
    }

    public Results runBattle() {
        final int numberOfRounds = 1;
        final BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600);
        final RobotSpecification[] selectedRobots = engine.getLocalRepository("org.example.PushRobot,sample.Tracker");
        final BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        engine.runBattle(battleSpec, true);
        final BattleResults myResults = x.getTeamLeaderName().contains("org.example.PushRobot") ? x : y;
        final BattleResults enemyResults = myResults.getTeamLeaderName().equals(x.getTeamLeaderName()) ? y : x;
        return new Results(myResults, enemyResults);
    }

    @Override
    public void close() {
        engine.close();
    }

    private static void allowRobotsReadExternalFiles() {
        System.setProperty("NOSECURITY", "true");
    }
}
