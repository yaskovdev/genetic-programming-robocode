package org.example;

import org.spiderland.Psh.GAIndividual;
import org.spiderland.Psh.GATestCase;
import org.spiderland.Psh.Interpreter;
import org.spiderland.Psh.Program;
import org.spiderland.Psh.PushGP;
import org.spiderland.Psh.PushGPIndividual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
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
        _testCases = IntStream.range(0, 3)
                .mapToObj(i -> new GATestCase(i, 0))
                .toList();
    }

    @Override
    protected void InitInterpreter(final Interpreter interpreter) {
        Stream.of("robot.ahead", "robot.back", "robot.turnleft", "robot.turnright", "robot.turngunleft", "robot.turngunright", "robot.turnradarleft", "robot.turnradarright", "robot.fire")
                .map(it -> new DummyUnaryInstruction(it, steps))
                .forEach(it -> interpreter.AddInstruction(it.getName(), it));
    }

    // TODO: compete against robots from the same generation
    @Override
    public float EvaluateTestCase(final GAIndividual individual, final Object input, final Object output) {
        steps.set(0);
        final PushGPIndividual robot = (PushGPIndividual) individual;
        final Program program = robot._program;
        final String programString = program.toString();
        System.setProperty("RobotProgram.push", programString);
        final Results results = battleRunner.runBattle();
        final int diff = 360 - (results.myResults().getScore() - results.enemyResults().getScore() + 180);
        if (diff == 360) {
            _interpreter.clearStacks();
            _interpreter.Execute(program, _executionLimit);
            return diff + (steps.get() == 0 ? 10 : 0);
        } else {
            return diff;
        }
    }

    /**
     * Let's optimize the worst battle. Assuming the errors are all non-negative.
     */
    @Override
    protected float AbsoluteAverageOfErrors(final ArrayList<Float> errors) {
        return Collections.max(errors);
    }
}
