package org.example;

import org.spiderland.Psh.Program;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.Robot;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.StatusEvent;

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
        this.program = new Program(interpreter, "((integer.rot) ((integer.ln) ((78 (code.do*count)) ((integer.fromfloat code.fromfloat code.fromboolean) (integer.fromfloat exec.dup code.fromboolean) integer.-) (integer.rot integer.fromfloat) ((integer.rand integer.dup) robot.ahead))) (((integer.fromfloat ((((((integer.fromfloat (((exec.pop (integer.shove) (((code.stackdepth))) (exec.yankdup) ((integer.*)))) ((((integer.rot) integer.max) robot.ahead integer.dup) robot.turngunleft exec.k (integer.fromfloat code.do*count integer.fromfloat)))) robot.ahead (integer.fromfloat code.do*count code.fromboolean)) integer.fromboolean) exec.yankdup)) (((integer.fromfloat integer.dup) (integer.fromfloat code.do*count code.fromboolean) integer.dup) code.do*count exec.k integer.rot))) robot.ahead integer.dup) code.dup exec.rand integer.max))");
    }

    @Override
    public void run() {
        interpreter.Execute(program);
    }
}
