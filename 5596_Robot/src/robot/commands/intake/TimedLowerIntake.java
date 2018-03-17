package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class TimedLowerIntake extends TSafeCommand {
	
	private double speed;
	
	public TimedLowerIntake(double time, double speed) {
		super(time);
		requires (Robot.intakeSubsystem);
		this.speed = speed;
	}
	
	public void initialize() {
		Robot.intakeSubsystem.setIntakeTiltSpeed(speed);
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
		Robot.intakeSubsystem.setIntakeTiltSpeed(0);
	}
}
