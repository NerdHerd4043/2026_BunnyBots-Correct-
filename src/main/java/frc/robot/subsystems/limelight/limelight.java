package frc.robot.subsystems.limelight;

import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class limelight extends SubsystemBase {

    public Command getTv() {
        return this.runOnce(() -> {
            System.out.println(LimelightHelpers.getTV("limelight-left"));
            LimelightHelpers.getLimelightNTStringArray(getSubsystem(), getName());

            if (LimelightHelpers.getRawFiducials("limelight-left").length != 0) {
                System.out.println(LimelightHelpers.getRawFiducials("limelight-left")[0].id);
            }

            System.out.println("It Works?");
        });
    }

    // public getAprilTags
}
