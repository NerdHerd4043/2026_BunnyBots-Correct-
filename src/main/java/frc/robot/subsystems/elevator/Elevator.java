package frc.robot.subsystems.elevator;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@Logged
public class Elevator extends SubsystemBase {
    private SparkMax leftMotor = new SparkMax(ElevatorConstants.leftElevatorMotorID, MotorType.kBrushless);
    private SparkMax rightMotor = new SparkMax(ElevatorConstants.rightElevatorMotorID, MotorType.kBrushless);

    private SparkLimitSwitch limitSwitch;

    public Elevator() {
        final SparkMaxConfig leftMotorConfig = new SparkMaxConfig();
        final SparkMaxConfig rightMotorConfig = new SparkMaxConfig();

        leftMotorConfig.follow(rightMotor, true);

        leftMotorConfig.smartCurrentLimit(ElevatorConstants.currentLimit);
        rightMotorConfig.smartCurrentLimit(ElevatorConstants.currentLimit);

        rightMotorConfig.limitSwitch.reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen);

        leftMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        limitSwitch = rightMotor.getReverseLimitSwitch();

    }

    private void stop() {
        rightMotor.stopMotor();
    }

    private void goUp() {
        if (false) {
            rightMotor.set(ElevatorConstants.speed);
        } else {
            stop();
        }
    }

    private void goDown() {
        if (!limitSwitch.isPressed()) {
            rightMotor.set(-ElevatorConstants.speed);
        } else {
            stop();
        }
    }

    public Command goUpCommand() {
        return this.runOnce(() -> {
            goUp();
        }).finallyDo(() -> {
            stop();
        });

    }

    public Command goDownCommand() {
        return this.runOnce(() -> {
            goDown();
        }).finallyDo(() -> {
            stop();
        });

    }

}
