package org.example;

import org.spiderland.Psh.Interpreter;
import org.spiderland.Psh.Program;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PushRobot extends Robot {

    private final Interpreter interpreter;

    private final Program program;

    public PushRobot() throws Exception {
        final RobotUnaryIntInstruction ahead = new RobotUnaryIntInstruction("robot.ahead", this::ahead);
        final RobotUnaryIntInstruction back = new RobotUnaryIntInstruction("robot.back", this::back);
        final RobotUnaryIntInstruction turnGunLeft = new RobotUnaryIntInstruction("robot.turngunleft", this::turnGunLeft);
        final RobotUnaryIntInstruction turnGunRight = new RobotUnaryIntInstruction("robot.turngunright", this::turnGunRight);
        this.interpreter = new Interpreter();
        this.interpreter.SetRandomParameters(-10, 10, 1, -10, 10, 0.01f, 40, 100);
        List.of(ahead, back, turnGunLeft, turnGunRight).
                forEach(it -> interpreter.AddInstruction(it.getName(), it));
        final String program = Files.readString(Paths.get("/Users/yaskovdev/dev/robot.push"), StandardCharsets.UTF_8);
        this.program = new Program(interpreter, program);
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
