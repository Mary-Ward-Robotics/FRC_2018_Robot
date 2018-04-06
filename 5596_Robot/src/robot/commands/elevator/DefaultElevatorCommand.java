package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

public class DefaultElevatorCommand extends Command {
	private double setpoint;
	public DefaultElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(Robot.oi.reset()) {
			Robot.elevatorSubsystem.resetEncoderCount();
		}

		// Look for the test command
//		if (Robot.oi.getTestRaiseElevator()) {
////			Scheduler.getInstance().add(new TimedRaiseElevatorCommand(1.2, 0.8)); //switch
//			Scheduler.getInstance().add(new TimedRaiseElevatorCommand(1.8, 0.8)); //scale
//		}
		
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			setpoint = Robot.elevatorSubsystem.getEncoderCount();
			
			double elevatorSpeed = Robot.oi.getElevatorSpeed();
			// If the elevator is moving down, then go slowly - limit the down speed to 0.33
			if (elevatorSpeed < 0) {
				elevatorSpeed *= 0.3;
			}
			Robot.elevatorSubsystem.setSpeed(elevatorSpeed*0.7);
		}
		else {
			Robot.elevatorSubsystem.setSpeed(0);
		}
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
