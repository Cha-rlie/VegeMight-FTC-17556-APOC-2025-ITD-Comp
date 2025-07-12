package org.firstinspires.ftc.teamcode.opModes.auto;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

@Autonomous(name="test")
public class test extends OpMode {

    GamepadEx gamePad1;
    GamepadEx gamePad2;
    boolean once = false;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        gamePad1 = new GamepadEx(gamepad1);
        gamePad2 = new GamepadEx(gamepad2);
        OpModeReference.getInstance().initHardware(hardwareMap, gamePad1, gamePad2, telemetry, 0, 0, 0);
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if (!once) {
            once = true;
            CommandScheduler.getInstance().run();
            CommandScheduler.getInstance().schedule(OpModeReference.getInstance().liftSubSystem.adjustUp());
        }
        CommandScheduler.getInstance().run();
    }
}
