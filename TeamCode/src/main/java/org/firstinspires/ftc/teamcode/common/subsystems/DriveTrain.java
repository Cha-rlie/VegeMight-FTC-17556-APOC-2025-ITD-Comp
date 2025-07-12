package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

public class DriveTrain extends SubsystemBase {
    public MotorEx FL;
    public MotorEx FR;
    public MotorEx BL;
    public MotorEx BR;
    public MecanumDrive driveTrain;
    private double velocityAdjuster = 1;
    public DriveTrain() {
        FL = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "FL", Motor.GoBILDA.RPM_312);
        FR = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "FR", Motor.GoBILDA.RPM_312);
        BL = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "BL", Motor.GoBILDA.RPM_312);
        BR = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "BR", Motor.GoBILDA.RPM_312);
        FL.setInverted(true);
        BL.setInverted(true);

        driveTrain = new MecanumDrive(false, FL, FR, BL, BR);
        //setDefaultCommand(new DriveCommand(this));
        //setDefaultCommand(new PerpetualCommand(new InstantCommand(this::driveRobotCentric, this)));
        //setDefaultCommand(new InstantCommand(() -> driveTrain.driveRobotCentric(OpModeReference.getInstance().getGamePad1().getLeftX(), OpModeReference.getInstance().getGamePad1().getLeftY(), OpModeReference.getInstance().getGamePad1().getRightX()), this));
    }

    @Override
    public void periodic() {
        //driveTrain.driveRobotCentric(OpModeReference.getInstance().getGamePad1().getLeftX(), OpModeReference.getInstance().getGamePad1().getLeftY(), OpModeReference.getInstance().getGamePad1().getRightX());
        OpModeReference.getInstance().getTelemetry().addData("Velocity Adjuster", velocityAdjuster);
    }

    public void driveRobotCentric(double x, double y, double rx) {
        driveTrain.driveRobotCentric(x*velocityAdjuster, y*velocityAdjuster, rx*velocityAdjuster, true);
    }

    public void decreaseVelocity() {
        // TODO: Make this dependent on states later
        velocityAdjuster = 0.7;
    }
}

class DriveCommand extends CommandBase {
    // The subsystem the command runs on
    private final DriveTrain driveTrainSystem;

    public DriveCommand(DriveTrain driveTrainSystem) {
        this.driveTrainSystem = driveTrainSystem;
        addRequirements(this.driveTrainSystem);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void execute() {

    }
}
