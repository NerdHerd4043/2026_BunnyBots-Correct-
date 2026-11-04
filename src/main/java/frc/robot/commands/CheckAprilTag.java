package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.LimelightConstants;

public class CheckAprilTag extends Command {
    private final Limelight limeLight;
    private BooleanSupplier run;

    public CheckAprilTag(Limelight limeLight, BooleanSupplier run) {
        this.limeLight = limeLight;
        this.run = run;

        addRequirements(this.limeLight);
    }

    @Override
    public void execute() {
        // do we have mr. target
        if (LimelightHelpers.getTV("limelight-left") && run.getAsBoolean()) {
            // Disable normal turning
            Drive.disableNormalTurning();

            turnTowardAprilTag();
            moveTowardAprilTag();

        } else {
            // Enable normal turning
            Drive.enableNormalTurning();
        }

    }

    private void turnTowardAprilTag() {
        // angles are good yes :D
        if (LimelightHelpers.getTX("limelight-left") >= 0 + LimelightConstants.deadband) {
            // Rotate robot right
            System.out.println("right");
            Drive.setLimelightTurningSpeed(LimelightConstants.turningSpeed);
        } else if (LimelightHelpers.getTX("limelight-left") <= 0 - LimelightConstants.deadband) {
            // Rotate robot left
            System.out.println("left");
            Drive.setLimelightTurningSpeed(-LimelightConstants.turningSpeed);
        } else {
            // Stop rotating
            System.out.println("stop");
            Drive.setLimelightTurningSpeed(0.0);
        }
    }

    private void moveTowardAprilTag() {
        Pose3d pos = LimelightHelpers.getCameraPose3d_TargetSpace("limelight-left");

        if (true) {

        } else if (true) {

        } else (true) {

        }
    }
}
