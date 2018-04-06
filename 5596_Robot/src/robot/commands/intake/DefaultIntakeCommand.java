package robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.elevator.SetElevatorHeightCommand;

/**
 *
 */
public class DefaultIntakeCommand extends Command {

	public DefaultIntakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intakeSubsystem.intakeClawClose();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.oi.getTestRaiseElevator()) {
//			Scheduler.getInstance().add(new TimedIntakeTiltCommand(1.4 , -0.4));
		}

		
		if (Robot.oi.getAutomaticIntake()) {
			Scheduler.getInstance().add(new TeleopAutomaticIntakeCommand());
		}
		
		//claw code
		if (Robot.oi.getClawOpen()) {
			Robot.intakeSubsystem.intakeClawOpen();
		} else if (Robot.oi.getClawOpen()&& Robot.intakeSubsystem.isCubeDetected()) {
			Robot.intakeSubsystem.intakeClawOpen();
			Robot.intakeSubsystem.outtakeCube();
		} else {
			Robot.intakeSubsystem.intakeClawClose();
		}

		// Intake / outtake code

		if (Robot.oi.getIntakeCube()) {
			Robot.intakeSubsystem.intakeCube();
		} else if (Robot.oi.getOuttakeCube()) {
			Robot.intakeSubsystem.outtakeCube();
		} else {
			Robot.intakeSubsystem.intakeStop();
		}
		
		//intake tilt code
		double intakeTiltSpeed = Robot.oi.getIntakeTiltSpeed();

		if (Math.abs(intakeTiltSpeed) > 0.1) {
			Robot.intakeSubsystem.setIntakeTiltSpeed(intakeTiltSpeed);
		} else {
			Robot.intakeSubsystem.setIntakeTiltSpeed(0);
		}
		
		//TODO add automatic calibration for tilt motor
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
