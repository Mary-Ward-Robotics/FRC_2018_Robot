package robot.subsystems;

import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotMap;
import robot.commands.elevator.DefaultElevatorCommand;

public class ElevatorSubsystem extends TSubsystem {

	public static double MIN_LEVEL = 0;
	public static double MAX_LEVEL = 5;

	
	TPwmSpeedController elevatorMotor = new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.ELEVATOR_MOTOR_PWM_ADDRESS);

	public void setSpeed(double speed) {
		
		// If the elevator is at the top and the
		// speed is positive, then set the speed
		// to zero.
		elevatorMotor.set(speed);
	} 

	@Override
	public void init() {
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated mthod stub
		setDefaultCommand(new DefaultElevatorCommand());
	}

	@Override
	public void updatePeriodic() {

		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Elevator Motor", elevatorMotor.get());
	}

}
