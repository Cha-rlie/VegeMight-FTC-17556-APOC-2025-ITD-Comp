package org.firstinspires.ftc.teamcode.opModes.autoOp;


import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.OpModeReference;
import org.firstinspires.ftc.teamcode.common.util.RobotState;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous (name="Four_Sample_Autonomous", group="Sample")

public class Four_Sample_Autonomous extends CommandOpMode {
    // Initialise the PedroPathing Follower
    private boolean once = true;
    private Follower follower;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;
    private Timer pathTimer, actionTimer, opmodeTimer;

    // Initialise the poses
    private final Pose startPose = new Pose(9.000, 113, Math.toRadians(0));  // Starting position
    private final Pose scorePose = new Pose(14.7, 128.8, Math.toRadians(-45)); // Scoring position

    private final Pose pickup1Pose = new Pose(24, 121.5, Math.toRadians(0)); // First sample pickup
    private final Pose pickup2Pose = new Pose(24, 131.5, Math.toRadians(0)); // Second sample pickup
    private final Pose pickup3Pose = new Pose(24, 135, Math.toRadians(30)); // Third sample pickup

    private final Pose parkPose = new Pose(60, 95, Math.toRadians(90));    // Parking position
    private final Pose parkControlPose = new Pose(60, 120); // Control point for curved path

    // Declare paths and pathchains
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;


    public void buildPaths() {
        // Path for scoring preload
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());



        grabPickup1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 121.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 121.500, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 131.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 131.500, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 135.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(30))
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 135.000, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(-45))
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // Move from start to scoring position
                follower.followPath(scorePreload);
                setPathState(1);
                OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSIT)
                        .andThen(new WaitCommand(2000)).schedule();
                break;

            case 1: // Wait until the robot is near the scoring position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSITRELEASE)
                            .andThen(new WaitCommand(500))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.HOVERBEFOREGRAB))
                            .schedule();
                    follower.followPath(grabPickup1, true);
                    setPathState(2);
                }
                break;

            case 2: // Wait until the robot is near the first sample pickup position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRAB)
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRABCLOSE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .schedule();
                    follower.followPath(scorePickup1, true);
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSIT).schedule();
                    setPathState(3);
                }
                break;

            case 3: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSITRELEASE)
                            .andThen(new WaitCommand(500))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.HOVERBEFOREGRAB))
                            .schedule();
                    follower.followPath(grabPickup2, true);
                    setPathState(4);
                }
                break;

            case 4: // Wait until the robot is near the second sample pickup position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRAB)
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRABCLOSE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .schedule();
                    follower.followPath(scorePickup2, true);
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSIT).schedule();
                    setPathState(5);
                }
                break;

            case 5: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSITRELEASE)
                            .andThen(new WaitCommand(500))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.HOVERBEFOREGRAB))
                            .schedule();
                    follower.followPath(grabPickup3, true);
                    setPathState(6);
                }
                break;

            case 6: // Wait until the robot is near the third sample pickup position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRAB)
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.GRABCLOSE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .schedule();
                    follower.followPath(scorePickup3, true);
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSIT).schedule();
                    setPathState(7);
                }
                break;

            case 7: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.DEPOSITRELEASE)
                            .andThen(new WaitCommand(500))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.IDLE))
                            .andThen(new WaitCommand(100))
                            .andThen(OpModeReference.getInstance().globalsSubSystem.setRobotStateCommand(RobotState.PARKASCENT))
                            .schedule();
                    follower.followPath(park, true);
                    setPathState(8);
                }
                break;

            case 8: // Wait until the robot is near the parking position
                if (!follower.isBusy()) {
                    setPathState(-1); // End the autonomous routine
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


    @Override
    public void run() {
        if (once) {
            opmodeTimer.resetTimer();
            setPathState(0);
            once = false;
        }
        // These loop the movements of the robot
        CommandScheduler.getInstance().run();
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        OpModeReference.getInstance().initHardware(hardwareMap, new GamepadEx(gamepad1), new GamepadEx(gamepad2), telemetry, 0, 0 ,0);

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class); /* Check Later */
        follower.setStartingPose(startPose);
        buildPaths();
        CommandScheduler.getInstance().run();
    }
}
