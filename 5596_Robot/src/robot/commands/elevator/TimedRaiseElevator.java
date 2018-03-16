package robot.commands.elevator;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class TimedRaiseElevator extends TSafeCommand {
	
	private double speed;
	
	public TimedRaiseElevator(double time, double speed) {
		super(time);
		requires (Robot.elevatorSubsystem);
		this.speed = speed;
	}
	
	public void initialize() {
		Robot.elevatorSubsystem.setSpeed(speed);
	}
	
	protected void execute() {
	}
	
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		return false;
	}
	
	protected void end() {
		Robot.elevatorSubsystem.setSpeed(0);
	}
}
