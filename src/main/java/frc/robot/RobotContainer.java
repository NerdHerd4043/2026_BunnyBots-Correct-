// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.CheckAprilTag;
import frc.robot.commands.Drive;
import frc.robot.subsystems.drivebase.DriveConstants;
import frc.robot.subsystems.drivebase.Drivebase;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.limelight.Limelight;
import cowlib.Util;

public class RobotContainer {
  private final CommandXboxController controller = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  Drivebase drivebase = new Drivebase();
  Limelight limelight = new Limelight();
  Elevator elevator = new Elevator();

  public RobotContainer() {
    drivebase.setDefaultCommand(
        new Drive(
            drivebase,
            this::getScaledXY,
            () -> scaleRotationAxis(controller.getRightX())));

    configureBindings();
  }

  private double deadband(double input, double deadband) {
    if (Math.abs(input) < deadband) {
      return 0;
    } else {
      return input;
    }
  }

  private double[] getScaledXY() {
    // Array for storing the x/y inputs from the controller
    double[] xy = new double[2];

    // Assigning inputs to array locations. X and Y are switched because the
    // controller is funky.
    xy[0] = deadband(-controller.getLeftY(), DriveConstants.deadband);
    xy[1] = deadband(-controller.getLeftX(), DriveConstants.deadband);

    Util.square2DVector(xy);

    return xy;
  }

  private double scaleRotationAxis(double input) {
    return this.deadband(this.squared(input), DriveConstants.deadband) *
        drivebase.getMaxAngleVelocity() * -0.6;
  }

  private double squared(double input) {
    return Math.copySign(input * input, input);
  }

  public double getFieldAngle() {
    return drivebase.getFieldAngle();
  }

  private void configureBindings() {

    Trigger cancelAutoAprilTag = new Trigger(() -> anyJoystickInput());

    Command aprilTagCommand = Commands.sequence(new CheckAprilTag(limelight, drivebase).until(cancelAutoAprilTag));

    controller.a().toggleOnTrue(aprilTagCommand);

    controller.povUp().whileTrue(elevator.goUpCommand());
    controller.povDown().whileTrue(elevator.goDownCommand());

  }

  private boolean anyJoystickInput() {
    return deadband(controller.getLeftY(), DriveConstants.autoCancelThreshold) != 0
        || deadband(controller.getLeftX(), DriveConstants.autoCancelThreshold) != 0
        || deadband(controller.getRightX(), DriveConstants.autoCancelThreshold) != 0;
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
