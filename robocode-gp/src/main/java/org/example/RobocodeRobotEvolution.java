package org.example;

import org.spiderland.Psh.GAIndividual;
import org.spiderland.Psh.GATestCase;
import org.spiderland.Psh.Interpreter;
import org.spiderland.Psh.Program;
import org.spiderland.Psh.PushGP;
import org.spiderland.Psh.PushGPIndividual;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RobocodeRobotEvolution extends PushGP {

    private final AtomicInteger steps = new AtomicInteger(0);
    private final BattleRunner battleRunner;

    public RobocodeRobotEvolution() {
        this.battleRunner = new BattleRunner();
    }

    @Override
    protected void InitFromParameters() throws Exception {
        super.InitFromParameters();
        // TODO: what should be the test cases?
        _testCases = List.of(new GATestCase(0, 0));
    }

    @Override
    protected void InitInterpreter(final Interpreter interpreter) {
//        // TODO: add number generators somehow? Seems like they are there, just very rare
        Stream.of("robot.ahead", "robot.back", "robot.turngunleft", "robot.turngunright")
                .map(it -> new DummyUnaryInstruction(it, steps))
                .forEach(it -> interpreter.AddInstruction(it.getName(), it));
    }

    // TODO: compete against robots from the same generation
    // TODO: implement more robot instructions
    @Override
    public float EvaluateTestCase(final GAIndividual individual, final Object input, final Object output) {
        steps.set(0);
        final PushGPIndividual robot = (PushGPIndividual) individual;
        final Program program = robot._program;
        final String programString = program.toString();
        System.setProperty("robot.push", programString);
        final Results results = battleRunner.runBattle();
        final int diff = results.myResults().getScore() - results.enemyResults().getScore();
        if (diff >= 0) {
            return 0;
        } else {
            _interpreter.clearStacks();
            _interpreter.Execute(program, _executionLimit);
            return Math.max(0, 1000 - steps.get()) + Math.abs(diff);
        }
    }
}
