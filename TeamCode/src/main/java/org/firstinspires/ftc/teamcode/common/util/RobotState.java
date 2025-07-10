package org.firstinspires.ftc.teamcode.common.util;

public enum RobotState {

    // New global states
    IDLE,
    REJECT,

    // New sample states
    DEPOSIT,
    HOVERAFTERGRAB,
    HOVERBEFOREGRAB,
    GRAB,

    // Backwards sample states
    BACKWARDHOVERBEFOREGRAB,
    BACKWARDGRAB,
    BACKWARDHOVERAFTERGRAB,
    BACKWARDSCORE,

    // New specimen scoring states
    DEPOSITSPECIMEN,
    SPECHOVER,
    SPECGRAB,

    // Park
    PARKNOASCENT,
    PARKASCENT

}
