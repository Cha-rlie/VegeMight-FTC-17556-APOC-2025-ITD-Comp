package org.firstinspires.ftc.teamcode.opModes.teleOp;

import android.graphics.Path;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.Globals;

@TeleOp(name="TELEOP")
public class FreshLiberty extends CommandOpMode {
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

        // ---------------- BUTTON BINDINGS -------------------
        // State Machine
        gamePad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.forwardsRobotState());
        gamePad1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(OpModeReference.getInstance().globalsSubSystem.backwardsRobotState());
        gamePad1.getGamepadButton(GamepadKeys.Button.START).whenPressed(OpModeReference.getInstance().globalsSubSystem.goToIdle()); //same as OPTIONS
        gamePad2.getGamepadButton(GamepadKeys.Button.START).whenPressed(OpModeReference.getInstance().globalsSubSystem.goToIdle());

        // Manual Adjustments
        gamePad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(OpModeReference.getInstance().liftSubSystem.adjustUp());
        gamePad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(OpModeReference.getInstance().liftSubSystem.adjustDown());
        gamePad2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(OpModeReference.getInstance().liftSubSystem.adjustUp());
        gamePad2.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(OpModeReference.getInstance().liftSubSystem.adjustDown());

        gamePad1.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(OpModeReference.getInstance().intakeSubSystem.toggleClaw());
        gamePad2.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(OpModeReference.getInstance().intakeSubSystem.toggleClaw());

        gamePad1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(OpModeReference.getInstance().armSubSystem.adjustArmUp());
        gamePad1.getGamepadButton(GamepadKeys.Button.A).whenPressed(OpModeReference.getInstance().armSubSystem.adjustArmDown());

        gamePad2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustWristUp());
        gamePad2.getGamepadButton(GamepadKeys.Button.A).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustWristDown());

        gamePad1.getGamepadButton(GamepadKeys.Button.X).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustRotLeft());
        gamePad1.getGamepadButton(GamepadKeys.Button.B).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustRotRight());
        gamePad2.getGamepadButton(GamepadKeys.Button.X).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustRotLeft());
        gamePad2.getGamepadButton(GamepadKeys.Button.B).whenPressed(OpModeReference.getInstance().intakeSubSystem.adjustRotRight());

    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        //new InstantCommand(() -> OpModeReference.getInstance().driveTrainSubSystem.inputJoySticks(gamePad1.getLeftX(), gamePad1.getLeftY(), gamePad1.getRightX())).schedule();
        OpModeReference.getInstance().driveTrainSubSystem.driveRobotCentric(gamePad1.getLeftX(), gamePad1.getLeftY(), gamePad1.getRightX());
        telemetry.update();
    }
}
