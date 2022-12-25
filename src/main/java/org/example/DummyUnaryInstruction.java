package org.example;

import org.spiderland.Psh.Instruction;
import org.spiderland.Psh.IntStack;
import org.spiderland.Psh.Interpreter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class DummyUnaryInstruction extends Instruction {

    private static final Set<String> MOVES = Set.of("robot.ahead", "robot.back", "robot.turnleft", "robot.turnright");

    private final String name;
    private final AtomicInteger steps;

    public DummyUnaryInstruction(final String name, final AtomicInteger steps) {
        this.name = name;
        this.steps = steps;
    }

    @Override
    public void Execute(final Interpreter interpreter) {
        final IntStack intStack = interpreter.intStack();
        if (intStack.size() > 0) {
            final int stepsCount = intStack.pop();
            if (MOVES.contains(name)) {
                steps.addAndGet(Math.abs(stepsCount));
            }
        }
    }

    public String getName() {
        return name;
    }
}
