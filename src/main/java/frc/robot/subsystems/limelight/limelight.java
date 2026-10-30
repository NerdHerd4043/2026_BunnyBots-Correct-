package frc.robot.subsystems.limelight;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.limelight.LimelightConstants;

public class Limelight extends SubsystemBase {
    public Command getTv() {
        return this.runOnce(() -> {
            // do we have mr. target
            if (LimelightHelpers.getTV("limelight-left")) {
                // angles are good yes :D
                if (LimelightHelpers.getTX("limelight-left") >= 0) {
                    // Rotate robot right
                    System.out.println("right");
                } else if (LimelightHelpers.getTX("limelight-left") <= 0) {
                    // Rotate robot left
                    System.out.println("left");
                } else {
                    // Stop rotating
                }

            }

        });
    }
}