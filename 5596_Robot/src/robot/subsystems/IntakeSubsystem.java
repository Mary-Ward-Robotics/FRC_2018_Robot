package robot.subsystems;

import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.intake.DefaultIntakeCommand;

public class IntakeSubsystem extends TSubsystem {

	// Limit switches to detect if there is a cube loaded
	public TLimitSwitch cubeDetectedSwitch = new TLimitSwitch(RobotMap.CUBE_DETECT_DIO_PORT,
			TLimitSwitch.DefaultState.FALSE);

	// Motor that moves the roller to suck in the cube
	private TPwmSpeedController intakeRollerMotor = 
			new TPwmSpeedController(TPwmSpeedControllerType.SPARK,
			RobotMap.INTAKE_ROLLER_MOTOR_PWM_ADDRESS, RobotConst.INTAKE_ROLLER_ORIENTATION);
	// Motor that moves the arm up and down
	private TPwmSpeedController intakeTiltMotor = 
			new TPwmSpeedController(TPwmSpeedControllerType.SPARK,
			RobotMap.INTAKE_TILT_MOTOR_PWM_ADDRESS, RobotConst.INTAKE_TILT_ORIENTATION);

//	private TEncoder intakeTiltEncoder = intakeTiltMotor.getEncoder();

	// The intake clamp
	private DoubleSolenoid intakeClaw = 
			new DoubleSolenoid(RobotMap.INTAKE_CLAW_PNEUMATIC_PORT, RobotMap.INTAKE_CLAW_PNEUMATIC_PORT+1);
	
	// // The motors in the claw (arm) that "sucks" in the cube
	// private TCanSpeedController rightIntakeClawMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.RIGHT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	// private TCanSpeedController leftIntakeClawMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.LEFT_INTAKE_CLAW_WHEELS_CAN_ADDRESS);
	//
	// // Intake wheels used to draw in or remove a cube (the wheels inside the
	// robot)
	// private TCanSpeedController leftIntakeMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.LEFT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);
	// private TCanSpeedController rightIntakeMotor = new
	// TCanSpeedController(TCanSpeedControllerType.TALON_SRX,
	// RobotMap.RIGHT_INTAKE_RAIL_WHEELS_CAN_ADDRESS);

	@Override
	public void init() {
		intakeClawClose();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntakeCommand());
	}

	public void intakeClawOpen() {
		intakeClaw.set(Value.kForward);
	}

	public void intakeClawClose() {
		intakeClaw.set(Value.kReverse);
	}

	public void intakeCube() {
		intakeRollerMotor.set(1.0);
	}

	public void outtakeCube() {
		intakeRollerMotor.set(-1.0);
	}
	
	public void tiltIntakeArmUp() {
//		if (getTiltEncoderCount() < LIFT_UP_ENCODER_COUNT) {
			intakeTiltMotor.set(0.8);
//		}
	}
	
	public void tiltIntakeArmDown() {
//		if (getTiltEncoderCount() > LIFT_DOWN_ENCODER_COUNT) {
			intakeTiltMotor.set(-0.8);
//		}
	}


	
	public void setIntakeTiltSpeed(double speed) {
		intakeTiltMotor.set(speed);
	}

	public boolean isCubeDetected() {
		// Cube is detected if both limit switches have detected something, aka a cube
		return cubeDetectedSwitch.atLimit();
	}


	public void intakeStop() {
		intakeRollerMotor.stopMotor();
	}
	
	public boolean isIntakeMotorRunning() {
		return intakeRollerMotor.get() > 0.1;
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putBoolean("Intake Claw Open", intakeClaw.get() == Value.kForward);
		SmartDashboard.putBoolean("Intake Cube Detected", isCubeDetected());
	}

}
