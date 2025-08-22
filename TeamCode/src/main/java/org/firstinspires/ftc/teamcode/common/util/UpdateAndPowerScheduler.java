package org.firstinspires.ftc.teamcode.common.util;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

import org.firstinspires.ftc.teamcode.common.OpModeReference;

public class UpdateAndPowerScheduler extends SubsystemBase {
    public boolean powerIntake = false;
    public boolean powerOuttake = false;
    public boolean powerLift = false;

    public boolean intakeUpdate = false;
    public boolean outtakeUpdate = false;
    public boolean liftUpdate= false;

    public boolean intakeBusy = false;
    public boolean outtakeBusy = false;
    public boolean liftBusy = false;

    public boolean robotBusy = false;

    Globals globals;

    public UpdateAndPowerScheduler() {
        globals = OpModeReference.getInstance().globalsSubSystem;
    }

    public RunCommand updatePower() {
        return new RunCommand(() -> {
            switch (globals.getRobotState()) {
                case IDLE:
                    powerIntake = false;
                    powerOuttake = false;
                case REJECT:
                case INIT:
                    powerOuttake = true;
                    powerIntake = true;
                case OUTTAKE:
                    powerIntake = false;
                    powerOuttake = true;
                case INTAKE:
                    powerIntake = true;
                    powerOuttake = false;
            }
            if (intakeBusy && outtakeBusy && liftBusy) {
                robotBusy=true;
            } else {
                robotBusy=false;
            }
        });
    }

    public InstantCommand intakeUpdate(){
        return new InstantCommand(()->{
           intakeUpdate=true;
        });
    }

    public InstantCommand outtakeUpdate(){
        return new InstantCommand(()->{
           outtakeUpdate=true;
        });
    }

    public InstantCommand liftUpdate(){
        return new InstantCommand(()->{
           liftUpdate = true;
        });
    }

}
