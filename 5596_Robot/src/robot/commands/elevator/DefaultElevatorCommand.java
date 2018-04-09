package robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.RobotConst;

public class DefaultElevatorCommand extends Command {
	public DefaultElevatorCommand() {
		requires(Robot.elevatorSubsystem);
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		if(Robot.oi.reset()) {
			Robot.elevatorSubsystem.resetEncoderCount();
		}
		
		if((Robot.oi.getElevatorSpeed()) < 0 && Robot.elevatorSubsystem.upperLimitReached()) {
			Robot.elevatorSubsystem.setSpeed(0);
		}
		
		if((Robot.oi.getElevatorSpeed()) > 0 && Robot.elevatorSubsystem.lowerLimitReached()) {
			Robot.elevatorSubsystem.setSpeed(0);
		}
		
		if (Math.abs(Robot.oi.getElevatorSpeed()) > 0.1) {
			double liftCount = Robot.elevatorSubsystem.getEncoderCount();
			
			double elevatorSpeed = Robot.oi.getElevatorSpeed()*0.8;
				// If the elevator is moving down, then go slowly - limit the down speed to 0.33
			if (elevatorSpeed < 0) {
				//if elevator is 20% of the way down, slow down
				if(liftCount <= RobotConst.ELEVATOR_MAX_HEIGHT_COUNT*0.2) {
					elevatorSpeed *= 0.8;
					} else {
				elevatorSpeed *= 0.7;
				}
			}
				
			//if elevator is 80% of the way to the max, slow down
			if(liftCount >= RobotConst.ELEVATOR_MAX_HEIGHT_COUNT*0.8) {
			elevatorSpeed *= 0.4;
			}
			
			Robot.elevatorSubsystem.setSpeed(elevatorSpeed);
		}
		else {
			Robot.elevatorSubsystem.setSpeed(0);
		}
		
		//new test thing
//		double elevatorSpeed = Robot.oi.getElevatorSpeed()*0.7;
//		if(Robot.oi.getElevatorSpeed() > 0.1) { //going down
//			if(Robot.elevatorSubsystem.lowerLimitReached() != true) {
//				Robot.elevatorSubsystem.setSpeed(elevatorSpeed);
//			} else {Robot.elevatorSubsystem.setSpeed(-0.1);}
//		}
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
