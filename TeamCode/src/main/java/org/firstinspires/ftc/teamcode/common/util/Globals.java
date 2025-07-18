package org.firstinspires.ftc.teamcode.common.util;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

import java.util.HashMap;


public class Globals extends SubsystemBase {

    public static final Globals INSTANCE = new Globals();
    public static boolean isSampleModeTrue = true;
    public static boolean isBackwardsMode = false;
    public boolean updateRobotStateTrue = true;
    public boolean backwardsMode = false; // Maybe? For now, not used
    public boolean isLiftDown = false;
    public boolean liftAcceptState = false;
    public boolean pitchAcceptState = false;
    public boolean armAcceptState = false;
    public boolean intakeAcceptState = false;
    private boolean notPressedBefore;

    // Declare the global variables
    private RobotState robotState;
    public RobotState lastRobotState;

    private HashMap <RobotState, RobotState> goForwardStateValuesOnly;
    private HashMap <RobotState, RobotState> goBackwardStateValuesOnly;
    private HashMap <RobotState, RobotState> goForwardStateForBackwardModeValuesOnly;
    private HashMap <RobotState, RobotState> goBackwardStateForBackwardModeValuesOnly;
    private HashMap <RobotState, RobotState> goForwardStateValuesOnlyForSpec;
    private HashMap <RobotState, RobotState> goBackwardStateValuesOnlyForSpec;

