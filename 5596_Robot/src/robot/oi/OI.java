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
 * 		Right Stick X-axis 	= Drive Motor Turn 
 * 		Left Stick Y-axis  	= Drive Motor Speed
 * 		Right Stick Press  	= Toggle PIDs
 * 		Left Stick Press 	= Toggle Compressor
 * 	Buttons:
 * 		Start Button 		= Reset Encoders and Gyro 
 * 		Back Button 		= Cancel any Command
 * 		X Button			= Automatic intake
 * 		Y Button			= intake Toggle
 * 		B Button			= Automatic intake cancel
 * 
 * 	Bumpers/Triggers:
 *      Right Bumper        = Intake Cube
 *      Right Trigger       = Outtake Cube
 * 		Left Bumper			= High Gear
 *      Left Trigger        = Intake Open
 * 
 * Operator Controller
 * 	Sticks:
 * 		Left Stick Y-axis  	= Elevator Motor Speed (Manual Control)
 * 		Right Stick X    	= Right Ramp Rear Adjust
 * 		Right Stick Y    	= Left Ramp Rear Adjust
 * 		Right Stick Press  	= 
 * 		Left Stick Press 	= 
 * 	Buttons:
 * 		Y Button 			= Eject Cube out Front of the Robot
 * 		A Button 			= Eject Cube out Back of the Robot
 * 		X Button			= Deploy Ramps
 * 	Bumpers/Triggers:
 * 		Right Bumper		= Move Elevator Up One Level
 * 		Left Bumper			= Move Elevator Down One Level		
 * 		Right Trigger		= Move Intake Forearms to Inner Position 
 * 		Left Trigger 		= Move Intake Forearms to Outer Position 
 *	POV
 *		45					= Raise Right Ramp
 *		315					= Raise Left Ramp
 *		135					= Lower Right Ramp
 *		225					= Lower Left Ramp
 *
 *
 */
public class OI {

	public AutoSelector autoSelector = new AutoSelector();

	private TGameController driverController = new TGameController_Xbox(0);
	private TGameController operatorController = new TGameController_Logitech(1);
	
	public TRumbleManager driverRumble = new TRumbleManager("Driver", driverController);


	private TToggle pneumaticsToggle = new TToggle(driverController, TStick.LEFT);
	private TToggle pidToggle = new TToggle(driverController, TStick.RIGHT);

	private TButtonPressDetector elevatorUpButtonPress = 
			new TButtonPressDetector(operatorController,TButton.RIGHT_BUMPER);

	private TButtonPressDetector elevatorDownButtonPress = 
			new TButtonPressDetector(operatorController,TButton.LEFT_BUMPER);

	public void init() {
		pneumaticsToggle.set(true);
	}

	//Driver Controller
	public double getSpeed() {
		return - driverController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public double getTurn() {
		return - driverController.getAxis(TStick.RIGHT, TAxis.Y);
	}

	public boolean getAutomaticIntake() {
		return driverController.getButton(TButton.X);
	}
	public boolean getAutomaticIntakeCancel() {
		return driverController.getButton(TButton.B);
	}
	
	/*public boolean getForwardThrust() {
	//	return gameController.getButton(TButton.A);
	//}

	public boolean getStartDriveDirection() {
		return gameController.getButton(TButton.B);
	}

	public int getArcCommand() {
		return gameController.getPOV();
	}*/

	public boolean getCancelCommand() {
		return driverController.getButton(TButton.BACK);
	}

	public boolean reset() {
		return driverController.getButton(TButton.START);
	}
	
	public int getPov() {
		return driverController.getPOV();
	}

	public boolean getTurboOn() {
		return driverController.getButton(TButton.LEFT_BUMPER);
	}

	public boolean getCompressorEnabled() {
		return pneumaticsToggle.get();
	}

	public boolean getSpeedPidEnabled() {
		return pidToggle.get();
	}

	public void setSpeedPidToggle(boolean state) {
		pidToggle.set(state);
	}

	//Operator Controller
	
	public double getClimbSpeed() {
		double upSpeed = operatorController.getTrigger(TTrigger.LEFT);
		double downSpeed = operatorController.getTrigger(TTrigger.RIGHT);
		
		if (upSpeed > 0.05) {
			return - upSpeed;
		}
		
		if (downSpeed > 0.05) {
			return downSpeed;
		}
		return 0;
	}
	
	public boolean getTestRaiseElevator() {
		return operatorController.getButton(TButton.X);
	}
	
	public double getElevatorSpeed() {
		return - operatorController.getAxis(TStick.LEFT, TAxis.Y);
	}

	public boolean getElevatorUp() {
		return elevatorUpButtonPress.get();
	}

	public boolean getElevatorDown() {
		return elevatorDownButtonPress.get();
	}


	/*
	 * Intake Buttons
	 */

	public boolean getClawOpen() {
		return driverController.getButton(TTrigger.LEFT); 
	}

	public boolean getIntakeCube() {
		return driverController.getButton(TButton.RIGHT_BUMPER); 
	}

	public boolean getOuttakeCube() {
		return operatorController.getButton(TButton.A)
				|| driverController.getButton(TTrigger.RIGHT);
	}

	public double getIntakeTiltSpeed() {
		return - operatorController.getAxis(TStick.RIGHT, TAxis.Y);
	}

	public boolean getTiltArmUp() {
		return false; // TODO get a button
	}

	public boolean getTiltArmDown() {
		return false; //TODO: get a button
	}

	public void updatePeriodic() {
		pneumaticsToggle.updatePeriodic();
		pidToggle.updatePeriodic();
		driverRumble.updatePeriodic();
	}


}
