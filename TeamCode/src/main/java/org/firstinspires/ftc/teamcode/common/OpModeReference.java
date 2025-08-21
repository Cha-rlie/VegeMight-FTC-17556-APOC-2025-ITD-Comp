package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.common.subsystems.Intake;
import org.firstinspires.ftc.teamcode.common.subsystems.Lift;
import org.firstinspires.ftc.teamcode.common.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.common.subsystems.Outtake;
import org.firstinspires.ftc.teamcode.common.util.Globals;
import org.firstinspires.ftc.teamcode.common.util.UpdateAndPowerScheduler;

public class OpModeReference {
    private static OpModeReference instance = null;
    HardwareMap hardwareMap;
    GamepadEx gamePad1;
    GamepadEx gamePad2;
    Telemetry telemetry;
    public Globals globalsSubSystem;
    public UpdateAndPowerScheduler updateAndPowerScheduler;
    public DriveTrain driveTrainSubSystem;
    public Lift liftSubSystem;
    public Intake intakeSubSystem;
    public Outtake outtakeSubSystem;
    public Limelight limelightSubsystem;


    public static OpModeReference getInstance() {
        if (instance == null) instance = new OpModeReference();
        return instance;
    }

    public void initHardware(final HardwareMap hardwareMap, GamepadEx gamepad1, GamepadEx gamepad2, Telemetry telemetry, double xStart, double yStart, double headingStart) {
        this.hardwareMap = hardwareMap;
        this.gamePad1 = gamepad1;
        this.gamePad2 = gamepad2;
        this.telemetry = telemetry;

        globalsSubSystem = new Globals();
        updateAndPowerScheduler = new UpdateAndPowerScheduler();
        driveTrainSubSystem = new DriveTrain();
        liftSubSystem = new Lift();
        intakeSubSystem = new Intake();
        outtakeSubSystem = new Outtake();
        limelightSubsystem = new Limelight();
    }

    public void nullify() {
        instance = null;
    }

    public HardwareMap getHardwareMap() {
        return this.hardwareMap;
    }

    public GamepadEx getGamePad1() {
        return gamePad1;
    }

    public GamepadEx getGamePad2() {
        return gamePad2;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }
}