    // Constructor that builds the drivetrain subsystem class and Hashmaps :D
    public Globals() {
        isSampleModeTrue = true;
        notPressedBefore = true;
        goForwardStateValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.DEPOSIT);
            put(RobotState.REJECT, RobotState.HOVERBEFOREGRAB);
            put(RobotState.DEPOSIT, RobotState.DEPOSITRELEASE);
            put(RobotState.DEPOSITRELEASE, RobotState.IDLE);
            put(RobotState.HOVERAFTERGRAB, RobotState.IDLE);
            put(RobotState.HOVERBEFOREGRAB, RobotState.GRAB);
            put(RobotState.GRAB, RobotState.GRABCLOSE);
            put(RobotState.GRABCLOSE, RobotState.HOVERAFTERGRAB);
        }};
        goBackwardStateValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.HOVERBEFOREGRAB);
            put(RobotState.DEPOSIT, RobotState.IDLE);
            put(RobotState.DEPOSITRELEASE, RobotState.IDLE);
            put(RobotState.HOVERAFTERGRAB, RobotState.GRAB);
            put(RobotState.HOVERBEFOREGRAB, RobotState.IDLE);
            put(RobotState.GRAB, RobotState.HOVERBEFOREGRAB);
            put(RobotState.GRABCLOSE, RobotState.HOVERBEFOREGRAB);
        }};
        // For Specimen Mode
        goForwardStateValuesOnlyForSpec = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.DEPOSITSPECIMEN);
            put(RobotState.DEPOSITSPECIMEN, RobotState.DEPOSITSPECIMENRELEASE);
            put(RobotState.DEPOSITSPECIMENRELEASE, RobotState.IDLE);
            put(RobotState.SPECHOVERBEFOREGRAB, RobotState.SPECGRAB);
            put(RobotState.SPECGRAB, RobotState.IDLE);
        }};
        goBackwardStateValuesOnlyForSpec = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.SPECHOVERBEFOREGRAB);
            put(RobotState.DEPOSITSPECIMEN, RobotState.IDLE);
            put(RobotState.DEPOSITSPECIMENRELEASE, RobotState.IDLE);
            put(RobotState.SPECHOVERBEFOREGRAB, RobotState.IDLE);
            put(RobotState.SPECGRAB, RobotState.SPECHOVERBEFOREGRAB);
        }};
        // For Backwards Mode
        goForwardStateForBackwardModeValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.BACKWARDSCORE);
            put(RobotState.BACKWARDSCORE, RobotState.IDLE);
            put(RobotState.BACKWARDHOVERBEFOREGRAB, RobotState.BACKWARDGRAB);
            put(RobotState.BACKWARDGRAB, RobotState.BACKWARDHOVERAFTERGRAB);
            put(RobotState.BACKWARDHOVERAFTERGRAB, RobotState.IDLE);
        }};
        goBackwardStateForBackwardModeValuesOnly = new HashMap<RobotState, RobotState>() {{
            put(RobotState.IDLE, RobotState.BACKWARDHOVERBEFOREGRAB);
            put(RobotState.BACKWARDSCORE, RobotState.IDLE);
            put(RobotState.BACKWARDHOVERBEFOREGRAB, RobotState.IDLE);
            put(RobotState.BACKWARDGRAB, RobotState.BACKWARDHOVERBEFOREGRAB);
            put(RobotState.BACKWARDHOVERAFTERGRAB, RobotState.BACKWARDGRAB);
        }};

        robotState = RobotState.IDLE;
        lastRobotState = RobotState.IDLE;
        backwardsMode = false;
        resetAllSubsystemAcceptance();

    }

    public RobotState getRobotState() {
        return robotState;
    }

    @NonNull
    public void setRobotState(RobotState newRobotState) {
        lastRobotState = robotState;
        robotState = newRobotState;
        updateRobotStateTrue = true;
        resetAllSubsystemAcceptance();
    }

    public InstantCommand setRobotStateCommand(RobotState newRobotState) {
        return new InstantCommand(()-> {
            lastRobotState = robotState;
            robotState = newRobotState;
            updateRobotStateTrue = true;
            resetAllSubsystemAcceptance();
        });
    }

    @NonNull
    public InstantCommand toggleBackwardsMode() {
        return new InstantCommand(() -> {
            if (robotState == RobotState.IDLE) {
                backwardsMode = !backwardsMode;
            }
        });
    }

    @NonNull
    public InstantCommand park() {
        return new InstantCommand(()->{
            updateRobotStateTrue=true;
            resetAllSubsystemAcceptance();
            lastRobotState=robotState;
            robotState=RobotState.PARKNOASCENT;
        });
    }

    @NonNull
    public InstantCommand forwardsRobotState() {
        return new InstantCommand(()-> {
                            updateRobotStateTrue = true;
                            resetAllSubsystemAcceptance();
                            lastRobotState = robotState;
                            if (!backwardsMode) {
                                robotState = isSampleModeTrue ? goForwardStateValuesOnly.get(getRobotState()) : goForwardStateValuesOnlyForSpec.get(getRobotState());
                            } else {robotState = goForwardStateForBackwardModeValuesOnly.get(getRobotState());}
                        }
                    /*getTelemetry().addLine("FORWARDS!!!!!!!!!!!!!!");
                            updateRobotStateTrue=true;
                            if (Globals.updateRobotStateTrue) {
                                new Lambda("Immmmm going forwards").addExecute(() -> goForwardState.get(Globals.getRobotState()));
                            }
                        }*/
                );
    }

    @NonNull
    public InstantCommand backwardsRobotState() {
        return new InstantCommand(()-> {
                            updateRobotStateTrue = true;
                            resetAllSubsystemAcceptance();
                            lastRobotState = robotState;
                            if (!backwardsMode) {
                                robotState = isSampleModeTrue ? goBackwardStateValuesOnly.get(getRobotState()) : goBackwardStateValuesOnlyForSpec.get(getRobotState());
                            } else {
                                robotState = goBackwardStateForBackwardModeValuesOnly.get(getRobotState());
                            }
                        }
                    /*getTelemetry().addLine("BACKWARDS!!!!!!!!!!!!!!");
                            updateRobotStateTrue=true;
                            if (Globals.updateRobotStateTrue) {
                                new Lambda("Immmmm going back").addExecute(() -> goBackwardState.get(Globals.getRobotState()));
                            }
                        }*/
                );
    }

    @NonNull
    public InstantCommand goToIdle() {
        return new InstantCommand(()-> {
                    if (robotState != RobotState.IDLE) {
                        lastRobotState = robotState;
                        robotState = RobotState.IDLE;
                        updateRobotStateTrue = true;
                        resetAllSubsystemAcceptance();
                    }
                });
    }

    public InstantCommand goToInit() {
        return new InstantCommand(()->{
            lastRobotState = RobotState.INIT;
            robotState = RobotState.INIT;
            updateRobotStateTrue = true;
            resetAllSubsystemAcceptance();
        });
    }

    public InstantCommand toggleSampleSpecimenModes(double triggerValue) {
        return new InstantCommand(() -> {
           if (triggerValue > 0.5) {
               if (notPressedBefore == true) {
                   if (robotState == RobotState.IDLE) {
                       isSampleModeTrue = !isSampleModeTrue;
                   } else if (robotState == RobotState.DEPOSITRELEASE) {
                       goToIdle().schedule();
                       isSampleModeTrue = !isSampleModeTrue;
                   }
                   notPressedBefore = false;
               }
            } else {
               if (triggerValue < 0.1) {
                   notPressedBefore = true;
               }
           }
        });
    }

    @NonNull
    public InstantCommand reject() {
        return new InstantCommand(()->{
                    // Go to idle?
                    robotState= RobotState.REJECT;
                });
    }

    @NonNull
    public InstantCommand parkAscent() {
        return new InstantCommand(()->{
                    // Go to idle?
                    robotState = RobotState.PARKASCENT;
                });
    }

    @NonNull
    public InstantCommand parkNoAscent(double rightTrigger){
        return new InstantCommand(()->{
                    if (rightTrigger > 0.5) {
                        robotState = RobotState.PARKNOASCENT;
                    }
                });
    }

    public InstantCommand changeState(){
        return new InstantCommand(()-> {
                    isSampleModeTrue = !isSampleModeTrue;
                });
    }

    @Override
    public void periodic() {
        if (allStatesBeenChanged()) {updateRobotStateTrue = false;}
        OpModeReference.getInstance().getTelemetry().addData("Robot State", robotState);
        OpModeReference.getInstance().getTelemetry().addData("Update Robot State", updateRobotStateTrue);
        OpModeReference.getInstance().getTelemetry().addData("Lift Accepted New State?", liftAcceptState);
        OpModeReference.getInstance().getTelemetry().addData("Arm Accepted New State?", armAcceptState);
        OpModeReference.getInstance().getTelemetry().addData("isSampleTrue", isSampleModeTrue);
        OpModeReference.getInstance().getTelemetry().addData("backwardMode", backwardsMode);
    }

    private void resetAllSubsystemAcceptance() {
        liftAcceptState = false;
        pitchAcceptState = false;
        armAcceptState = false;
        intakeAcceptState = false;
    }
    private boolean allStatesBeenChanged() {
        return liftAcceptState && pitchAcceptState && armAcceptState && intakeAcceptState;
    }

}