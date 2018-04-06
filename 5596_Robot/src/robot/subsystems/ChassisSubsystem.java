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

	public ChassisSubsystem() {

		// Uncomment this block to use CAN based speed controllers
				// Uncomment this constructor to use PWM based Speed controllers
				super(	new TPigeonImu(RobotMap.GYRO_CAN_PORT, true),
						new TPwmSpeedController(TPwmSpeedControllerType.SPARK, 
								RobotMap.LEFT_DRIVE_MOTOR_PWM_ADDRESS,
								RobotConst.LEFT_MOTOR_ORIENTATION), 
						new TPwmSpeedController(TPwmSpeedControllerType.SPARK, 
								RobotMap.RIGHT_DRIVE_MOTOR_PWM_ADDRESS,
								RobotConst.RIGHT_MOTOR_ORIENTATION),
						RobotConst.DRIVE_GYRO_PID_KP,
						RobotConst.DRIVE_GYRO_PID_KI);

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
	// Turbo routines
	// ********************************************************************************************************************
	public void enableTurbo() {
		turboEnabled = true;
		setMaxEncoderSpeed(RobotConst.MAX_HIGH_GEAR_SPEED);
		shifter.set(Value.kReverse);
	}

	public void disableTurbo() {
		turboEnabled = false;
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
