package frc.robot.subsystems.intake;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    private SparkMax intakeLeft = new SparkMax(0, MotorType.kBrushless);
    private SparkMax intakeRight = new SparkMax(0, MotorType.kBrushless);

    public Intake() {
        final SparkMaxConfig intakeLeftConfig = new SparkMaxConfig();
        final SparkMaxConfig intakeRightConfig = new SparkMaxConfig();

        intakeLeftConfig.follow(intakeRight);
        intakeLeftConfig.inverted(false);
        intakeRightConfig.inverted(true);

        intakeLeft.configure(intakeLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        intakeRight.configure(intakeRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);        
    }

    public void startIntake() {
        intakeRight.set(.2);
    }

    public void stopIntake() {
        intakeRight.stopMotor();
    }

    public Command runIntake() {
        return this.runOnce(() -> {
        startIntake();
        }).finallyDo(() -> {
            stopIntake();
        });
    }
}
