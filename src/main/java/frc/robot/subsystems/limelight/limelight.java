package frc.robot.subsystems.limelight;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limelight extends SubsystemBase {
    public boolean getTv() {
        return LimelightHelpers.getTV("limelight");
    }
}
