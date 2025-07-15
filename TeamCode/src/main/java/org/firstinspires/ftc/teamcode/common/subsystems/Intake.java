package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import java.util.HashMap;

public class Intake extends SubsystemBase {
    Servo clawRot;
    Servo wrist;
    Servo clawGripper;

    public static double wristPosition = 0;
    public static double wristAdjustment = 0;
    public static double rotPosition = 0.5;
    public static double rotAdjustment = 0;
    private static boolean clawOpen = true;
    private HashMap<RobotState, Double> stateToPositionMapForWrist;
    private HashMap<RobotState, Double> stateToPositionMapForRot;
    Globals globals;
    private boolean isBusy;

    public Intake() {
        isBusy = false;
        CommandScheduler.getInstance().registerSubsystem(this);
        wrist = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "W");
        clawRot = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "R");
        clawGripper = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "G");

        stateToPositionMapForWrist = new HashMap<RobotState, Double>() {{
            put(RobotState.INIT, 0.7);
            put(RobotState.IDLE, 0.06);
            put(RobotState.DEPOSIT, 0.0);
            put(RobotState.DEPOSITRELEASE, 0.0);
            put(RobotState.HOVERBEFOREGRAB, 0.56);
            put(RobotState.GRAB, 0.68);
            put(RobotState.GRABCLOSE, 0.68);
            put(RobotState.HOVERAFTERGRAB, 0.56);
            put(RobotState.SPECHOVERBEFOREGRAB, 0.0);
            put(RobotState.SPECGRAB, 0.0);
            put(RobotState.DEPOSITSPECIMEN, 0.0);
            put(RobotState.BACKWARDGRAB, 0.052);
            put(RobotState.BACKWARDHOVERAFTERGRAB, 0.052);
            put(RobotState.BACKWARDHOVERBEFOREGRAB, 0.052);
            put(RobotState.BACKWARDSCORE, 0.60);
        }};

        stateToPositionMapForRot = new HashMap<RobotState, Double>() {{
            put(RobotState.INIT, 0.0);
            put(RobotState.IDLE, 0.0);
            put(RobotState.DEPOSIT, 0.0);
            put(RobotState.DEPOSITRELEASE, 0.0);
            put(RobotState.HOVERBEFOREGRAB, 0.0);
            put(RobotState.GRAB, 0.0);
            put(RobotState.GRABCLOSE, 0.0);
            put(RobotState.HOVERAFTERGRAB, 0.0);
            put(RobotState.SPECHOVERBEFOREGRAB, 0.0);
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
        OpModeReference.getInstance().getTelemetry().addData("Wrist Pos", wrist.getPosition());
        OpModeReference.getInstance().getTelemetry().addData("Claw Rot Pos", clawRot.getPosition());
        OpModeReference.getInstance().getTelemetry().addData("Claw Grip Open?", clawOpen);
        isBusy = true;
    }

    public RunCommand controlIntake() {
        return new RunCommand(() -> {
            isBusy = false;
            if (globals.updateRobotStateTrue && !globals.intakeAcceptState) {
                globals.intakeAcceptState = true;
                rotPosition = stateToPositionMapForRot.get(globals.getRobotState());
                wristPosition = stateToPositionMapForWrist.get(globals.getRobotState());
                switch (globals.getRobotState()) {
                    case HOVERBEFOREGRAB:
                    case DEPOSITRELEASE:
                        clawOpen = true;
                        break;
                    case GRABCLOSE:
                        clawOpen = true;
                        new WaitCommand(700).andThen(new RunCommand(() -> clawOpen = false)).schedule();
                    default:
                        clawOpen = false;
                        break;
                }
                if (globals.getRobotState() == RobotState.HOVERBEFOREGRAB || globals.getRobotState() == RobotState.GRAB || globals.getRobotState() == RobotState.HOVERAFTERGRAB) {
                    if (globals.lastRobotState == RobotState.HOVERBEFOREGRAB || globals.lastRobotState == RobotState.GRAB || globals.lastRobotState == RobotState.HOVERAFTERGRAB) {
                        rotAdjustment = rotAdjustment;
                        wristAdjustment = wristAdjustment;
                    } else {
                        rotAdjustment = 0;
                        wristAdjustment = 0;}
                } else {
                    rotAdjustment = 0;
                    wristAdjustment = 0;}
            }
            wrist.setPosition(wristPosition + wristAdjustment);
            clawRot.setPosition(rotPosition + rotAdjustment);
            if (clawOpen) {
                clawGripper.setPosition(0.75);
            } else {clawGripper.setPosition(0.97);}
        }, this);
    }

    public InstantCommand toggleClaw() {
        return new InstantCommand(() -> {
           clawOpen = !clawOpen;
        });
    }

    @NonNull
    public InstantCommand adjustWristUp() {
        return new InstantCommand(()-> {
            wristAdjustment = wristPosition + wristAdjustment + 0.03 < 1 ? wristAdjustment + 0.03 : 1-wristPosition;
            OpModeReference.getInstance().getTelemetry().addLine("Wrist Adjusted");
        });
    }

    @NonNull
    public InstantCommand adjustWristDown() {
        return new InstantCommand(()-> {
            wristAdjustment = wristPosition + wristAdjustment - 0.03 > 0 ? wristAdjustment - 0.03 : -wristPosition;
            OpModeReference.getInstance().getTelemetry().addLine("Wrist Adjusted");
        });
    }

    public InstantCommand adjustRotLeft() {
        return new InstantCommand(() -> {
            rotAdjustment = rotPosition + rotAdjustment - 0.2 > 0 ? rotAdjustment - 0.2 : -rotPosition;
        });
    }

    public InstantCommand adjustRotRight() {
        return new InstantCommand(() -> {
            rotAdjustment = rotPosition + rotAdjustment + 0.2 < 1 ? rotAdjustment + 0.2 : 1-rotPosition;
        });
    }

    @NonNull
    public InstantCommand resetAdjustment() {
        return new InstantCommand(() -> {
            rotAdjustment = 0;
            OpModeReference.getInstance().getTelemetry().addLine("Arm Adjustment Reset");
        });
    }

    public boolean isBusy() {return isBusy;}

}
