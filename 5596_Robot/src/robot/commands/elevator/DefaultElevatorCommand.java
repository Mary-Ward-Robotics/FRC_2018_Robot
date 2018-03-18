package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

public class DefaultElevatorCommand extends Command {

	public DefaultElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		// Look for the test command
		if (Robot.oi.getTestRaiseElevator()) {
//			Scheduler.getInstance().add(new TimedRaiseElevator(1.2, 0.8)); //switch
			Scheduler.getInstance().add(new TimedRaiseElevatorCommand(2, 0.8)); //scale
		}
		
		// Read the joystick 
		// If the joystick is pressed, then 
		// override the elevator movement.
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			
			double elevatorSpeed = Robot.oi.getElevatorSpeed();
			
			// If the elevator is moving down, then go slowly - limit the down speed to 0.33
			if (elevatorSpeed < 0) {
				elevatorSpeed /= 3;
			}
			
			Robot.elevatorSubsystem.setSpeed(elevatorSpeed);
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
