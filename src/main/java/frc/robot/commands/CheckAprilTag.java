package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.limelight.Limelight;
import frc.robot.subsystems.limelight.LimelightConstants;

public class CheckAprilTag extends Command {
    final Limelight limeLight;
    private Drivebase drivebase;

    /*
     * This is the PID loop array
     * 
     * Index 0 is for rotation control
     * Index 1 is for left and right movment (X) control
     * Index 2 is for forward movment (Y) control
     */
    private PIDController[] PIDArray = {
            new PIDController(LimelightConstants.RotkP, LimelightConstants.RotkI, LimelightConstants.RotkD),
            new PIDController(LimelightConstants.XkP, LimelightConstants.XkI, LimelightConstants.XkD),
            new PIDController(LimelightConstants.YkP, LimelightConstants.YkI, LimelightConstants.YkD)
    };

    public CheckAprilTag(Limelight limeLight, Drivebase drivebase) {
        this.limeLight = limeLight;
        this.drivebase = drivebase;

        addRequirements(this.limeLight);

        for (int i = 0; i < 3; i++) {
            PIDArray[i].disableContinuousInput();
            PIDArray[i].setSetpoint(0.0);
        }

    }

    @Override
    public void execute() {
        // do we have mr. target
        if (LimelightHelpers.getTV("limelight-left")) {
            Pose3d pos = LimelightHelpers.getTargetPose3d_RobotSpace("limelight-left");

            drivebase.robotOrientedDrive(getXSpeed(pos), getYSpeed(pos), getTurningSpeed());

        } else {
            drivebase.robotOrientedDrive(0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isFinished() {
        return !LimelightHelpers.getTV("limelight-left");
    }

    @Override
    public void end(boolean interrupted) {
        drivebase.robotOrientedDrive(0.0, 0.0, 0.0);
    }

    private double getTurningSpeed() {
        // angles are good yes :D
        if (LimelightHelpers.getTX("limelight-left") >= 0 + LimelightConstants.turningDeadband) {
            // Rotate robot right
            return LimelightConstants.turningSpeed;

        } else if (LimelightHelpers.getTX("limelight-left") <= 0 - LimelightConstants.turningDeadband) {
            // Rotate robot left
            return -LimelightConstants.turningSpeed;

        }
        // Stop turning
        return 0.0;

    }

    private double getXSpeed(Pose3d pos) {
        if (pos.getRotation().getY() > 0 + LimelightConstants.xMovingDeadband) {
            // Move the robot left
            return -LimelightConstants.movingSpeed;

        } else if (pos.getRotation().getY() < 0 - LimelightConstants.xMovingDeadband) {
            // Move the robot right
            return LimelightConstants.movingSpeed;

        }

        return 0.0;

    }

    private double getYSpeed(Pose3d pos) {
        if (pos.getX() > 0 + LimelightConstants.yMovingDeadband) {
            // Move the robot forword
            return LimelightConstants.movingSpeed;

        }

        return 0.0;

    }
}
