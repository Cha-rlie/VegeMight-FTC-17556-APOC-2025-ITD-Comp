package org.firstinspires.ftc.teamcode.common.subsystems;

import com.arcrobotics.ftclib.command.InstantCommand;
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

    double requiredTotalExtension=250;
    double possibleadjustmentValue =0;
    int storedadjustmentValue=0;

    Globals globals;
    public Limelight() {
        limelight = OpModeReference.getInstance().getHardwareMap().get(Limelight3A.class, "limelight");
    }

    public void init(){
        globals = OpModeReference.getInstance().globalsSubSystem;
        limelight.start();
    }

    public InstantCommand storeLimelightValue(){
        return new InstantCommand(()-> {
            LLResult result = limelight.getLatestResult();
            requiredTotalExtension= (int) ((((limelightHeight-sampleHeight)/Math.tan(Math.toRadians(limelightAngle-result.getTy())))-15.625)/0.0425);
            possibleadjustmentValue = (int) requiredTotalExtension-OpModeReference.getInstance().liftSubSystem.RTP;
            if (result != null) {
                if (result.isValid() && requiredTotalExtension <800){
                    OpModeReference.getInstance().getTelemetry().addData("Req ext",requiredTotalExtension);
                    OpModeReference.getInstance().getTelemetry().addData("ty", result.getTy());
                    storedadjustmentValue = (int) possibleadjustmentValue;
                    OpModeReference.getInstance().getTelemetry().addData("Attempted extension", possibleadjustmentValue);
                } else if (result.isValid()){
                    storedadjustmentValue = 800-OpModeReference.getInstance().liftSubSystem.RTP;
                    OpModeReference.getInstance().getTelemetry().addData("Attempted extension", possibleadjustmentValue);
                } else {
                    OpModeReference.getInstance().getTelemetry().addLine("Invalid Result");
                }
            } else {
                OpModeReference.getInstance().getTelemetry().addLine("Null Result");
            }
        });
    }

    public InstantCommand extensionLimelight(){
        return new InstantCommand(()-> {
            if (globals.getRobotState() == RobotState.HOVERBEFOREGRAB) {
                OpModeReference.getInstance().liftSubSystem.adjustment = storedadjustmentValue;
            }
        });
    }




}
