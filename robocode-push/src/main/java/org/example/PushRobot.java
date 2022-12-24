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
        final RobotUnaryIntInstruction turnLeft = new RobotUnaryIntInstruction("robot.turnleft", this::turnLeft);
        final RobotUnaryIntInstruction turnRight = new RobotUnaryIntInstruction("robot.turnright", this::turnRight);
        final RobotUnaryIntInstruction turnGunLeft = new RobotUnaryIntInstruction("robot.turngunleft", this::turnGunLeft);
        final RobotUnaryIntInstruction turnGunRight = new RobotUnaryIntInstruction("robot.turngunright", this::turnGunRight);
        final RobotUnaryIntInstruction turnRadarLeft = new RobotUnaryIntInstruction("robot.turnradarleft", this::turnRadarLeft);
        final RobotUnaryIntInstruction turnRadarRight = new RobotUnaryIntInstruction("robot.turnradarright", this::turnRadarRight);
        final RobotUnaryIntInstruction fire = new RobotUnaryIntInstruction("robot.fire", this::fire);
        this.interpreter = new RobotInterpreter(this::turnRadarLeft);
        this.interpreter.SetRandomParameters(-500, 500, 1, -500, 500, 1, 40, 100);
        List.of(ahead, back, turnLeft, turnRight, turnGunLeft, turnGunRight, turnRadarLeft, turnRadarRight, fire)
                .forEach(it -> interpreter.AddInstruction(it.getName(), it));
        this.program = new Program(interpreter, System.getProperty("RobotProgram.push"));
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
