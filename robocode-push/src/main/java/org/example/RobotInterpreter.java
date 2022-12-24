package org.example;

import org.spiderland.Psh.Interpreter;

import java.util.List;

public class RobotInterpreter extends Interpreter {

    public RobotInterpreter(List<RobotUnaryIntInstruction> instructions) {
        instructions.forEach(it -> DefineInstruction(it.getName(), it));
    }
}
