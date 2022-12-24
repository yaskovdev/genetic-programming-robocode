package org.example;

import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattleStartedEvent;

import java.util.function.BiConsumer;

public class BattleObserver extends BattleAdaptor {

    private final BiConsumer<BattleResults, BattleResults> scoresCallback;

    public BattleObserver(BiConsumer<BattleResults, BattleResults> scoresCallback) {
        this.scoresCallback = scoresCallback;
    }

    @Override
    public void onBattleStarted(BattleStartedEvent event) {
    }

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(final BattleCompletedEvent e) {
        final BattleResults[] results = e.getSortedResults();
        if (results.length != 2) {
            throw new RuntimeException("Unexpected results length " + results.length);
        }
        scoresCallback.accept(results[0], results[1]);
    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
}
