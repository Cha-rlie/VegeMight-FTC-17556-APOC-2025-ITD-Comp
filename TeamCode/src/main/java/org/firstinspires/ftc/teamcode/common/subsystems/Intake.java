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
        });
    }
}
