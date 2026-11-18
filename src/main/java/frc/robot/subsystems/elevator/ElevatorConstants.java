package frc.robot.subsystems.elevator;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class ElevatorConstants {
    public static final int elevLeftID = 13;
    public static final int elevRightID = 14;

    // PID
    public static final Double kP = 0.0;
    public static final Double kI = 0.0;
    public static final Double kD = 0.0;

    // FF
    public static final Double kS = 0.0;
    public static final Double kG = 0.0;
    public static final Double kV = 0.0;

    public static final TrapezoidProfile.Constraints constraints = // I just lost the game
            new TrapezoidProfile.Constraints(90, 190);

    public static final int currentLimit = 40;

    public static final double[] elevatorHeights = { 0.0 };

    public static final double maxElevatorHeight = 134.49;

}
