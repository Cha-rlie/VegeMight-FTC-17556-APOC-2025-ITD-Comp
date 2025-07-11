package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import java.util.HashMap;

public class Intake extends SubsystemBase {
    Servo clawRot;
    Servo wrist;
    Servo clawGripper;

    public static double rotPosition = 0.5;
    public static double adjustment = 0;
    private static boolean clawOpen = true;
    private HashMap<RobotState, Double> stateToPositionMapForRot;
    Globals globals;

    public Intake() {
        CommandScheduler.getInstance().registerSubsystem(this);
        clawGripper = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "G");
        clawRot = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "R");
        wrist = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "W");

        stateToPositionMapForRot = new HashMap<RobotState, Double>() {{
            put(RobotState.IDLE, 0.50);
            put(RobotState.DEPOSIT, 0.41);
            put(RobotState.HOVERBEFOREGRAB, 0.50);
            put(RobotState.GRAB, 0.52);
            put(RobotState.HOVERAFTERGRAB, 0.50);
            put(RobotState.SPECHOVER, 0.0);
            put(RobotState.SPECGRAB, 0.0);
            put(RobotState.DEPOSITSPECIMEN, 0.0);
            put(RobotState.BACKWARDGRAB, 0.052);
            put(RobotState.BACKWARDHOVERAFTERGRAB, 0.052);
            put(RobotState.BACKWARDHOVERBEFOREGRAB, 0.052);
            put(RobotState.BACKWARDSCORE, 0.60);
        }};

        globals = OpModeReference.getInstance().globalsSubSystem;

        setDefaultCommand(new PerpetualCommand(controlIntake())); //Perpetual Command is very important, cause InstantCommands end right after they execute
    }

    @Override
    public void periodic() {
        //hardware.leftArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        //hardware.rightArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        OpModeReference.getInstance().getTelemetry().addData("Arm RTP", rotPosition);
        OpModeReference.getInstance().getTelemetry().addData("Actual Left Arm Pos", clawRot.getPosition());
        OpModeReference.getInstance().getTelemetry().addData("Actual Right Arm Pos", clawGripper.getPosition());
    }

    public RunCommand controlIntake() {
        return new RunCommand(() -> {
            if (globals.updateRobotStateTrue && !globals.intakeAcceptState) {
                globals.intakeAcceptState = true;
                rotPosition = stateToPositionMapForRot.get(globals.getRobotState());
                if (globals.getRobotState() == RobotState.HOVERBEFOREGRAB || globals.getRobotState() == RobotState.GRAB || globals.getRobotState() == RobotState.HOVERAFTERGRAB) {
                    if (globals.lastRobotState == RobotState.HOVERBEFOREGRAB || globals.getRobotState() == RobotState.GRAB || globals.getRobotState() == RobotState.HOVERAFTERGRAB) {
                        adjustment = adjustment;
                    } else {adjustment = 0;}
                } else {adjustment = 0;}
            }
            clawRot.setPosition(rotPosition+adjustment);
            clawGripper.setPosition(rotPosition+adjustment);
        }, this);
    }

    @NonNull
    public InstantCommand adjustArmUp() {
        return new InstantCommand(()-> {
            adjustment += 0.03;
            OpModeReference.getInstance().getTelemetry().addLine("Claw Rot Adjusted");
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
