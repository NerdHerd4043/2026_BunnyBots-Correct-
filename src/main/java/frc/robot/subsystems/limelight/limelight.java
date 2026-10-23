package frc.robot.subsystems.limelight;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limelight extends SubsystemBase {
    public Command getTv() {
        return this.runOnce(() -> {LimelightHelpers.getTV("limelight");});
    }
}
