package frc.robot.subsystems.elevator;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private SparkMax elevL = new SparkMax(ElevatorConstants.SparkMaxes.elevLeftID, MotorType.kBrushless);
    private SparkMax elevR = new SparkMax(ElevatorConstants.SparkMaxes.elevRightID, MotorType.kBrushless);

    private ElevatorFeedforward elevFF = new ElevatorFeedforward(0, 0, 0);
    private ProfiledPIDController PIDController = new ProfiledPIDController(0, 0, 0, null);

    private RelativeEncoder encoder = elevR.getEncoder();

    public Elevator() {
        final SparkMaxConfig configL = new SparkMaxConfig();
        final SparkMaxConfig configR = new SparkMaxConfig();

        configL.follow(elevR);
        configL.inverted(false);
        configR.inverted(true);

        this.elevL.configure(configL, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.elevR.configure(configR, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void elevatorTo0() {
        PIDController.setGoal(0);
    }

    public void elevatorTo() {
        PIDController.setGoal(5);
    }

    public Double getEncoderRadons() {
        return encoder.getPosition() * 2 * Math.PI;
    }

    private void runPID() {
        var setPoint = PIDController.getSetpoint();
        Double PIDOutput = PIDController.calculate(getEncoderRadons());
        Double FFOutput = elevFF.calculate(setPoint.position, setPoint.velocity);
        Double outputV = PIDOutput + FFOutput;

        elevR.setVoltage(outputV);
    }

    @Override
    public void periodic() {
        super.periodic();

        runPID();
    }

}
