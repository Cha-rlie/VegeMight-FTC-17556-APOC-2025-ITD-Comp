package org.firstinspires.ftc.teamcode.opModes.autoOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous (name="Five Sample Autonomous Blue 🟦", group="Samples")

public class Five_Sample_Autonomous_Blue extends OpMode {
    // Initialise the PedroPathing Follower
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
    private PathChain pickup1, pickup2, pickup3, deposit1, deposit2, deposit3, sub4, deposit4;


    public void buildPaths() {
        // Path for scoring preload
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());



        pickup1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 121.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        deposit1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 121.500, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        pickup2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 131.500, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0))
                .build();

        deposit2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 131.500, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
                .build();

        pickup3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.700, 128.800, Point.CARTESIAN),
                                new Point(24.000, 135.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(30))
                .build();

        deposit3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(24.000, 135.000, Point.CARTESIAN),
                                new Point(14.700, 128.800, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(-45))
                .build();
        sub4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(14.7,128.8,Point.CARTESIAN),
                                new Point(72,96, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45),Math.toRadians(-90))
                .build();
        deposit4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(72,96,Point.CARTESIAN),
                                new Point(24, 135, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // Move from start to scoring position
                follower.followPath(scorePreload);
                setPathState(1);
                break;

            case 1: // Wait until the robot is near the scoring position
                if (!follower.isBusy()) {
                    follower.followPath(pickup1, true);
                    setPathState(2);
                }
                break;

            case 2: // Wait until the robot is near the first sample pickup position
                if (!follower.isBusy()) {
                    follower.followPath(deposit1, true);
                    setPathState(3);
                }
                break;

            case 3: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    follower.followPath(pickup2, true);
                    setPathState(4);
                }
                break;

            case 4: // Wait until the robot is near the second sample pickup position
                if (!follower.isBusy()) {
                    follower.followPath(deposit2, true);
                    setPathState(5);
                }
                break;

            case 5: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    follower.followPath(pickup3, true);
                    setPathState(6);
                }
                break;

            case 6: // Wait until the robot is near the third sample pickup position
                if (!follower.isBusy()) {
                    follower.followPath(deposit3, true);
                    setPathState(7);
                }
                break;

            case 7: // Wait until the robot returns to the scoring position
                if (!follower.isBusy()) {
                    follower.followPath(sub4, true);
                    setPathState(8);
                }
                break;

            case 8: // Wait until the robot is near the parking position
                if (!follower.isBusy()) {
                    follower.followPath(deposit4,true);
                    setPathState(9); // End the autonomous routine
                }
                break;
            case 9:
                if(!follower.isBusy()){
                    follower.followPath(park,true);
                    setPathState(10);
                }
            case 10:
                if(!follower.isBusy()){
                    setPathState(-1);
                }
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


    @Override
    public void loop() {

        // These loop the movements of the robot
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
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class); /* Check Later */
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
}
