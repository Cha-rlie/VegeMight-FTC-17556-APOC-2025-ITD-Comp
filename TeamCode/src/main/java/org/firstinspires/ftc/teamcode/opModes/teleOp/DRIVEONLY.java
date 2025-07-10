package org.firstinspires.ftc.teamcode.opModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="DRIVEONLY")
public class DRIVEONLY extends CommandOpMode {
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

        FL = new MotorEx(hardwareMap, "FL", Motor.GoBILDA.RPM_312);
        FR = new MotorEx(hardwareMap, "FR", Motor.GoBILDA.RPM_312);
        BL = new MotorEx(hardwareMap, "BL", Motor.GoBILDA.RPM_312);
        BR = new MotorEx(hardwareMap, "BR", Motor.GoBILDA.RPM_312);
        FL.setInverted(true);
        BL.setInverted(true);

        driveTrain = new MecanumDrive(false, FL, FR, BL, BR);
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        driveTrain.driveRobotCentric(gamePad1.getLeftX(), gamePad1.getLeftY(), gamePad1.getRightX());
        telemetry.update();
    }

}
