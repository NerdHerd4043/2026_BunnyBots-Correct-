package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.limelight.limelight;

public class checkAprilTag extends Command {
    private final limelight limeLight;

    public checkAprilTag(limelight limeLight) {
        this.limeLight = limeLight;
        addRequirements(this.limeLight);
    }

    @Override
    public void execute() {
        System.out.println(this.limeLight.getTv());
        System.out.println("checkAprilTag.execute()");
    }
}
