package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.subsystems.ElevatorSubsystem;

public class DefaultElevatorCommand extends Command {

	public DefaultElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		// Read the joystick 
		// If the joystick is pressed, then 
		// override the elevator movement.
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.2) {
			Robot.elevatorSubsystem.setSpeed(Robot.oi.getElevatorSpeed());
		}
		else {
			Robot.elevatorSubsystem.setSpeed(0);
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
