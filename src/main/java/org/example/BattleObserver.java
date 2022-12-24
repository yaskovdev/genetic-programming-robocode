package org.example;

import robocode.BattleResults;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleMessageEvent;

import java.util.function.BiConsumer;

public class BattleObserver extends BattleAdaptor {

    private final BiConsumer<BattleResults, BattleResults> scoresCallback;

    public BattleObserver(BiConsumer<BattleResults, BattleResults> scoresCallback) {
        this.scoresCallback = scoresCallback;
    }

    public void onBattleCompleted(final BattleCompletedEvent e) {
        final BattleResults[] results = e.getSortedResults();
        if (results.length != 2) {
            throw new RuntimeException("Unexpected results length " + results.length);
        }
        scoresCallback.accept(results[0], results[1]);
    }

    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println("Msg> " + e.getMessage());
    }

    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
}
