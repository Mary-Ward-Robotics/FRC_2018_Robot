package robot.oi;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TButtonPressDetector;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TGameController_Xbox;
import com.torontocodingcollective.oi.TRumbleManager;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TToggle;
import com.torontocodingcollective.oi.TTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


/**Driver Controller
 * 	Sticks:
 * 		Right Stick Y-axis 	= Drive Motor Tank Drive 
 * 		Left Stick Y-axis  	= Drive Motor Tank Drive
 * 		Right Stick Press  	= Toggle PIDs
 * 	Buttons:
 * 		Start Button 		= Reset Encoders and Gyro 
 * 		Back Button 		= Cancel any Command
 * 		X Button			= Automatic intake
 * 		B Button			= Automatic intake cancel
 * 
 * 	Bumpers/Triggers:
 * 		Left Bumper			= High Gear (Turbo)
 *      Left Trigger        = Intake Claw Open
 *      Right Bumper        = Intake Cube
 *      Right Trigger       = Outtake Cube
 * 
 * Operator Controller
 * 	Sticks:
 * 		Left Stick Y-axis  	= Elevator Motor Speed (Manual Control)
 * 		Right Stick Y-axis	= Intake Tilt Motor Speed (Manual Control)
 * 		Right Stick X    	= Right Ramp Rear Adjust
 * 		Right Stick Y    	= Left Ramp Rear Adjust
 * 		Right Stick Press  	= 
 * 		Left Stick Press 	= 
 * 	Buttons:
 *      A Button            = 
 * 		X Button			= 
 * 	Bumpers/Triggers:
 * 		Left Trigger 		= Climb Speed Up (Manual Control)
 * 		Right Trigger		= Climb Speed down (Manual Control) 
 * 		Left Bumper			= Open intake		
 * 		Right Bumper		= 
 *	POV
 *		
 *
 *
 */
public class OI {
	
	// Declare an autoSelector to put it on the Smartdashboard
	public AutoSelector autoSelector = new AutoSelector();

	private TGameController driverController = new TGameController_Xbox(0);
	private TGameController operatorController = new TGameController_Logitech(1);
	
	public TRumbleManager driverRumble = new TRumbleManager("Driver", driverController);

	private TToggle pidToggle = new TToggle(driverController, TStick.RIGHT);

	private TButtonPressDetector elevatorUpButtonPress = 
			new TButtonPressDetector(operatorController,TButton.Y);

	private TButtonPressDetector elevatorDownButtonPress = 
			new TButtonPressDetector(operatorController,TButton.A);

	//******************************************************
	// Driver Controller
	//******************************************************
	public int getPov() {
		return operatorController.getPOV();
	}
	
	public double getLeftMotorSpeed() {
		return - driverController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public double getRightMotorSpeed() {
		return - driverController.getAxis(TStick.RIGHT, TAxis.Y);
	}

	public boolean getTurboOn() {
		return driverController.getButton(TButton.LEFT_BUMPER);
	}

	public boolean getCancelCommand() {
		return driverController.getButton(TButton.BACK);
	}

	public boolean reset() {
		return driverController.getButton(TButton.START);
	}

	public boolean getSpeedPidEnabled() {
		return pidToggle.get();
	}

	public void setSpeedPidToggle(boolean state) {
		pidToggle.set(state);
	}

	//******************************************************
	// Operator Controller
	//******************************************************
	public boolean getTestRaiseElevator() {
		return operatorController.getButton(TButton.X);
	}
	
	//elevator
	public double getElevatorSpeed() {
		return - operatorController.getAxis(TStick.LEFT, TAxis.Y);
	}
	
	public boolean getElevatorUp() {
		return elevatorUpButtonPress.get();
	}

	public boolean getElevatorDown() {
		return elevatorDownButtonPress.get();
	}
	
	//climb
	public double getClimbSpeed() {
		double upSpeed = operatorController.getTrigger(TTrigger.LEFT);
		double downSpeed = operatorController.getTrigger(TTrigger.RIGHT);
		if (upSpeed > 0.05) {
			return upSpeed;
		}
		
		if (downSpeed > 0.05) {
			return - downSpeed;
		}
		return 0;
	}
	
	//intake
	public double getIntakeTiltSpeed() {
		return - operatorController.getAxis(TStick.RIGHT, TAxis.Y);
	}
	
	public boolean getAutomaticIntake() {
		return operatorController.getButton(TButton.X);
	}
	
	public boolean getAutomaticIntakeCancel() {
		return operatorController.getButton(TButton.B);
	}
	
	public boolean getClawOpen() {
		return operatorController.getButton(TButton.LEFT_BUMPER); 
	}
	
	public boolean getIntakeCube() {
		int povDirection = getPov();
		if(povDirection == 0) {
			return true;
		} else {
			return false;
			}
	}

	public boolean getOuttakeCube() {
		int povDirection = getPov();
		if(povDirection == 180) {
			return true;
		} else {
			return false;
			}
	}

	
	public boolean getTiltArmUp() {
		return false; // TODO get a button
	}

	public boolean getTiltArmDown() {
		return false; //TODO: get a button
	}

	public void updatePeriodic() {
		pidToggle.updatePeriodic();
		driverRumble.updatePeriodic();
	}
}
