package frc.robot.subsystems.intake;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private SparkMax intakeMotor = new SparkMax(IntakeConstants.intakeMotorID, MotorType.kBrushless);

    public Intake() {
        final SparkMaxConfig intakeConfig = new SparkMaxConfig();

        intakeConfig.inverted(true);

        intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void runIntake() {
        intakeMotor.set(IntakeConstants.intakeMotorSpeed);
    }

    public void stopIntake() {
        intakeMotor.stopMotor();
    }

    public Command runIntakeCommand() {
        return this.runOnce(() -> {
            runIntake();
        }).finallyDo(() -> {
            stopIntake();
        });
    }
}
