package org.example;

import org.spiderland.Psh.GAIndividual;
import org.spiderland.Psh.GATestCase;
import org.spiderland.Psh.Interpreter;
import org.spiderland.Psh.Program;
import org.spiderland.Psh.PushGP;
import org.spiderland.Psh.PushGPIndividual;

import java.util.List;

public class RobocodeRobotEvolution extends PushGP {

    private final BattleRunner battleRunner;

    public RobocodeRobotEvolution() {
        this.battleRunner = new BattleRunner();
    }

    @Override
    protected void InitInterpreter(final Interpreter interpreter) {
        // TODO: add number generators somehow?
        List.of("robot.ahead", "robot.back", "robot.turngunleft", "robot.turngunright")
                .forEach(it -> interpreter.AddInstruction(it, new DummyUnaryInstruction()));
        // TODO: what should be the test cases?
        _testCases = List.of(new GATestCase(0, 0));
    }

    @Override
    public float EvaluateTestCase(final GAIndividual individual, final Object input, final Object output) {
        final PushGPIndividual robot = (PushGPIndividual) individual;
        final Program program = robot._program;
        System.setProperty("robot.push", program.toString());
        final Results results = battleRunner.runBattle();
        final int diff = results.myResults().getScore() - results.enemyResults().getScore();
        return diff >= 0 ? 0 : -diff;
    }
}
