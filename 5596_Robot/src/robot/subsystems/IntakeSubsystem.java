package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TDioEncoder;
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
	public TLimitSwitch cubeDetectedSwitch = new TLimitSwitch(
			RobotMap.CUBE_DETECT_DIO_PORT,
			TLimitSwitch.DefaultState.OPEN);
	
//	//Limit switches to detect if the tilt motor has reached it's limits
//	public TLimitSwitch upperLimitSwitch = new TLimitSwitch(
//			RobotMap.INTAKE_TILT_UP_LIMIT,
//			TLimitSwitch.DefaultState.OPEN);
//	
//	public TLimitSwitch lowerLimitSwitch = new TLimitSwitch(
//			RobotMap.INTAKE_TILT_DOWN_LIMIT,
//			TLimitSwitch.DefaultState.OPEN);

	// Motor that moves the roller to suck in the cube
	private TPwmSpeedController intakeRollerMotor = new TPwmSpeedController(
			TPwmSpeedControllerType.SPARK,
			RobotMap.INTAKE_ROLLER_MOTOR_PWM_ADDRESS,
			RobotConst.INTAKE_ROLLER_ORIENTATION);
	
	// Motor that moves the arm up and down
	private TPwmSpeedController intakeTiltMotor = new TPwmSpeedController(
			TPwmSpeedControllerType.SPARK,
			RobotMap.INTAKE_TILT_MOTOR_PWM_ADDRESS, 
			RobotConst.INTAKE_TILT_ORIENTATION);


	TDioEncoder tiltEncoder = new TDioEncoder(
			RobotMap.INTAKE_TILT_ENCODER_DIO_PORT,
			RobotMap.INTAKE_TILT_ENCODER_DIO_PORT+1, true);

	// The intake clamp
	private DoubleSolenoid intakeClaw = new DoubleSolenoid(
			RobotMap.INTAKE_CLAW_PNEUMATIC_PORT, 
			RobotMap.INTAKE_CLAW_PNEUMATIC_PORT+1);
	
	@Override
	public void init() {
		intakeClawClose();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultIntakeCommand());
	}

	//claw
	public void intakeClawOpen() {
		intakeClaw.set(Value.kForward);
	}

	public void intakeClawClose() {
		intakeClaw.set(Value.kReverse);
	}

	//intake
	public void intakeCube() {
		intakeRollerMotor.set(1.0);
	}

	public void outtakeCube() {
		intakeRollerMotor.set(-1.0);
	}
	
	public void setSpeedCube(double speed) {
		intakeRollerMotor.set(speed);
	}
	
	public void intakeStop() {
		intakeRollerMotor.stopMotor();
	}
	
	public boolean isIntakeMotorRunning() {
		return intakeRollerMotor.get() > 0.1;
	}
	
	//tilt
	public void tiltIntakeArmUp() {
		if (getTiltEncoderCount() < RobotConst.INTAKE_TILT_UP_ENCODER_COUNT) {
			intakeTiltMotor.set(-0.2);
		}
	}
	
	public void tiltIntakeArmDown() {
		if (getTiltEncoderCount() > RobotConst.INTAKE_TILT_DOWN_ENCODER_COUNT) {
			intakeTiltMotor.set(0.2);
		}
	}
	
	public void setIntakeTiltSpeed(double speed) {
		intakeTiltMotor.set(speed);
	}

	//tilt sensors
	public double getTiltEncoderCount() {
		return tiltEncoder.get();
	}
	
	public void resetTiltEncodercount() {
		tiltEncoder.reset();
	}
	
//	public boolean upperLimitReached() {
//		return upperLimitSwitch.atLimit();
//	}
//	
//	public boolean lowerLimitReached() {
//		return lowerLimitSwitch.atLimit();
//	}
	
	public boolean isCubeDetected() {
		return !cubeDetectedSwitch.atLimit();
	}

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		SmartDashboard.putBoolean("Intake Claw Open", intakeClaw.get() == Value.kForward);
		SmartDashboard.putBoolean("Intake Cube Detected", isCubeDetected());
		SmartDashboard.putNumber("Intake Tilt Encoder", tiltEncoder.get());
		SmartDashboard.putBoolean("Cube Detected", isCubeDetected());
	}

}
