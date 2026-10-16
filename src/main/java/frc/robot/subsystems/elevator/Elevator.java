package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    private SparkMax elevL = new SparkMax(13, MotorType.kBrushless);
    private SparkMax elevR = new SparkMax(14, MotorType.kBrushless);
    public Elevator() {
        final SparkMaxConfig configL = new SparkMaxConfig();
        final SparkMaxConfig configR = new SparkMaxConfig();

        configL.follow(elevR);
        configL.inverted(false);
        configR.inverted(true);
    }
    
}
