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

    private static final int NUMBER_OF_BATTLES_PER_TEST = 3;
    private static final int MAX_SCORE = 180;
    private static final int MAX_POSSIBLE_DIFFERENCE = MAX_SCORE * 2;
    private static final int PENALTY_FOR_NOT_MOVING = 10;

    private final AtomicInteger steps = new AtomicInteger(0);
    private final BattleRunner battleRunner;

    public RobocodeRobotEvolution() {
        this.battleRunner = new BattleRunner();
    }

    @Override
    protected void initFromParameters() throws Exception {
        super.initFromParameters();
        testCases = IntStream.range(0, NUMBER_OF_BATTLES_PER_TEST)
            .mapToObj(i -> new GATestCase(i, 0))
            .toList();
    }

    @Override
    protected void initInterpreter(final Interpreter interpreter) {
        Stream.of("robot.ahead", "robot.back", "robot.turnleft", "robot.turnright", "robot.turngunleft", "robot.turngunright", "robot.turnradarleft", "robot.turnradarright", "robot.fire")
            .map(it -> new DummyUnaryInstruction(it, steps))
            .forEach(it -> interpreter.addInstruction(it.getName(), it));
    }

    @Override
    public float evaluateTestCase(final GAIndividual individual, final Object input, final Object output) {
        steps.set(0);
        final PushGPIndividual robot = (PushGPIndividual) individual;
        final Program program = robot._program;
        final String programString = program.toString();
        System.setProperty("RobotProgram.push", programString);
        final Results results = battleRunner.runBattle();

        final int diff = MAX_POSSIBLE_DIFFERENCE - (results.myResults().getScore() - results.enemyResults().getScore() + MAX_SCORE);
        if (diff == MAX_POSSIBLE_DIFFERENCE) {
            interpreter.clearStacks();
            interpreter.execute(program, executionLimit);
            return diff + (steps.get() == 0 ? PENALTY_FOR_NOT_MOVING : 0);
        } else {
            return diff;
        }
    }

    /**
     * Let us optimize the worst battle. The method assumes that the list of the errors is not empty and the errors are all non-negative.
     */
    @Override
    protected float absoluteAverageOfErrors(final ArrayList<Float> errors) {
        return Collections.max(errors);
    }
}
