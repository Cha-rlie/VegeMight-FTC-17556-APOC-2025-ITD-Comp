package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

public class Flag extends SubsystemBase {
    Servo flag;
    Globals globals;

    public Flag() {
        CommandScheduler.getInstance().registerSubsystem(this);
        flag = OpModeReference.getInstance().getHardwareMap().get(Servo.class, "F");

        globals = OpModeReference.getInstance().globalsSubSystem;

        setDefaultCommand(new PerpetualCommand(whenParkFlagExtendo()));
    }

    @Override
    public void periodic() {
        //hardware.leftArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        //hardware.rightArm.setPosition(Math.min(armMiniTargetPosition, armTargetPosition));
        OpModeReference.getInstance().getTelemetry().addData("Flag Pos", flag.getPosition());
    }


    public RunCommand whenParkFlagExtendo() {
        return new RunCommand(() -> {
            if(globals.getRobotState() == RobotState.PARKNOASCENT) {
                flag.setPosition(0);
            } else {
                flag.setPosition(0.55);
            }
        }, this);
    }

    public InstantCommand storeFlag() {
        return new InstantCommand(()->{
            flag.setPosition(0);
        });
    }
}
