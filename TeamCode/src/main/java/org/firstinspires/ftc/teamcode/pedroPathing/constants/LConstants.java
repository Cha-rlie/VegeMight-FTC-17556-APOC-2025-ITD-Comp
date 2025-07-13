package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = (0.0019899880089877093+0.0019981202792547018+0.00197695001562371)/3;
        ThreeWheelConstants.strafeTicksToInches = (0.0020335514181436167+0.002034232009972923+0.0020171074447580526+0.0020236840538007815)/4;
        ThreeWheelConstants.turnTicksToInches = 0.001801027701274487;
        ThreeWheelConstants.leftY = 5.25;
        ThreeWheelConstants.rightY = -5.25;
        ThreeWheelConstants.strafeX = -7.9;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "FR";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "BL";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "FL";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.FORWARD;
    }
}



