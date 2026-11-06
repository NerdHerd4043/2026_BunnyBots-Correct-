// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivebase.Drivebase;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Drive extends Command {

  private final Drivebase drivebase;
  private final Supplier<double[]> speedXY;
  private final DoubleSupplier rot;

  // false if the limelight is controling the turning of the robot
  private static boolean turningEnabled = true;
  // is the speed at which the robot is turning when being controled by the
  // limelight
  private static double limelightTurningSpeed = 0.0;

  // false if the limelight is controling the moving of the robot
  private static boolean movingEnabled = true;
  // is the speed at which the robot is moving when being controled by the
  // limelight
  private static double[] XYLL = { 0.0, 0.0 };

  /** Creates a new Drive. */
  public Drive(Drivebase drivebase, Supplier<double[]> speedXY, DoubleSupplier rot) {
    this.drivebase = drivebase;
    this.speedXY = speedXY;
    this.rot = rot;

    this.addRequirements(this.drivebase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double[] xy = { 0.0, 0.0 };
    var r = 0.0;

    if (turningEnabled) {
      r = this.rot.getAsDouble();
    } else {
      r = limelightTurningSpeed;
    }

    if (movingEnabled) {
      xy = this.speedXY.get();
    } else {
      xy = XYLL;
    }

    this.drivebase.defaultDrive(xy[0], xy[1], r, true);
  }

  public static void disableNormalTurning() {
    turningEnabled = false;
  }

  public static void enableNormalTurning() {
    turningEnabled = true;
  }

  public static void setLimelightTurningSpeed(double turningSpeed) {
    limelightTurningSpeed = turningSpeed;
  }

  public static void disableNormalMoving() {
    movingEnabled = false;
  }

  public static void enableNormalMoving() {
    movingEnabled = true;
  }

  public static void setLimelightMovingSpeed(double[] xy) {
    XYLL = xy;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
