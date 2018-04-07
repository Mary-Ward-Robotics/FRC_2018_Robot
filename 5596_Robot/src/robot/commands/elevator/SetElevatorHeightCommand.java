package robot.commands.elevator;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class SetElevatorHeightCommand extends TSafeCommand {
	
	private double encoderHeight;
	boolean lift = true;
	
	public SetElevatorHeightCommand(double encoderHeight) {
		super(0);
		requires (Robot.elevatorSubsystem);
		this.encoderHeight = encoderHeight;
	}
	
	public void initialize() {
		
		double currentHeight = Robot.elevatorSubsystem.getEncoderCount();
		if(currentHeight < encoderHeight) {
			Robot.elevatorSubsystem.setSpeed(0.5);
		} else {
			Robot.elevatorSubsystem.setSpeed(-0.3);
			lift = false;
		}
	}
	
	protected void execute() {
	}
	
	protected boolean isFinished() {
		
		if (super.isFinished()) {
			return true;
		}
		
		double currentHeight = Robot.elevatorSubsystem.getEncoderCount();

		if (lift) {
			if (currentHeight > encoderHeight) {
				return true;
			}
		}
		else {
			if (currentHeight < encoderHeight) {
				return true;
			}
		}
		return false;
	}
	
	protected void end() {
		Robot.elevatorSubsystem.setSpeed(0);
	}
}
