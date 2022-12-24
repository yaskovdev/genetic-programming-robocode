package org.example;

import org.spiderland.Psh.Instruction;
import org.spiderland.Psh.IntStack;
import org.spiderland.Psh.Interpreter;

import java.util.function.Consumer;

public class RobotUnaryIntInstruction extends Instruction {

    private final String name;
    private final Consumer<Integer> callback;

    public RobotUnaryIntInstruction(final String name, final Consumer<Integer> callback) {
        this.name = name;
        this.callback = callback;
    }

    @Override
    public void Execute(final Interpreter interpreter) {
        final IntStack intStack = interpreter.intStack();
        if (intStack.size() > 0) {
            final int argument = intStack.pop();
            callback.accept(argument);
        }
    }

    public String getName() {
        return name;
    }
}
