package org.firstinspires.ftc.teamcode.common.util;

public enum RobotState {

    // New global states
    INIT,
    IDLE,
    REJECT,

    // New sample states
    DEPOSIT,
    DEPOSITRELEASE,
    HOVERAFTERGRAB,
    HOVERBEFOREGRAB,
    GRAB,
    GRABCLOSE,

    // Backwards sample states
    BACKWARDHOVERBEFOREGRAB,
    BACKWARDGRAB,
    BACKWARDHOVERAFTERGRAB,
    BACKWARDSCORE,

    // New specimen scoring states
    DEPOSITSPECIMEN,
    DEPOSITSPECIMENRELEASE,
    SPECHOVERBEFOREGRAB,
    SPECGRAB,

    // Park
    PARKNOASCENT,
    PARKASCENT

}
