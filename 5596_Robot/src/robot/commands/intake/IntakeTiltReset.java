package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

/**
 *
 */
public class IntakeTiltReset extends TSafeCommand {

    public IntakeTiltReset() {
    	super(3);
        requires(Robot.intakeSubsystem);
    }

    protected void initialize() {
    	Robot.intakeSubsystem.tiltIntakeArmDown();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(Robot.intakeSubsystem.lowerLimitReached()) {
//    		Robot.intakeSubsystem.resetTiltEncodercount();
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	return Robot.intakeSubsystem.lowerLimitReached();
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Finished Reset");
    	Robot.intakeSubsystem.setIntakeTiltSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
