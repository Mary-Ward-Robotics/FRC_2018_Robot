package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class BackupCommand extends TSafeCommand {
	
	private final double distanceEncoderCounts;

	public BackupCommand(double distance) {
		
		this.distanceEncoderCounts =  
				Math.abs(distance) * RobotConst.DRIVE_ENCODER_COUNTS_PER_INCH;
	}
	
	protected void initialize() {
		super.initialize();
		Robot.chassisSubsystem.resetEncoders();
		Robot.chassisSubsystem.setSpeed(-.4, -.4);
	}
	
	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}
	
		if (Robot.chassisSubsystem.getEncoderDistance() < -distanceEncoderCounts) {
			return true;
		}
		
		return false;
	}
}
