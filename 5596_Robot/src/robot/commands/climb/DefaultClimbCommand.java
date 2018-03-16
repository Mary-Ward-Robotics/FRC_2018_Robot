package robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class DefaultClimbCommand extends Command {

	public DefaultClimbCommand() {
		requires(Robot.climbSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		// Read the joystick 
		// If the joystick is pressed, then 
		// override the elevator movement.
		if (Math.abs(Robot.oi.getClimbSpeed()) > 0.1) {
			Robot.climbSubsystem.setSpeed(Robot.oi.getClimbSpeed());
		}
		else {
			Robot.climbSubsystem.setSpeed(0);
		}
			
		
//		// Increment and decrement.
//		if (Robot.oi.getElevatorUp()) {
//			addHeight();
//		}
//
//		if (Robot.oi.getElevatorDown()) {
//			subtractHeight();
//		}
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
