package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.climb.DefaultClimbCommand;

public class ClimbSubsystem extends TSubsystem {

	TPwmSpeedController climbMotor = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.CLIMB_MOTOR_PWM_ADDRESS);

	public void setSpeed(double speed) {
		
		// If the elevator is at the top and the
		// speed is positive, then set the speed
		// to zero.
		climbMotor.set(speed);
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
	}

}
