package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

/**
 *
 */
public class SetIntakeTiltCommand extends TSafeCommand {
	double setpoint;
	boolean angleDir = true;

    public SetIntakeTiltCommand(double setpoint) {
        requires(Robot.intakeSubsystem);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double currentAngle = Robot.intakeSubsystem.getTiltEncoderCount();
		if(currentAngle < setpoint) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0.5);
		} else {
			Robot.intakeSubsystem.setIntakeTiltSpeed(-0.3);
			angleDir = false;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (super.isFinished()) {
			return true;
		}
		
		double currentAngle = Robot.intakeSubsystem.getTiltEncoderCount();

		if (angleDir) {
			if (currentAngle > setpoint) {
				return true;
			}
		}
		else {
			if (currentAngle < setpoint) {
				return true;
			}
		}
		
		return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.elevatorSubsystem.setSpeed(-0.05);
    }
}
