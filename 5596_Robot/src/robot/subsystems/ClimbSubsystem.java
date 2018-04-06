package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TDioEncoder;
import com.torontocodingcollective.sensors.limitSwitch.TLimitSwitch;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.climb.DefaultClimbCommand;

public class ClimbSubsystem extends TSubsystem {
	
	TLimitSwitch upperLimitSwitch = new TLimitSwitch(
			RobotMap.CLIMB_UP_LIMIT,
			TLimitSwitch.DefaultState.OPEN);

	TPwmSpeedController climbMotor = new TPwmSpeedController(
			TPwmSpeedControllerType.SPARK, 
			RobotMap.CLIMB_MOTOR_PWM_ADDRESS,
			RobotConst.CLIMB_MOTOR_ORIENTATION);

	TDioEncoder climbEncoder =new TDioEncoder(
			RobotMap.CLIMB_ENCODER_DIO_PORT,
			RobotMap.CLIMB_ENCODER_DIO_PORT+1);
	
	
	public void setSpeed(double speed) {
		climbMotor.set(speed);
	} 
	
	public double getClimbEncoderCount() {
		return climbEncoder.get();
	}
	
	public boolean upperLimitReached() {
		return upperLimitSwitch.atLimit();
	}

	@Override
	public void init() {
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated mthod stub
		setDefaultCommand(new DefaultClimbCommand());
	}

	@Override
	public void updatePeriodic() {

		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Climb Motor", climbMotor.get());
		SmartDashboard.putNumber("Climb Encoder", climbEncoder.get());
	}

}
