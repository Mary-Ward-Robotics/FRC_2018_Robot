package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

/**
 *
 */
public class SetTiltCommand extends TSafeCommand {
	double setpoint;
	boolean angleDir = true;

    public SetTiltCommand(double setpoint) {
		super(4);
        requires(Robot.intakeSubsystem);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double currentAngle = Robot.intakeSubsystem.getTiltEncoderCount();
		if(currentAngle < setpoint) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0.5);
		} else {
			Robot.intakeSubsystem.setIntakeTiltSpeed(-0.5);
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
		if(super.isTimedOut()) {System.out.println("Command timed out");}
		Robot.intakeSubsystem.setIntakeTiltSpeed(-0.05);
    }
}
