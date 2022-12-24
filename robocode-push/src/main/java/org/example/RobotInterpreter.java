package org.example;

import org.spiderland.Psh.Interpreter;

import java.util.function.Consumer;

public class RobotInterpreter extends Interpreter {

    private final Consumer<Integer> ahead;

    public RobotInterpreter(Consumer<Integer> ahead) {
        this.ahead = ahead;
    }

    @Override
    public int Step(int maxSteps) {
        int executed = 0;
        while (maxSteps != 0 && _execStack.size() > 0) {
            ExecuteInstruction(_execStack.pop());
            maxSteps--;
            executed++;
            if (executed % 1000 == 0) {
                ahead.accept(0);
            }
        }

        _totalStepsTaken += executed;
        return executed;
    }
}
