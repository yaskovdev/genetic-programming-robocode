package org.example;

import org.spiderland.Psh.Instruction;
import org.spiderland.Psh.IntStack;
import org.spiderland.Psh.Interpreter;

public class DummyUnaryInstruction extends Instruction {

    @Override
    public void Execute(final Interpreter interpreter) {
        final IntStack intStack = interpreter.intStack();
        if (intStack.size() > 0) {
            intStack.pop();
        }
    }
}
