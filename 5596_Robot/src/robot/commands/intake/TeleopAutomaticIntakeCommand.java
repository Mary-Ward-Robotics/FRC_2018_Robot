package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class TeleopAutomaticIntakeCommand extends TSafeCommand {	
	
	private enum State { INTAKE, INTAKE_DELAY, ELEVATE, FINISH };
	
	State state = State.INTAKE;
	double reverseStartTime = 0;
	double intakeStopDelayStartTime = 0;

	public TeleopAutomaticIntakeCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
		Robot.oi.driverRumble.rumbleOn();
	}
	
	protected void execute() {

		switch (state) {
		case INTAKE:
			if (Robot.intakeSubsystem.isCubeDetected()) {
				Robot.oi.driverRumble.rumbleOff();
				Robot.intakeSubsystem.intakeClawClose();
				intakeStopDelayStartTime = timeSinceInitialized(); 
				state = State.INTAKE_DELAY;
			}
			break;
			
		case INTAKE_DELAY:
			if (timeSinceInitialized() > intakeStopDelayStartTime + .5) {
				Robot.intakeSubsystem.intakeStop();
				state = State.FINISH;
			}
			break;
			
		case ELEVATE:
//			if (Robot.elevatorSubsystem.getLevel() <= 1) {
//				Scheduler.getInstance().add(new SetElevatorHeightCommand(1));
//			}
			state = State.FINISH;
			break;
			
		default:
			break;
		}
	}
	
	protected void end(){
		Robot.oi.driverRumble.rumbleOff();
		Robot.intakeSubsystem.intakeClawClose();
		Robot.intakeSubsystem.intakeStop();
	}
	
	protected boolean isFinished() {
		if (Robot.oi.getAutomaticIntakeCancel()){
			return true;
		}
		if (super.isFinished()) {
			return true;
		}
		if (state == State.FINISH) {
			return true;
		}
		return false;
	}

}