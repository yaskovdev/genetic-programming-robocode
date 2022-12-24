package org.example;

import org.spiderland.Psh.Interpreter;

import java.util.function.Consumer;

public class RobotInterpreter extends Interpreter {

    private final Consumer<Integer> dummyCommand;

    /**
     * @param dummyCommand a command to send to the robot every 1000 steps to check if the round is over.
     */
    public RobotInterpreter(Consumer<Integer> dummyCommand) {
        this.dummyCommand = dummyCommand;
    }

    @Override
    public int Step(int maxSteps) {
        int executed = 0;
        while (maxSteps != 0 && _execStack.size() > 0) {
            ExecuteInstruction(_execStack.pop());
            maxSteps--;
            executed++;
            if (executed % 1000 == 0) {
                dummyCommand.accept(0);
            }
        }

        _totalStepsTaken += executed;
        return executed;
    }
}
