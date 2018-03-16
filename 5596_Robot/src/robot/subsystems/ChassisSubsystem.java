package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TDioEncoder;
import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.sensors.gyro.TPigeonImu;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TGryoDriveSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.drive.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends TGryoDriveSubsystem {

	private DoubleSolenoid shifter = new DoubleSolenoid(RobotMap.SHIFTER_PNEUMATIC_PORT, RobotMap.SHIFTER_PNEUMATIC_PORT+1);

	public boolean LOW_GEAR = false;
	public boolean HIGH_GEAR = true;

	private boolean turboEnabled = false;

	private double leftSpeedSetpoint = 0;
	private double rightSpeedSetpoint = 0;
	
	public ChassisSubsystem() {

		// Uncomment this block to use CAN based speed controllers
				// Uncomment this constructor to use PWM based Speed controllers
				super(	new TPigeonImu(RobotMap.GYRO_CAN_PORT),
						new TPwmSpeedController(TPwmSpeedControllerType.SPARK, 
								RobotMap.LEFT_DRIVE_MOTOR_PWM_ADDRESS,
								RobotConst.LEFT_MOTOR_ORIENTATION), 
						new TPwmSpeedController(TPwmSpeedControllerType.SPARK, 
								RobotMap.RIGHT_DRIVE_MOTOR_PWM_ADDRESS,
								RobotConst.RIGHT_MOTOR_ORIENTATION));

				// Get the encoders attached to the CAN bus speed controller.
				TEncoder leftEncoder  = new TDioEncoder(RobotMap.LEFT_ENCODER_DIO_PORT, RobotMap.LEFT_ENCODER_DIO_PORT+1);
				TEncoder rightEncoder  = new TDioEncoder(RobotMap.RIGHT_ENCODER_DIO_PORT, RobotMap.RIGHT_ENCODER_DIO_PORT+1);

				super.setEncoders(
						leftEncoder,  RobotConst.LEFT_ENCODER_ORIENTATION,
						rightEncoder, RobotConst.RIGHT_ENCODER_ORIENTATION,
						RobotConst.DRIVE_SPEED_PID_KP,
						RobotConst.MAX_LOW_GEAR_SPEED);
	}

	@Override
	public void init() {
		disableTurbo();
	};

	// Initialize the default command for the Chassis subsystem.
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultChassisCommand());
	}

	// ********************************************************************************************************************
	// Slew rate on acceleration (takes at least 1 second to stop)
	// ********************************************************************************************************************
	@Override
	public void setSpeed(double leftSpeedSetpoint, double rightSpeedSetpoint) {

		this.leftSpeedSetpoint = leftSpeedSetpoint;
		this.rightSpeedSetpoint = rightSpeedSetpoint;
		
		updateSpeed();
	}
	
	private void updateSpeed() {
		
		// If the PIDs are enabled, then bypass the soft drive code
		if (speedPidsEnabled()) {
			super.setSpeed(leftSpeedSetpoint, rightSpeedSetpoint);
			return;
		}
		
		double leftSpeed = leftMotor.get();
		double rightSpeed = rightMotor.get();
		
		if (Math.abs(leftSpeedSetpoint - leftSpeed) < .02) {
			leftSpeed = leftSpeedSetpoint;
		}
		else if (Math.abs(leftSpeedSetpoint - leftSpeed) > .8) {
			if (leftSpeedSetpoint > leftSpeed) {
				leftSpeed += .05;
			}
			else {
				leftSpeed -= .05;
			}
		}
		else {
			if (leftSpeedSetpoint > leftSpeed) {
				leftSpeed += .03;//.02
			}
			else {
				leftSpeed -= .03;
			}
		}

		if (Math.abs(rightSpeedSetpoint - rightSpeed) < .02) {
			rightSpeed = rightSpeedSetpoint;
		}
		else if (Math.abs(rightSpeedSetpoint - rightSpeed) > .8) {
			if (rightSpeedSetpoint > rightSpeed) {
				rightSpeed += .05;
			}
			else {
				rightSpeed -= .05;
			}
		}
		else {
			if (rightSpeedSetpoint > rightSpeed) {
				rightSpeed += .03;
			}
			else {
				rightSpeed -= .03;
			}
		}
		
		super.setSpeed(leftSpeedSetpoint, rightSpeedSetpoint);
	}
	
	// ********************************************************************************************************************
	// Turbo routines
	// ********************************************************************************************************************
	public void enableTurbo() {
		turboEnabled = true;
		//System.out.println("Turbo enabled");
		setMaxEncoderSpeed(RobotConst.MAX_HIGH_GEAR_SPEED);
		shifter.set(Value.kReverse);
	}

	public void disableTurbo() {
		turboEnabled = false;
		//System.out.println("Turbo disabled");
		setMaxEncoderSpeed(RobotConst.MAX_LOW_GEAR_SPEED);
		shifter.set(Value.kForward);
	}

	public boolean isTurboEnabled() {
		return turboEnabled;
	}

	// ********************************************************************************************************************
	// Update the SmartDashboard
	// ********************************************************************************************************************
	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {

		super.updatePeriodic();

		SmartDashboard.putBoolean("Turbo Enabled", isTurboEnabled());
	}

}
