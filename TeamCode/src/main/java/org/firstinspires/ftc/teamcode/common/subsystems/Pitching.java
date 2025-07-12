package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import java.util.HashMap;

public class Pitching extends SubsystemBase {
    public DcMotorEx pitchingMotor;
    private HashMap<RobotState, Integer> stateToValueMap;
    public int runToPos = 0;
    Globals globals;

    public Pitching() {
        CommandScheduler.getInstance().registerSubsystem(this);
        pitchingMotor = OpModeReference.getInstance().getHardwareMap().get(DcMotorEx.class, "P");
        //pitchingMotor = new MotorEx(OpModeReference.getInstance().getHardwareMap(), "P", Motor.GoBILDA.RPM_435);
        pitchingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitchingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pitchingMotor.setTargetPositionTolerance(50);
        pitchingMotor.setTargetPosition(0);
        pitchingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pitchingMotor.setPower(1);
        runToPos = 0;

        stateToValueMap = new HashMap<RobotState, Integer>() {{
            put(RobotState.INIT, 0);
            put(RobotState.IDLE, 0);
            put(RobotState.DEPOSIT, 0);
            put(RobotState.DEPOSITRELEASE, 0);
            put(RobotState.HOVERBEFOREGRAB, 830);
            put(RobotState.GRAB, 830);
            put(RobotState.HOVERAFTERGRAB, 830);
            put(RobotState.SPECHOVERBEFOREGRAB, 0);
            put(RobotState.SPECGRAB, 0);
            put(RobotState.DEPOSITSPECIMEN, 0);
            put(RobotState.PARKASCENT, 0);
            put(RobotState.PARKNOASCENT, 0);
        }};

        globals = OpModeReference.getInstance().globalsSubSystem;

        setDefaultCommand(new PerpetualCommand(turnPitching())); //Perpetual Command is very important, cause InstantCommands end right after they execute
    }

    @Override
    public void periodic() {
        //hardware.leftArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        //hardware.rightArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        OpModeReference.getInstance().getTelemetry().addData("Pitch Position", pitchingMotor.getCurrentPosition());
        OpModeReference.getInstance().getTelemetry().addData("Pitching RTP", runToPos);
        OpModeReference.getInstance().getTelemetry().addData("Pitching Power", pitchingMotor.getPower());
    }

    public RunCommand turnPitching() {
        return new RunCommand(() -> {
            if (globals.updateRobotStateTrue && !globals.pitchAcceptState) {
                if (stateToValueMap.containsKey(globals.getRobotState())) {
                    if (globals.getRobotState() == RobotState.IDLE) {
                        if (globals.lastRobotState == RobotState.GRAB || globals.lastRobotState == RobotState.HOVERBEFOREGRAB || globals.lastRobotState == RobotState.HOVERAFTERGRAB) {
                            runToPos = stateToValueMap.get(globals.getRobotState());
                        }
                    } else {
                        runToPos = stateToValueMap.get(globals.getRobotState());
                    }
                } else {
                    runToPos = stateToValueMap.get(RobotState.IDLE);
                }
                globals.pitchAcceptState = true;
            }
            pitchingMotor.setTargetPosition(runToPos);
            pitchingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //pitchingMotor.setPower(1);
            pitchingMotor.setPower(gradualCalculatedPower());
        }, this);
    }

    public InstantCommand adjustPitchingUp() {
        return new InstantCommand(()-> {
                    runToPos += 50;
                });

    }

    public InstantCommand adjustPitchingDown(){
        return new InstantCommand(()-> {
                    runToPos -= 50;
                });

    }

    private double gradualCalculatedPower() {
        double totalDistance = 830;
        double distanceLeft = globals.getRobotState() == RobotState.IDLE ? pitchingMotor.getCurrentPosition() : 830 - pitchingMotor.getCurrentPosition();
        return Math.min(Math.pow(((distanceLeft + 830*0.4)/830),8) + 0.15, 1);
    }



}