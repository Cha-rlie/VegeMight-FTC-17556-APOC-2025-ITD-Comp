package org.firstinspires.ftc.teamcode.opModes.autoOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

public class Five_Spec_Autonomous extends OpMode {
    private Follower follower;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;
    private Timer pathTimer, actionTimer, opmodeTimer;

    // Initialise the poses
    private final Pose startPose = new Pose(8.000, 65.000, Math.toRadians(0));  // Starting position

    // Declare paths and pathchains
    private PathChain depositOne, humanPlayerTwo, humanPlayerThree, humanPlayerFour, pickupTwo, depositTwo, pickupThree, depositThree, pickupFour, depositFour, pickupFive,depositFive,park;




    public void buildPaths() {
        // Path for scoring preload

        depositOne = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(8.000, 65.000, Point.CARTESIAN),
                                new Point(36.000, 65.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        humanPlayerTwo = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(36.000, 65.000, Point.CARTESIAN),
                                new Point(17.486, 1.029, Point.CARTESIAN),
                                new Point(88.457, 57.857, Point.CARTESIAN),
                                new Point(77.914, 21.086, Point.CARTESIAN),
                                new Point(68.914, 23.657, Point.CARTESIAN),
                                new Point(18.000, 25.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        humanPlayerThree = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(18.000, 25.000, Point.CARTESIAN),
                                new Point(112.629, 27.000, Point.CARTESIAN),
                                new Point(75.600, 12.343, Point.CARTESIAN),
                                new Point(20.000, 11.829, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        humanPlayerFour = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(20.000, 11.829, Point.CARTESIAN),
                                new Point(84.600, 15.943, Point.CARTESIAN),
                                new Point(78.429, 2.571, Point.CARTESIAN),
                                new Point(20.000, 8.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pickupTwo = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(20.000, 8.000, Point.CARTESIAN),
                                new Point(8.500, 30.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        depositTwo = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(8.500, 30.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(35.000, 72.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pickupThree = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(35.000, 72.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(8.500, 30.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        depositThree = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(8.500, 30.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(35.000, 72.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pickupFour = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(35.000, 72.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(8.500, 30.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        depositFour = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(8.500, 30.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(35.000, 72.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        pickupFive = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(35.000, 72.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(8.500, 30.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        depositFive = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(8.500, 30.000, Point.CARTESIAN),
                                new Point(9.000, 72.000, Point.CARTESIAN),
                                new Point(35.000, 72.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        park = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Point(35.000, 72.000, Point.CARTESIAN),
                                new Point(2.057, 10.543, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();



    }
    /*Final Scores can possibly be same paths*/
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0: // preload score
                follower.followPath(depositOne);
                setPathState(1);
                break;

            case 1: // HP2
                if (!follower.isBusy()) {
                    follower.followPath(humanPlayerTwo, true);
                    setPathState(2);
                }
                break;

            case 2: // HP3
                if (!follower.isBusy()) {
                    follower.followPath(humanPlayerThree, true);
                    setPathState(3);
                }
                break;

            case 3: // HP4
                if (!follower.isBusy()) {
                    follower.followPath(humanPlayerFour, true);
                    setPathState(4);
                }
                break;

            case 4: // Pickup Spec 2
                if (!follower.isBusy()) {
                    follower.followPath(pickupTwo, true);
                    setPathState(5);
                }
                break;

            case 5: // Score
                if (!follower.isBusy()) {
                    follower.followPath(depositTwo, true);
                    setPathState(6);
                }
                break;

            case 6: // Spec 3
                if (!follower.isBusy()) {
                    follower.followPath(pickupThree, true);
                    setPathState(7);
                }
                break;

            case 7: // Score
                if (!follower.isBusy()) {
                    follower.followPath(depositThree, true);
                    setPathState(8);
                }
                break;

            case 8: //Spec 4
                if (!follower.isBusy()) {
                    follower.followPath(pickupFour, true);
                    setPathState(9); // End the autonomous routine
                }
                break;
            case 9: // Score
                if (!follower.isBusy()){
                    follower.followPath(depositFour,true);
                    setPathState(10);
                }
            case 10: // Spec 5
                if (!follower.isBusy()){
                    follower.followPath(pickupFive, true);
                    setPathState(11);
                }
            case 11: // Score
                if(!follower.isBusy()){
                    follower.followPath(depositFive, true);
                    setPathState(12);
                }
            case 12: //Park
                if(!follower.isBusy()){
                    follower.followPath(park, true);
                    setPathState(13);
                }
            case 13:
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
