package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.LimelightConstants;

public class CheckAprilTag extends Command {
    final Limelight limeLight;
    private BooleanSupplier run;
    private RobotContainer robotContainer;
    private Drivebase drivebase;

    public CheckAprilTag(Limelight limeLight, BooleanSupplier run, RobotContainer robotContainer, Drivebase drivebase) {
        this.limeLight = limeLight;
        this.run = run;
        this.robotContainer = robotContainer;
        this.drivebase = drivebase;

        addRequirements(this.limeLight);
    }

    @Override
    public void execute() {
        // do we have mr. target
        if (LimelightHelpers.getTV("limelight-left") && run.getAsBoolean()) {
            Pose3d pos = LimelightHelpers.getTargetPose3d_CameraSpace("limelight-left");

            drivebase.robotOrientedDrive(getXSpeed(pos), getYSpeed(pos), getTurningSpeed());

        }
    }

    private double getTurningSpeed() {
        // angles are good yes :D
        if (LimelightHelpers.getTX("limelight-left") >= 0 + LimelightConstants.turningDeadband) {
            // Rotate robot right
            return LimelightConstants.turningSpeed;

        } else if (LimelightHelpers.getTX("limelight-left") <= 0 - LimelightConstants.turningDeadband) {
            // Rotate robot left
            return -LimelightConstants.turningSpeed;

        } else {
            // Stop turning
            return 0.0;

        }
    }

    private double getXSpeed(Pose3d pos) {
        if (pos.getRotation().getY() > 0 + LimelightConstants.yMovingDeadband) {
            // Move the robot left
            return -LimelightConstants.movingSpeed;

        } else if (pos.getRotation().getY() < 0 - LimelightConstants.yMovingDeadband) {
            // Move the robot right
            return LimelightConstants.movingSpeed;

        }

        return 0.0;

    }

    private double getYSpeed(Pose3d pos) {
        if (pos.getX() > 0 + LimelightConstants.xMovingDeadband) {
            // Move the robot forword
            return LimelightConstants.movingSpeed;

        }

        return 0.0;

    }
}
