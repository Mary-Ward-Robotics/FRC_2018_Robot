package robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.RobotConst;

public class DefaultClimbCommand extends Command {

	public DefaultClimbCommand() {
		requires(Robot.climbSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		if (Math.abs(Robot.oi.getClimbSpeed()) > 0.1) {
			//check if 80% of the way up, and slow down
			if(Robot.climbSubsystem.getClimbEncoderCount() >= RobotConst.CLIMB_MAX_HEIGHT_COUNT*0.8) {
				Robot.climbSubsystem.setSpeed(Robot.oi.getClimbSpeed()*0.3);
			} else {
				Robot.climbSubsystem.setSpeed(Robot.oi.getClimbSpeed());
				}
			
		} else {
			Robot.climbSubsystem.setSpeed(0);
		}

		if(Robot.oi.reset() == true) {
			Robot.climbSubsystem.resetClimbEncoder();
		}
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
