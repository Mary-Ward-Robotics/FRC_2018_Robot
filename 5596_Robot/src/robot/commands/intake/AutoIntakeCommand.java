package robot.commands.intake;

import robot.Robot;
import robot.commands.drive.TSafeCommand;

public class AutoIntakeCommand extends TSafeCommand {	
	
	private enum State { INTAKE, INTAKE_DELAY, FINISH };
	
	State state = State.INTAKE;
	double reverseStartTime = 0;
	double intakeStopDelayStartTime = 0;

	public AutoIntakeCommand() {
		requires(Robot.intakeSubsystem);
	}
	
	protected void initialize() {
		Robot.intakeSubsystem.intakeCube();
		Robot.intakeSubsystem.intakeClawOpen();
	}
	
	protected void execute() {

		switch (state) {
		case INTAKE:
			if (Robot.intakeSubsystem.isCubeDetected() || timeSinceInitialized() > 3) {
				Robot.intakeSubsystem.intakeClawClose();
				intakeStopDelayStartTime = timeSinceInitialized(); 
				state = State.INTAKE_DELAY;
			}
			break;
			
		case INTAKE_DELAY:
			if (timeSinceInitialized() > intakeStopDelayStartTime + 1) {
				Robot.intakeSubsystem.intakeStop();
				state = State.FINISH;
			}
			break;
			
		default:
			break;
		}
	}
	
	protected void end(){
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