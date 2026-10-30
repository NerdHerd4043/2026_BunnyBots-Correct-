package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.limelight.Limelight;

public class CheckAprilTag extends Command {
    private final Limelight limeLight;

    public CheckAprilTag(Limelight limeLight) {
        this.limeLight = limeLight;
        addRequirements(this.limeLight);
    }

    @Override
    public void execute() {
        System.out.println(this.limeLight.getTv());
        System.out.println("checkAprilTag.execute()");
    }
}
