package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;
import org.firstinspires.ftc.teamcode.common.util.UpdateAndPowerScheduler;

import java.util.HashMap;

public class Intake extends SubsystemBase {
    Servo claw;
    Servo clawRot;
    Servo wristRot;
    Servo arm;

    public double clawAdjustment;
    public double clawRotAdjustment;
    public double wristRotAdjustment;
    public double armAdjustment;
    public boolean clawOpen = false;
    public boolean resetAdjustmentTrue=true;

    Globals globals;
    UpdateAndPowerScheduler updateAndPowerScheduler;
    public Intake() {
        globals = OpModeReference.getInstance().globalsSubSystem;
        updateAndPowerScheduler = OpModeReference.getInstance().updateAndPowerScheduler;
        setDefaultCommand(new PerpetualCommand(defaultCommand()));
    }

    @Override
    public void periodic() {
        OpModeReference.getInstance().getTelemetry().addData("clawPos", claw.getPosition());
    }

    public RunCommand defaultCommand() {
        return new RunCommand(()->{
            if (updateAndPowerScheduler.intakeUpdate) {
                if (!updateAndPowerScheduler.powerIntake){
                    claw.getController().pwmDisable();
                    clawRot.getController().pwmDisable();
                    wristRot.getController().pwmDisable();
                    arm.getController().pwmDisable();
                } else {
                    claw.getController().pwmEnable();
                    clawRot.getController().pwmEnable();
                    wristRot.getController().pwmEnable();
                    arm.getController().pwmEnable();
                }
                switch (globals.getRobotState()) { //Change to hashmap later? - maybe not
                    case IDLE:
                        if (resetAdjustmentTrue) {
                            clawAdjustment = 0;
                            clawRotAdjustment = 0;
                            wristRotAdjustment = 0;
                            armAdjustment = 0;
                        }
                        clawOpen=false;
                        clawRot.setPosition(0+clawRotAdjustment);
                        wristRot.setPosition(0+wristRotAdjustment);
                        arm.setPosition(0+armAdjustment);
                    case INIT:
                        if (resetAdjustmentTrue) {
                            clawAdjustment = 0;
                            clawRotAdjustment = 0;
                            wristRotAdjustment = 0;
                            armAdjustment = 0;
                        }
                        clawOpen=false;
                        clawRot.setPosition(0.1+clawRotAdjustment);
                        wristRot.setPosition(0.1+wristRotAdjustment);
                        arm.setPosition(0.1+armAdjustment);
                    case OUTTAKE:
                        clawOpen=false;
                        clawRot.setPosition(0.2+clawRotAdjustment);
                        wristRot.setPosition(0.2+wristRotAdjustment);
                        arm.setPosition(0.2+armAdjustment);
                    case INTAKE:
                        clawOpen=false;
                        clawRot.setPosition(0.3+clawRotAdjustment);
                        wristRot.setPosition(0.3+wristRotAdjustment);
                        arm.setPosition(0.3+armAdjustment);
                    case REJECT:
                        clawOpen=false;
                        clawRot.setPosition(0.4+clawRotAdjustment);
                        wristRot.setPosition(0.4+wristRotAdjustment);
                        arm.setPosition(0.4+armAdjustment);
                }
                updateAndPowerScheduler.intakeBusy=false;
                resetAdjustmentTrue=true;
                if (clawOpen) {
                    claw.setPosition(0);
                } else {
                    claw.setPosition(1);
                }
            }
        });
    }

    @NonNull
    public InstantCommand toggleClaw(){
        return new InstantCommand(()->{
            if (clawOpen) {
                clawOpen=false;
            } else {
                clawOpen = true;
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }

    @NonNull
    public InstantCommand adjustClawClockwise(){
        return new InstantCommand(()->{
            if (clawRotAdjustment+clawRot.getPosition()+0.25<1){
                clawRotAdjustment=clawRotAdjustment+0.25;
            } else {
                clawRotAdjustment = 1-clawRot.getPosition();
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }

    @NonNull
    public InstantCommand adjustClawCounterClockwise(){
        return new InstantCommand(()->{
            if (clawRotAdjustment+clawRot.getPosition()-0.25>0){
                clawRotAdjustment=clawRotAdjustment-0.25;
            } else {
                clawRotAdjustment=-clawRot.getPosition();
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }

    @NonNull
    public InstantCommand adjustWristClockwise() {
        return new InstantCommand(()->{
            if (wristRotAdjustment+wristRot.getPosition()+0.1<1) {
                wristRotAdjustment = wristRotAdjustment+0.1;
            } else {
                wristRotAdjustment = 1-wristRot.getPosition();
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }

    @NonNull
    public InstantCommand adjustWristCounterClockwise() {
        return new InstantCommand(()->{
            if (wristRotAdjustment+wristRot.getPosition()-0.1<0) {
                wristRotAdjustment = wristRotAdjustment-0.1;
            } else {
                wristRotAdjustment = -wristRot.getPosition();
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }
    @NonNull
    public InstantCommand adjustArmClockwise() {
        return new InstantCommand(()->{
            if (armAdjustment+arm.getPosition()+0.1<1) {
                armAdjustment = armAdjustment+0.1;
            } else {
                armAdjustment = 1-arm.getPosition();
            }
            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }

    @NonNull
    public InstantCommand adjustArmCounterClockwise() {
        return new InstantCommand(()->{
            if (armAdjustment+arm.getPosition()-0.1>0) {
                armAdjustment = armAdjustment-0.1;
            } else {
                armAdjustment = -arm.getPosition();
            }

            updateAndPowerScheduler.intakeUpdate=true;
            resetAdjustmentTrue=false;
        });
    }


}
