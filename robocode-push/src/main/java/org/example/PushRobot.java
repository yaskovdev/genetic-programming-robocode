package org.example;

import org.spiderland.Psh.Program;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.util.List;

public class PushRobot extends Robot {

    private final RobotInterpreter interpreter;

    private final Program program;

    public PushRobot() throws Exception {
        final RobotUnaryIntInstruction ahead = new RobotUnaryIntInstruction("robot.ahead", this::ahead);
        final RobotUnaryIntInstruction back = new RobotUnaryIntInstruction("robot.back", this::back);
        final RobotUnaryIntInstruction turnGunLeft = new RobotUnaryIntInstruction("robot.turngunleft", this::turnGunLeft);
        final RobotUnaryIntInstruction turnGunRight = new RobotUnaryIntInstruction("robot.turngunright", this::turnGunRight);
        this.interpreter = new RobotInterpreter(this::ahead);
        this.interpreter.SetRandomParameters(-500, 500, 1, -500, 500, 1, 40, 100);
        List.of(ahead, back, turnGunLeft, turnGunRight)
                .forEach(it -> interpreter.AddInstruction(it.getName(), it));
        this.program = new Program(interpreter, System.getProperty("robot.push"));
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
