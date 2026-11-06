package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.LimelightConstants;

public class CheckAprilTag extends Command {
    private final Limelight limeLight;
    private BooleanSupplier run;
    private RobotContainer robotContainer;

    public CheckAprilTag(Limelight limeLight, BooleanSupplier run, RobotContainer robotContainer) {
        this.limeLight = limeLight;
        this.run = run;
        this.robotContainer = robotContainer;

        addRequirements(this.limeLight);
    }

    @Override
    public void execute() {
        // do we have mr. target
        if (LimelightHelpers.getTV("limelight-left") && run.getAsBoolean()) {
            // Disable normal turning
            Drive.disableNormalTurning();

            // Disable normal moving
            Drive.disableNormalMoving();

            turnTowardAprilTag();
            moveTowardAprilTag();

        } else {
            // Enable normal turning
            Drive.enableNormalTurning();

            // Enable normal moving
            Drive.enableNormalMoving();
        }

    }

    private void turnTowardAprilTag() {
        // angles are good yes :D
        if (LimelightHelpers.getTX("limelight-left") >= 0 + LimelightConstants.turningDeadband) {
            // Rotate robot right
            Drive.setLimelightTurningSpeed(LimelightConstants.turningSpeed);

        } else if (LimelightHelpers.getTX("limelight-left") <= 0 - LimelightConstants.turningDeadband) {
            // Rotate robot left
            Drive.setLimelightTurningSpeed(-LimelightConstants.turningSpeed);

        } else {
            // Stop turning
            Drive.setLimelightTurningSpeed(0.0);

        }
    }

    private void moveTowardAprilTag() {
        Pose3d pos = LimelightHelpers.getTargetPose3d_CameraSpace("limelight-left");

        double[] XYSpeed = { 0.0, 0.0 };

        if (pos.getRotation().getY() > 0 + LimelightConstants.yMovingDeadband) {
            // Move the robot left
            System.out.println("Move Left");
            if (robotContainer.getFieldAngle() + 90 < 0) {
                XYSpeed[0] = -LimelightConstants.movingSpeed;
            } else {
                XYSpeed[0] = LimelightConstants.movingSpeed;
            }

        } else if (pos.getRotation().getY() < 0 - LimelightConstants.yMovingDeadband) {
            // Move the robot right
            System.out.println("Move Right");
            if (robotContainer.getFieldAngle() + 90 < 0) {
                XYSpeed[0] = LimelightConstants.movingSpeed;
            } else {
                XYSpeed[0] = -LimelightConstants.movingSpeed;
            }

        } else {
            XYSpeed[0] = 0.0;
        }

        if (pos.getX() > 0) {
            // Move the robot forword
            System.out.println("Move Forward");
            XYSpeed[1] = LimelightConstants.movingSpeed;

        } else {
            XYSpeed[1] = 0.0;
        }

        Drive.setLimelightMovingSpeed(XYSpeed);

    }
}
