package org.firstinspires.ftc.teamcode.common.subsystems;
import android.graphics.Path;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import java.util.HashMap;

public class Lift extends SubsystemBase {
    public MotorEx motorLiftL;
    public MotorEx motorLiftR;

    public int RTP;
    public int adjustment;
    Globals globals;

    public Lift() {
        CommandScheduler.getInstance().registerSubsystem(this);
        motorLiftL = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "LS", Motor.GoBILDA.RPM_1150);
        motorLiftR = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "RS", Motor.GoBILDA.RPM_1150);

        RTP = 0;
        adjustment = 0;
        OpModeReference.getInstance().getTelemetry().addLine("Slides Initalising");
        motorLiftL.stopAndResetEncoder();
        motorLiftR.stopAndResetEncoder();
        motorLiftR.setInverted(true);
        motorLiftR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motorLiftL.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motorLiftL.setPositionTolerance(50);
        motorLiftR.setPositionTolerance(50);
        motorLiftL.setTargetPosition(0);
        motorLiftR.setTargetPosition(0);
        motorLiftL.setRunMode(Motor.RunMode.PositionControl);
        motorLiftR.setRunMode(Motor.RunMode.PositionControl);
        motorLiftL.set(1);
        motorLiftR.set(1);

        globals = OpModeReference.getInstance().globalsSubSystem;

        setDefaultCommand(new PerpetualCommand(goToPosition())); //Perpetual Command is very important, cause InstantCommands end right after they execute
    }

    @Override
    public void periodic() {
        OpModeReference.getInstance().getTelemetry().addData("Lift RTP", RTP);
        OpModeReference.getInstance().getTelemetry().addData("Lift Adjustment", adjustment);
        OpModeReference.getInstance().getTelemetry().addData("Lift Target Position", motorLiftL.motorEx.getTargetPosition());
        OpModeReference.getInstance().getTelemetry().addData("LS Pos", motorLiftL.getCurrentPosition());
    }

    public InstantCommand turnArm() {
        return new InstantCommand(() -> {
            if (globals.updateRobotStateTrue && !globals.armAcceptState) {
                globals.armAcceptState = true;
                armPosition = stateToPositionMap.get(globals.getRobotState());
                adjustment = 0;
            }
            rightArm.setPosition(armPosition+adjustment);
            leftArm.setPosition(armPosition+adjustment);
        });
    }

    @NonNull
    public InstantCommand adjustArmUp() {
        return new InstantCommand(()-> {
            adjustment += 0.03;
            OpModeReference.getInstance().getTelemetry().addLine("Arm Adjusted");
        });
    }

    @NonNull
    public InstantCommand adjustArmDown() {
        return new InstantCommand(()-> {
            adjustment -= 0.03;
            OpModeReference.getInstance().getTelemetry().addLine("Arm Adjusted");
        });
    }

    @NonNull
    public InstantCommand resetAdjustment() {
        return new InstantCommand(() -> {
            adjustment = 0;
            OpModeReference.getInstance().getTelemetry().addLine("Arm Adjustment Reset");
        });
    }

}
