package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import java.util.HashMap;

public class Arm extends SubsystemBase {
    ServoEx leftArm;
    ServoEx rightArm;

    public static double armPosition = 0.35;
    public static double adjustment = 0;
    private HashMap<RobotState, Double> stateToPositionMap;
    Globals globals;

    public Arm() {
        CommandScheduler.getInstance().registerSubsystem(this);
        leftArm = OpModeReference.getInstance().getHardwareMap().get(ServoEx.class, "LA");
        rightArm = OpModeReference.getInstance().getHardwareMap().get(ServoEx.class, "RA");

        stateToPositionMap = new HashMap<RobotState, Double>() {{
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

        setDefaultCommand(new PerpetualCommand(turnArm())); //Perpetual Command is very important, cause InstantCommands end right after they execute
    }

    @Override
    public void periodic() {
        //hardware.leftArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        //hardware.rightArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        OpModeReference.getInstance().getTelemetry().addData("Arm RTP", armPosition);
        OpModeReference.getInstance().getTelemetry().addData("Actual Left Arm Pos", leftArm.getPosition());
        OpModeReference.getInstance().getTelemetry().addData("Actual Right Arm Pos", rightArm.getPosition());
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
