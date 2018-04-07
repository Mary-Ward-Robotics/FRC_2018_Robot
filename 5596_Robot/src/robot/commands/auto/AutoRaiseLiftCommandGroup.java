package robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.RobotConst;
import robot.commands.drive.DriveDistanceCommand;
import robot.commands.elevator.SetElevatorHeightCommand;
import robot.commands.intake.SetIntakeTiltCommand;

/**
 *
 */
public class AutoRaiseLiftCommandGroup extends CommandGroup {

    public AutoRaiseLiftCommandGroup(double forward) {
    	addParallel(new SetIntakeTiltCommand(-27));
		addParallel(new SetElevatorHeightCommand(RobotConst.ELEVATOR_SCALE_HEIGHT_COUNT));
		addSequential(new DriveDistanceCommand(forward, 20, 0.4, 7.0, true));;
    }
}
