package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
 */
public class DefaultChassisCommand extends Command {

	public DefaultChassisCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {


		if (Robot.oi.getTurboOn()) {
			Robot.chassisSubsystem.enableTurbo();
		}
		else {
			Robot.chassisSubsystem.disableTurbo();
		}

		if (Robot.oi.reset()){
			Robot.chassisSubsystem.resetGyroAngle();
			Robot.chassisSubsystem.resetEncoders();
		}

		if (Robot.oi.getSpeedPidEnabled()) {
			Robot.chassisSubsystem.enableSpeedPids();
		}
		else {
			Robot.chassisSubsystem.disableSpeedPids();
		}


		// Tank drive uses the speeds directly
		double leftSpeed  = scaleValue(Robot.oi.getLeftMotorSpeed());		
		double rightSpeed = scaleValue(Robot.oi.getRightMotorSpeed());
		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
	}


	// This routine scales a joystick value to make the 
	// acceleration and turning more smooth.  All values that are
	// less than 0.5 are cut in half, and values above 0.5 are
	// scaled to be from 0.25 to 1.0.
	private double scaleValue(double value) {

		double absValue = Math.abs(value);

		if (absValue < 0.08) { 
			return 0;
		}

		if (absValue < 0.5) {
			return value / 2;
		}

		// Follow a y=mx + b curve to scale inputs from
		// 0.5 to 1.0 to outputs of 0.25 to 1.0
		if (value > 0) {
			return 0.25 + (value-0.5) * 1.5;
		}

		return - 0.25 + (value+0.5) * 1.5; 
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
