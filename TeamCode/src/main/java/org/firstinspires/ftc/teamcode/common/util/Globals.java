package org.firstinspires.ftc.teamcode.common.util;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

import java.util.HashMap;


public class Globals extends SubsystemBase {

    public static final Globals INSTANCE = new Globals();
    public static boolean isSampleModeTrue = true;
    public boolean updateRobotStateTrue = true;
    public boolean liftAcceptState = false;
    public boolean intakeAcceptState = false;

    // Declare the global variables
    private RobotState robotState;
    public RobotState lastRobotState;

    private HashMap <RobotState, RobotState> goForwardStateValuesOnly;
    private HashMap <RobotState, RobotState> goBackwardStateValuesOnly;

    // Constructor that builds the drivetrain subsystem class and Hashmaps :D
    public Globals() {
        goForwardStateValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.OUTTAKE);
            put(RobotState.OUTTAKE, RobotState.IDLE);
            put(RobotState.INTAKE, RobotState.IDLE);
            put(RobotState.INIT, RobotState.IDLE);
        }};
        goBackwardStateValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.INTAKE);
            put(RobotState.OUTTAKE, RobotState.IDLE);
            put(RobotState.INTAKE, RobotState.IDLE);
            put(RobotState.INIT, RobotState.IDLE);
        }};

        robotState = RobotState.IDLE;
        lastRobotState = RobotState.IDLE;
    }

    public RobotState getRobotState() {
        return robotState;
    }

    @NonNull
    public void setRobotState(RobotState newRobotState) {
        lastRobotState = robotState;
        robotState = newRobotState;
        updateRobotStateTrue = true;
    }

    public InstantCommand setRobotStateCommand(RobotState newRobotState) {
        return new InstantCommand(()-> {
            lastRobotState = robotState;
            robotState = newRobotState;
            updateRobotStateTrue = true;
        });
    }

    @NonNull
    public InstantCommand forwardsRobotState() {
        return new InstantCommand(()-> {
                            robotState = goForwardStateValuesOnly.get(getRobotState());
                        }
                );
    }

    @NonNull
    public InstantCommand backwardsRobotState() {
        return new InstantCommand(()-> {
                            robotState = goBackwardStateValuesOnly.get(getRobotState());
                        }
                );
    }

    @NonNull
    public InstantCommand goToIdle() {
        return new InstantCommand(()-> {
                    if (robotState != RobotState.IDLE) {
                        robotState = RobotState.IDLE;
                    }
                });
    }


    @NonNull
    public InstantCommand reject() {
        return new InstantCommand(()->{
                    robotState= RobotState.REJECT;
                });
    }


    @Override
    public void periodic() {
        OpModeReference.getInstance().getTelemetry().addData("Robot State", robotState);
        OpModeReference.getInstance().getTelemetry().addData("Update Robot State", updateRobotStateTrue);
        OpModeReference.getInstance().getTelemetry().addData("Lift Accepted New State?", liftAcceptState);
        OpModeReference.getInstance().getTelemetry().addData("isSampleTrue", isSampleModeTrue);
    }

}