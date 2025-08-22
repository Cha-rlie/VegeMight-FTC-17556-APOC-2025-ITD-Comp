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

    Globals globals;
    UpdateAndPowerScheduler updateAndPowerScheduler;

    public Intake() {


        globals = OpModeReference.getInstance().globalsSubSystem;
        updateAndPowerScheduler = OpModeReference.getInstance().updateAndPowerScheduler;
        setDefaultCommand(new PerpetualCommand(defaultCommand()));
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
                        claw.setPosition(0);
                        clawRot.setPosition(0);
                        wristRot.setPosition(0);
                        arm.setPosition(0);
                    case INIT:
                        claw.setPosition(0.1);
                        clawRot.setPosition(0.1);
                        wristRot.setPosition(0.1);
                        arm.setPosition(0.1);
                    case OUTTAKE:
                        claw.setPosition(0.2);
                        clawRot.setPosition(0.2);
                        wristRot.setPosition(0.2);
                        arm.setPosition(0.2);
                    case INTAKE:
                        claw.setPosition(0.3);
                        clawRot.setPosition(0.3);
                        wristRot.setPosition(0.3);
                        arm.setPosition(0.3);
                    case REJECT:
                        claw.setPosition(0.4);
                        clawRot.setPosition(0.4);
                        wristRot.setPosition(0.4);
                        arm.setPosition(0.4);
                }
            }
        });
    }
}
