package org.example;

import org.spiderland.Psh.Interpreter;
import org.spiderland.Psh.Program;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.util.List;

public class PushRobot extends Robot {

    private static final String PROGRAM = "(0 4 code.quote (100 robot.ahead 360 robot.turngunright 100 robot.back -360 robot.turngunleft) code.do*range)";

    private final Interpreter interpreter;

    private final Program program;

    public PushRobot() throws Exception {
        final RobotUnaryIntInstruction ahead = new RobotUnaryIntInstruction("robot.ahead", this::ahead);
        final RobotUnaryIntInstruction back = new RobotUnaryIntInstruction("robot.back", this::back);
        final RobotUnaryIntInstruction turnGunLeft = new RobotUnaryIntInstruction("robot.turngunleft", this::turnGunLeft);
        final RobotUnaryIntInstruction turnGunRight = new RobotUnaryIntInstruction("robot.turngunright", this::turnGunRight);
        this.interpreter = new RobotInterpreter(List.of(ahead, back, turnGunLeft, turnGunRight));
        this.interpreter.SetRandomParameters(-10, 10, 1, -10, 10, 0.01f, 40, 100);
        this.program = new Program(interpreter, PROGRAM);
    }

    @Override
    public void run() {
        interpreter.Execute(program);
    }

    @Override
    public void onScannedRobot(final ScannedRobotEvent event) {
        fire(1);
    }
}
