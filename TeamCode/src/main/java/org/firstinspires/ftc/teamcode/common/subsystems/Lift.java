package org.firstinspires.ftc.teamcode.common.subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.onbotjava.OnBotJavaProgrammingMode;
import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.UpdateAndPowerScheduler;

public class Lift extends SubsystemBase {
    public DcMotorEx spoolR;
    public DcMotorEx spoolL;

    public int RTP;

    Globals globals;
    UpdateAndPowerScheduler updateAndPowerScheduler;

    public Lift() {
        spoolR.setDirection(DcMotorSimple.Direction.REVERSE);
        spoolR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spoolL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spoolR.setPower(1);
        spoolL.setPower(1);

        globals = OpModeReference.getInstance().globalsSubSystem;
        updateAndPowerScheduler = OpModeReference.getInstance().updateAndPowerScheduler;
        setDefaultCommand(new PerpetualCommand(defaultCommand()));
    }

    public RunCommand defaultCommand() {
        return new RunCommand(()->{
           if (updateAndPowerScheduler.liftUpdate) {
               if (updateAndPowerScheduler.powerLift){
                   spoolR.setMotorEnable();
                   spoolL.setMotorEnable();
               } else {
                   spoolR.setMotorDisable();
                   spoolL.setMotorDisable();
               }
               switch (globals.getRobotState()) {
                   case REJECT:
                   case INIT:
                   case IDLE:
                   case OUTTAKE:
                   case INTAKE:
                       spoolR.setPower(1);
                       spoolL.setPower(1);
                       RTP=0;
               }
           }
           spoolR.setTargetPosition(RTP);
           spoolL.setTargetPosition(RTP);
        });
    }

}
