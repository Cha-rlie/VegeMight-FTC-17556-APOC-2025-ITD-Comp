package org.firstinspires.ftc.teamcode.common.subsystems;

import android.graphics.Path;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

public class Limelight extends SubsystemBase {
    private Limelight3A limelight;

    double limelightHeight = 17;
    double limelightAngle = 24.920;
    double sampleHeight = 3.5;
    Globals globals;
    public Limelight() {
        limelight = OpModeReference.getInstance().getHardwareMap().get(Limelight3A.class, "limelight");
    }

    public void init(){
        globals = OpModeReference.getInstance().globalsSubSystem;
        limelight.start();
    }

    public InstantCommand extendLimelight(){
        return new InstantCommand(()-> {
            LLResult result = limelight.getLatestResult();
            if (result != null) {
                if (result.isValid())/* && globals.getRobotState()== RobotState.HOVERBEFOREGRAB)*/ {
                    OpModeReference.getInstance().getTelemetry().addData("Req ext",(((limelightHeight-sampleHeight)/Math.tan(Math.toRadians(limelightAngle-result.getTy())))-15.625)/0.0425);
                    OpModeReference.getInstance().getTelemetry().addData("ty", result.getTy());
                    OpModeReference.getInstance().liftSubSystem.adjustment = (int) ((((limelightHeight-sampleHeight)/Math.tan(Math.toRadians(limelightAngle-result.getTy())))-15.625)/0.0425)-OpModeReference.getInstance().liftSubSystem.RTP;
                    OpModeReference.getInstance().getTelemetry().addData("Attempted extension",(int) ((((limelightHeight-sampleHeight)/Math.tan(Math.toRadians(limelightAngle-result.getTy())))-15.625)/0.0425)-OpModeReference.getInstance().liftSubSystem.RTP );
                } else {
                    OpModeReference.getInstance().getTelemetry().addLine("Invalid Result");
                }
            } else {
                OpModeReference.getInstance().getTelemetry().addLine("Null Result");
            }
        });
    }


}
