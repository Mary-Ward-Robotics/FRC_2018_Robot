package robot.subsystems;

import com.torontocodingcollective.sensors.encoder.TDioEncoder;
import com.torontocodingcollective.speedcontroller.TPwmSpeedController;
import com.torontocodingcollective.speedcontroller.TPwmSpeedControllerType;
import com.torontocodingcollective.subsystem.TSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.elevator.DefaultElevatorCommand;

public class ElevatorSubsystem extends TSubsystem {

	public static double MIN_LEVEL = 0;
	public static double MAX_LEVEL = 5;

	
	TPwmSpeedController elevatorMotor = 
			new TPwmSpeedController(TPwmSpeedControllerType.SPARK, RobotMap.ELEVATOR_MOTOR_PWM_ADDRESS,
					RobotConst.ELEVATOR_MOTOR_ORIENTATION);
	
	TDioEncoder elevatorEncoder = 
			new TDioEncoder(RobotMap.LIFT_ENCODER_DIO_PORT,
					RobotMap.LIFT_ENCODER_DIO_PORT+1);
	
	PIDController elevatorPID = new PIDController(0, 0, 0, 0, elevatorEncoder, elevatorMotor);
	
	public void setSpeed(double speed) {
		elevatorMotor.set(speed);
	} 
	
	public void getEncoderCount() {
		elevatorEncoder.get();
	}
	
	public void setSetpoint(double setpoint) {
		elevatorPID.setSetpoint(setpoint);
	}

	@Override
	public void init() {
		elevatorPID.setOutputRange(-1, 1);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new DefaultElevatorCommand());
	}

	@Override
	public void updatePeriodic() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("Elevator Motor", elevatorMotor.get());
		SmartDashboard.putNumber("Elevator Encoder", elevatorEncoder.get());
	}

}
