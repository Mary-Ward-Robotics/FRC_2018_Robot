package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
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
    }
}
