package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.RobotState;

@TeleOp(name="TELEOP")
public class FreshLibrary extends CommandOpMode {
    GamepadEx gamePad1;
    GamepadEx gamePad2;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        gamePad1 = new GamepadEx(gamepad1);
        gamePad2 = new GamepadEx(gamepad2);

        OpModeReference.getInstance().initHardware(hardwareMap, gamePad1, gamePad2, telemetry, 0, 0, 0);

        // TRIANGLE = Y
        // CIRCLE = B
        // CROSS = A
        // SQUARE = X

        // ---------------- BUTTON BINDINGS ------------------- //
        // State Machine
        gamePad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.forwardsRobotState());
        gamePad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.backwardsRobotState());
        gamePad2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.forwardsRobotState());
        gamePad2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.backwardsRobotState());
        gamePad1.getGamepadButton(GamepadKeys.Button.START).whenPressed(OpModeReference.getInstance().globalsSubSystem.goToIdle()); //same as OPTIONS
        gamePad2.getGamepadButton(GamepadKeys.Button.START).whenPressed(OpModeReference.getInstance().globalsSubSystem.goToIdle());

        // Manual Adjustments
        gamePad1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(OpModeReference.getInstance().limelightSubsystem.storeLimelightValue());

    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        //new InstantCommand(() -> OpModeReference.getInstance().driveTrainSubSystem.inputJoySticks(gamePad1.getLeftX(), gamePad1.getLeftY(), gamePad1.getRightX())).schedule();
        OpModeReference.getInstance().driveTrainSubSystem.driveRobotCentric(gamePad1.getLeftX(), gamePad1.getLeftY(), gamePad1.getRightX());
        telemetry.update();
    }
}
