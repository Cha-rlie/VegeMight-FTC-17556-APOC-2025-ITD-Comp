//package org.firstinspires.ftc.teamcode.pedroPathing;
//
//import com.arcrobotics.ftclib.command.CommandBase;
//import com.arcrobotics.ftclib.command.Subsystem;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.common.OpModeReference;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class RRCommand extends CommandBase {
//    // Wrapper based off guide from RR Docs
//    // https://rr.brott.dev/docs/v1-0/guides/ftclib-commands/
//
//    private final OpModeReference opModeReference = OpModeReference.getInstance();
//    private boolean isCommandFinished = false;
//    private final Action action;
//    private Set<Subsystem> requirements = new HashSet<>();
//
//    public RRCommand(Action action) {
//        this.action = action;
//    }
//
//    @Override
//    public Set<Subsystem> getRequirements() {
//        return requirements;
//    }
//
//    @Override
//    public void initialize() {
//
//    }
//
//    @Override
//    public void execute() {
//        FtcDashboard dashboard = FtcDashboard.getInstance();
//        TelemetryPacket packet = new TelemetryPacket();
//        action.preview(packet.fieldOverlay());
//        isCommandFinished = !action.run(new TelemetryPacket());
//        dashboard.sendTelemetryPacket(packet);
//    }
//
//    @Override
//    public boolean isFinished() {
//        return isCommandFinished;
//    }
//
//}