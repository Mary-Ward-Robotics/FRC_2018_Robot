package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.commands.drive.ReverseDriveCommand;
import robot.commands.elevator.SetElevatorHeightCommand;
import robot.commands.intake.SetTiltCommand;

/**
 *
 */
public class AutoBackOffScale extends CommandGroup {

    public AutoBackOffScale() {
    	System.out.println("Backing off scale");
    	addParallel(new SetTiltCommand(0));
		addParallel(new SetElevatorHeightCommand(0));
		addSequential(new ReverseDriveCommand(20, 310, 0.5, 7.0, true));
    }
}
