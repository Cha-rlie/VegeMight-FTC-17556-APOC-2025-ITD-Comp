package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

@TeleOp(name = "DRIVEWITHSUBSYSTEM")
public class DRIVEWITHSUBSYSTEM extends CommandOpMode {
    GamepadEx gamePad1;
    GamepadEx gamePad2;

    MotorEx FL;
    MotorEx FR;
    MotorEx BL;
    MotorEx BR;
    MecanumDrive driveTrain;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        gamePad1 = new GamepadEx(gamepad1);
        gamePad2 = new GamepadEx(gamepad2);

        OpModeReference.getInstance().initHardware(hardwareMap, gamePad1, gamePad2, telemetry, 0, 0, 0);
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}
